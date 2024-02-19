package com.agamatech.worderworld.utils

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.webkit.URLUtil
import android.widget.Toast
import androidx.annotation.RawRes
import androidx.core.content.ContextCompat

fun <T : Number> T.dpToPx(): Float = toFloat() * Resources.getSystem().displayMetrics.density

fun <T : Number> T.pxToDp(): Float = toFloat() / Resources.getSystem().displayMetrics.density

fun startLink(context: Context, uriString: String) {
    val urlValid = URLUtil.isValidUrl(uriString)
    if (!urlValid) {
        Toast.makeText(context, "Url is invalid", Toast.LENGTH_LONG).show()
        return
    }
    val viewIntent = Intent(Intent.ACTION_VIEW, Uri.parse(uriString))
    if (viewIntent.resolveActivity(context.packageManager) != null) {
        ContextCompat.startActivity(context, viewIntent, null)
    } else {
        Toast.makeText(context, "You don't have any browser to open web page", Toast.LENGTH_LONG).show()
    }
}

fun getDrawableId(context: Context, resourceName: String): Int? = try {
    getResourceId(context, resourceName, "drawable")
} catch (e: Exception) {
    null
}

fun getResourceId(context: Context, resourceName: String, resourceTypeName: String): Int? = try {
    context.resources.getIdentifier(resourceName, resourceTypeName, context.packageName)
} catch (e: Exception) {
    null
}

fun Resources.getRawTextFile(@RawRes id: Int) =
    openRawResource(id).bufferedReader().use { it.readText() }