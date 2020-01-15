package com.kbokka.android.cameraintentsample

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

  private var _imageUri: Uri? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
  }

  public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    if (requestCode == 200 && resultCode == Activity.RESULT_OK) {
      val ivCamera = findViewById<ImageView>(R.id.ivCamera)
      ivCamera.setImageURI(_imageUri)

      super.onActivityResult(requestCode, resultCode, data)
    }
  }

  @SuppressLint("SimpleDateFormat")
  fun onCameraImageClick(view: View) {
    if (ActivityCompat.checkSelfPermission(
        this,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
      ) != PackageManager.PERMISSION_GRANTED
    ) {
      val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
      ActivityCompat.requestPermissions(this, permissions, 200)
      return
    }

    val dateFormat = SimpleDateFormat("yyyyMMddHHmmss")
    val now = Date()
    val nowStr = dateFormat.format(now)
    val fileName = "UseCameraActivityPhoto_${nowStr}.jpg"

    val values = ContentValues()
    values.put(MediaStore.Images.Media.TITLE, fileName)
    values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
    _imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    intent.putExtra(MediaStore.EXTRA_OUTPUT, _imageUri)
    startActivityForResult(intent, 200)
  }

  override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
    if (requestCode == 200 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
      onCameraImageClick(findViewById<ImageView>(R.id.ivCamera))
    }
  }
}
