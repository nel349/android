# SCALE Encoding Explained: The Blockchain's Binary Language

**Author:** Claude (for Norman)
**Date:** January 26, 2026
**Context:** Understanding transaction serialization in Kuira wallet

---

## 🎯 What is SCALE?

**SCALE** = **S**imple **C**oncatenated **A**ggregate **L**ittle-**E**ndian

It's a **binary encoding format** created by Parity Technologies for Substrate-based blockchains (including Polkadot, Kusama, and **Midnight**).

---

## 🤔 Why Do Blockchains Need Special Encoding?

### The Problem: Sending Data Over the Network

When you create a transaction, you have structured data:

```kotlin
// Your transaction in memory (Kotlin objects)
Intent(
    guaranteedUnshieldedOffer = UnshieldedOffer(
        inputs = listOf(
            UtxoSpend(value = BigInteger("100000000000"), ...)
        ),
        outputs = listOf(
            UtxoOutput(value = BigInteger("50000000000"), ...)
        ),
        signatures = listOf(ByteArray(64))
    ),
    ttl = 1704067200000
)
```

**Question:** How do you send this to the blockchain node?

### Option 1: JSON ❌ Too Verbose

```json
{
  "guaranteedUnshieldedOffer": {
    "inputs": [
      {
        "value": "100000000000",
        "owner": "abc123...",
        "tokenType": "000000..."
      }
    ],
    "outputs": [...],
    "signatures": ["deadbeef..."]
  },
  "ttl": 1704067200000
}
```

**Problems:**
- **Size:** 2-5x larger than necessary (field names, quotes, brackets)
- **Parsing:** Slower (text parsing vs binary reading)
- **Ambiguity:** Different JSON libraries might format differently
- **Blockchain Cost:** Every byte costs money (storage/bandwidth)

### Option 2: Binary Encoding ✅ Efficient

```
4d4e01020000a0dec5adc9350000003c...
```

**Benefits:**
- **Compact:** Only the actual data, no labels
- **Fast:** Direct memory mapping
- **Deterministic:** Same data = same bytes (always)
- **Cheap:** Smaller size = lower fees

**This is what SCALE provides.**

---

## 📚 How SCALE Works: The Basics

### 1. Numbers: Compact Encoding

SCALE uses **compact encoding** for unsigned integers to save space.

#### Single-Byte Mode (0-63)
```
Value: 0
SCALE: 00

Value: 1
SCALE: 04  (1 << 2 = 4, because mode = 0b00)

Value: 63
SCALE: fc  (63 << 2 = 252 = 0xFC)
```

**Format:** `0b00XXXXXX` (top 2 bits = 00 means single byte)

#### Two-Byte Mode (64-16383)
```
Value: 64
SCALE: 01 01  ((64 << 2) | 0x01 = 257 = 0x0101)

Value: 100
SCALE: 91 01  ((100 << 2) | 0x01 = 401 = 0x0191)

Value: 16383
SCALE: fd ff  ((16383 << 2) | 0x01 = 65533 = 0xFFFD)
```

**Format:** `0b01XXXXXX XXXXXXXX` (top 2 bits = 01 means two bytes)

#### Four-Byte Mode (16384+)
```
Value: 100000
SCALE: 02 93 84 06  ((100000 << 2) | 0x02)
```

**Format:** `0b10XXXXXX ...` (top 2 bits = 10 means four bytes)

**Why Compact?** Small numbers (common in transactions) use fewer bytes.

---

### 2. Fixed-Size Data: Direct Encoding

```kotlin
// 32-byte hash
val hash = ByteArray(32) { 0xAB.toByte() }

// SCALE: Just the bytes (no length prefix)
abababababababababababababababababababababababababababababababab
```

**Rule:** If size is known from context, just write the bytes.

---

### 3. Variable-Length Data: Length Prefix

```kotlin
// Vec<u8> (byte array)
val data = byteArrayOf(0x01, 0x02, 0x03)

// SCALE: [compact_length][data]
0c 01 02 03
↑  ↑______
|  └─ Data (3 bytes)
└─── Length = 3 in compact encoding (3 << 2 = 12 = 0x0C)
```

**Rule:** Variable-length data starts with compact-encoded length.

---

### 4. Structs: Field Concatenation

```kotlin
// Kotlin struct
data class Person(
    val age: UInt,      // compact u32
    val name: String    // Vec<u8>
)

val person = Person(age = 25u, name = "Bob")

// SCALE encoding:
64                    // age: 25 in compact (25 << 2 = 100 = 0x64)
0c 42 6f 62          // name: length=3, "Bob" in ASCII
↑  ↑______
|  └─ "Bob" bytes (0x42='B', 0x6F='o', 0x62='b')
└─── length = 3
```

