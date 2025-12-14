# Midnight Wallet Android

**Month 2 Project** | 30-40 hours | Weeks 5-8

Build a zero-knowledge privacy-focused cryptocurrency wallet for Android. Demonstrate multi-module Clean Architecture, advanced security, and blockchain expertise.

---

## Project Overview

**What:** ZK (zero-knowledge) crypto wallet for Midnight blockchain on Android. Features secure key management, balance viewing, send/receive transactions, and seed phrase backup/restore.

**Why this project:**
- **Portfolio differentiator:** Shows blockchain expertise (from your background)
- **Complex architecture:** Multi-module, Clean Arch, security-first design
- **Real-world problem:** Privacy-preserving crypto transactions
- **Interview story:** "I built a production-grade crypto wallet with zero-knowledge proofs"
- **Unique:** Not just another CRUD app - cryptography, security, real money

**Midnight Wallet Libraries Reference:**
- Located at: `/Users/norman/Development/midnight/midnight-libraries/midnight-wallet`
- Study these for crypto operations (key generation, signing, ZK proofs)

**Tech Stack:**
- Kotlin (100%)
- Multi-module Clean Architecture
- Jetpack Compose (100% Compose UI)
- Hilt (dependency injection)
- Room (encrypted database)
- Coroutines & Flow
- EncryptedSharedPreferences (secure key storage)
- Keystore (hardware-backed keys)
- Security: AES-256, ed25519 signatures, BIP39 seed phrases
- Testing: JUnit, MockK, Turbine, Compose UI tests
- GitHub Actions (CI/CD)

---

## Features (MVP)

### 1. Secure Wallet Creation
- Generate ed25519 key pair (private/public keys)
- Derive wallet address from public key
- Generate BIP39 24-word seed phrase
- Encrypt private key with user PIN
- Store in Android Keystore (hardware-backed)

### 2. Wallet Restore
- Import from 24-word seed phrase
- Validate BIP39 checksum
- Re-derive key pair from seed
- Encrypt and store securely

### 3. Balance Viewing
- Display wallet balance (MIDNIGHT tokens)
- Fetch from blockchain API (REST or RPC)
- Offline-first: cache balance in Room
- Pull-to-refresh

### 4. Send Transaction
- Input: Recipient address, amount, optional memo
- Validate address format
- Sign transaction with private key (ed25519)
- Broadcast to Midnight network
- Show pending → confirmed status
- Transaction history

### 5. Receive
- Display wallet address (text + QR code)
- Copy to clipboard
- Share via system share sheet

### 6. Backup & Security
- PIN/biometric authentication
- Backup seed phrase (show once, user must write down)
- Verify backup (user enters random words)
- Encrypted local database (SQLCipher)

---

## Multi-Module Architecture

```
midnight-wallet/
├── app/                           # Main app module (navigation, DI setup)
├── feature/
│   ├── wallet/                    # Wallet home screen (balance, transactions)
│   ├── send/                      # Send transaction flow
│   ├── receive/                   # Receive (QR code, address)
│   ├── settings/                  # Settings, backup, security
│   └── onboarding/                # Create/restore wallet flow
├── core/
│   ├── crypto/                    # Key generation, signing, encryption
│   ├── network/                   # Blockchain API client (Retrofit)
│   ├── database/                  # Room (entities, DAOs, encrypted DB)
│   ├── datastore/                 # DataStore/EncryptedSharedPreferences
│   ├── ui/                        # Design system, shared Compose components
│   ├── domain/                    # Use cases, repositories (interfaces)
│   ├── common/                    # Extensions, utils, constants
│   └── testing/                   # Test utilities, fakes
├── build-logic/                   # Convention plugins
└── gradle/libs.versions.toml      # Version catalog
```

### Module Dependencies

```
:app
  ├─ :feature:wallet
  ├─ :feature:send
  ├─ :feature:receive
  ├─ :feature:settings
  └─ :feature:onboarding

:feature:* (any feature module)
  ├─ :core:domain
  ├─ :core:ui
  └─ :core:common

:core:domain
  └─ :core:common

:core:network
  ├─ :core:domain
  └─ :core:common

:core:database
  ├─ :core:domain
  └─ :core:common

:core:crypto
  └─ :core:common
```

**Dependency rule:** Features can depend on core, not other features. Core modules don't depend on features.

