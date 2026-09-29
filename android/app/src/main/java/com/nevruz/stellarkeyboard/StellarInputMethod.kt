package com.nevruz.stellarkeyboard

import android.graphics.Color
import android.inputmethodservice.InputMethodService
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.LinearLayout

class StellarInputMethod : InputMethodService() {

    private lateinit var keyboard: LinearLayout

    private lateinit var modeManager: KeyboardModeManager

    private lateinit var emojiPanel: StellarEmojiPanel

    private val state = StellarKeyboardState()

    private var emojiMode = false

    override fun onCreate() {
        super.onCreate()

        modeManager = KeyboardModeManager(this)

        KeyboardLayout.currentType =
            StellarPreferences(this).getLayout()

        applyKeyboardMode(
            modeManager.getMode()
        )
    }

    override fun onCreateInputView(): View {
        keyboard = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setBackgroundColor(
                StellarTheme.backgroundColor
            )
        }

        render()

        return keyboard
    }

    private fun render() {
        keyboard.removeAllViews()

        if (emojiMode) {
            emojiPanel = StellarEmojiPanel(
                this,
                onEmojiSelected = { emoji ->
                    currentInputConnection?.commitText(
                        emoji,
                        1
                    )
                },
                onBackToKeyboard = {
                    emojiMode = false
                    render()
                }
            )

            keyboard.addView(
                emojiPanel.create(),
                LinearLayout.LayoutParams(
                    -1,
                    -1
                )
            )

            return
        }

        addTopBar()
        addKeyboardRows()
    }

    private fun addTopBar() {
        val row = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER
            setPadding(
                dp(4),
                dp(4),
                dp(4),
                dp(4)
            )
        }

        val modeButton = KeyboardModeButton(
            this,
            modeManager
        ) { mode ->
            applyKeyboardMode(mode)
            render()
        }

        row.addView(
            modeButton,
            LinearLayout.LayoutParams(
                dp(50),
                dp(42)
            )
        )

        val emojiButton =
            android.widget.Button(this).apply {
                text = "😊"
                textSize = 20f
                isAllCaps = false

                setOnClickListener {
                    emojiMode = true
                    render()
                }
            }

        row.addView(
            emojiButton,
            LinearLayout.LayoutParams(
                dp(50),
                dp(42)
            )
        )

        val layoutButton =
            android.widget.Button(this).apply {
                text =
                    if (
                        KeyboardLayout.currentType ==
                        KeyboardLayout.Type.TURKISH_Q
                    ) {
                        "Q"
                    } else {
                        "F"
                    }

                isAllCaps = false

                setOnClickListener {
                    KeyboardLayout.toggle()

                    StellarPreferences(this@StellarInputMethod)
                        .setLayout(
                            KeyboardLayout.currentType
                        )

                    render()
                }
            }

        row.addView(
            layoutButton,
            LinearLayout.LayoutParams(
                dp(50),
                dp(42)
            )
        )

        val title =
            android.widget.TextView(this).apply {
                text = "Stellar"
                textSize = 14f
                gravity = Gravity.CENTER
                setTextColor(
                    StellarTheme.textColor
                )
            }

        row.addView(
            title,
            LinearLayout.LayoutParams(
                0,
                dp(42),
                1f
            )
        )

        keyboard.addView(
            row,
            LinearLayout.LayoutParams(
                -1,
                dp(48)
            )
        )
    }

    private fun addKeyboardRows() {
        KeyboardLayout.rows().forEach { row ->
            val layoutRow = LinearLayout(this).apply {
                orientation = LinearLayout.HORIZONTAL
                gravity = Gravity.CENTER
            }

            row.forEach { key ->
                val button = StellarGlassKey(
                    this,
                    key
                ) {
                    commit(
                        key
                    )
                }

                layoutRow.addView(
                    button,
                    LinearLayout.LayoutParams(
                        0,
                        dp(50),
                        1f
                    ).apply {
                        setMargins(
                            dp(2),
                            dp(3),
                            dp(2),
                            dp(3)
                        )
                    }
                )
            }

            keyboard.addView(
                layoutRow,
                LinearLayout.LayoutParams(
                    keyboardWidth(),
                    dp(56)
                )
            )
        }

        addBottomRow()
    }

    private fun addBottomRow() {
        val row = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER
        }

        addBottomButton(
            row,
            "⇧",
            1f
        ) {
            state.toggleShift()
        }

        addBottomButton(
            row,
            "⌫",
            1f
        ) {
            currentInputConnection
                ?.deleteSurroundingText(1, 0)
        }

        addBottomButton(
            row,
            ",",
            0.8f
        ) {
            commit(",")
        }

        addBottomButton(
            row,
            "Boşluk",
            4f
        ) {
            commit(" ")
        }

        addBottomButton(
            row,
            ".",
            0.8f
        ) {
            commit(".")
        }

        addBottomButton(
            row,
            "↵",
            1.2f
        ) {
            handleEnter()
        }

        keyboard.addView(
            row,
            LinearLayout.LayoutParams(
                keyboardWidth(),
                dp(56)
            )
        )
    }

    private fun addBottomButton(
        row: LinearLayout,
        text: String,
        weight: Float,
        action: () -> Unit
    ) {
        val button = StellarGlassKey(
            this,
            text,
            action
        )

        row.addView(
            button,
            LinearLayout.LayoutParams(
                0,
                dp(50),
                weight
            ).apply {
                setMargins(
                    dp(2),
                    dp(3),
                    dp(2),
                    dp(3)
                )
            }
        )
    }

    private fun commit(text: String) {
        val output =
            if (
                state.shifted ||
                state.capsLock
            ) {
                text.uppercase()
            } else {
                text
            }

        currentInputConnection?.commitText(
            output,
            1
        )

        state.afterCharacterTyped()
    }

    private fun handleEnter() {
        val connection =
            currentInputConnection ?: return

        val action =
            currentInputEditorInfo
                ?.imeOptions
                ?.and(
                    EditorInfo.IME_MASK_ACTION
                )
                ?: EditorInfo.IME_ACTION_NONE

        if (action != EditorInfo.IME_ACTION_NONE) {
            connection.performEditorAction(action)
        } else {
            connection.commitText(
                "\n",
                1
            )
        }
    }

    private fun applyKeyboardMode(
        mode: KeyboardMode
    ) {
        val window = window.window ?: return

        when (mode) {
            KeyboardMode.FULL -> {
                window.setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT
                )
            }

            KeyboardMode.ONE_HANDED_LEFT,
            KeyboardMode.ONE_HANDED_RIGHT -> {
                window.setLayout(
                    dp(340),
                    WindowManager.LayoutParams.WRAP_CONTENT
                )

                window.attributes =
                    window.attributes.apply {
                        gravity =
                            if (
                                mode ==
                                KeyboardMode.ONE_HANDED_LEFT
                            ) {
                                Gravity.BOTTOM or
                                    Gravity.START
                            } else {
                                Gravity.BOTTOM or
                                    Gravity.END
                            }
                    }
            }

            KeyboardMode.FLOATING -> {
                window.setLayout(
                    dp(360),
                    dp(300)
                )

                window.attributes =
                    window.attributes.apply {
                        gravity =
                            Gravity.CENTER
                    }
            }
        }
    }

    private fun keyboardWidth(): Int {
        return when (modeManager.getMode()) {
            KeyboardMode.FULL ->
                WindowManager.LayoutParams.MATCH_PARENT

            KeyboardMode.ONE_HANDED_LEFT,
            KeyboardMode.ONE_HANDED_RIGHT ->
                dp(340)

            KeyboardMode.FLOATING ->
                dp(360)
        }
    }

    override fun onStartInput(
        attribute: EditorInfo?,
        restarting: Boolean
    ) {
        super.onStartInput(
            attribute,
            restarting
        )

        state.reset()
        emojiMode = false
    }

    private fun dp(value: Int): Int {
        return (
            value *
                resources.displayMetrics.density
            ).toInt()
    }
}
