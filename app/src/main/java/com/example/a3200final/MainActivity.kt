package com.example.a3200final


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var imageView2: ImageView
    private var lastBitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Check camera permission
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                REQUEST_CAMERA_PERMISSION
            )
        }

        // Set up views
        imageView = findViewById(R.id.image_view)
        imageView2 = findViewById(R.id.image_view2)

        val takePhotoButton = findViewById<Button>(R.id.take_photo_button)

        // Set up click listener for take photo button
        takePhotoButton.setOnClickListener {
            takePhoto()
        }
    }

    private fun takePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            imageView.setImageBitmap(imageBitmap) // Set bitmap to the original ImageView
            imageView2.setImageBitmap(lastBitmap)
            lastBitmap = imageBitmap
        }
    }

    companion object {
        private const val REQUEST_CAMERA_PERMISSION = 1
        private const val REQUEST_IMAGE_CAPTURE = 2
    }
}
