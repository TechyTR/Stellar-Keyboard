package com.nevruz.stellarkeyboard

import android.graphics.Color

object StellarTheme {

    enum class Mode {
        DARK,
        LIGHT
    }

    var mode = Mode.DARK

    var accentColor: Int = Color.rgb(105, 160, 255)

    val backgroundColor: Int
        get() {
            return if (mode == Mode.DARK) {
                Color.rgb(14, 18, 28)
            } else {
                Color.rgb(238, 242, 248)
            }
        }

    val keyColor: Int
        get() {
            return if (mode == Mode.DARK) {
                Color.argb(185, 55, 64, 84)
            } else {
                Color.argb(220, 255, 255, 255)
            }
        }

    val textColor: Int
        get() {
            return if (mode == Mode.DARK) {
                Color.WHITE
            } else {
                Color.rgb(25, 30, 40)
            }
        }

    val secondaryTextColor: Int
        get() {
            return if (mode == Mode.DARK) {
                Color.rgb(180, 188, 205)
            } else {
                Color.rgb(80, 88, 105)
            }
        }
}
