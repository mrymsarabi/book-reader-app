// FileDownloader.kt

package com.example.bookreaderapp.util

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment

object FileDownloader {

    fun downloadFile(context: Context, fileUrl: String, fileName: String): Long {
        val request = DownloadManager.Request(Uri.parse(fileUrl))
            .setTitle(fileName)
            .setDescription("Downloading")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
            .setAllowedOverMetered(true)  // Optional: allow downloading over metered networks

        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        return downloadManager.enqueue(request)
    }
}
