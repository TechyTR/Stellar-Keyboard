import gi

gi.require_version("IBus", "1.0")
from gi.repository import IBus


class StellarEngine:

    def __init__(self, ibus_engine):
        self.engine = ibus_engine

        self.shift = False
        self.caps_lock = False

        self.layout = "Q"

        self.text = ""

    def process_key(self, keyval, keycode, state):
        if keyval in (
            IBus.KEY_Shift_L,
            IBus.KEY_Shift_R
        ):
            self.shift = True
            return True

        if keyval == IBus.KEY_Caps_Lock:
            self.caps_lock = not self.caps_lock
            return True

        if keyval in (
            IBus.KEY_Return,
            IBus.KEY_KP_Enter
        ):
            self.commit("\n")
            self.reset_shift()
            return True

        if keyval in (
            IBus.KEY_BackSpace,
            IBus.KEY_Delete
        ):
            self.delete_character()
            return True

        if keyval == IBus.KEY_space:
            self.commit(" ")
            self.reset_shift()
            return True

        character = self.key_to_character(keyval)

        if character is None:
            return False

        if self.shift or self.caps_lock:
            character = character.upper()

        self.commit(character)
        self.reset_shift()

        return True

    def key_to_character(self, keyval):

        try:
            character = chr(keyval)
        except (TypeError, ValueError):
            return None

        if len(character) != 1:
            return None

        if character.isprintable():
            return character

        return None

    def commit(self, text):
        self.engine.commit_text(
            text,
            1
        )

        self.text += text

    def delete_character(self):
        self.engine.delete_surrounding_text(
            -1,
            1
        )

        if self.text:
            self.text = self.text[:-1]

    def reset_shift(self):
        self.shift = False
