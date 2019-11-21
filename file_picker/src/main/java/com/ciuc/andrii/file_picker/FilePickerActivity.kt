package com.ciuc.andrii.file_picker

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.Settings
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.widget.TextViewCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chootdev.recycleclick.RecycleClick
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.activity_file_picker.*
import kotlinx.android.synthetic.main.activity_file_picker2.*
import java.io.File
import java.util.*
import kotlin.collections.ArrayList


class FilePickerActivity : AppCompatActivity() {

    private var mediaFiles = ArrayList<FilePick>()
    private var currentPath = ""

    private lateinit var adapter: FilesAdapter

    private lateinit var currentRecyclerView: RecyclerView
    private lateinit var currentChosenItems: TextView
    private lateinit var currentImageChosenItems: ImageView
    private lateinit var currentImageChange: ImageView
    private lateinit var currentRootView: ConstraintLayout
    private lateinit var currentToolbar: Toolbar
    private lateinit var currentLinear: LinearLayout
    private lateinit var currentScrollView: HorizontalScrollView

    private var currentImageList: Int = R.drawable.ic_list
    private var currentImageGrid: Int = R.drawable.ic_grid
    private var currentChipStyle: Int = R.style.Widget_MaterialComponents_Chip_Action
    private var currentGridCount: Int = 3

    private var rootPath = ""

    private var mapPathToChip = hashMapOf<String, Chip>()
    private var mapIdToPath = hashMapOf<Int, String>()
    private var listExtensions = arrayOf<String>()
    protected var STORAGE_PERMISSION = 9652
    protected var REQUESET_OPEN_APP_SETTINGS = 9651

    private var storageWord = "Phone"

    val lp = LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val style = intent.getIntExtra(TOOLBAR_POSITION, 1)

        if (style == 1) {
            setContentView(R.layout.activity_file_picker)
            currentRecyclerView = recyclerView
            currentChosenItems = chosenItems
            currentImageChosenItems = imageChosenItems
            currentImageChange = imageChange
            currentRootView = rootView
            currentToolbar = toolbar
            currentLinear = linearChips
            currentScrollView = scroll
        } else if (style == 2) {
            setContentView(R.layout.activity_file_picker2)
            currentRecyclerView = recyclerView2
            currentChosenItems = chosenItems2
            currentImageChosenItems = imageChosenItems2
            currentImageChange = imageChange2
            currentRootView = rootView2
            currentToolbar = toolbar2
            currentLinear = linearChips2
            currentScrollView = scroll2
        }

        supportActionBar?.hide()


        setCustomStylesFromExtra()


        val gridLayoutManager =
            GridLayoutManager(this@FilePickerActivity, currentGridCount)

        val linearLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        currentRecyclerView.layoutManager = linearLayoutManager



        lp.setMargins(10, 0, 10, 0)

        adapter = FilesAdapter(mediaFiles, isList = true)




        RecycleClick.addTo(currentRecyclerView).setOnItemClickListener { _, position, _ ->
            if (mediaFiles[position].file.isDirectory) {
                currentPath = mediaFiles[position].file.absolutePath

                val newChip =
                    createNewChip(mediaFiles[position].file.absolutePath.substringAfterLast('/'))

                currentLinear.addView(newChip, lp)
                mapPathToChip[currentPath] = newChip

                loadFiles(currentPath)


            } else {
                adapter.setBackGEnabled(position)
                if (adapter.itemsChosen != 0) {
                    currentChosenItems.text = adapter.itemsChosen.toString()
                    currentImageChosenItems.visibility = View.VISIBLE
                } else {
                    currentChosenItems.text = ""
                    currentImageChosenItems.visibility = View.INVISIBLE
                }
            }
        }

        currentImageChosenItems.setOnClickListener {
          /*  val intent = Intent(this, MainActivity::class.java)
            intent.putStringArrayListExtra(
                CHOSEN_FILES,
                adapter.list.filter { it.isChosen }.map { it.file }.map { it.absolutePath } as ArrayList<String>?)
            setResult(FILE_REQUEST_CODE, intent)
            finish()*/

        }

