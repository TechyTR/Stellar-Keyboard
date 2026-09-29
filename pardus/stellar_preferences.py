import json
import os


class StellarPreferences:

    DEFAULTS = {
        "layout": "Q",

        # İlk açılış artık yüzen klavye.
        "keyboard_mode": "floating",

        "theme": "dark",

        "sound_enabled": True,
        "sound_type": "soft",

        "clipboard_enabled": True,
        "clipboard_limit": 20,

        "emoji_recent_enabled": True,

        "floating_width": 520,
        "floating_height": 300,

        "floating_x": -1,
        "floating_y": -1,

        "one_hand_side": "right",

        "language": "tr"
    }

    def __init__(self):
        self.directory = os.path.expanduser(
            "~/.config/stellar-keyboard"
        )

        self.file = os.path.join(
            self.directory,
            "settings.json"
        )

        os.makedirs(
            self.directory,
            exist_ok=True
        )

        self.data = self.load()

    def load(self):
        if not os.path.exists(self.file):
            data = self.DEFAULTS.copy()
            self.save(data)
            return data

        try:
            with open(
                self.file,
                "r",
                encoding="utf-8"
            ) as file:
                loaded = json.load(file)

            data = self.DEFAULTS.copy()
            data.update(loaded)

            return data

        except (
            OSError,
            json.JSONDecodeError
        ):
            return self.DEFAULTS.copy()

    def save(self, data=None):
        if data is not None:
            self.data = data

        with open(
            self.file,
            "w",
            encoding="utf-8"
        ) as file:
            json.dump(
                self.data,
                file,
                ensure_ascii=False,
                indent=2
            )

    def get(self, key):
        return self.data.get(
            key,
            self.DEFAULTS.get(key)
        )

    def set(self, key, value):
        self.data[key] = value
        self.save()

    def get_layout(self):
        return self.get("layout")

    def set_layout(self, layout):
        self.set(
            "layout",
            layout.upper()
        )

    def get_keyboard_mode(self):
        return self.get(
            "keyboard_mode"
        )

    def set_keyboard_mode(self, mode):
        self.set(
            "keyboard_mode",
            mode
        )

    def get_theme(self):
        return self.get("theme")

    def set_theme(self, theme):
        self.set(
            "theme",
            theme
        )

    def sound_enabled(self):
        return bool(
            self.get("sound_enabled")
        )

    def set_sound_enabled(self, enabled):
        self.set(
            "sound_enabled",
            bool(enabled)
        )

    def clipboard_enabled(self):
        return bool(
            self.get("clipboard_enabled")
        )

    def set_clipboard_enabled(self, enabled):
        self.set(
            "clipboard_enabled",
            bool(enabled)
        )

    def get_floating_size(self):
        return (
            int(self.get("floating_width")),
            int(self.get("floating_height"))
        )

    def set_floating_size(
        self,
        width,
        height
    ):
        self.data["floating_width"] = int(width)
        self.data["floating_height"] = int(height)
        self.save()

    def get_floating_position(self):
        return (
            int(self.get("floating_x")),
            int(self.get("floating_y"))
        )

    def set_floating_position(
        self,
        x,
        y
    ):
        self.data["floating_x"] = int(x)
        self.data["floating_y"] = int(y)
        self.save()
