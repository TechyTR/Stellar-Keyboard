import json
import os


class StellarPreferences:

    def __init__(self):
        self.directory = os.path.expanduser(
            "~/.config/stellar-keyboard"
        )

        self.file = os.path.join(
            self.directory,
            "settings.json"
        )

        self.defaults = {
            "layout": "Q",
            "theme": "dark",
            "keyboard_mode": "full",
            "language": "tr",
            "sound": True,
            "vibration": True
        }

        self.data = self.load()

    def load(self):
        os.makedirs(
            self.directory,
            exist_ok=True
        )

        if not os.path.exists(self.file):
            self.save(self.defaults.copy())
            return self.defaults.copy()

        try:
            with open(
                self.file,
                "r",
                encoding="utf-8"
            ) as file:
                data = json.load(file)

            result = self.defaults.copy()
            result.update(data)

            return result

        except (
            OSError,
            json.JSONDecodeError
        ):
            return self.defaults.copy()

    def save(self, data=None):
        if data is not None:
            self.data = data

        os.makedirs(
            self.directory,
            exist_ok=True
        )

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
            self.defaults.get(key)
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
