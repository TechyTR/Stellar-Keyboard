class StellarAutoCorrect:

    COMMON = {
        "slm": "selam",
        "mrb": "merhaba",
        "nbr": "ne haber",
        "tmm": "tamam",
        "tm": "tamam",
        "ok": "okey",
        "evt": "evet",
        "hyr": "hayır",
        "bi": "bir",
        "bsy": "bir şey",
        "sana": "sana",
    }

    def correct(self, word):
        if not word:
            return word

        return self.COMMON.get(
            word.lower(),
            word
        )

    def process_sentence(self, text):
        words = text.split(" ")

        if not words:
            return text

        corrected = [
            self.correct(word)
            for word in words
        ]

        return " ".join(corrected)
