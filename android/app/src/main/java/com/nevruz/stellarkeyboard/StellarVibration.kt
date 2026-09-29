package com.nevruz.stellarkeyboard

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager

object StellarVibration {

    fun tap(context: Context) {
        val vibrator = if (Build.VERSION.SDK_INT >= 31) {
            val manager = context.getSystemService(
                VibratorManager::class.java
            )
            manager?.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            context.getSystemService(
                Context.VIBRATOR_SERVICE
            ) as? Vibrator
        }

        if (vibrator == null || !vibrator.hasVibrator()) {
            return
        }

        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(
                VibrationEffect.createOneShot(
                    12L,
                    VibrationEffect.DEFAULT_AMPLITUDE
                )
            )
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(12L)
        }
    }
}
