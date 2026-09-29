package com.nevruz.stellarkeyboard

import android.graphics.Color
import android.inputmethodservice.InputMethodService
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.view.inputmethod.EditorInfo

class StellarInputMethod : InputMethodService() {

    private lateinit var keyboard: LinearLayout

    private lateinit var suggestionView: StellarSuggestionView

    private lateinit var suggestionContainer: LinearLayout

    private val state = StellarKeyboardState()

    private val suggestionEngine = SuggestionEngine()

    private val textProcessor = StellarTextProcessor()

    private lateinit var preferences: StellarPreferences

    override fun onCreate() {
        super.onCreate()

        preferences = StellarPreferences(this)

        KeyboardLayout.currentType =
            preferences.getLayout()

        StellarTheme.mode =
            if (preferences.isDarkTheme()) {
                StellarTheme.Mode.DARK
            } else {
                StellarTheme.Mode.LIGHT
            }

        StellarTheme.accentColor =
            preferences.getAccentColor()
    }

    override fun onCreateInputView(): View {
        keyboard = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(
                dp(5),
                dp(7),
                dp(5),
                dp(7)
            )
            setBackgroundColor(
                StellarTheme.backgroundColor
            )
        }

        createSuggestionBar()
        renderKeyboard()

        return keyboard
    }

    private fun createSuggestionBar() {
        suggestionView = StellarSuggestionView { word ->
            replaceCurrentWord(word)
        }

        suggestionContainer =
            suggestionView.create(this)

        keyboard.addView(
            suggestionContainer,
            LinearLayout.LayoutParams(
                -1,
                dp(42)
            )
        )
    }

    private fun renderKeyboard() {
        if (!::keyboard.isInitialized) return

        while (keyboard.childCount > 1) {
            keyboard.removeViewAt(1)
        }

        addLayoutRow()

        if (state.symbols) {
            addRow(
                listOf(
                    "1", "2", "3", "4", "5",
                    "6", "7", "8", "9", "0"
                )
            )

            addRow(
                listOf(
                    "@", "#", "₺", "%", "&",
                    "-", "+", "(", ")", "/"
                )
            )

            addRow(
                listOf(
                    "*", "\"", "'", ":",
                    ";", "!", "?", "=", "_"
                )
            )
        } else {
            addRow(
                listOf(
                    "1", "2", "3", "4", "5",
                    "6", "7", "8", "9", "0"
                )
            )

            KeyboardLayout.rows().forEachIndexed { index, row ->

                val keys = when (index) {
                    0 -> row

                    1 -> row

                    else -> listOf(
                        if (state.shifted) "⇧" else "⇧"
                    ) + row + listOf("⌫")
                }

                addRow(keys)
            }
        }

        addBottomRow()

        updateSuggestions()
    }

    private fun addLayoutRow() {
        val row = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER
        }

        val layoutName =
            if (
                KeyboardLayout.currentType ==
                KeyboardLayout.Type.TURKISH_Q
            ) {
                "Q"
            } else {
                "F"
            }

        val layoutButton = createKey(
            layoutName,
            1f
        ) {
            KeyboardLayout.toggle()

            preferences.setLayout(
                KeyboardLayout.currentType
            )

            renderKeyboard()
        }

        row.addView(layoutButton)

        val title = TextView(this).apply {
            text = "Stellar Klavye"
            textSize = 14f
            gravity = Gravity.CENTER
            setTextColor(
                StellarTheme.secondaryTextColor
            )
        }

        row.addView(
            title,
            LinearLayout.LayoutParams(
                0,
                dp(40),
                4f
            )
        )

        val symbolButton = createKey(
            "123",
            1f
        ) {
            state.symbols = !state.symbols
            renderKeyboard()
        }

        row.addView(symbolButton)

        keyboard.addView(row)
    }

    private fun addRow(keys: List<String>) {
        val row = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER
        }

        keys.forEach { key ->
            row.addView(
                createKey(
                    key,
                    1f
                ) {
                    handleKey(key)
                }
            )
        }

        keyboard.addView(
            row,
            LinearLayout.LayoutParams(
                -1,
                dp(51)
            )
        )
    }

    private fun addBottomRow() {
        val row = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER
        }

        addBottomKey(row, "?123", 1f) {
            state.symbols = !state.symbols
            renderKeyboard()
        }

        addBottomKey(row, ",", 0.7f) {
            commit(",")
            updateSuggestions()
        }

        addBottomKey(row, "Boşluk", 3.8f) {
            commitWithCorrection(" ")
        }

        addBottomKey(row, ".", 0.7f) {
            commit(".")
            updateSuggestions()
        }

        addBottomKey(row, "↵", 1.2f) {
            handleEnter()
        }

        keyboard.addView(
            row,
            LinearLayout.LayoutParams(
                -1,
                dp(53)
            )
        )
    }

    private fun addBottomKey(
        row: LinearLayout,
        text: String,
        weight: Float,
        action: () -> Unit
    ) {
        row.addView(
            createKey(
                text,
                weight,
                action
            )
        )
    }

    private fun createKey(
        text: String,
        weight: Float,
        action: () -> Unit
    ): View {
        val key = StellarGlassKey(
            this,
            text,
            action
        )

        return key.apply {
            layoutParams = LinearLayout.LayoutParams(
                0,
                dp(48),
                weight
            ).apply {
                setMargins(
                    dp(2),
                    dp(3),
                    dp(2),
                    dp(3)
                )
            }
        }
    }

    private fun handleKey(key: String) {
        when (key) {
            "⇧" -> {
                if (state.shifted) {
                    state.enableCapsLock()
                } else {
                    state.toggleShift()
                }

                renderKeyboard()
            }

            "⌫" -> {
                currentInputConnection
                    ?.deleteSurroundingText(1, 0)

                updateSuggestions()
            }

            else -> {
                val output =
                    if (state.shifted || state.capsLock) {
                        key.uppercase()
                    } else {
                        key
                    }

                commit(output)

                state.afterCharacterTyped()

                updateSuggestions()

                if (!state.capsLock) {
                    renderKeyboard()
                }
            }
        }
    }

    private fun commitWithCorrection(
        separator: String
    ) {
        val connection = currentInputConnection
            ?: return

        val before = connection.getTextBeforeCursor(
            100,
            0
        )?.toString() ?: ""

        val corrected =
            textProcessor.correctBeforeSpace(before)

        if (corrected != null) {
            val currentWord =
                StellarWordExtractor.currentWord(before)

            connection.deleteSurroundingText(
                currentWord.length,
                0
            )

            connection.commitText(
                corrected,
                1
            )
        }

        connection.commitText(
            separator,
            1
        )

        updateSuggestions()
    }

    private fun replaceCurrentWord(
        replacement: String
    ) {
        val connection = currentInputConnection
            ?: return

        val before = connection.getTextBeforeCursor(
            100,
            0
        )?.toString() ?: ""

        val currentWord =
            StellarWordExtractor.currentWord(before)

        if (currentWord.isEmpty()) {
            return
        }

        connection.deleteSurroundingText(
            currentWord.length,
            0
        )

        connection.commitText(
            replacement,
            1
        )

        updateSuggestions()
    }

    private fun updateSuggestions() {
        if (!::suggestionView.isInitialized) {
            return
        }

        val before =
            currentInputConnection
                ?.getTextBeforeCursor(100, 0)
                ?.toString()
                ?: ""

        val currentWord =
            StellarWordExtractor.currentWord(before)

        val suggestions =
            suggestionEngine.suggest(currentWord)

        suggestionView.update(
            this,
            suggestions
        )
    }

    private fun handleEnter() {
        val connection =
            currentInputConnection ?: return

        val info = currentInputEditorInfo

        val action =
            info?.imeOptions?.and(
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
                connection.commitText(
                    "\n",
                    1
                )
            }
        }

        updateSuggestions()
    }

    private fun commit(text: String) {
        currentInputConnection?.commitText(
            text,
            1
        )
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

        if (::keyboard.isInitialized) {
            renderKeyboard()
        }
    }

    private fun dp(value: Int): Int {
        return (
            value *
                resources.displayMetrics.density
            ).toInt()
    }
}
