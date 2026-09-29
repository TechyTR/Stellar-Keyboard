import gi

gi.require_version("Gtk", "3.0")
from gi.repository import Gtk, Gdk

from stellar_preferences import StellarPreferences
from keyboard_layout import get_layout
from stellar_config import DEFAULT_LAYOUT


class StellarKeyboardUI(Gtk.Window):

    def __init__(self):
        super().__init__(
            title="Stellar Keyboard"
        )

        self.preferences = StellarPreferences()

        self.layout_name = (
            self.preferences.get_layout()
            or DEFAULT_LAYOUT
        )

        self.mode = (
            self.preferences.get_keyboard_mode()
            or "full"
        )

        self.set_decorated(False)
        self.set_resizable(False)

        self.set_position(
            Gtk.WindowPosition.CENTER
        )

        self.build()

    def build(self):
        self.main_box = Gtk.Box(
            orientation=Gtk.Orientation.VERTICAL,
            spacing=4
        )

        self.add(self.main_box)

        self.create_header()
        self.create_keyboard()

        self.show_all()

    def create_header(self):
        header = Gtk.Box(
            orientation=Gtk.Orientation.HORIZONTAL,
            spacing=4
        )

        layout_button = Gtk.Button(
            label=self.layout_name
        )

        layout_button.connect(
            "clicked",
            self.toggle_layout
        )

        mode_button = Gtk.Button(
            label="▣"
        )

        mode_button.connect(
            "clicked",
            self.show_mode_menu
        )

        emoji_button = Gtk.Button(
            label="😊"
        )

        emoji_button.connect(
            "clicked",
            self.show_emoji_panel
        )

        title = Gtk.Label(
            label="Stellar Keyboard"
        )

        header.pack_start(
            layout_button,
            False,
            False,
            0
        )

        header.pack_start(
            mode_button,
            False,
            False,
            0
        )

        header.pack_start(
            emoji_button,
            False,
            False,
            0
        )

        header.pack_start(
            title,
            True,
            True,
            0
        )

        self.main_box.pack_start(
            header,
            False,
            False,
            0
        )

    def create_keyboard(self):
        self.keyboard_box = Gtk.Box(
            orientation=Gtk.Orientation.VERTICAL,
            spacing=3
        )

        self.main_box.pack_start(
            self.keyboard_box,
            False,
            False,
            0
        )

        self.refresh_keyboard()

    def refresh_keyboard(self):
        for child in self.keyboard_box.get_children():
            self.keyboard_box.remove(child)

        layout = get_layout(
            self.layout_name
        )

        for row in layout:
            row_box = Gtk.Box(
                orientation=Gtk.Orientation.HORIZONTAL,
                spacing=2
            )

            for character in row:
                button = Gtk.Button(
                    label=character
                )

                button.set_size_request(
                    48,
                    48
                )

                button.connect(
                    "clicked",
                    self.on_key_clicked,
                    character
                )

                row_box.pack_start(
                    button,
                    True,
                    True,
                    0
                )

            self.keyboard_box.pack_start(
                row_box,
                False,
                False,
                0
            )

        self.add_bottom_row()

        self.show_all()

    def add_bottom_row(self):
        row = Gtk.Box(
            orientation=Gtk.Orientation.HORIZONTAL,
            spacing=2
        )

        shift = Gtk.Button(
            label="⇧"
        )

        backspace = Gtk.Button(
            label="⌫"
        )

        space = Gtk.Button(
            label="Boşluk"
        )

        enter = Gtk.Button(
            label="↵"
        )

        row.pack_start(
            shift,
            False,
            False,
            0
        )

        row.pack_start(
            backspace,
            False,
            False,
            0
        )

        row.pack_start(
            space,
            True,
            True,
            0
        )

        row.pack_start(
            enter,
            False,
            False,
            0
        )

        self.keyboard_box.pack_start(
            row,
            False,
            False,
            0
        )

    def on_key_clicked(
        self,
        button,
        character
    ):
        print(
            f"Stellar: {character}"
        )

    def toggle_layout(self, button):
        if self.layout_name == "Q":
            self.layout_name = "F"
        else:
            self.layout_name = "Q"

        self.preferences.set_layout(
            self.layout_name
        )

        button.set_label(
            self.layout_name
        )

        self.refresh_keyboard()

    def show_mode_menu(self, button):
        menu = Gtk.Menu()

        modes = [
            ("Tam klavye", "full"),
            ("Tek el — Sol", "one_handed_left"),
            ("Tek el — Sağ", "one_handed_right"),
            ("Yüzen klavye", "floating")
        ]

        for label, mode in modes:
            item = Gtk.MenuItem(
                label=label
            )

            item.connect(
                "activate",
                self.change_mode,
                mode
            )

            menu.append(item)

        menu.show_all()

        menu.popup_at_widget(
            button,
            Gdk.Gravity.SOUTH,
            Gdk.Gravity.NORTH,
            None
        )

    def change_mode(self, item, mode):
        self.mode = mode

        self.preferences.set_keyboard_mode(
            mode
        )

        self.apply_mode()

    def apply_mode(self):
        if self.mode == "full":
            self.set_default_size(
                700,
                300
            )

        elif self.mode in (
            "one_handed_left",
            "one_handed_right"
        ):
            self.set_default_size(
                520,
                300
            )

        elif self.mode == "floating":
            self.set_default_size(
                480,
                260
            )

        self.show_all()

    def show_emoji_panel(self, button):
        dialog = StellarEmojiWindow(
            self
        )

        dialog.show_all()


class StellarEmojiWindow(Gtk.Window):

    def __init__(self, parent):
        super().__init__(
            title="Stellar Emoji"
        )

        self.set_transient_for(parent)
        self.set_default_size(
            500,
            300
        )

        box = Gtk.Box(
            orientation=Gtk.Orientation.VERTICAL,
            spacing=4
        )

        self.add(box)

        emojis = (
            "😀 😃 😄 😁 😆 😅 😂 🤣 "
            "😊 😇 🙂 🙃 😉 😌 😍 🥰 "
            "😘 😗 😙 😚 😋 😛 😝 😜 "
            "🤪 🤨 🧐 🤓 😎 🤩 🥳 "
            "❤️ 🧡 💛 💚 💙 💜 🖤 🤍 "
            "🐶 🐱 🐭 🐹 🐰 🦊 🐻 🐼 "
            "🍎 🍊 🍋 🍌 🍉 🍇 🍓 🫐 "
            "🍔 🍟 🍕 🌭 🌮 🌯 🍿 🍩"
        )

        grid = Gtk.Grid()

        grid.set_row_spacing(3)
        grid.set_column_spacing(3)

        for index, emoji in enumerate(
            emojis.split()
        ):
            button = Gtk.Button(
                label=emoji
            )

            button.set_size_request(
                48,
                42
            )

            row = index // 8
            column = index % 8

            grid.attach(
                button,
                column,
                row,
                1,
                1
            )

        box.pack_start(
            grid,
            True,
            True,
            0
        )
