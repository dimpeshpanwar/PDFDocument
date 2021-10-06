package com.itamazons.pdfdocument

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.itextpdf.text.pdf.BaseFont
import android.widget.Toast
import android.view.View
import java.io.File

import android.os.Environment

import java.io.FileOutputStream

import com.itextpdf.text.pdf.PdfWriter

import java.io.FileNotFoundException

import java.io.IOException

import android.graphics.Bitmap

import java.io.ByteArrayOutputStream

import android.content.Intent
import android.util.Log
import android.widget.Button

import android.widget.RelativeLayout
import com.itextpdf.text.Document
import com.itextpdf.text.Image


class MainActivity : AppCompatActivity() {
    var dirpath: String? = null
    lateinit var relativeLayout: RelativeLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btngen).setOnClickListener {
            // get view group using reference
            relativeLayout = findViewById(R.id.print) as RelativeLayout
            // convert view group to bitmap
            relativeLayout.setDrawingCacheEnabled(true)
            relativeLayout.buildDrawingCache()
            val bm: Bitmap = relativeLayout.getDrawingCache()
            val share = Intent(Intent.ACTION_SEND)
            share.type = "image/jpeg"
            val bytes = ByteArrayOutputStream()
            bm.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
            val f = File(
                Environment.getExternalStorageDirectory().toString() + File.separator + "image.jpg"
            )
            try {
                f.createNewFile()
                val fo = FileOutputStream(f)
                fo.write(bytes.toByteArray())
                imageToPDF()
            } catch (e: IOException) {
                Log.d("pdferror", e.toString())
                e.printStackTrace()
            }
        }
    }


    fun layoutToImage(view: View) {
//        // get view group using reference
//        relativeLayout = view.findViewById(R.id.print) as RelativeLayout
//        // convert view group to bitmap
//        relativeLayout.setDrawingCacheEnabled(true)
//        relativeLayout.buildDrawingCache()
//        val bm: Bitmap = relativeLayout.getDrawingCache()
//        val share = Intent(Intent.ACTION_SEND)
//        share.type = "image/jpeg"
//        val bytes = ByteArrayOutputStream()
//        bm.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
//        val f = File(
//            Environment.getExternalStorageDirectory().toString() + File.separator + "image.jpg"
//        )
//        try {
//            f.createNewFile()
//            val fo = FileOutputStream(f)
//            fo.write(bytes.toByteArray())
//            imageToPDF()
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
    }

    fun imageToPDF() {
        try {
            val document = Document()
            dirpath = Environment.getExternalStorageDirectory().toString()
            PdfWriter.getInstance(
                document,
                FileOutputStream("$dirpath/NewPDF.pdf")
            ) //  Change pdf's name.
            document.open()
            val img: Image = Image.getInstance(
                Environment.getExternalStorageDirectory().toString() + File.separator + "image.jpg"
            )
            val scaler: Float = (document.getPageSize().getWidth() - document.leftMargin()
                    - document.rightMargin() - 0) / img.getWidth() * 100
            img.scalePercent(scaler)
            img.setAlignment(Image.ALIGN_CENTER or Image.ALIGN_TOP)
            document.add(img)
            document.close()
            Toast.makeText(this, "PDF Generated successfully!..", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Error...", Toast.LENGTH_SHORT).show()

        }
    }
}