---

## Clean Architecture Layers

### Domain Layer (:core:domain)

**Entities:**
```kotlin
data class Wallet(
    val address: String,
    val publicKey: String,
    val createdAt: Long
)

data class Transaction(
    val hash: String,
    val from: String,
    val to: String,
    val amount: BigDecimal,
    val status: TransactionStatus,
    val timestamp: Long
)

enum class TransactionStatus { PENDING, CONFIRMED, FAILED }
```

**Repositories (interfaces):**
```kotlin
interface WalletRepository {
    suspend fun createWallet(pin: String): Result<Wallet>
    suspend fun restoreWallet(seedPhrase: List<String>, pin: String): Result<Wallet>
    suspend fun getWallet(): Wallet?
    suspend fun getBalance(): Flow<BigDecimal>
}

interface TransactionRepository {
    suspend fun sendTransaction(to: String, amount: BigDecimal, pin: String): Result<Transaction>
    suspend fun getTransactions(): Flow<List<Transaction>>
}
```

**Use Cases:**
```kotlin
class CreateWalletUseCase(private val repo: WalletRepository) {
    suspend operator fun invoke(pin: String): Result<Wallet> {
        return repo.createWallet(pin)
    }
}

class SendTransactionUseCase(
    private val repo: TransactionRepository,
    private val walletRepo: WalletRepository
) {
    suspend operator fun invoke(to: String, amount: BigDecimal, pin: String): Result<Transaction> {
        // Validate balance
        val balance = walletRepo.getBalance().first()
        if (balance < amount) return Result.failure(InsufficientFundsException())

        // Send transaction
        return repo.sendTransaction(to, amount, pin)
    }
}
```

---

### Data Layer

#### :core:crypto (Cryptography)

**KeyGenerator:**
```kotlin
class KeyGenerator {
    fun generateKeyPair(): KeyPair {
        // ed25519 key pair generation
        // Use Tink or BouncyCastle
    }

    fun deriveAddress(publicKey: ByteArray): String {
        // SHA-256 hash + encoding
    }

    fun generateSeedPhrase(): List<String> {
        // BIP39 mnemonic (24 words)
    }

    fun restoreKeyPairFromSeed(seedPhrase: List<String>): KeyPair {
        // BIP39 → seed → derive keys
    }
}
```

**TransactionSigner:**
```kotlin
class TransactionSigner(private val keyStore: SecureKeyStore) {
    suspend fun signTransaction(tx: Transaction, pin: String): ByteArray {
        val privateKey = keyStore.getPrivateKey(pin)
        // ed25519 signature
    }
}
```

**SecureKeyStore:**
```kotlin
class SecureKeyStore(private val context: Context) {
    fun storePrivateKey(privateKey: ByteArray, pin: String) {
        // Encrypt with AES-256 (key derived from PIN + salt)
        // Store in Android Keystore
    }

    fun getPrivateKey(pin: String): ByteArray {
        // Decrypt from Keystore
    }
}
```

---

#### :core:network (Blockchain API)

**MidnightApi (Retrofit):**
```kotlin
interface MidnightApi {
    @GET("balance/{address}")
    suspend fun getBalance(@Path("address") address: String): BalanceResponse

    @GET("transactions/{address}")
    suspend fun getTransactions(@Path("address") address: String): List<TransactionDto>

    @POST("broadcast")
    suspend fun broadcastTransaction(@Body tx: SignedTransactionDto): BroadcastResponse
}
```

**NetworkDataSource:**
```kotlin
class MidnightNetworkDataSource(private val api: MidnightApi) {
    suspend fun getBalance(address: String): Result<BigDecimal> {
        return try {
            val response = api.getBalance(address)
            Result.success(response.balance.toBigDecimal())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
```

---

#### :core:database (Room + Encryption)

**Entities:**
```kotlin
@Entity(tableName = "wallet")
data class WalletEntity(
    @PrimaryKey val address: String,
    val publicKey: String,
    val encryptedPrivateKey: String,
    val createdAt: Long
)

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey val hash: String,
    val from: String,
    val to: String,
    val amount: String,
    val status: String,
    val timestamp: Long
)
```

