class StellarInput:

    def __init__(self, callback=None):
        self.callback = callback

    def send(self, text):
        if not text:
            return

        if self.callback:
            self.callback(text)

    def send_key(self, key):
        self.send(key)

    def backspace(self):
        if self.callback:
            self.callback(
                "\b"
            )

    def enter(self):
        self.send("\n")

    def space(self):
        self.send(" ")
