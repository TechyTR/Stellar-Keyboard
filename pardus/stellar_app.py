#!/usr/bin/env python3

import sys

import gi

gi.require_version("Gtk", "3.0")
from gi.repository import Gtk

from stellar_ui import StellarKeyboardUI
from stellar_logger import logger


def main():
    logger.info(
        "Stellar Keyboard başlatılıyor."
    )

    window = StellarKeyboardUI()

    window.connect(
        "destroy",
        Gtk.main_quit
    )

    Gtk.main()


if __name__ == "__main__":
    try:
        main()
    except KeyboardInterrupt:
        logger.info(
            "Stellar Keyboard kapatıldı."
        )
        sys.exit(0)
    except Exception:
        logger.exception(
            "Stellar Keyboard beklenmeyen hata verdi."
        )
        sys.exit(1)
