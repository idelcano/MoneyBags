package com.idelcano.moneycontrol.moneycontrol.utils

import java.security.SecureRandom
import java.util.regex.Pattern

//Class copied from Dhis2 SDK
object DhisCodeGenerator {

    private val CODE_PATTERN = Pattern.compile("^[a-zA-Z]{1}[a-zA-Z0-9]{10}$")
    private val LETTERS = "abcdefghijklmnopqrstuvwxyz" + "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    private val ALLOWED_CHARS = "0123456789$LETTERS"
    private val NUMBER_OF_CODE_POINTS = ALLOWED_CHARS.length
    private val CODE_SIZE = 11

    /**
     * Generates a pseudo random string using the allowed characters.
     *
     * @param codeSize the number of characters in the code.
     * @return the code.
     */
    @JvmOverloads
    public fun generateCode(codeSize: Int = CODE_SIZE): String {
        // Using the system default algorithm and seed
        val sr = SecureRandom()

        val randomChars = CharArray(codeSize)

        // first char should be a letter
        randomChars[0] = LETTERS[sr.nextInt(LETTERS.length)]

        for (i in 1 until codeSize) {
            randomChars[i] = ALLOWED_CHARS[sr.nextInt(NUMBER_OF_CODE_POINTS)]
        }

        return String(randomChars)
    }

    /**
     * Tests whether the given code is valid.
     *
     * @param code the code to validate.
     * @return true if the code is valid.
     */
    fun isValidCode(code: String?): Boolean {
        return code != null && CODE_PATTERN.matcher(code).matches()
    }
}
/**
 * Generates a pseudo random string using the allowed characters.
 * Code is 11 characters long.
 *
 * @return the code.
 */
