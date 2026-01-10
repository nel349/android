# Midnight Wallet Android - Refined Implementation Plan

**Project Type:** Month 2+ | 80-120 hours | Weeks 5-16
**Status:** ⏳ Planning Complete - Ready to Start Phase 1

---

## Executive Summary

**Scope:** Full-featured Midnight wallet with ZK privacy and DApp support for Android.

**Technical Approach:** Pure Kotlin/JNI implementation - **NO WASM** (avoiding failed WAMR/React Native approaches).

**Critical Finding:** Midnight-libraries are TypeScript/WASM-based with **ZERO native mobile support**. We must port/reimplement core functionality in Kotlin.

**Timeline:** 80-120 hours across 8-12 weeks (extended from original 30-40 hour estimate).

**Why Extended:** Full wallet + DApp connector + porting crypto from TypeScript/Rust + building Substrate RPC client from scratch.

---

## Investigation Results

### Midnight Libraries Analysis
- **Tech Stack:** TypeScript/JavaScript with Rust core compiled to WASM (5-10MB binaries)
- **Mobile Support:** None - no Kotlin/Java bindings, no Android documentation
- **Complete SDK Available:**
  - HD wallets (BIP-32/BIP-44 derivation path: `m/44'/2400'/account'/role/index`)
  - Address derivation (SHA-256 hash → Bech32m format)
  - Transaction signing (Schnorr over secp256k1, BIP-340)
  - Shielded transactions (zswap)
  - Contract interaction

### Failed WASM Integration (MidnightWasmTest Project)
- **WAMR:** 80% complete on iOS, blocked on externref function calling
- **Polygen:** Can't handle externref (heavily used by Midnight WASM)
- **WebView:** Too complex (74KB JS glue injection, performance issues)
- **Critical Blocker:** externref types required for passing SecretKeys between JS and WASM

### Why Pure Kotlin Approach

✅ **Advantages:**
- Clean architecture (no WASM runtime complexity)
- Better performance (native code)
- Full control over implementation
- Mobile optimization (memory, battery)
- Smaller app size (vs 10MB+ WASM bundles)

❌ **Trade-offs:**
- Must reimplement/port core crypto
- Can't use official Midnight SDK directly
- More initial development time
- Must sync with protocol updates

---

## Address Derivation Algorithm (Lace Compatibility) ✅

**CRITICAL:** This exact algorithm ensures wallet compatibility with Lace (official Midnight wallet).

### Complete Derivation Chain:

```
1. Mnemonic (24 words)
   ↓
2. BIP-39 → Seed (512 bits)
   ↓
3. BIP-32 HD Derivation → Private Key
   Path: m/44'/2400'/account'/role/index
   - COIN_TYPE = 2400 (Midnight's coin type)
   - Roles: Night (0,1), Dust (2), Zswap (3), Metadata (4)
   ↓
4. Schnorr/secp256k1 (BIP-340) → Public Key (32 bytes)
   - NOT Ed25519 (common misconception)
   - x-only public key format
   ↓
5. SHA-256(publicKey) → Address (32 bytes)
   - Source: midnight-ledger/base-crypto/src/hash.rs::persistent_hash
   - Simple SHA-256 digest, no additional processing
   ↓
6. Bech32m Encoding → Human-readable Address
   - Prefix: "mn_addr_{network}" or "mn_addr" (mainnet)
   - Example: mn_addr_testnet1qxy...
```

### Implementation Sources:

| Step | Source File | Library/Method |
|------|-------------|----------------|
| BIP-39 | `midnight-wallet/packages/hd/src/MnemonicUtils.ts` | `@scure/bip39` |
| BIP-32 | `midnight-wallet/packages/hd/src/HDWallet.ts:123` | `@scure/bip32` HDKey.derive() |
| Schnorr | `midnight-ledger/base-crypto/src/signatures.rs:17` | `k256::schnorr` (BIP-340) |
| Hash | `midnight-ledger/base-crypto/src/hash.rs:92` | `Sha256::digest()` |
| Bech32m | `midnight-wallet/packages/address-format/src/index.ts:58` | `@scure/base` bech32m.encode() |

