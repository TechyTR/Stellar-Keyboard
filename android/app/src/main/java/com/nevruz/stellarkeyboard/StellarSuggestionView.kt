package com.nevruz.stellarkeyboard

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView

class StellarSuggestionView(
    private val onSuggestionSelected: (String) -> Unit
) {

    private lateinit var container: LinearLayout

    fun create(parent: android.content.Context): LinearLayout {
        container = LinearLayout(parent).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER
            setPadding(dp(parent, 6), dp(parent, 4), dp(parent, 6), dp(parent, 4))
            setBackgroundColor(Color.TRANSPARENT)
        }

        return container
    }

    fun update(
        context: android.content.Context,
        suggestions: List<String>
    ) {
        if (!::container.isInitialized) return

        container.removeAllViews()

        val values = suggestions.take(3)

        if (values.isEmpty()) {
            addItem(context, "Stellar Klavye", false) {}
            return
        }

        values.forEach { suggestion ->
            addItem(context, suggestion, true) {
                onSuggestionSelected(suggestion)
            }
        }
    }

    private fun addItem(
        context: android.content.Context,
        text: String,
        clickable: Boolean,
        action: () -> Unit
    ) {
        val view = TextView(context).apply {
            this.text = text
            textSize = 14f
            gravity = Gravity.CENTER
            typeface = Typeface.DEFAULT_BOLD
            setTextColor(StellarTheme.textColor)
            setPadding(
                dp(context, 12),
                dp(context, 6),
                dp(context, 12),
                dp(context, 6)
            )

            background = GradientDrawable().apply {
                setColor(StellarTheme.keyColor)
                cornerRadius = dp(context, 14).toFloat()
            }

            alpha = if (clickable) 1f else 0.65f

            if (clickable) {
                setOnClickListener {
                    action()
                }
            }
        }

        container.addView(
            view,
            LinearLayout.LayoutParams(
                0,
                dp(context, 38),
                1f
            ).apply {
                setMargins(
                    dp(context, 3),
                    0,
                    dp(context, 3),
                    0
                )
            }
        )
    }

    private fun dp(
        context: android.content.Context,
        value: Int
    ): Int {
        return (
            value * context.resources.displayMetrics.density
        ).toInt()
    }
}
