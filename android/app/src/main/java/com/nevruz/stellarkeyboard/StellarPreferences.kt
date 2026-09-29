package com.nevruz.stellarkeyboard

import android.content.Context

class StellarPreferences(context: Context) {

    private val preferences = context.getSharedPreferences(
        "stellar_keyboard",
        Context.MODE_PRIVATE
    )

    fun getLayout(): KeyboardLayout.Type {
        return when (
            preferences.getString("layout", "Q")
        ) {
            "F" -> KeyboardLayout.Type.TURKISH_F
            else -> KeyboardLayout.Type.TURKISH_Q
        }
    }

    fun setLayout(layout: KeyboardLayout.Type) {
        preferences.edit()
            .putString(
                "layout",
                when (layout) {
                    KeyboardLayout.Type.TURKISH_Q -> "Q"
                    KeyboardLayout.Type.TURKISH_F -> "F"
                }
            )
            .apply()
    }

    fun isDarkTheme(): Boolean {
        return preferences.getBoolean("dark_theme", true)
    }

    fun setDarkTheme(enabled: Boolean) {
        preferences.edit()
            .putBoolean("dark_theme", enabled)
            .apply()
    }

    fun getAccentColor(): Int {
        return preferences.getInt(
            "accent_color",
            StellarTheme.accentColor
        )
    }

    fun setAccentColor(color: Int) {
        preferences.edit()
            .putInt("accent_color", color)
            .apply()
    }
}
