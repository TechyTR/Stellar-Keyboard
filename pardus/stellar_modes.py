MODES = (
    "full",
    "one_handed_left",
    "one_handed_right",
    "floating"
)


MODE_NAMES = {
    "full": "Tam klavye",
    "one_handed_left": "Tek el — Sol",
    "one_handed_right": "Tek el — Sağ",
    "floating": "Yüzen klavye"
}


def is_valid_mode(mode):
    return mode in MODES


def mode_name(mode):
    return MODE_NAMES.get(
        mode,
        MODE_NAMES["full"]
    )


def next_mode(mode):
    try:
        index = MODES.index(mode)
    except ValueError:
        return "full"

    index += 1

    if index >= len(MODES):
        index = 0

    return MODES[index]
