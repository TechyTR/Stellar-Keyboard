class StellarState:

    def __init__(self):
        self.shift = False
        self.caps_lock = False
        self.symbols = False

        self.layout = "Q"
        self.mode = "full"

        self.emoji_category = "smileys"

        self.current_word = ""

    def reset(self):
        self.shift = False
        self.symbols = False
        self.current_word = ""

    def toggle_shift(self):
        self.shift = not self.shift

    def toggle_caps_lock(self):
        self.caps_lock = not self.caps_lock

    def toggle_symbols(self):
        self.symbols = not self.symbols

    def set_layout(self, layout):
        self.layout = layout.upper()

    def set_mode(self, mode):
        self.mode = mode

    def set_emoji_category(self, category):
        self.emoji_category = category

    def add_character(self, character):
        self.current_word += character

    def remove_character(self):
        if self.current_word:
            self.current_word = (
                self.current_word[:-1]
            )
