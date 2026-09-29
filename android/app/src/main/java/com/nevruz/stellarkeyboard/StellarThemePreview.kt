package com.nevruz.stellarkeyboard

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView

class StellarThemePreview(
    context: Context
) : LinearLayout(context) {

    init {
        orientation = HORIZONTAL
        gravity = Gravity.CENTER
        setPadding(
            dp(8),
            dp(8),
            dp(8),
            dp(8)
        )

        setBackground(
            GradientDrawable().apply {
                setColor(
                    StellarTheme.keyColor
                )
                cornerRadius =
                    dp(20).toFloat()

                setStroke(
                    dp(1),
                    Color.argb(
                        60,
                        255,
                        255,
                        255
                    )
                )
            }
        )

        val text = TextView(context).apply {
            this.text =
                "a   s   d   f   g   h   j   k   l"
            textSize = 15f
            gravity = Gravity.CENTER
            setTextColor(
                StellarTheme.textColor
            )
        }

        addView(
            text,
            LayoutParams(
                -1,
                dp(46)
            )
        )
    }

    private fun dp(value: Int): Int {
        return (
            value *
                resources.displayMetrics.density
            ).toInt()
    }
}
