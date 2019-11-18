package com.ciuc.andrii.filepicker

import android.content.Context
import android.content.Intent


class FilePickerIntent(val packageContext: Context, val cls: Class<*>) : Intent(packageContext, cls) {

    var toolbarStyle: ToolbarStyle = ToolbarStyle.TOP

    init {
        this.putExtra(STYLE, toolbarStyle)
    }

    var customExtensions: Array<String> = arrayOf()

    init {
        this.putExtra(ARRAY_EXTENSION, arrayOf<String>())
    }

    var textStyleName: Int = R.style.Widget_AppCompat_TextView

    init {
        this.putExtra(TEXT_STYLE_NAME, R.style.Widget_AppCompat_TextView)
    }

    var textStyleDate: Int = R.style.Widget_AppCompat_TextView

    init {
        this.putExtra(TEXT_STYLE_NAME, R.style.Widget_AppCompat_TextView)
    }

    var textStyleSize: Int = R.style.Widget_AppCompat_TextView

    init {
        this.putExtra(TEXT_STYLE_SIZE, R.style.Widget_AppCompat_TextView)
    }

    var simpleDateFormat: String = "MM/dd/yyyy kk:mm"

    init {
        this.putExtra(SIMPLE_DATE_FORMAT, "MM/dd/yyyy kk:mm")
    }

    var objectWord: String = "objects"

    init {
        this.putExtra(OBJECT_WORD, "objects")
    }

    var listItemStyle: Int = R.style.Widget_MaterialComponents_CardView

    init {
        this.putExtra(LIST_ITEM_STYLE, R.style.Widget_MaterialComponents_CardView)
    }

    var listItemSelectedStyle: Int = R.style.Widget_MaterialComponents_CardView

    init {
        this.putExtra(LIST_ITEM_SELECTED_STYLE, R.style.Widget_MaterialComponents_CardView)
    }

    var customLayoutList: Int = R.layout.recycler_view_item

    init {
        this.putExtra(CUSTOM_LAYOUT_LIST, R.layout.recycler_view_item)
    }

    var customLayoutGrid: Int = R.layout.recycler_view_item_grid

    init {
        this.putExtra(CUSTOM_LAYOUT_GRID, R.layout.recycler_view_item_grid)
    }

    var customBackgroundStyle: Int = android.R.color.white

    init {
        this.putExtra(ROOT_BACKGROUND, android.R.color.white)
    }

    var customBackgroundToolbarStyle: Int = android.R.color.white

    init {
        this.putExtra(TOOLBAR_BACKGROUND, android.R.color.white)
    }

    var customListImage: Int = R.drawable.ic_list

    init {
        this.putExtra(LIST_IMAGE, R.drawable.ic_list)
    }

    var customGridImage: Int = R.drawable.ic_grid

    init {
        this.putExtra(GRID_IMAGE, R.drawable.ic_grid)
    }

    var customDoneImage: Int = R.drawable.ic_done

    init {
        this.putExtra(DONE_IMAGE, R.drawable.ic_done)
    }

    var customTextCountStyle: Int = R.style.Widget_AppCompat_TextView

    init {
        this.putExtra(TEXT_COUNT_STYLE, R.style.Widget_AppCompat_TextView)
    }

    var customGridCount: Int = 3

    init {
        this.putExtra(GRID_COUNT, 3)
    }


    var customChipStyle: Int = R.style.Widget_MaterialComponents_Chip_Action

    init {
        this.putExtra(CUSTOM_CHIP_STYLE, R.style.Widget_MaterialComponents_Chip_Action)
    }

    var storageWord: String = "Phone"

    init {
        this.putExtra(STORAGE_WORD, "Phone")
    }


    inner class Builder {
        private val intent: FilePickerIntent = FilePickerIntent(packageContext, cls)

        fun withStyle(number: ToolbarStyle): Builder {
            intent.putExtra(STYLE, number.styleID)
            intent.toolbarStyle = number
            return this
        }


        fun withExtension(vararg arguments: String): Builder {
            intent.putExtra(ARRAY_EXTENSION, arguments)
            return this
        }

        fun withTextStyleName(style: Int): Builder {
            intent.putExtra(TEXT_STYLE_NAME, style)
            intent.textStyleName = style
            return this
        }

        fun withTextStyleDate(style: Int): Builder {
            intent.putExtra(TEXT_STYLE_DATE, style)
            intent.textStyleDate = style
            return this
        }

        fun withTextStyleSize(style: Int): Builder {
            intent.putExtra(TEXT_STYLE_SIZE, style)
            intent.textStyleSize = style
            return this
        }

        fun withSimpleDateFormat(dateFormat: String): Builder {
            intent.putExtra(SIMPLE_DATE_FORMAT, dateFormat)
            intent.simpleDateFormat = dateFormat
            return this
        }

        fun withObjectsWord(word: String): Builder {
            intent.putExtra(OBJECT_WORD, word)
            intent.objectWord = word
            return this
        }

        fun withListItemStyle(style: Int): Builder {
            intent.putExtra(LIST_ITEM_STYLE, style)
            intent.listItemStyle = style
            return this
        }

        fun withListItemSelectedStyle(style: Int): Builder {
            intent.putExtra(LIST_ITEM_SELECTED_STYLE, style)
            intent.listItemSelectedStyle = style
            return this
        }

        fun withCustomLayoutList(layout: Int): Builder {
            intent.putExtra(CUSTOM_LAYOUT_LIST, layout)
            intent.customLayoutList = layout
            return this
        }

        fun withCustomLayoutGrid(layout: Int): Builder {
            intent.putExtra(CUSTOM_LAYOUT_GRID, layout)
            intent.customLayoutGrid = layout
            return this
        }

        fun withBackgroundStyle(background: Int): Builder {
            intent.putExtra(ROOT_BACKGROUND, background)
            customBackgroundStyle = background
            return this
        }

        fun withBackgroundToolbar(background: Int): Builder {
            intent.putExtra(TOOLBAR_BACKGROUND, background)
            customBackgroundToolbarStyle = background
            return this
        }

        fun withListImage(image: Int): Builder {
            intent.putExtra(LIST_IMAGE, image)
            customListImage = image
            return this
        }

        fun withGridImage(image: Int): Builder {
            intent.putExtra(GRID_IMAGE, image)
            customGridImage = image
            return this
        }

        fun withDoneImage(image: Int): Builder {
            intent.putExtra(DONE_IMAGE, image)
            customDoneImage = image
            return this
        }

        fun withChipStyle(style: Int): Builder {
            intent.putExtra(CUSTOM_CHIP_STYLE, style)
            customChipStyle = style
            return this
        }


        fun withTextCountStyle(style: Int): Builder {
            intent.putExtra(TEXT_COUNT_STYLE, style)
            customTextCountStyle = style
            return this
        }

        fun withGridCount(count: Int): Builder {
            intent.putExtra(GRID_COUNT, count)
            customGridCount = count
            return this
        }

        fun withStorageWord(word: String): Builder {
            intent.putExtra(STORAGE_WORD, word)
            storageWord = word
            return this
        }

        fun build(): FilePickerIntent {
            return intent
        }

    }
}