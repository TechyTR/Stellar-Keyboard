package com.nevruz.stellarkeyboard

class AutoCorrectEngine {

    private val corrections = mapOf(
        "slm" to "selam",
        "mrb" to "merhaba",
        "tmm" to "tamam",
        "tesekkur" to "teşekkür",
        "tesekkurler" to "teşekkürler",
        "lutfen" to "lütfen",
        "nasilsin" to "nasılsın",
        "nasil" to "nasıl",
        "neden" to "neden",
        "simdi" to "şimdi",
        "yarin" to "yarın",
        "bugun" to "bugün",
        "cok" to "çok",
        "icin" to "için",
        "suan" to "şu an",
        "hersey" to "her şey",
        "birsey" to "bir şey"
    )

    fun correct(word: String): String {
        if (word.isBlank()) {
            return word
        }

        val leading = word.takeWhile {
            !it.isLetterOrDigit()
        }

        val trailing = word.takeLastWhile {
            !it.isLetterOrDigit()
        }

        val core = word
            .removePrefix(leading)
            .removeSuffix(trailing)

        val lower = core.lowercase()

        val corrected = corrections[lower]
            ?: return word

        val result = if (core.firstOrNull()
                ?.isUpperCase() == true
        ) {
            corrected.replaceFirstChar {
                it.uppercase()
            }
        } else {
            corrected
        }

        return leading + result + trailing
    }
}
