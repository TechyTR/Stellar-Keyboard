class StellarTheme:

    THEMES = {
        "dark": {
            "background": "#101218",
            "surface": "#181B23",
            "key": "#20242D",
            "key_pressed": "#343A49",
            "text": "#FFFFFF",
            "secondary": "#AAB1C0",
            "accent": "#6EA8FF",
            "border": "#343A48"
        },

        "light": {
            "background": "#F2F4F8",
            "surface": "#FFFFFF",
            "key": "#FFFFFF",
            "key_pressed": "#E1E5EC",
            "text": "#17191E",
            "secondary": "#646A76",
            "accent": "#4D82E8",
            "border": "#D8DDE6"
        },

        "glass_dark": {
            "background": "#0D1018",
            "surface": "#171C29",
            "key": "#202A3A",
            "key_pressed": "#34445D",
            "text": "#FFFFFF",
            "secondary": "#AEBBD0",
            "accent": "#75B7FF",
            "border": "#40516B"
        },

        "glass_light": {
            "background": "#EAF0F8",
            "surface": "#F8FAFD",
            "key": "#FFFFFF",
            "key_pressed": "#DCE6F3",
            "text": "#18202C",
            "secondary": "#637084",
            "accent": "#5798F5",
            "border": "#C7D4E5"
        }
    }

    def __init__(self, name="dark"):
        self.set_theme(name)

    def set_theme(self, name):
        if name not in self.THEMES:
            name = "dark"

        self.name = name

    @property
    def colors(self):
        return self.THEMES[
            self.name
        ]

    def background(self):
        return self.colors["background"]

    def surface(self):
        return self.colors["surface"]

    def key(self):
        return self.colors["key"]

    def key_pressed(self):
        return self.colors["key_pressed"]

    def text(self):
        return self.colors["text"]

    def secondary(self):
        return self.colors["secondary"]

    def accent(self):
        return self.colors["accent"]

    def border(self):
        return self.colors["border"]
