package com.nevruz.stellarkeyboard

import android.content.Context

class KeyboardModeManager(context: Context) {

    private val preferences = context.getSharedPreferences(
        "stellar_keyboard",
        Context.MODE_PRIVATE
    )

    fun getMode(): KeyboardMode {
        return when (preferences.getString("keyboard_mode", "FULL")) {
            "ONE_HANDED_LEFT" -> KeyboardMode.ONE_HANDED_LEFT
            "ONE_HANDED_RIGHT" -> KeyboardMode.ONE_HANDED_RIGHT
            "FLOATING" -> KeyboardMode.FLOATING
            else -> KeyboardMode.FULL
        }
    }

    fun setMode(mode: KeyboardMode) {
        preferences.edit()
            .putString("keyboard_mode", mode.name)
            .apply()
    }
}
