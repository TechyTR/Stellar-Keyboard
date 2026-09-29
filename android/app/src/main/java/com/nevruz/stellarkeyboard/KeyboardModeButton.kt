package com.nevruz.stellarkeyboard

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.widget.Button
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.TextView

class StellarEmojiPanel(
    private val context: Context,
    private val onEmojiSelected: (String) -> Unit,
    private val onBackToKeyboard: () -> Unit
) {

    private lateinit var root: LinearLayout
    private lateinit var grid: GridLayout

    private var category = EmojiCategory.SMILEYS

    fun create(): LinearLayout {
        root = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(StellarTheme.backgroundColor)
            setPadding(
                dp(6),
                dp(6),
                dp(6),
                dp(6)
            )
        }

        createHeader()
        createGrid()

        return root
    }

    private fun createHeader() {
        val header = LinearLayout(context).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
        }

        addCategoryButton(
            header,
            "😀",
            EmojiCategory.SMILEYS
        )

        addCategoryButton(
            header,
            "❤️",
            EmojiCategory.HEARTS
        )

        addCategoryButton(
            header,
            "🐶",
            EmojiCategory.ANIMALS
        )

        addCategoryButton(
            header,
            "🍕",
            EmojiCategory.FOOD
        )

        val keyboard = Button(context).apply {
            text = "⌨"
            textSize = 20f
            isAllCaps = false
            setOnClickListener {
                onBackToKeyboard()
            }
        }

        header.addView(
            keyboard,
            LinearLayout.LayoutParams(
                0,
                dp(44),
                1f
            )
        )

        root.addView(
            header,
            LinearLayout.LayoutParams(
                -1,
                dp(48)
            )
        )
    }

    private fun addCategoryButton(
        parent: LinearLayout,
        text: String,
        value: EmojiCategory
    ) {
        val button = Button(context).apply {
            this.text = text
            textSize = 20f
            isAllCaps = false
            setOnClickListener {
                category = value
                refresh()
            }
        }

        parent.addView(
            button,
            LinearLayout.LayoutParams(
                0,
                dp(44),
                1f
            )
        )
    }

    private fun createGrid() {
        grid = GridLayout(context).apply {
            columnCount = 8
        }

        root.addView(
            grid,
            LinearLayout.LayoutParams(
                -1,
                0,
                1f
            )
        )

        refresh()
    }

    private fun refresh() {
        if (!::grid.isInitialized) return

        grid.removeAllViews()

        val emojis = when (category) {
            EmojiCategory.SMILEYS -> EmojiData.smileys
            EmojiCategory.HEARTS -> EmojiData.hearts
            EmojiCategory.ANIMALS -> EmojiData.animals
            EmojiCategory.FOOD -> EmojiData.food
        }

        emojis.forEach { emoji ->
            val button = TextView(context).apply {
                text = emoji
                textSize = 27f
                gravity = Gravity.CENTER
                typeface = Typeface.DEFAULT

                setOnClickListener {
                    onEmojiSelected(emoji)
                }
            }

            grid.addView(
                button,
                GridLayout.LayoutParams().apply {
                    width = 0
                    height = dp(52)
                    columnSpec = GridLayout.spec(
                        GridLayout.UNDEFINED,
                        1f
                    )
                }
            )
        }
    }

    private fun dp(value: Int): Int {
        return (
            value *
                context.resources.displayMetrics.density
            ).toInt()
    }
}
