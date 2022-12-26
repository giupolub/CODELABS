package com.example.firebasecursosf.storage

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.br.jafapps.bdfirestore.util.Util
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.firebasecursosf.R
import com.example.firebasecursosf.databinding.ActivityDownloadBinding
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

class DownloadActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityDownloadBinding
    private lateinit var storage: FirebaseStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDownloadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        storage = Firebase.storage

        binding.buttonDownload.setOnClickListener(this)
        binding.buttonRemove.setOnClickListener(this)

        binding.progressDownload.visibility = View.GONE
    }

    override fun onClick(v: View) {
        when (v.id) {
            binding.buttonDownload.id -> {
                buttonDownload()
            }
            binding.buttonRemove.id -> {
                buttonRemove()
            }
        }
    }

    private fun buttonDownload() {
        if (Util.statusInternet(this)) {
            downloadImageName()
        } else {
            Util.exibirToast(this, "Sem conexão com a internet")
        }
    }

    private fun buttonRemove() {
        if (Util.statusInternet(this)) {
            removeImageName()
        } else {
            Util.exibirToast(this, "Sem conexão com a internet")
        }
    }

    // MÉTODOS PARA REALIZAR O DOWNLOAD DA IMAGEM NO STORAGE DO FIREBASE

    private fun downloadImageUrl1() {
        val urlImage =
            "https://firebasestorage.googleapis.com/v0/b/fir-cursosf-6951f.appspot.com/o/1571952414963.jpg?alt=media&token=d2acfc6c-9ea1-497d-8360-a1e9472159cf"
        Glide.with(this).load(urlImage).placeholder(R.drawable.ic_download)
            .into(binding.imageDownload)
    }

    private fun downloadImageUrl2() {
        binding.progressDownload.visibility = View.VISIBLE

        val urlImage =
            "https://firebasestorage.googleapis.com/v0/b/fir-cursosf-6951f.appspot.com/o/1571952414963.jpg?alt=media&token=d2acfc6c-9ea1-497d-8360-a1e9472159cf"

        Glide.with(this).asBitmap().load(urlImage).listener(object : RequestListener<Bitmap> {

            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Bitmap>?,
                isFirstResource: Boolean
            ): Boolean {
                Util.exibirToast(baseContext, "Erro ao realizar o download: ${e.toString()}")
                binding.progressDownload.visibility = View.GONE
                return false
            }

            override fun onResourceReady(
                resource: Bitmap?,
                model: Any?,
                target: Target<Bitmap>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                Util.exibirToast(baseContext, "Download realizado com sucesso")
                binding.progressDownload.visibility = View.GONE
                return false
            }
        }).into(binding.imageDownload)
    }

    private fun downloadImageName() {

        val reference = storage.reference.child("image1").child("itachi.jpg")

        reference.downloadUrl.addOnSuccessListener {

            binding.progressDownload.visibility = View.VISIBLE

            val urlImage = it.toString()

            Glide.with(this).asBitmap().load(urlImage).listener(object : RequestListener<Bitmap> {

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    isFirstResource: Boolean
                ): Boolean {
                    Util.exibirToast(baseContext, "Erro ao realizar o download: ${e.toString()}")
                    binding.progressDownload.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Bitmap?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    Util.exibirToast(baseContext, "Download realizado com sucesso")
                    binding.progressDownload.visibility = View.GONE
                    return false
                }
            }).into(binding.imageDownload)

        }.addOnFailureListener {
            Util.exibirToast(baseContext, "Erro ao acessar imagem: ${it.message.toString()}")
        }
    }

    // MÉTODOS PARA REALIZAR A REMOÇÃO DA IMAGEM NO STORAGE DO FIREBASE

    private fun removeImageUrl() {

        val urlImage =
            "https://firebasestorage.googleapis.com/v0/b/fir-cursosf-6951f.appspot.com/o/image1%2Fitachi.jpg?alt=media&token=6a2632da-a529-4a55-be54-e62b19b14425"

        val reference = storage.getReferenceFromUrl(urlImage)

        reference.delete().addOnSuccessListener {
            Util.exibirToast(this, "Sucesso ao remover a imagem: ")

        }.addOnFailureListener {
            Util.exibirToast(this, "Erro ao remover a imagem: ${it.message.toString()}")

        }
    }

    private fun removeImageName() {

        val name = "itachi.jpg"

        val reference = storage.reference.child("image1").child(name)

        reference.delete().addOnSuccessListener {
            Util.exibirToast(this, "Sucesso ao remover a imagem: ")

        }.addOnFailureListener {
            Util.exibirToast(this, "Erro ao remover a imagem: ${it.message.toString()}")

        }
    }

}