**DAOs:**
```kotlin
@Dao
interface WalletDao {
    @Query("SELECT * FROM wallet LIMIT 1")
    suspend fun getWallet(): WalletEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWallet(wallet: WalletEntity)
}

@Dao
interface TransactionDao {
    @Query("SELECT * FROM transactions ORDER BY timestamp DESC")
    fun getTransactions(): Flow<List<TransactionEntity>>

    @Insert
    suspend fun insertTransaction(tx: TransactionEntity)
}
```

**Database:**
```kotlin
@Database(entities = [WalletEntity::class, TransactionEntity::class], version = 1)
abstract class MidnightDatabase : RoomDatabase() {
    abstract fun walletDao(): WalletDao
    abstract fun transactionDao(): TransactionDao

    companion object {
        fun create(context: Context): MidnightDatabase {
            return Room.databaseBuilder(
                context,
                MidnightDatabase::class.java,
                "midnight.db"
            )
            .openHelperFactory(SupportFactory(SQLiteDatabase.getBytes("your_passphrase".toCharArray())))
            .build()
        }
    }
}
```

---

### Presentation Layer (:feature modules)

#### :feature:wallet (Home Screen)

**ViewModel (MVI pattern):**
```kotlin
data class WalletUiState(
    val wallet: Wallet? = null,
    val balance: BigDecimal = BigDecimal.ZERO,
    val transactions: List<Transaction> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed interface WalletIntent {
    object LoadWallet : WalletIntent
    object Refresh : WalletIntent
}

class WalletViewModel(
    private val getWalletUseCase: GetWalletUseCase,
    private val getBalanceUseCase: GetBalanceUseCase,
    private val getTransactionsUseCase: GetTransactionsUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(WalletUiState())
    val state: StateFlow<WalletUiState> = _state.asStateFlow()

    fun onIntent(intent: WalletIntent) {
        when (intent) {
            is WalletIntent.LoadWallet -> loadWallet()
            is WalletIntent.Refresh -> refresh()
        }
    }

    private fun loadWallet() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            val wallet = getWalletUseCase()
            combine(
                flowOf(wallet),
                getBalanceUseCase(wallet.address),
                getTransactionsUseCase(wallet.address)
            ) { w, bal, txs ->
                WalletUiState(wallet = w, balance = bal, transactions = txs, isLoading = false)
            }.collect { newState ->
                _state.update { newState }
            }
        }
    }
}
```

**Compose UI:**
```kotlin
@Composable
fun WalletScreen(
    viewModel: WalletViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onIntent(WalletIntent.LoadWallet)
    }

    Scaffold(
        topBar = { WalletTopBar() }
    ) { padding ->
        when {
            state.isLoading -> LoadingView()
            state.error != null -> ErrorView(state.error!!)
            else -> WalletContent(
                balance = state.balance,
                transactions = state.transactions,
                onRefresh = { viewModel.onIntent(WalletIntent.Refresh) },
                onSendClick = { /* navigate to send screen */ },
                onReceiveClick = { /* navigate to receive screen */ }
            )
        }
    }
}
```

---

## Implementation Timeline

### Week 5: Architecture + Setup (8-10 hours)

**Tasks:**
- Set up multi-module project structure
- Create version catalog (libs.versions.toml)
- Set up convention plugins (build-logic/)
- Define domain entities, repository interfaces
- Set up Hilt modules
- Study Midnight blockchain docs and crypto libraries

**Deliverable:** Empty project structure, all modules created, domain layer defined

---

### Week 6: Crypto + Data Layer (10-12 hours)

**Tasks:**
- Implement KeyGenerator (ed25519, BIP39)
- Implement SecureKeyStore (Android Keystore, AES-256)
- Implement TransactionSigner
- Set up Room database (encrypted)
- Implement WalletRepository
- Write unit tests for crypto layer (80%+ coverage)

**Deliverable:** Core crypto and database working, unit tested

---

### Week 7: Network + UI (10-12 hours)

**Tasks:**
- Implement MidnightApi (Retrofit)
- Implement TransactionRepository
- Build Compose UI:
  - Onboarding (create/restore wallet)
  - Wallet home (balance, transactions)
  - Send transaction
  - Receive (QR code)
- Implement ViewModels (MVI pattern)

**Deliverable:** Full app flow working (create wallet → view balance → send/receive)

---

### Week 8: Testing + Polish + CI/CD (8-10 hours)

