import gi

gi.require_version(
    "Gtk",
    "3.0"
)

from gi.repository import Gtk


class StellarClipboard:

    def __init__(
        self,
        preferences
    ):
        self.preferences = preferences

        self.items = []

        self.limit = int(
            self.preferences.get(
                "clipboard_limit"
            )
        )

    def add(self, text):
        if not text:
            return

        if not self.preferences.clipboard_enabled():
            return

        text = str(text).strip()

        if not text:
            return

        if text in self.items:
            self.items.remove(text)

        self.items.insert(
            0,
            text
        )

        self.items = self.items[
            :self.limit
        ]

    def remove(self, text):
        if text in self.items:
            self.items.remove(text)

    def clear(self):
        self.items.clear()

    def get_items(self):
        return list(self.items)

    def copy_from_system(self):
        clipboard = Gtk.Clipboard.get(
            Gtk.Clipboard.get_default(
                Gtk.Settings.get_default()
            )
        )

        text = clipboard.wait_for_text()

        if text:
            self.add(text)

        return text

    def paste_to_system(self, text):
        if not text:
            return

        clipboard = Gtk.Clipboard.get(
            Gtk.Clipboard.get_default(
                Gtk.Settings.get_default()
            )
        )

        clipboard.set_text(
            text,
            -1
        )

        clipboard.store()
