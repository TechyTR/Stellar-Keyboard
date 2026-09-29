class StellarSettings:

    def __init__(self, preferences):
        self.preferences = preferences

    def set_theme(self, theme):
        self.preferences.set_theme(
            theme
        )

    def get_theme(self):
        return self.preferences.get_theme()

    def toggle_sound(self):
        enabled = (
            self.preferences.sound_enabled()
        )

        self.preferences.set_sound_enabled(
            not enabled
        )

    def sound_enabled(self):
        return self.preferences.sound_enabled()

    def toggle_clipboard(self):
        enabled = (
            self.preferences.clipboard_enabled()
        )

        self.preferences.set_clipboard_enabled(
            not enabled
        )

    def clipboard_enabled(self):
        return (
            self.preferences.clipboard_enabled()
        )

    def set_mode(self, mode):
        self.preferences.set_keyboard_mode(
            mode
        )

    def get_mode(self):
        return self.preferences.get_keyboard_mode()
