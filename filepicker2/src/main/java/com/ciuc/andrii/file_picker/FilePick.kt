package com.ciuc.andrii.filepicker

import java.io.File

data class FilePick(
    val file: File,
    var isChosen: Boolean = false
)