**Rule:** Concatenate field encodings in order (no separators).

---

### 5. Enums: Tag Byte + Data

```kotlin
// Rust enum
enum Result {
    Ok(value: u32),
    Err(message: String)
}

// Example 1: Ok(42)
SCALE: 00 a8 00 00 00
       ↑  ↑__________
       |  └─ value: 42 as u32 (little-endian)
       └─── tag: 0 = Ok variant

// Example 2: Err("fail")
SCALE: 01 10 66 61 69 6c
       ↑  ↑  ↑___________
       |  |  └─ "fail" in ASCII
       |  └─── length: 4
       └────── tag: 1 = Err variant
```

**Rule:** First byte is variant index, then variant data.

---

## 🌉 Real Example: Midnight Transaction

Let's encode a simplified transaction.

### Kotlin Structure

```kotlin
data class SimpleTransaction(
    val inputs: List<Input>,
    val outputs: List<Output>,
    val ttl: Long
)

data class Input(
    val value: BigInteger,
    val intentHash: ByteArray  // 32 bytes
)

data class Output(
    val value: BigInteger,
    val owner: ByteArray  // 32 bytes
)
```

### Example Transaction

```kotlin
val tx = SimpleTransaction(
    inputs = listOf(
        Input(
            value = BigInteger("1000"),
            intentHash = ByteArray(32) { 0xAA.toByte() }
        )
    ),
    outputs = listOf(
        Output(
            value = BigInteger("500"),
            owner = ByteArray(32) { 0xBB.toByte() }
        ),
        Output(
            value = BigInteger("500"),
            owner = ByteArray(32) { 0xCC.toByte() }
        )
    ),
    ttl = 1704067200000
)
```

### SCALE Encoding (Step by Step)

```
1. Inputs count: 1
   → Compact: 04 (1 << 2)

2. Input[0].value: 1000
   → Compact: a2 0f (1000 << 2 = 4000 = 0x0FA0, little-endian = a0 0f)

3. Input[0].intentHash: 32 bytes of 0xAA
   → Direct: aaaa...aaaa (32 bytes)

4. Outputs count: 2
   → Compact: 08 (2 << 2)

5. Output[0].value: 500
   → Compact: d1 07 (500 << 2 = 2000 = 0x07D0, little-endian = d0 07)

6. Output[0].owner: 32 bytes of 0xBB
   → Direct: bbbb...bbbb (32 bytes)

7. Output[1].value: 500
   → Compact: d1 07

8. Output[1].owner: 32 bytes of 0xCC
   → Direct: cccc...cccc (32 bytes)

9. TTL: 1704067200000 (u64, little-endian)
   → Direct: 00 90 1e d7 8c 01 00 00
```

### Final SCALE Bytes

```
04                          // inputs.len = 1
a2 0f                       // inputs[0].value = 1000
aaaa...aaaa (32 bytes)     // inputs[0].intentHash

08                          // outputs.len = 2
d1 07                       // outputs[0].value = 500
bbbb...bbbb (32 bytes)     // outputs[0].owner
d1 07                       // outputs[1].value = 500
cccc...cccc (32 bytes)     // outputs[1].owner

00 90 1e d7 8c 01 00 00    // ttl (u64)
```

**Total Size:** ~105 bytes (vs ~300+ bytes in JSON)

---

## 🔧 Why Midnight Uses SCALE

### 1. Midnight is Built on Substrate

**Substrate** is a blockchain framework created by Parity Technologies.

Midnight blockchain uses Substrate under the hood, so it inherits:
- SCALE encoding for transactions
- JSON-RPC interface (Substrate standard)
- Extrinsic format (transaction wrapper)

### 2. Compatibility

All Substrate chains use SCALE:
- Polkadot
- Kusama
- Moonbeam
- Astar
- **Midnight**

This means:
- Battle-tested encoding (billions of transactions)
- Standardized tooling (polkadot.js)
- Well-documented

---

## 🛠️ How Kuira Implements SCALE

### We DON'T Implement SCALE Ourselves

**Why?** It's complex and error-prone.

**Instead:** We use the **same Rust library** that Lace wallet uses.

```
Kuira (Kotlin)
    ↓ JNI
Rust FFI (kuira_crypto_ffi)
    ↓ Uses
midnight-ledger crate
    ↓ Uses
parity-scale-codec (official SCALE library)
```

### The Flow

