package com.example.firebasecursosf.storage

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.content.FileProvider
import com.example.firebasecursosf.R
import com.example.firebasecursosf.databinding.ActivityUploadBinding
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.io.File

class UploadActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityUploadBinding
    private lateinit var storage: FirebaseStorage
    private var uriImage: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        storage = Firebase.storage

        binding.buttonUpload.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when (v.id) {
            binding.buttonUpload.id -> {

            }
        }
    }

    // FUNÇÃO PARA CRIAR O MENU SUPERIOR DA SUPPORT ACTION BAR

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_upload, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // AÇÕES DE CLICK NO MENU SUPERIOR DA SUPPORT ACTION BAR

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_gallery -> {
                getGalleryImage()
            }
            R.id.menu_camera -> {
                getCameraImage()
            }
            else -> return true
        }
        return super.onOptionsItemSelected(item)
    }

    // MÉTODOS PARA PEGAR A IMAGEM DO DISPOSITIVO

    private fun getGalleryImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(Intent.createChooser(intent, "Escolha uma Imagem"), 11)
    }

    private fun getCameraImage() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

            val contentValues = ContentValues()
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            val resolver = contentResolver
            uriImage = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)

        } else {

            val authorization = "com.example.firebasecursosf"
            val directory =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val nameImage = directory.path + "/CursoFS" + System.currentTimeMillis() + ".jpg"
            val file = File(nameImage)
            uriImage = FileProvider.getUriForFile(baseContext, authorization, file)

        }

        intent.putExtra(MediaStore.EXTRA_OUTPUT, uriImage)
        startActivityForResult(intent, 22)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == 11 && data != null) {
                uriImage = data.data
                binding.imageUpload.setImageURI(uriImage)

            } else if (requestCode == 22 && uriImage != null) {
                binding.imageUpload.setImageURI(uriImage)

            }
        }
    }
}