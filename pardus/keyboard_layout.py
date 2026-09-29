TURKISH_Q = [
    ["q", "w", "e", "r", "t", "y", "u", "ı", "o", "p", "ğ", "ü"],
    ["a", "s", "d", "f", "g", "h", "j", "k", "l", "ş", "i"],
    ["z", "x", "c", "v", "b", "n", "m", "ö", "ç"]
]

TURKISH_F = [
    ["f", "g", "ğ", "ı", "o", "d", "r", "n", "h", "p", "q", "w"],
    ["u", "i", "e", "a", "ü", "t", "k", "m", "l", "y", "ş"],
    ["j", "ö", "v", "c", "ç", "z", "s", "b"]
]


def get_layout(name="Q"):
    if name.upper() == "F":
        return TURKISH_F

    return TURKISH_Q
