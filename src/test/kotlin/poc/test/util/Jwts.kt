package poc.test.util

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import poc.config.AUTHORITY_READ
import poc.config.AUTHORITY_WRITE
import java.security.Key
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.util.*

// generate a fake token for testing
fun main() {
    val signatureAlgorithm = SignatureAlgorithm.RS256
    val keySize = 2048
    val keyPair = generateKeys(signatureAlgorithm, keySize)

    println("no scope")
    generateJwt(keyPair, signatureAlgorithm)

    println("reader")
    generateJwt(keyPair, signatureAlgorithm, scopes = setOf(AUTHORITY_READ))

    println("writer")
    generateJwt(keyPair, signatureAlgorithm, scopes = setOf(AUTHORITY_WRITE))

    println("reader and writer")
    generateJwt(keyPair, signatureAlgorithm, scopes = setOf(AUTHORITY_READ, AUTHORITY_WRITE))
}

fun generateJwt(keyPair: KeyPair, signatureAlgorithm: SignatureAlgorithm, scopes: Set<String> = emptySet()) {
    val privateKey = keyPair.private
    val publicKey = keyPair.public

    // create JWT
    val compactJws = Jwts.builder()
        .setHeaderParam("typ", "JWT")
        .setId(UUID.randomUUID().toString())
        .setIssuedAt(Date())
        .setSubject("any subject")
        .claim("scope", scopes.joinToString(separator = " "))
        .signWith(signatureAlgorithm, privateKey)
        .compact()
    println("jwt: $compactJws")

    val jws = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(compactJws)
    println("jws: $jws")
}

private fun generateKeys(signatureAlgorithm: SignatureAlgorithm, keySize: Int): KeyPair {
    val keyGen = KeyPairGenerator.getInstance(signatureAlgorithm.familyName)
    keyGen.initialize(keySize)

    val keyPair = keyGen.genKeyPair()
    val privateKey = keyPair.private
    val publicKey = keyPair.public

    printKey(privateKey)
    printKey(publicKey, isPrivateKey = false)

    return keyPair
}

private fun printKey(key: Key, isPrivateKey: Boolean = true) {
    val keyType = if (isPrivateKey) "PRIVATE" else "PUBLIC"
    println("-----BEGIN RSA $keyType KEY-----")
    println(Base64.getMimeEncoder().encodeToString(key.encoded))
    println("-----END RSA $keyType KEY-----")
    println()
}