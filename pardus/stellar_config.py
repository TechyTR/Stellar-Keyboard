#!/usr/bin/env python3

import os


APP_NAME = "Stellar Keyboard"

APP_ID = "com.nevruz.StellarKeyboard"

ENGINE_NAME = "StellarKeyboard"

VERSION = "1.0.0"

CONFIG_DIRECTORY = os.path.expanduser(
    "~/.config/stellar-keyboard"
)

DATA_DIRECTORY = os.path.expanduser(
    "~/.local/share/stellar-keyboard"
)

CACHE_DIRECTORY = os.path.expanduser(
    "~/.cache/stellar-keyboard"
)

SUPPORTED_LAYOUTS = (
    "Q",
    "F"
)

SUPPORTED_MODES = (
    "full",
    "one_handed_left",
    "one_handed_right",
    "floating"
)

DEFAULT_LAYOUT = "Q"

DEFAULT_MODE = "full"

DEFAULT_THEME = "dark"

LANGUAGE = "tr"

IBUS_COMPONENT_NAME = (
    "com.nevruz.StellarKeyboard"
)

IBUS_ENGINE_NAME = (
    "StellarKeyboard"
)

IBUS_ENGINE_LONG_NAME = (
    "Stellar Keyboard"
)

IBUS_ENGINE_DESCRIPTION = (
    "Stellar Keyboard Türkçe IBus klavyesi"
)
