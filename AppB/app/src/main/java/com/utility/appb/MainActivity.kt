package com.utility.appb

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.textView)

        try {
            val uri = Uri.parse("content://com.example.appa.provider/name")
            val cursor = contentResolver.query(uri, null, null, null, null)

            cursor?.let {
                if (it.moveToFirst()) {
                    val name = it.getString(it.getColumnIndexOrThrow("name"))
                    textView.text = "Name from App A: $name"
                }
                cursor.close()
            } ?: Log.e("AppB", "Cursor is null")
        } catch (e: Exception) {
            Log.e("AppB", "Error accessing content provider", e)
        }

    }
}
