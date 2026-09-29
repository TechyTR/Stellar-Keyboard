package com.nevruz.stellarkeyboard

object StellarWordExtractor {

    private val wordPattern = Regex(
        "[\\p{L}\\p{M}0-9çğıöşüÇĞİÖŞÜ]+$"
    )

    fun currentWord(textBeforeCursor: String): String {
        val match = wordPattern.find(textBeforeCursor)
        return match?.value ?: ""
    }

    fun removeCurrentWord(
        textBeforeCursor: String
    ): Int {
        return currentWord(textBeforeCursor).length
    }
}