        currentImageChange.setOnClickListener {
            if (adapter.isList) {
                currentRecyclerView.layoutManager = gridLayoutManager
                adapter.isList = false
                currentRecyclerView.adapter = adapter

                currentImageChange.setImageResource(currentImageList)
            } else {
                currentRecyclerView.layoutManager = linearLayoutManager
                adapter.isList = true
                currentRecyclerView.adapter = adapter
                currentImageChange.setImageResource(currentImageGrid)

            }

        }
    }

    override fun onResume() {
        super.onResume()
        if (checkPermissions()) {
            currentPath =
                this.getExternalFilesDir(null)!!.absolutePath.substringBeforeLast("/Android/")
            rootPath = currentPath

            if (listExtensions.isNullOrEmpty()) {
                val files = File(currentPath).listFiles()
                if (files != null && files.isNotEmpty()) {
                    files.forEach {
                        mediaFiles.add(FilePick(it))
                    }
                    val rootChip = createNewChip(storageWord)
                    currentLinear.addView(rootChip, lp)
                    mapPathToChip[currentPath] = rootChip
                }
            } else if (listExtensions.isNotEmpty()) {
                mediaFiles.clear()
                mediaFiles.addAll(
                    searchFilesWithExtension2(
                        arrayListOf(),
                        File(currentPath),
                        listExtensions
                    ).map { FilePick(it) })

                listExtensions.forEach {
                    val rootChip = createNewChip(it, true)
                    currentLinear.addView(rootChip, lp)
                    mapPathToChip[currentPath] = rootChip
                }

            }


            adapter = FilesAdapter(mediaFiles, isList = true)
            currentRecyclerView.adapter = adapter



        } else {
            Toast.makeText(this, "Please, check permissions", Toast.LENGTH_LONG).show()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (mediaFiles.isNotEmpty()) {
            outState.putStringArrayList("ARRAY_STRING", mediaFiles.filter { it.isChosen }.map { it.file.absolutePath } as ArrayList<String>)
            outState.putStringArrayList("ARRAY_STRING_MEDIA", mediaFiles.map { it.file.absolutePath } as ArrayList<String>)
            Log.d("sbsbssgesgewhgew", "On Save Instant state -> ${mediaFiles.filter { it.isChosen }.map { it.file.absolutePath }}")
        }
    }

    override fun onRestoreInstanceState(
        savedInstanceState: Bundle?
    ) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.d("sbsbssgesgewhgew", "On Restore Instant state ")
        val mediaFilesList = savedInstanceState?.getStringArrayList("ARRAY_STRING_MEDIA")?.map { FilePick(File(it)) } as ArrayList<FilePick>
        Log.d("sbsbssgesgewhgew", "On Restore Instant state mediaFilesList.size ->>>>> ${mediaFilesList.size}")
        mediaFiles = mediaFilesList
        if (mediaFiles.isNotEmpty()) {
            val list = savedInstanceState.getStringArrayList("ARRAY_STRING")
            Log.d("sbsbssgesgewhgew", "On Restore Instant state list.size ->>>>> ${list?.size}")
            Log.d("sbsbssgesgewhgew", "On Restore Instant state list $list")
            if (list != null) {
                val listChosen = mediaFiles.filter { it.file.absolutePath in list }
                listChosen.forEach {
                    it.isChosen = true
                }
                Log.d("sbsbssgesgewhgew", "On Restore Instant state ->  ${mediaFiles.filter { it.file.absolutePath in list }}")
                adapter = FilesAdapter(mediaFiles, adapter.isList)
                adapter.itemsChosen = listChosen.size
                currentRecyclerView.adapter = adapter

                currentChosenItems.text = adapter.itemsChosen.toString()
                currentImageChosenItems.visibility = View.VISIBLE
            }
        }
    }

    private fun setCustomStylesFromExtra() {
        currentImageList = intent.getIntExtra(LIST_IMAGE, R.drawable.ic_list)
        currentImageGrid = intent.getIntExtra(GRID_IMAGE, R.drawable.ic_grid)

        currentChipStyle =
            intent.getIntExtra(CUSTOM_CHIP_STYLE, R.style.Widget_MaterialComponents_Chip_Action)

        currentGridCount = intent.getIntExtra(GRID_COUNT, 3)


        listExtensions = intent.getStringArrayExtra(ARRAY_EXTENSION) ?: arrayOf()

        currentImageChange.setImageResource(currentImageGrid)

        currentImageChosenItems.setImageResource(intent.getIntExtra(DONE_IMAGE, R.drawable.ic_done))

        currentRootView.setBackgroundResource(
            intent.getIntExtra(
                ROOT_BACKGROUND,
                R.drawable.background_white
            )
        )

        currentToolbar.setBackgroundResource(
            intent.getIntExtra(
                TOOLBAR_BACKGROUND,
                R.drawable.background_white
            )
        )

        TextViewCompat.setTextAppearance(
            currentChosenItems,
            intent.getIntExtra(TEXT_COUNT_STYLE, R.style.TextAppearance_AppCompat)
        )



        FilesAdapter.textStyleName =
            intent.getIntExtra(TEXT_STYLE_NAME, R.style.Widget_AppCompat_TextView)
        FilesAdapter.textStyleDate =
            intent.getIntExtra(TEXT_STYLE_DATE, R.style.Widget_AppCompat_TextView)
        FilesAdapter.textStyleSize =
            intent.getIntExtra(TEXT_STYLE_SIZE, R.style.Widget_AppCompat_TextView)
        FilesAdapter.simpleDateFormat =
            intent.getStringExtra(SIMPLE_DATE_FORMAT) ?: "MM/dd/yyyy kk:mm"
        FilesAdapter.objectWord = intent.getStringExtra(OBJECT_WORD) ?: "objects"
        FilesAdapter.listItemStyle =
            intent.getIntExtra(LIST_ITEM_STYLE, R.style.Widget_MaterialComponents_CardView)
        FilesAdapter.listItemSelectedStyle = intent.getIntExtra(
            LIST_ITEM_SELECTED_STYLE,
            R.style.Widget_MaterialComponents_CardView
        )
        FilesAdapter.customLayoutList =
            intent.getIntExtra(CUSTOM_LAYOUT_LIST, R.layout.recycler_view_item)
        FilesAdapter.customLayoutGrid =
            intent.getIntExtra(CUSTOM_LAYOUT_GRID, R.layout.recycler_view_item_grid)

        storageWord = intent.getStringExtra(STORAGE_WORD) ?: "Phone"
    }

    private fun createNewChip(text: String, isExtension: Boolean = false): Chip {
        val newChip = Chip(ContextThemeWrapper(this, currentChipStyle), null, 0)
        newChip.text = text
        newChip.isClickable = true
        newChip.isFocusable = true
        newChip.id = ViewCompat.generateViewId()

        mapIdToPath[newChip.id] = currentPath

        if (!isExtension) {
            newChip.setOnClickListener {
                if (mapIdToPath[it.id] != null) {
                    var counter = 0

                    while (currentPath != mapIdToPath[it.id].toString()) {
                        if (mapPathToChip[currentPath] != null) {
                            currentLinear.removeView(mapPathToChip[currentPath] as View)
                            mapIdToPath.remove(mapPathToChip[currentPath]?.id)
                            mapPathToChip.remove(currentPath)
                            counter++
                            currentPath = currentPath.substringBeforeLast('/')
                        }
                    }
                    currentPath = mapIdToPath[it.id].toString()
                    loadFiles(currentPath)
                }
            }
        }

        currentScrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT)

        return newChip
    }

    private fun loadFiles(path: String) {
        val files = File(path).listFiles()
        if (files != null) {
            if (files.isNotEmpty()) {
                mediaFiles.clear()
                files.forEach {
                    mediaFiles.add(FilePick(it))
                }
                adapter =
                    FilesAdapter(mediaFiles, adapter.isList)
                currentRecyclerView.adapter = adapter
            } else {
                mediaFiles.clear()
                adapter = FilesAdapter(mediaFiles, adapter.isList)
                currentRecyclerView.adapter = adapter
                Toast.makeText(this, "Directory is empty", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onBackPressed() {

        if (currentPath != rootPath) {
            if (mapPathToChip[currentPath] != null) {
                currentLinear.removeView(mapPathToChip[currentPath] as View)
                mapIdToPath.remove(mapPathToChip[currentPath]?.id)
                mapPathToChip.remove(currentPath)
            }
            currentPath = currentPath.substringBeforeLast('/')

            val files = File(currentPath).listFiles()
            if (files != null && files.isNotEmpty()) {
                mediaFiles.clear()
                files.forEach {
                    mediaFiles.add(FilePick(it))
                }
                adapter = FilesAdapter(mediaFiles, adapter.isList)
                currentRecyclerView.adapter = adapter
                currentChosenItems.text = ""
                currentImageChosenItems.visibility = View.INVISIBLE
            }
        } else {
            super.onBackPressed()

        }
    }


    private fun searchFilesWithExtension2(
        arrayForAdding: ArrayList<File>,
        currentDirectory: File,
        mExtension: Array<String>
    ): ArrayList<File> {
        if (currentDirectory.isDirectory) {
            val files = currentDirectory.listFiles()
            if (files != null && files.isNotEmpty()) {
                files.filter { element ->
                    element.isDirectory || element.absolutePath.substringAfterLast(
                        '.'
                    ) in mExtension
                }.forEach {
                    if (it.isDirectory) {
                        searchFilesWithExtension2(arrayForAdding, it, mExtension)
                    } else if (it.absolutePath.substringAfterLast('.') in mExtension) {
                        arrayForAdding.add(it)
                    }
                }
            }
        } else {
            return arrayForAdding
        }
        return arrayForAdding
    }


    private fun checkPermissions(): Boolean {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            ) {
                openAppSettings()
                return false
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ),
                    STORAGE_PERMISSION
                )
                return false
            }

        } else {
            return true
        }
    }


    private fun openAppSettings() {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivityForResult(intent, REQUESET_OPEN_APP_SETTINGS)
    }


}

