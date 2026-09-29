package com.nevruz.stellarkeyboard

import android.app.Activity
import android.os.Bundle
import android.content.Intent
import android.provider.Settings
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import android.graphics.Color
import android.graphics.Typeface

class MainActivity : Activity() {

    private lateinit var preferences: StellarPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        preferences = StellarPreferences(this)

        buildInterface()
    }

    private fun buildInterface() {
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(dp(24), dp(32), dp(24), dp(32))
            setBackgroundColor(
                StellarTheme.backgroundColor
            )
        }

        val title = TextView(this).apply {
            text = "Stellar Klavye"
            textSize = 28f
            typeface = Typeface.DEFAULT_BOLD
            gravity = Gravity.CENTER
            setTextColor(
                StellarTheme.textColor
            )
        }

        root.addView(
            title,
            LinearLayout.LayoutParams(
                -1,
                dp(60)
            )
        )

        val subtitle = TextView(this).apply {
            text = "Türkçe Q / F • Liquid Glass"
            textSize = 15f
            gravity = Gravity.CENTER
            setTextColor(
                StellarTheme.secondaryTextColor
            )
        }

        root.addView(
            subtitle,
            LinearLayout.LayoutParams(
                -1,
                dp(45)
            )
        )

        addButton(
            root,
            "Klavye ayarlarını aç"
        ) {
            try {
                startActivity(
                    Intent(
                        Settings.ACTION_INPUT_METHOD_SETTINGS
                    )
                )
            } catch (_: Exception) {
                startActivity(
                    Intent(Settings.ACTION_SETTINGS)
                )
            }
        }

        addButton(
            root,
            "Stellar Klavye'yi seç"
        ) {
            try {
                val manager =
                    getSystemService(
                        android.view.inputmethod.InputMethodManager::class.java
                    )

                manager?.showInputMethodPicker()
            } catch (_: Exception) {
            }
        }

        addButton(
            root,
            "Tema ve görünüm"
        ) {
            startActivity(
                Intent(
                    this,
                    ThemeActivity::class.java
                )
            )
        }

        setContentView(root)
    }

    private fun addButton(
        root: LinearLayout,
        text: String,
        action: () -> Unit
    ) {
        val button = android.widget.Button(this).apply {
            this.text = text
            textSize = 15f
            isAllCaps = false
            setTextColor(
                StellarTheme.textColor
            )
            setOnClickListener {
                action()
            }
        }

        root.addView(
            button,
            LinearLayout.LayoutParams(
                -1,
                dp(54)
            ).apply {
                setMargins(
                    0,
                    dp(6),
                    0,
                    dp(6)
                )
            }
        )
    }

    private fun dp(value: Int): Int {
        return (
            value *
                resources.displayMetrics.density
            ).toInt()
    }
}