```kotlin
// 1. Kotlin: Build transaction
val intent = Intent(...)

// 2. Convert to JSON (intermediate format)
val inputsJson = """[{"value": "1000", ...}]"""
val outputsJson = """[{"value": "500", ...}]"""

// 3. Pass to Rust FFI
external fun nativeSerializeTransaction(
    inputsJson: String,
    outputsJson: String,
    ...
): String?

// 4. Rust does:
// - Parse JSON → Rust structs
// - Encode with parity-scale-codec
// - Return hex string

// 5. Kotlin receives SCALE hex
val scaleHex = nativeSerializeTransaction(...)
// → "4d4e01020000a0dec5..."
```

---

## 📊 SCALE vs Other Encodings

| Feature | SCALE | JSON | Protobuf | MessagePack |
|---------|-------|------|----------|-------------|
| **Size** | ✅ Small | ❌ Large | ✅ Small | ✅ Small |
| **Speed** | ✅ Fast | ❌ Slow | ✅ Fast | ✅ Fast |
| **Deterministic** | ✅ Yes | ⚠️ No* | ✅ Yes | ✅ Yes |
| **Human Readable** | ❌ No | ✅ Yes | ❌ No | ❌ No |
| **Schema Required** | ❌ No | ❌ No | ✅ Yes | ❌ No |
| **Blockchain Use** | ✅ Common | ❌ Rare | ⚠️ Some | ❌ Rare |

*JSON can have different whitespace, field ordering, etc.

**Winner for Blockchain:** SCALE (or Protobuf for some chains like Cosmos)

---

## 🎓 Key Takeaways

### What is SCALE?
A binary encoding format that converts structured data into compact byte arrays.

### Why Use It?
- **Efficient:** Smaller transactions = lower fees
- **Fast:** Direct binary encoding/decoding
- **Deterministic:** Same data always produces same bytes (critical for blockchain)
- **Standard:** Used across entire Substrate ecosystem

### How We Use It in Kuira?
1. Build transaction in Kotlin (high-level objects)
2. Convert to JSON (intermediate format)
3. Pass to Rust FFI (via JNI)
4. Rust encodes to SCALE using `parity-scale-codec`
5. Return hex string to Kotlin
6. Submit to blockchain node

### Do You Need to Understand SCALE Deeply?
**For using Kuira:** No - it's handled by Rust FFI.
**For debugging:** Yes - helps understand what's being sent to the blockchain.
**For implementing:** No - we delegate to battle-tested libraries.

---

## 🔍 Debugging SCALE Output

### How to Inspect SCALE Bytes

When you see a hex string like:
```
4d4e01020000a0dec5adc9350000003c...
```

**Decode it:**
1. Use https://polkadot.js.org/apps/ (for Substrate chains)
2. Use `scale-info` Rust library
3. Parse manually (if you know the structure)

### Common Patterns to Recognize

```
04               → Length = 1 (most common)
08               → Length = 2
0c               → Length = 3
10               → Length = 4
00               → Number 0 or empty
01 00 00 00      → u32 = 1 (little-endian)
```

### Example: Recognizing Structure

```
04                    ← inputs.len = 1
  a2 0f              ← inputs[0].value (compact)
  aaaa...aaaa        ← inputs[0].hash (32 bytes)
08                    ← outputs.len = 2
  d1 07              ← outputs[0].value
  bbbb...bbbb        ← outputs[0].owner (32 bytes)
  d1 07              ← outputs[1].value
  cccc...cccc        ← outputs[1].owner (32 bytes)
```

---

## 📚 Further Reading

### Official SCALE Documentation
- Substrate Docs: https://docs.substrate.io/reference/scale-codec/
- Parity SCALE Codec: https://github.com/paritytech/parity-scale-codec

### Related Concepts
- **Substrate:** Blockchain framework Midnight is built on
- **Extrinsic:** Substrate's term for "transaction" (includes SCALE-encoded data)
- **Polkadot.js:** JavaScript library for interacting with Substrate chains

### In Our Codebase
- Rust FFI: `core/crypto-ffi/src/transaction.rs` (uses `parity-scale-codec`)
- Wrapper: `TransactionSerializer.kt` (calls Rust via JNI)
- Tests: `ExtrinsicWrapperTest.kt` (tests compact encoding)

---

## 🎯 Summary: SCALE in One Sentence

**SCALE is a compact binary format that converts blockchain transactions from human-readable structures (like JSON) into efficient byte arrays that can be sent over the network and stored on-chain.**

**Analogy:** Like ZIP compression for blockchain data - same information, much smaller size.

---

**Questions?**
- How does SCALE compare to Bitcoin's serialization? (Bitcoin uses custom format, similar philosophy)
- Why not just use Protobuf? (SCALE is simpler, no schema required)
- Can I decode SCALE manually? (Yes, but tedious - use tools)
