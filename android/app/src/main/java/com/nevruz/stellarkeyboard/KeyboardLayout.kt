package com.nevruz.stellarkeyboard

object KeyboardLayout {

    enum class Type {
        TURKISH_Q,
        TURKISH_F
    }

    var currentType = Type.TURKISH_Q

    private val qRows = listOf(
        listOf("q", "w", "e", "r", "t", "y", "u", "ı", "o", "p", "ğ", "ü"),
        listOf("a", "s", "d", "f", "g", "h", "j", "k", "l", "ş", "i"),
        listOf("z", "x", "c", "v", "b", "n", "m", "ö", "ç")
    )

    private val fRows = listOf(
        listOf("f", "g", "ğ", "ı", "o", "d", "r", "n", "h", "p", "q", "w"),
        listOf("u", "i", "e", "a", "ü", "t", "k", "m", "l", "y"),
        listOf("j", "ö", "v", "c", "ç", "z", "s", "b")
    )

    fun rows(): List<List<String>> {
        return when (currentType) {
            Type.TURKISH_Q -> qRows
            Type.TURKISH_F -> fRows
        }
    }

    fun toggle() {
        currentType = when (currentType) {
            Type.TURKISH_Q -> Type.TURKISH_F
            Type.TURKISH_F -> Type.TURKISH_Q
        }
    }
}
