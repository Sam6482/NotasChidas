package com.example.notaschidas.utils

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream

fun uriToFile(context: Context, uri: Uri): File {
    val inputStream = context.contentResolver.openInputStream(uri)
    val file = File.createTempFile("upload_", ".jpg", context.cacheDir)
    val output = FileOutputStream(file)

    inputStream?.copyTo(output)

    output.close()
    inputStream?.close()

    return file
}