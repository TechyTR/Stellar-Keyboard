package com.nevruz.stellarkeyboard

class SuggestionEngine {

    private val commonWords = listOf(
        "ben",
        "sen",
        "biz",
        "siz",
        "bu",
        "şu",
        "çok",
        "daha",
        "için",
        "ile",
        "bir",
        "ve",
        "ama",
        "çünkü",
        "olan",
        "olarak",
        "nasıl",
        "neden",
        "bugün",
        "yarın",
        "şimdi",
        "sonra",
        "tamam",
        "merhaba",
        "teşekkür",
        "evet",
        "hayır",
        "istiyorum",
        "yapıyorum",
        "yapabilir",
        "olabilir",
        "gelecek",
        "güzel",
        "harika"
    )

    fun suggest(input: String): List<String> {
        val word = input
            .trim()
            .lowercase()

        if (word.isEmpty()) {
            return emptyList()
        }

        return commonWords
            .filter {
                it.startsWith(word) && it != word
            }
            .sortedBy {
                it.length
            }
            .take(3)
    }
}
