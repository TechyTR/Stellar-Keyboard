import gi

gi.require_version(
    "Gtk",
    "3.0"
)

from gi.repository import Gtk


class StellarKey(Gtk.Button):

    def __init__(
        self,
        text,
        callback=None,
        sound=None
    ):
        super().__init__(
            label=text
        )

        self.key_text = text
        self.callback = callback
        self.sound = sound

        self.set_size_request(
            48,
            48
        )

        self.set_can_focus(
            False
        )

        self.connect(
            "clicked",
            self._clicked
        )

        self.connect(
            "pressed",
            self._pressed
        )

    def _pressed(self, button):
        if self.sound:
            self.sound.play_key()

    def _clicked(self, button):
        if self.callback:
            self.callback(
                self.key_text
            )