**Tasks:**
- Unit tests (ViewModels, UseCases, Repositories)
- Integration tests (Repository + DAO + API)
- Compose UI tests (user flows)
- Security audit (key storage, encryption, ProGuard)
- GitHub Actions CI/CD (build, test, lint)
- Architecture documentation (diagrams)
- README (setup, architecture, screenshots)

**Deliverable:** Production-ready app, published on GitHub

---

## Security Considerations

### 1. Private Key Storage
**Problem:** Private keys must never be stored in plaintext
**Solution:**
- Store encrypted in Android Keystore (hardware-backed on modern devices)
- Encrypt with AES-256, key derived from user PIN + salt (PBKDF2)
- Use `EncryptedSharedPreferences` for seed phrase backup (optional)

### 2. PIN/Biometric Authentication
**Problem:** Prevent unauthorized access to wallet
**Solution:**
- BiometricPrompt for biometric auth (fingerprint, face)
- PIN fallback if biometric not available
- Lock wallet after 3 failed attempts (time delay)

### 3. Transaction Signing
**Problem:** Transactions must be signed offline, never send private key
**Solution:**
- Sign locally with ed25519
- Only broadcast signed transaction to network
- Verify signature before broadcast

### 4. Code Obfuscation
**Problem:** Reverse engineering could expose vulnerabilities
**Solution:**
- Enable R8 obfuscation (ProGuard rules)
- Remove logs in release builds
- Don't hardcode API keys (use BuildConfig)

### 5. Root Detection
**Problem:** Rooted devices can access Keystore
**Solution:**
- Detect root (RootBeer library)
- Warn user (don't block, user's choice)

---

## Trade-offs & Design Decisions

### Multi-Module vs Single Module
**Chosen:** Multi-module
**Why:** Scales better, faster builds (parallel), clear boundaries
**Trade-off:** More setup complexity, navigation between modules

### Clean Architecture vs MVVM Only
**Chosen:** Clean Architecture (domain + data + presentation layers)
**Why:** Testable, decoupled, easier to swap implementations (e.g., different blockchain API)
**Trade-off:** More boilerplate (UseCases, Repository interfaces)

### Room vs Realm vs Firebase
**Chosen:** Room (encrypted with SQLCipher)
**Why:** Official, Kotlin-friendly, encryption support
**Trade-off:** More setup than Firebase, but better security control

### MVI vs MVVM
**Chosen:** MVI (Model-View-Intent)
**Why:** Unidirectional data flow, single immutable state, easier debugging
**Trade-off:** More boilerplate than simple MVVM

### On-Device Signing vs Server-Side
**Chosen:** On-device signing (private key never leaves device)
**Why:** True self-custody, no trust in server
**Trade-off:** More complex, user responsible for backups

---

## Testing Strategy

### Unit Tests (80%+ coverage)

**:core:crypto:**
- Test key generation (ed25519 valid keys)
- Test BIP39 seed phrase (valid mnemonics, checksum)
- Test encryption/decryption (round-trip)
- Test transaction signing (valid ed25519 signatures)

**:core:domain:**
- Test UseCases (mock repositories)
- Test business logic (e.g., insufficient funds validation)

**:core:data:**
- Test Repository implementations (mock DAO + API)
- Test mappers (DTO ↔ Entity ↔ Domain model)

### Integration Tests

- Repository + Room + API (use Hilt test module)
- End-to-end flow: Create wallet → send tx → verify in DB

### Compose UI Tests

- Test user flows:
  - Create wallet → backup seed phrase
  - Send transaction → confirm success
  - Restore wallet from seed phrase

---

## Resources

**Midnight Blockchain:**
- Midnight Wallet Libraries: `/Users/norman/Development/midnight/midnight-libraries/midnight-wallet`
- Study existing wallet implementation for crypto operations