### Validation Strategy:

1. **Test Vectors:** Use BIP-32/BIP-340 published test vectors
2. **Cross-Check:** Generate same mnemonic in Lace and our wallet → addresses MUST match
3. **Unit Tests:** Test each step independently with known outputs

**Compatibility Guarantee:** If same mnemonic → same addresses between Lace and Android wallet.

---

## Configuration Decisions

Based on user requirements:

| Requirement | Decision |
|-------------|----------|
| **Proof Server** | User-hosted (Docker), provide endpoint URL in settings |
| **Networks** | Testnet + Preview support with configurable endpoints |
| **Crypto Libraries** | Zcash's `kotlin-bip39` + bitcoinj-core for Schnorr/secp256k1 (BIP-340) |
| **Timeline** | Whatever it takes to do it right (8-12 weeks realistic) |
| **Settings Panel** | Required for network/endpoint configuration with testnet/preview defaults |

---

## Phased Implementation

### Phase 1: Foundation ⏳ NOT STARTED
**Timeline:** Weeks 5-6 (20-25 hours)
**Goal:** Kotlin crypto primitives and key management

#### Tasks:
1. **Multi-Module Setup**
   - Create convention plugins (build-logic/)
   - Set up version catalog (libs.versions.toml)
   - Configure all modules (app, feature/*, core/*)
   - Set up Hilt modules

2. **BIP-39 Seed Phrase**
   - **Library:** `cash.z.ecc.android:kotlin-bip39` (Zcash)
   - Generate 24-word mnemonics
   - Checksum validation
   - Seed derivation

3. **BIP-32 HD Key Derivation**
   - **Library:** Included in kotlin-bip39
   - Derivation path: `m/44'/2400'/account'/role/index`
   - Roles: Night (0,1), Dust (2), Zswap (3), Metadata (4)

4. **Schnorr secp256k1 Key Pairs (BIP-340)**
   - **Library:** `bitcoinj-core` or custom implementation
   - **Algorithm:** Schnorr signatures over secp256k1 (NOT Ed25519)
   - Generate from HD seed
   - 32-byte public key (BIP-340 x-only format)

5. **Address Derivation**
   - **Algorithm:** `address = SHA-256(schnorrPublicKey32bytes)`
   - **Source:** `/midnight-ledger/base-crypto/src/hash.rs` (persistent_hash)
   - **Result:** 32-byte address (hex or bytes)

6. **Bech32m Address Formatting**
   - **Port from:** `@midnight-ntwrk/wallet-sdk-address-format`
   - **Prefix:** `mn_addr_{network}` or `mn_addr` (mainnet)
   - **Example:** `mn_addr_testnet1abc...`
   - Encode 32-byte address to Bech32m string
   - Checksum validation

7. **Secure Storage**
   - Android Keystore (hardware-backed)
   - EncryptedSharedPreferences (seed backup)
   - Room + SQLCipher (wallet metadata)

#### Deliverables:
- [ ] `:core:crypto` module complete
- [ ] `:core:domain` entities defined
- [ ] Unit tests (100% crypto coverage with test vectors)
- [ ] Can create/restore wallet, derive addresses

#### Files to Create:
```
core/crypto/
  ├── BIP39SeedGenerator.kt
  ├── HDKeyDerivation.kt
  ├── SchnorrKeyPair.kt (BIP-340 secp256k1)
  ├── AddressDerivation.kt (SHA-256 hash)
  ├── Bech32mFormatter.kt (mn_addr encoding)
  └── SecureKeyStore.kt

core/domain/
  ├── entities/Wallet.kt
  ├── entities/Address.kt
  └── repository/WalletRepository.kt
```

**Blockers:** None

---

### Phase 2: Unshielded Transactions ⏳ NOT STARTED
**Timeline:** Week 7 (15-20 hours)
**Goal:** Send/receive unshielded tokens (no privacy yet)

#### Tasks:
1. **Substrate RPC Client**
   - **Challenge:** Port from Polkadot.js (TypeScript)
   - **Tech:** OkHttp + WebSocket
   - **Methods:** `chain_getBlockHash()`, `state_getStorage()`, `author_submitExtrinsic()`

2. **SCALE Codec**
   - **Port from:** `parity-scale-codec` (Rust)
   - Encode/decode Substrate transactions
   - Critical for transaction serialization

3. **Transaction Building**
   - **Port from:** `@midnight-ntwrk/ledger` (Rust WASM)
   - Build unsigned transaction
   - Sign with Schnorr (BIP-340)
   - Broadcast to network

4. **Balance Queries**
   - RPC calls to query blockchain state
   - Parse unshielded balance

#### Deliverables:
- [ ] `:core:network` module (Substrate RPC)
- [ ] `:core:ledger` module (transaction logic)
- [ ] Can send/receive unshielded tokens
- [ ] Integration tests with testnet

#### Files to Create:
```
core/network/
  ├── SubstrateRpcClient.kt
  ├── PolkadotApi.kt
  └── dto/BlockchainResponses.kt

core/ledger/
  ├── UnshieldedTransaction.kt
  ├── TransactionBuilder.kt
  ├── TransactionSigner.kt
  └── ScaleCodec.kt
```

**Blockers:** Need testnet endpoint from user

---

### Phase 3: Shielded Transactions ⏳ NOT STARTED
**Timeline:** Weeks 8-9 (20-25 hours)
**Goal:** Private transactions with ZK proofs

#### Tasks:
1. **Proof Server Client**
   - HTTP REST API to user's proof server
   - Send transaction inputs
   - Receive ZK proofs

2. **Shielded Transaction Logic**
   - **Port from:** `@midnight-ntwrk/zswap` (client-side only)
   - Note construction
   - Nullifier derivation
   - Proof integration

3. **Transaction Flow:**
   ```
   1. App: Create shielded tx inputs
   2. App → Proof Server: HTTP POST with inputs
   3. Proof Server: Generate ZK proof (compute-intensive)
   4. Proof Server → App: Return proof
   5. App: Combine proof + tx, sign, broadcast
   ```

#### Deliverables:
- [ ] `:core:prover-client` module
- [ ] Shielded transaction support
- [ ] Can send private transactions

#### Files to Create:
```
core/prover-client/
  ├── ProofServerClient.kt
  ├── dto/ProofRequest.kt
  └── dto/ProofResponse.kt

core/ledger/
  ├── ShieldedTransaction.kt
  ├── NoteConstructor.kt
  └── NullifierDerivation.kt
```

**Blockers:** Need proof server URL from user

---

### Phase 4: Indexer Integration ⏳ NOT STARTED
**Timeline:** Weeks 9-10 (15-20 hours)
**Goal:** Fast state sync (vs slow RPC polling)

#### Tasks:
1. **Indexer HTTP Client**
   - Query transaction history
   - Query balances (shielded + unshielded)
   - WebSocket for real-time updates

2. **Local Caching**
   - **Port from:** `@midnight-ntwrk/indexer-public-data-provider`
   - Room database for state
   - Sync on app start + periodic refresh

#### Deliverables:
- [ ] `:core:indexer` module
- [ ] Faster balance updates
- [ ] Transaction history

#### Files to Create:
```
core/indexer/
  ├── IndexerClient.kt
  ├── StateProvider.kt
  └── dto/IndexerResponses.kt

core/database/
  ├── entities/TransactionEntity.kt
  └── dao/TransactionDao.kt
```

**Blockers:** Need indexer endpoint from user

---

### Phase 5: DApp Connector ⏳ NOT STARTED
**Timeline:** Weeks 10-11 (15-20 hours)
**Goal:** Allow mobile dapps to request signatures

#### Tasks:
1. **Deep Link Protocol**
   - Register `midnight://` URL scheme
   - Parse DApp requests
   - Show permission dialog
   - Return signatures to DApp

2. **Contract Signing**
   - Parse contract call requests
   - Build contract transactions
   - Sign with user approval

3. **Alternative: WebView Bridge**
   - If DApp runs in wallet's WebView
   - JavaScript bridge: `window.midnight.request()`

#### Deliverables:
- [ ] `:feature:dapp-connector` module
- [ ] Deep link handling
- [ ] Contract signing API
- [ ] Permission management

#### Files to Create:
```
feature/dapp-connector/
  ├── DAppConnectorActivity.kt
  ├── DAppRequest.kt
  ├── ApprovalDialog.kt
  └── ContractSigner.kt

core/contracts/
  ├── ContractCallBuilder.kt
  └── ContractABI.kt
```

**Blockers:** None

---

### Phase 6: UI & Polish ⏳ NOT STARTED
**Timeline:** Weeks 11-12 (10-15 hours)
**Goal:** Complete Compose UI

#### Screens:
1. **Onboarding**
   - Create wallet → seed phrase → verify backup
   - Restore from seed
   - PIN/biometric setup

2. **Wallet Home**
   - Balance (shielded + unshielded)
   - Transaction history (LazyColumn)
   - Send/Receive buttons

3. **Send Transaction**
   - Recipient address input
   - Amount input
   - Shielded/unshielded toggle
   - Fee estimation
   - Confirmation screen

4. **Receive**
   - Address display (text + QR)
   - Copy/share

5. **Settings** ⭐ IMPORTANT
   - **Network Configuration Panel:**
     - Testnet (default endpoints)
     - Preview (default endpoints)
     - Custom (user-provided endpoints)
   - **Endpoints to configure:**
     - Node RPC (WebSocket)
     - Indexer API (HTTP)
     - Proof Server (HTTP)
   - Backup seed phrase (re-auth required)
   - Security settings
   - About/version

#### Deliverables:
- [ ] All feature modules with Compose UI
- [ ] Navigation (Compose Navigation)
- [ ] Material3 design system
- [ ] Settings panel for endpoint configuration

**Blockers:** None

---

## Multi-Module Architecture

```
midnight-wallet/
├── app/                           # Navigation, DI setup
├── feature/
│   ├── onboarding/                # Create/restore wallet
│   ├── wallet/                    # Home (balance, history)
│   ├── send/                      # Send transaction
│   ├── receive/                   # Receive + QR code
│   ├── settings/                  # Settings + network config ⭐
│   └── dapp-connector/            # DApp integration
├── core/
│   ├── crypto/                    # BIP-39, HD keys, Schnorr/secp256k1 (PORT)
│   ├── ledger/                    # Transactions (PORT)
│   ├── network/                   # Substrate RPC (NEW)
│   ├── indexer/                   # Indexer client (NEW)
│   ├── prover-client/             # Proof server API (NEW)
│   ├── database/                  # Room + SQLCipher
│   ├── datastore/                 # EncryptedSharedPreferences
│   ├── domain/                    # Use cases, repos
│   ├── ui/                        # Design system
│   ├── common/                    # Extensions, utils
│   └── testing/                   # Test utilities
└── build-logic/                   # Convention plugins
```

---

## Porting Strategy

### From Midnight Libraries (What to Port)

| Source | Destination | Strategy |
|--------|-------------|----------|
| `@scure/bip39` (TS) | `:core:crypto` | Use Zcash `kotlin-bip39` |
| `@scure/bip32` (TS) | `:core:crypto` | Included in `kotlin-bip39` |
| Schnorr/secp256k1 (BIP-340) | `:core:crypto` | Use `bitcoinj-core` or custom impl |
| SHA-256 (address hash) | `AddressDerivation.kt` | Built-in Java SHA-256 |
| `@midnight-ntwrk/wallet-sdk-address-format` (TS) | `Bech32mFormatter.kt` | Port Bech32m logic |
| `@midnight-ntwrk/ledger` (Rust) | `:core:ledger` | Reverse-engineer + SCALE codec |
| `@midnight-ntwrk/zswap` (Rust) | `:core:ledger` | Port client-side only |
| Polkadot.js (TS) | `:core:network` | Port essential RPC methods |

### What NOT to Port (Remote Services)

1. **ZK Proof Generation** - User's proof server (too compute-intensive)
2. **Ledger Runtime** - Stays on-chain
3. **Node Infrastructure** - Use existing Midnight nodes

---

## External Dependencies

### User Must Provide:

| Service | Type | Purpose | Example |
|---------|------|---------|---------|
| **Node RPC** | WebSocket | Blockchain queries | `wss://rpc.testnet.midnight.network` |
| **Indexer** | HTTP | Fast state sync | `https://indexer.testnet.midnight.network` |
| **Proof Server** | HTTP | ZK proof generation | `https://prover.mydomain.com` |

### Network Defaults (in Settings):

**Testnet:**
- Node: `wss://rpc.testnet.midnight.network`
- Indexer: `https://indexer.testnet.midnight.network`
- Proof Server: (user provides)

**Preview:**
- Node: `wss://rpc.preview.midnight.network`
- Indexer: `https://indexer.preview.midnight.network`
- Proof Server: (user provides)

**Custom:**
- All endpoints user-configurable

---

## Risk Assessment

### 🔴 High Risk
1. **Transaction Format Mismatch** - Kotlin doesn't match Midnight's expectations
   - *Mitigation:* Extensive testnet validation, compare with TypeScript SDK
2. **SCALE Codec Bugs** - Incorrect serialization
   - *Mitigation:* Test vectors, validate against Rust
3. **Proof Server API** - Changes, auth issues
   - *Mitigation:* Mock server first, document API contract

### 🟡 Medium Risk
1. **Polkadot.js RPC** - Missing/incorrect methods
   - *Mitigation:* Start minimal, expand as needed
2. **Indexer Changes** - API updates break client
   - *Mitigation:* Version calls, graceful errors

### 🟢 Low Risk
1. **Crypto** - Battle-tested libraries (bitcoinj-core for Schnorr, Zcash for BIP-39/32)
2. **Compose UI** - Already experienced from Weather App
3. **Address Compatibility** - Simple SHA-256 hash, validated against Lace wallet

---

## Testing Strategy

### Phase 1: Crypto (Unit Tests)
- BIP-39: Standard word lists
- BIP-32: Published test vectors (from BIP-32 spec)
- Schnorr/secp256k1: BIP-340 test vectors
- Address derivation: Compare with Lace wallet outputs
- **Goal:** 100% crypto confidence, Lace compatibility

### Phase 2-3: Integration
- Mock blockchain (WireMock)
- Mock proof server
- **Goal:** Fast feedback without network

### Phase 4-6: End-to-End
- Midnight testnet
- Real transactions
- Real proof generation
- **Goal:** Production readiness

---

## Success Criteria

### Phase 1 (Foundation):
- [ ] Create wallet from seed
- [ ] Restore from seed
- [ ] Derive addresses (all roles)
- [ ] Secure key storage

### Phase 2 (Unshielded):
- [ ] Send unshielded tx
- [ ] Receive unshielded tx
- [ ] View balance

### Phase 3 (Shielded):
- [ ] Send shielded tx (via proof server)
- [ ] View shielded balance

### Phase 4 (Indexer):
- [ ] Fast state sync
- [ ] Transaction history

### Phase 5 (DApp):
- [ ] DApp request signature
- [ ] User approve/reject
- [ ] Sign contract calls

### Phase 6 (Polish):
- [ ] Complete Compose UI
- [ ] Network config panel
- [ ] 80%+ test coverage
- [ ] Published on GitHub
- [ ] Demo video

---

## Timeline

### Realistic Estimate: 80-120 hours

**Breakdown:**
- Phase 1: 20-25 hours
- Phase 2: 15-20 hours
- Phase 3: 20-25 hours
- Phase 4: 15-20 hours
- Phase 5: 15-20 hours
- Phase 6: 10-15 hours
- Buffer: +20 hours

**Schedule Options:**
- **Fast:** 8 weeks @ 10-15 hrs/week
- **Balanced:** 12 weeks @ 7-10 hrs/week

**Recommendation:** 12-week timeline for learning curve and debugging buffer.

---

## What We Need from User

### Now (Before Phase 2):
- [ ] Testnet endpoints (node, indexer, proof server)
- [ ] Preview endpoints (node, indexer, proof server)
- [ ] Test tokens (how to acquire)
- [ ] Proof server authentication details (if required)

### Later (Phase 3+):
- [ ] Example transactions to study
- [ ] Any Midnight-specific documentation
- [ ] Contact for technical questions (if available)

---

## Interview Story

> "I built a zero-knowledge cryptocurrency wallet for Midnight Network on Android. The main challenge was that Midnight's SDK is TypeScript/WASM-based with no mobile support - I had to port the entire crypto stack to Kotlin.
>
> I started by analyzing their failed WASM integration attempt which got stuck on externref handling. Instead, I took a pure Kotlin approach, using Zcash's kotlin-bip39 for HD wallet derivation and implementing Schnorr signatures over secp256k1 (BIP-340) for transaction signing. Address derivation was straightforward - just SHA-256 hash of the public key, then Bech32m encoding - but I validated it against Lace wallet to ensure compatibility.
>
> The trickiest part was transaction serialization - I had to reverse-engineer Midnight's Rust ledger (compiled to WASM) and reimplement their SCALE codec in Kotlin. I validated against test vectors and compared behavior with the TypeScript SDK to ensure compatibility.
>
> For zero-knowledge proofs, mobile devices can't generate them (too compute-intensive), so I architected it with a remote proof server. The app sends transaction inputs via HTTP, the server generates the proof, then the app combines it and broadcasts to the blockchain.
>
> The architecture uses Clean Architecture with 11 modules: 6 feature modules (onboarding, wallet, send, receive, settings, dapp-connector) and 5 core modules (crypto, ledger, network, indexer, prover-client). This separation let me test crypto in isolation with known test vectors before integrating with the blockchain.
>
> I achieved 80%+ test coverage: unit tests for all crypto operations, integration tests with mock blockchain, and end-to-end tests on Midnight's testnet. The wallet supports shielded and unshielded transactions, HD key derivation with Midnight-specific roles, and a DApp connector for mobile dapp integration."

**Follow-ups:**
- "How did you handle the SCALE codec?" → Studied Rust implementation, ported encode/decode logic with unit tests
- "Why not use WASM?" → Externref limitations, bundle size, performance - native Kotlin is cleaner
- "Security considerations?" → Android Keystore (hardware-backed), EncryptedSharedPreferences, SQLCipher, ProGuard obfuscation

---

## Progress Tracking

Track phase completion in this file:
- ⏳ NOT STARTED
- 🏗️ IN PROGRESS
- ✅ COMPLETE
- ❌ BLOCKED

**Current Phase:** Phase 1 (Foundation) - ⏳ NOT STARTED
**Next Milestone:** Multi-module setup + BIP-39 implementation
**Blockers:** None - ready to start

---

**Last Updated:** 2026-01-09 (Address derivation algorithm verified)
**Project Start Date:** TBD (after Week 4 completion)
**Expected Completion:** 8-12 weeks from start
