package com.nevruz.stellarkeyboard

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.MotionEvent
import android.widget.Button

class StellarGlassKey(
    context: Context,
    text: String,
    private val action: () -> Unit
) : Button(context) {

    private val normalColor = StellarTheme.keyColor

    private val pressedColor = Color.argb(
        235,
        Color.red(StellarTheme.accentColor),
        Color.green(StellarTheme.accentColor),
        Color.blue(StellarTheme.accentColor)
    )

    init {
        this.text = text
        textSize = 16f
        gravity = Gravity.CENTER
        isAllCaps = false
        minWidth = 0
        minimumWidth = 0
        minHeight = 0
        minimumHeight = 0
        setPadding(0, 0, 0, 0)
        setTextColor(StellarTheme.textColor)
        typeface = Typeface.DEFAULT_BOLD
        stateListAnimator = null

        background = background(normalColor)

        setOnTouchListener { view, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    view.animate()
                        .scaleX(0.93f)
                        .scaleY(0.93f)
                        .setDuration(55)
                        .start()

                    view.background = background(pressedColor)

                    StellarVibration.tap(context)

                    true
                }

                MotionEvent.ACTION_UP -> {
                    view.animate()
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(75)
                        .start()

                    view.background = background(normalColor)

                    performClick()

                    true
                }

                MotionEvent.ACTION_CANCEL -> {
                    view.animate()
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(75)
                        .start()

                    view.background = background(normalColor)

                    true
                }

                else -> true
            }
        }
    }

    override fun performClick(): Boolean {
        super.performClick()
        action()
        return true
    }

    private fun background(color: Int): GradientDrawable {
        return GradientDrawable().apply {
            setColor(color)
            cornerRadius = 16f * resources.displayMetrics.density
            setStroke(
                (0.7f * resources.displayMetrics.density).toInt().coerceAtLeast(1),
                Color.argb(45, 255, 255, 255)
            )
        }
    }
}
