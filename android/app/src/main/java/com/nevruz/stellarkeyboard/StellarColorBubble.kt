package com.nevruz.stellarkeyboard

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.widget.TextView

class StellarColorBubble(
    context: Context,
    private val bubbleColor: Int,
    private val action: () -> Unit
) : TextView(context) {

    init {
        gravity = Gravity.CENTER
        text = ""

        background = createBackground()

        setOnClickListener {
            animate()
                .scaleX(0.88f)
                .scaleY(0.88f)
                .setDuration(70)
                .withEndAction {
                    animate()
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(90)
                        .start()
                }
                .start()

            action()
        }
    }

    private fun createBackground():
        GradientDrawable {

        return GradientDrawable().apply {
            shape = GradientDrawable.OVAL
            setColor(bubbleColor)

            setStroke(
                dp(2),
                Color.argb(
                    110,
                    255,
                    255,
                    255
                )
            )
        }
    }

    private fun dp(value: Int): Int {
        return (
            value *
                resources.displayMetrics.density
            ).toInt()
    }
}
