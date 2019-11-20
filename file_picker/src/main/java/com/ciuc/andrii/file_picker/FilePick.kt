package com.ciuc.andrii.file_picker

import java.io.File

data class FilePick(
    val file: File,
    var isChosen: Boolean = false
)