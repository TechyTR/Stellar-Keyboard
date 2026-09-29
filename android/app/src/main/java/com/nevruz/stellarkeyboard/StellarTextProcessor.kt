package com.nevruz.stellarkeyboard

class StellarTextProcessor {

    private val autoCorrect = AutoCorrectEngine()

    fun correctBeforeSpace(
        textBeforeCursor: String
    ): String? {
        val word = StellarWordExtractor.currentWord(
            textBeforeCursor
        )

        if (word.isEmpty()) {
            return null
        }

        val corrected = autoCorrect.correct(word)

        if (corrected == word) {
            return null
        }

        return corrected
    }
}
