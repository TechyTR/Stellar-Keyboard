import os
import subprocess


class StellarSound:

    def __init__(self, preferences):
        self.preferences = preferences

    def play_key(self):
        if not self.preferences.sound_enabled():
            return

        self._play("key")

    def play_backspace(self):
        if not self.preferences.sound_enabled():
            return

        self._play("backspace")

    def play_enter(self):
        if not self.preferences.sound_enabled():
            return

        self._play("enter")

    def _play(self, sound_type):
        """
        Sistem ses altyapısı üzerinden kısa klavye sesi.

        Öncelik:
        paplay -> pw-play -> aplay
        """

        sound_files = {
            "key": "key.wav",
            "backspace": "backspace.wav",
            "enter": "enter.wav"
        }

        base = os.path.join(
            os.path.dirname(__file__),
            "sounds"
        )

        path = os.path.join(
            base,
            sound_files.get(
                sound_type,
                "key.wav"
            )
        )

        if not os.path.exists(path):
            return

        commands = [
            ["paplay", path],
            ["pw-play", path],
            ["aplay", "-q", path]
        ]

        for command in commands:
            try:
                subprocess.Popen(
                    command,
                    stdout=subprocess.DEVNULL,
                    stderr=subprocess.DEVNULL
                )
                return
            except (
                FileNotFoundError,
                OSError
            ):
                continue
