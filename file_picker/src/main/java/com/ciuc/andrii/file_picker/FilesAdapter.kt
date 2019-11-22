package com.ciuc.andrii.file_picker

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.widget.TextViewCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_view_item.view.*
import java.text.SimpleDateFormat
import java.util.*


class FilesAdapter(
    items: List<FilePick>,
    var isList: Boolean = true
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        var textStyleName = R.style.TextAppearance_AppCompat
        var textStyleDate = R.style.TextAppearance_AppCompat
        var textStyleSize = R.style.TextAppearance_AppCompat
        var simpleDateFormat = "MM/dd/yyyy kk:mm"
        var objectWord = "objects"

        var listItemStyle = R.style.Widget_MaterialComponents_CardView
        var listItemSelectedStyle = R.style.Widget_MaterialComponents_CardView

        var customLayoutList = R.layout.recycler_view_item
        var customLayoutGrid = R.layout.recycler_view_item_grid
    }

    var list: List<FilePick> = items
    var itemsChosen = 0
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        context = parent.context
        val view =
            if (isList)
                LayoutInflater.from(context).inflate(customLayoutList, parent, false)
            else
                LayoutInflater.from(context).inflate(
                    customLayoutGrid,
                    parent,
                    false
                )

        return FileViewHolder(view)

    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(parent: RecyclerView.ViewHolder, position: Int) {
        if (parent is FileViewHolder) {
            if (list[position].file.isDirectory) {

                if (parent.image != null) {
                    loadWithGlide(context, R.drawable.ic_f, parent.image)
                }
                //parent.rootConstraint.

                /*  var background = parent.rootConstraint.background as RippleDrawable

                  var rip1true = background.findDrawableByLayerId(R.id.rip1true)*/
                // rip1true.setColorFilter(Color.BLACK, PorterDuff.Mode.MULTIPLY)


                // Log.d("sbsbssgesgewhgew", "$rip1true")


                if (parent.textItem != null) {
                    TextViewCompat.setTextAppearance(parent.textItem, textStyleName)

                    var fileName = list[position].file.absolutePath.substringAfterLast('/')

                    if (fileName.length > 30) {
                        fileName = fileName.substring(0, 14) + "..." + fileName.substring(fileName.length - 13, fileName.length)
                    }
                    parent.textItem.text = fileName

                }

                if (parent.textDate != null) {
                    TextViewCompat.setTextAppearance(parent.textDate, textStyleDate)

                    parent.textDate.text =
                        SimpleDateFormat(simpleDateFormat).format(Date(list[position].file.lastModified()))
                }

                if (parent.textFileCount != null) {
                    TextViewCompat.setTextAppearance(parent.textFileCount, textStyleSize)

                    parent.textFileCount.text =
                        list[position].file.listFiles().size.toString() + " $objectWord"
                }


            } else {
                // val mimeTypeMap = MimeTypeMap.getSingleton()


                if (parent.image != null) {
                    when (MimeTypeMap.getFileExtensionFromUrl(list[position].file.absolutePath)) {
                        "jpg", "jpeg", "bmp", "gif", "png", "tif", "tiff" -> {
                            loadWithGlide(context, list[position].file.absolutePath, parent.image)
                        }
                        "txt" -> {
                            loadWithGlide(context, R.drawable.ic_txt, parent.image)
                        }
                        "mp3" -> {
                            loadWithGlide(context, R.drawable.ic_audio, parent.image)
                        }
                        "m4v", "mp4", "mpg", "mpeg", "3gp" -> {
                            loadWithGlide(context, R.drawable.ic_video, parent.image)
                        }
                        "rar", "zip" -> {
                            loadWithGlide(context, R.drawable.ic_archive, parent.image)
                        }
                        "xml" -> {
                            loadWithGlide(context, R.drawable.ic_xml, parent.image)
                        }
                        "pps", "ppt", "pptx" -> {
                            loadWithGlide(context, R.drawable.ic_pptx, parent.image)
                        }
                        "doc", "docx", ".docx" -> {
                           // loadWithGlide(context, R.drawable.ic_docx, parent.image)
                            loadSVG(R.drawable.ic_docx, parent.image)
                        }
                        "pdf" -> {
                            loadWithGlide(context, R.drawable.ic_pdf, parent.image)
                        }
                        else -> {
                            loadWithGlide(context, R.drawable.ic_file, parent.image)
                        }
                    }
                }


                if (parent.textItem != null) {
                    TextViewCompat.setTextAppearance(parent.textItem, textStyleName)

                    var fileName = list[position].file.absolutePath.substringAfterLast('/')

                    if (fileName.length > 30) {
                        fileName = fileName.substring(0, 14) + "..." + fileName.substring(fileName.length - 13, fileName.length)
                    }
                    parent.textItem.text = fileName
                }

                if (parent.textDate != null) {
                    TextViewCompat.setTextAppearance(parent.textDate, textStyleDate)
                    parent.textDate.text =
                        SimpleDateFormat(simpleDateFormat).format(Date(list[position].file.lastModified()))
                }

                if (parent.textFileCount != null) {
                    TextViewCompat.setTextAppearance(parent.textFileCount, textStyleSize)

                    val fileSizeInBytes: Double = list[position].file.length().toDouble()
                    val fileSizeInKB: Double = fileSizeInBytes / 1024
                    val fileSizeInMB: Double = fileSizeInKB / 1024

                    if (fileSizeInKB > 500) {
                        parent.textFileCount.text = "${fileSizeInMB.round(1)} Mb"
                    } else {
                        parent.textFileCount.text = "${fileSizeInKB.toInt()} Kb"
                    }

                }

                if (parent.rootConstraint != null) {
                    if (!list[position].isChosen) {
                        parent.rootConstraint.setBackgroundResource(R.drawable.ripple_dark)
                    } else {
                        parent.rootConstraint.setBackgroundResource(R.drawable.ripple_light)
                    }
                }


            }
        }

    }

    fun setBackGEnabled(position: Int) {
        if (list.isNotEmpty()) {

            if (list[position].isChosen) {
                list[position].isChosen = false
                itemsChosen--
            } else {
                list[position].isChosen = true
                itemsChosen++
            }
            notifyDataSetChanged()
            notifyItemChanged(position)
        }

    }

    inner class FileViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView? = view.imageView
        val textItem: TextView? = view.textItem
        val textDate: TextView? = view.textDate
        val textFileCount: TextView? = view.textFileCount
        val rootConstraint: RelativeLayout? = view.rootConstraint
    }
}
