package com.kbokka.android.cameraintentsample

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
  }

  public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    if (requestCode == 200 && resultCode == Activity.RESULT_OK) {
      val bitmap = data?.getParcelableExtra<Bitmap>("data")
      findViewById<ImageView>(R.id.ivCamera).setImageBitmap(bitmap)

      super.onActivityResult(requestCode, resultCode, data)
    }
  }

  fun onCameraImageClick(view: View) {
    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    startActivityForResult(intent, 200)
  }
}