**Cryptography:**
- [Tink](https://github.com/google/tink) - Google's crypto library for Android
- [BouncyCastle](https://www.bouncycastle.org/) - Java cryptography library
- [BIP39](https://github.com/bitcoin/bips/blob/master/bip-0039.mediawiki) - Mnemonic seed phrases

**Android Security:**
- [Android Keystore](https://developer.android.com/training/articles/keystore)
- [EncryptedSharedPreferences](https://developer.android.com/topic/security/data)
- [BiometricPrompt](https://developer.android.com/training/sign-in/biometric-auth)

**Multi-Module Architecture:**
- [Nowinandroid](https://github.com/android/nowinandroid) - Google's reference multi-module app
- [Build logic](https://developer.android.com/build/migrate-to-catalogs) - Convention plugins, version catalogs

**Similar Projects:**
- [Trust Wallet](https://github.com/trustwallet/wallet-core) - Multi-coin wallet (for reference)
- [Trezor Suite](https://github.com/trezor/trezor-suite) - Hardware wallet (architecture reference)

---

## Interview Story Template

**"Tell me about a complex project you built"**

> "I built a zero-knowledge cryptocurrency wallet for Android called Midnight Wallet. The main challenge was balancing security with usability while handling sensitive operations like key generation and transaction signing.
>
> I designed it using multi-module Clean Architecture with six core modules and five feature modules. This separation allowed me to isolate cryptographic operations in :core:crypto, making it easier to test and audit for security vulnerabilities.
>
> The trickiest part was secure key storage. I used Android Keystore for hardware-backed encryption, storing the private key encrypted with AES-256 using a key derived from the user's PIN via PBKDF2. I also implemented BIP39 seed phrase generation for wallet recovery.
>
> For the architecture, I chose MVI over traditional MVVM because wallet transactions require predictable, unidirectional state management - you can't have race conditions when dealing with real money. Every state transition is explicit and testable.
>
> The app achieved 85% test coverage with unit tests for crypto operations, integration tests for the repository layer, and Compose UI tests for critical user flows. I also set up GitHub Actions for CI/CD with automated security checks using ProGuard.
>
> This project deepened my understanding of Android security, cryptography, and blockchain. It also demonstrates my ability to architect large-scale apps with multiple modules while maintaining security best practices."

**Follow-up questions:**
- "How did you handle transaction failures?" → Retry logic with exponential backoff, persist pending txs in Room
- "What if user forgets PIN?" → Seed phrase recovery (must back up during setup)
- "How did you test cryptography?" → Unit tests with known test vectors, mock crypto in integration tests
- "Security trade-offs?" → On-device signing (no server trust) vs user backup responsibility

---

## Architecture Diagram

```
┌─────────────────────────────────────────────────────────────┐
│                         :app (Navigation)                    │
└───────────────────┬─────────────────────────────────────────┘
                    │
        ┌───────────┼───────────┬───────────┬──────────────┐
        │           │           │           │              │
        ▼           ▼           ▼           ▼              ▼
┌──────────┐ ┌──────────┐ ┌──────────┐ ┌──────────┐ ┌──────────┐
│ :feature │ │ :feature │ │ :feature │ │ :feature │ │ :feature │
│  :wallet │ │   :send  │ │ :receive │ │:settings │ │:onboard  │
└─────┬────┘ └─────┬────┘ └─────┬────┘ └─────┬────┘ └─────┬────┘
      │            │            │            │            │
      └────────────┴────────────┴────────────┴────────────┘
                              │
              ┌───────────────┼───────────────┬──────────┐
              ▼               ▼               ▼          ▼
      ┌──────────────┐ ┌──────────┐  ┌────────────┐ ┌────────┐
      │ :core:domain │ │ :core:ui │  │:core:common│ │:core:  │
      │ (UseCases,   │ │ (Design  │  │(Extensions)│ │testing │
      │ Repos)       │ │  System) │  │            │ │        │
      └──────┬───────┘ └──────────┘  └────────────┘ └────────┘
             │
   ┌─────────┼─────────┬─────────────┬────────────┐
   ▼         ▼         ▼             ▼            ▼
┌────────┐ ┌────────┐ ┌──────────┐ ┌───────────┐ ┌──────────┐
│ :core: │ │ :core: │ │  :core:  │ │  :core:   │ │  :core:  │
│ :crypto│ │:network│ │:database │ │:datastore │ │  :model  │
│        │ │(Retro) │ │  (Room)  │ │ (Prefs)   │ │          │
└────────┘ └────────┘ └──────────┘ └───────────┘ └──────────┘
```

---

**This project showcases your blockchain expertise and advanced Android skills. It's production-grade, secure, and tells a compelling story in interviews.** 🔒🚀
