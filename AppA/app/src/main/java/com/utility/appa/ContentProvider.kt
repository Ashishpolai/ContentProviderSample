package com.utility.appa

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.util.Log

class MyContentProvider : ContentProvider() {

    companion object {
        const val AUTHORITY = "com.example.appa.provider"
        val CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY/name")

        private const val CODE_NAME = 1

        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(AUTHORITY, "name", CODE_NAME)
        }

        private const val NAME = "John Doe"
    }

    override fun onCreate(): Boolean {
        Log.d("MyContentProvider", "ContentProvider initialized")
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        return if (uriMatcher.match(uri) == CODE_NAME) {
            val matrixCursor = android.database.MatrixCursor(arrayOf("name"))
            matrixCursor.addRow(arrayOf(NAME))
            matrixCursor
        } else {
            null
        }
    }

    override fun getType(uri: Uri): String? {
        return when (uriMatcher.match(uri)) {
            CODE_NAME -> "vnd.android.cursor.item/name"
            else -> null
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        throw UnsupportedOperationException("Not yet implemented")
    }
}
