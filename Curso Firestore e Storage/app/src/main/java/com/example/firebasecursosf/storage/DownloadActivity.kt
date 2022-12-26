package com.example.firebasecursosf.storage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.br.jafapps.bdfirestore.util.Util
import com.bumptech.glide.Glide
import com.example.firebasecursosf.R
import com.example.firebasecursosf.databinding.ActivityDownloadBinding

class DownloadActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityDownloadBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDownloadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonDownload.setOnClickListener(this)
        binding.buttonRemove.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            binding.buttonDownload.id -> {
                buttonDownload()
            }
            binding.buttonRemove.id -> {

            }
        }
    }

    private fun buttonDownload() {
        if (Util.statusInternet(this)) {
            downloadImage1()
        } else {
            Util.exibirToast(this, "Sem conexão com a internet")
        }
    }

    private fun downloadImage1() {
        val urlImage = "https://firebasestorage.googleapis.com/v0/b/fir-cursosf-6951f.appspot.com/o/1571952414963.jpg?alt=media&token=d2acfc6c-9ea1-497d-8360-a1e9472159cf"
        Glide.with(this).load(urlImage).placeholder(R.drawable.ic_download).into(binding.imageDownload)
    }
}