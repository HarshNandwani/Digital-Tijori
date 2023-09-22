package com.harshnandwani.digitaltijori.domain.use_case.backup_restore

import com.harshnandwani.digitaltijori.domain.util.toHexString
import java.lang.Exception
import java.security.MessageDigest
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import kotlin.jvm.Throws

class EncryptDecryptDataUseCase {

    private val algorithm = "AES/CBC/PKCS5Padding"

    @Throws(Exception::class) // Multiple exception types thrown
    operator fun invoke(mode: Mode, secretKey: String, data: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hash = digest.digest(secretKey.toByteArray())

        val sha256 = toHexString(hash)
        val keySpec = SecretKeySpec(sha256.take(32).toByteArray(), "AES")

        val cipher = Cipher.getInstance(algorithm)
        return if (mode == Mode.MODE_ENCRYPT) {
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, IvParameterSpec(ByteArray(16)))
            val res = cipher.doFinal(data.toByteArray())
            Base64.getEncoder().encodeToString(res)
        } else {
            cipher.init(Cipher.DECRYPT_MODE, keySpec, IvParameterSpec(ByteArray(16)))
            val res = cipher.doFinal(Base64.getDecoder().decode(data))
            String(res)
        }
    }
}

enum class Mode {
    MODE_ENCRYPT,
    MODE_DECRYPT
}
