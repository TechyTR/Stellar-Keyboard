package com.nevruz.stellarkeyboard

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View

object StellarKeyBackground {

    fun create(
        color: Int,
        radius: Float = 16f
    ): GradientDrawable {
        return GradientDrawable().apply {
            setColor(color)
            cornerRadius = radius
        }
    }

    fun pressed(
        view: View,
        normalColor: Int,
        pressedColor: Int
    ) {
        view.setOnTouchListener { touchedView, event ->
            when (event.action) {
                android.view.MotionEvent.ACTION_DOWN -> {
                    touchedView.scaleX = 0.94f
                    touchedView.scaleY = 0.94f

                    touchedView.background =
                        create(pressedColor)

                    true
                }

                android.view.MotionEvent.ACTION_UP,
                android.view.MotionEvent.ACTION_CANCEL -> {
                    touchedView.scaleX = 1f
                    touchedView.scaleY = 1f

                    touchedView.background =
                        create(normalColor)

                    if (event.action ==
                        android.view.MotionEvent.ACTION_UP
                    ) {
                        touchedView.performClick()
                    }

                    true
                }

                else -> true
            }
        }
    }
}
