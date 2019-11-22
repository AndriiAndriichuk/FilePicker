package com.ciuc.andrii.filepicker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.ciuc.andrii.file_picker.CHOSEN_FILES
import com.ciuc.andrii.file_picker.FilePickerActivity
import com.ciuc.andrii.file_picker.FilePickerIntent
import com.ciuc.andrii.file_picker.ToolbarPosition
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val FILE_REQUEST_CODE = 7896

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnPickFile.setOnClickListener {

            val intent =
                FilePickerIntent(this)
                    .Builder()
                    .withStyle(ToolbarPosition.TOP)
                    /*       .withTextStyleName(R.style.text_style)
                           .withTextStyleDate(R.style.text_style_date)
                           .withTextStyleSize(R.style.text_style_size)*/
                    .withSimpleDateFormat("MM/dd/ kk:mm")
                    .withObjectsWord("bobjects")
                    /*       .withListItemStyle(R.style.card_style)
                           .withListItemSelectedStyle(R.style.card_selected_style)
                           .withCustomLayoutList(R.layout.recycler_view_item2)
                           .withCustomLayoutGrid(R.layout.recycler_view_item_grid)
                           .withBackgroundStyle(R.drawable.background_white)
                           .withBackgroundToolbar(R.drawable.background_violet)
                           .withListImage(R.drawable.ic_list2)
                           .withGridImage(R.drawable.ic_grid2)
                           .withDoneImage(R.drawable.ic_done2)
                           .withChipStyle(R.style.Widget_MaterialComponents_Chip_Action)
                           .withTextCountStyle(R.style.text_count_style)*/
                    .withGridCount(3)
                    .withExtension("jpg", "gif", "mp4", "pdf", "txt")
                    .withStorageWord("Phone storage")
                    .build()

            startActivityForResult(intent, FILE_REQUEST_CODE)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == FILE_REQUEST_CODE) {
            if (data != null) {
                val ar1 = data.extras!!.getStringArrayList(CHOSEN_FILES)
                Log.d("sbsbssgesgewhgew", ar1.toString())
            }


        }
    }
}
