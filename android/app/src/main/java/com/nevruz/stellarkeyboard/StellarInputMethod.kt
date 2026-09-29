package com.nevruz.stellarkeyboard

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.inputmethodservice.InputMethodService
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class StellarInputMethod : InputMethodService() {

    private lateinit var keyboard: LinearLayout
    private var shifted = false
    private var symbols = false

    private val backgroundColor = Color.rgb(24, 28, 40)
    private val keyColor = Color.rgb(53, 60, 80)
    private val accentColor = Color.rgb(105, 160, 255)

    override fun onCreateInputView(): View {
        keyboard = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(4), dp(8), dp(4), dp(8))
            setBackgroundColor(backgroundColor)
        }

        renderKeyboard()
        return keyboard
    }

    private fun renderKeyboard() {
        keyboard.removeAllViews()

        addSuggestionRow()

        if (symbols) {
            addRow(
                listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "0")
            )
            addRow(
                listOf("@", "#", "$", "%", "&", "-", "+", "(", ")", "/")
            )
            addRow(
                listOf("*", "\"", "'", ":", ";", "!", "?", "₺")
            )
        } else {
            addRow(
                listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "0")
            )

            KeyboardLayout.rows().forEachIndexed { index, row ->
                val keys = if (index == 2) {
                    listOf("⇧") + row + listOf("⌫")
                } else {
                    row
                }

                addRow(keys)
            }
        }

        addBottomRow()
    }

    private fun addSuggestionRow() {
        val row = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER
        }

        val layoutButton = makeKey(
            if (KeyboardLayout.currentType ==
                KeyboardLayout.Type.TURKISH_Q
            ) "Q" else "F"
        ) {
            KeyboardLayout.toggle()
            renderKeyboard()
        }

        row.addView(
            layoutButton,
            LinearLayout.LayoutParams(0, dp(40), 1f)
        )

        val title = TextView(this).apply {
            text = "Stellar"
            textSize = 14f
            gravity = Gravity.CENTER
            setTextColor(Color.WHITE)
            typeface = Typeface.DEFAULT_BOLD
        }

        row.addView(
            title,
            LinearLayout.LayoutParams(0, dp(40), 4f)
        )

        val symbolButton = makeKey("123") {
            symbols = !symbols
            renderKeyboard()
        }

        row.addView(
            symbolButton,
            LinearLayout.LayoutParams(0, dp(40), 1f)
        )

        keyboard.addView(row)
    }

    private fun addRow(keys: List<String>) {
        val row = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER
        }

        keys.forEach { key ->
            val button = makeKey(key) {
                handleKey(key)
            }

            row.addView(
                button,
                LinearLayout.LayoutParams(
                    0,
                    dp(48),
                    1f
                ).apply {
                    setMargins(dp(2), dp(3), dp(2), dp(3))
                }
            )
        }

        keyboard.addView(row)
    }

    private fun addBottomRow() {
        val row = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER
        }

        fun add(
            text: String,
            weight: Float,
            action: () -> Unit
        ) {
            row.addView(
                makeKey(text, action),
                LinearLayout.LayoutParams(
                    0,
                    dp(48),
                    weight
                ).apply {
                    setMargins(dp(2), dp(3), dp(2), dp(3))
                }
            )
        }

        add("?123", 1f) {
            symbols = !symbols
            renderKeyboard()
        }

        add(",", 0.7f) {
            commit(",")
        }

        add("Türkçe", 4f) {
            commit(" ")
        }

        add(".", 0.7f) {
            commit(".")
        }

        add("↵", 1.2f) {
            handleEnter()
        }

        keyboard.addView(row)
    }

    private fun makeKey(
        text: String,
        action: () -> Unit
    ): Button {
        return Button(this).apply {
            this.text = text
            textSize = 16f
            isAllCaps = false
            setTextColor(Color.WHITE)
            typeface = Typeface.DEFAULT_BOLD
            background = roundedBackground(
                if (text == "↵" || text == "⇧") {
                    accentColor
                } else {
                    keyColor
                }
            )

            setPadding(0, 0, 0, 0)
            minWidth = 0
            minimumWidth = 0
            minHeight = 0
            minimumHeight = 0
            stateListAnimator = null

            setOnClickListener {
                action()
            }
        }
    }

    private fun handleKey(key: String) {
        when (key) {
            "⇧" -> {
                shifted = !shifted
                renderKeyboard()
            }

            "⌫" -> {
                val connection = currentInputConnection
                connection?.deleteSurroundingText(1, 0)
            }

            else -> {
                val output = if (shifted) {
                    key.uppercase()
                } else {
                    key
                }

                commit(output)

                if (shifted) {
                    shifted = false
                    renderKeyboard()
                }
            }
        }
    }

    private fun handleEnter() {
        val connection = currentInputConnection ?: return
        val info = currentInputEditorInfo

        val action = info?.imeOptions?.and(
            EditorInfo.IME_MASK_ACTION
        ) ?: EditorInfo.IME_ACTION_NONE

        when (action) {
            EditorInfo.IME_ACTION_GO,
            EditorInfo.IME_ACTION_SEARCH,
            EditorInfo.IME_ACTION_SEND,
            EditorInfo.IME_ACTION_NEXT,
            EditorInfo.IME_ACTION_DONE -> {
                connection.performEditorAction(action)
            }

            else -> {
                connection.commitText("\n", 1)
            }
        }
    }

    private fun commit(text: String) {
        currentInputConnection?.commitText(text, 1)
    }

    private fun roundedBackground(color: Int) =
        GradientDrawable().apply {
            setColor(color)
            cornerRadius = dp(12).toFloat()
        }

    private fun dp(value: Int): Int {
        return (value * resources.displayMetrics.density).toInt()
    }
}
