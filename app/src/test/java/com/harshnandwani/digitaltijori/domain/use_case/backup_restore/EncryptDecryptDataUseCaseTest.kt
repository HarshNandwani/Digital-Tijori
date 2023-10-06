package com.harshnandwani.digitaltijori.domain.use_case.backup_restore

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test
import java.lang.Exception

class EncryptDecryptDataUseCaseTest {

    private val sut = EncryptDecryptDataUseCase()

    private val plainText = "Hey, this is my sample plain text! -@[]{} Ok bye."
    private val secretKey = "secretKey"
    private val encryptedText = "oolhU7IPTa3eiadEq245HFMZwq/kV60SL4B4KObAkons0H9SbRq7kfHxUtJSUs40SwY3LiOeMqPv3+G/50rTCw=="

    @Test
    fun `WHEN secretKey is blank THEN throw IllegalArgumentException`() {
        assertThrows(IllegalArgumentException::class.java) {
            sut(Mode.values().random(), "", plainText)
        }
    }

    @Test
    fun `WHEN data is blank THEN throw IllegalArgumentException`() {
        assertThrows(IllegalArgumentException::class.java) {
            sut(Mode.values().random(), secretKey, "")
        }
    }

    @Test
    fun `WHEN Mode is MODE_ENCRYPT and secretKey, data are provided THEN return encrypted data`() {
        val myTestEncryption = sut(Mode.MODE_ENCRYPT, secretKey, plainText)
        assertEquals(encryptedText, myTestEncryption)
    }

    @Test
    fun `WHEN Mode is MODE_DECRYPT and wrong secretKey is provided THEN throw Exception`() {
        assertThrows(Exception::class.java) {
            sut(Mode.MODE_DECRYPT, "invalidSecretKey", encryptedText)
        }
    }

    @Test
    fun `WHEN Mode is MODE_DECRYPT and correct secretKey, data are provided THEN return encrypted data`() {
        val myTestDecryption = sut(Mode.MODE_DECRYPT, secretKey, encryptedText)
        assertEquals(plainText, myTestDecryption)
    }

    @Test
    fun `encryption and decryption on random key, data`() {
        val key = generateRandomString((1..32).random())
        val plaintext = generateRandomString(1_00_00_000)

        val encryptedText = sut(Mode.MODE_ENCRYPT, key, plaintext)
        val decrypted = sut(Mode.MODE_DECRYPT, key, encryptedText)

        assertEquals(plaintext, decrypted)
    }

    private fun generateRandomString(length: Int) : String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }

}
