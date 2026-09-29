class StellarFloating:

    MIN_WIDTH = 360
    MAX_WIDTH = 1200

    MIN_HEIGHT = 220
    MAX_HEIGHT = 700

    def __init__(
        self,
        window,
        preferences
    ):
        self.window = window
        self.preferences = preferences

    def apply_saved_size(self):
        width, height = (
            self.preferences.get_floating_size()
        )

        width = self.clamp_width(width)
        height = self.clamp_height(height)

        self.window.resize(
            width,
            height
        )

    def set_size(
        self,
        width,
        height
    ):
        width = self.clamp_width(width)
        height = self.clamp_height(height)

        self.window.resize(
            width,
            height
        )

        self.preferences.set_floating_size(
            width,
            height
        )

    def save_position(self):
        x, y = self.window.get_position()

        self.preferences.set_floating_position(
            x,
            y
        )

    def restore_position(self):
        x, y = (
            self.preferences.get_floating_position()
        )

        if x >= 0 and y >= 0:
            self.window.move(
                x,
                y
            )

    def clamp_width(self, width):
        return max(
            self.MIN_WIDTH,
            min(
                self.MAX_WIDTH,
                int(width)
            )
        )

    def clamp_height(self, height):
        return max(
            self.MIN_HEIGHT,
            min(
                self.MAX_HEIGHT,
                int(height)
            )
        )
