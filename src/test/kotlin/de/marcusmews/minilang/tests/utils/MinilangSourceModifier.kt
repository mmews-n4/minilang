package de.marcusmews.minilang.tests.utils

import kotlin.random.Random

/**
 * This utility modifies the code: it either removes or adds arbitrary text.
 */
class MinilangSourceModifier {

    /**
     *  Text additions may include all characters that the lexer refers explicitly, but also other characters.
     */
    private val validCharacters = listOf(
        '+', '-', '*', '/', '^', '=', ',', '{', '}', '(', ')', '-', '>', '"', ' ', '\t', '\n'
    ) + ('a'..'z') + ('A'..'Z') + ('0'..'9')

    fun modifySourceCode(seed: Long, sourceCode: String): String {
        val random = Random(seed)
        val modifiedCode = StringBuilder(sourceCode)

        // Decide randomly whether to add or remove a character
        if (random.nextBoolean() && modifiedCode.isNotEmpty()) {
            // Remove a character at a random position
            val position = random.nextInt(modifiedCode.length)
            modifiedCode.deleteCharAt(position)
        } else {
            // Add a character at a random position
            val position = random.nextInt(modifiedCode.length + 1)
            val newChar = validCharacters.random(random)
            modifiedCode.insert(position, newChar)
        }

        return modifiedCode.toString()
    }
}