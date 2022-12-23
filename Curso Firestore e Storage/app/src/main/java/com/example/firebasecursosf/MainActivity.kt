package com.example.firebasecursosf

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.br.jafapps.bdfirestore.util.Util
import com.example.firebasecursosf.databinding.ActivityMainBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cardDownloadImage.setOnClickListener(this)
        binding.cardUploadImage.setOnClickListener(this)
        binding.cardReadData.setOnClickListener(this)
        binding.cardSaveChangeRemove.setOnClickListener(this)
        binding.cardCategories.setOnClickListener(this)
        binding.cardSignOut.setOnClickListener(this)

        permission()
        authListener()
    }

    // Para verificar se as permissões de uso do celular foram fornecidas pelo usuário:
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        for (result in grantResults) {
            if (result == PackageManager.PERMISSION_DENIED) {
                Util.exibirToast(
                    this,
                    "Forneça as permissões para o bom funcionamento do aplicativo"
                )
                finish()
                break
            }
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            binding.cardDownloadImage.id -> {

            }
            binding.cardUploadImage.id -> {

            }
            binding.cardReadData.id -> {

            }
            binding.cardSaveChangeRemove.id -> {

            }
            binding.cardCategories.id -> {

            }
            // Para deslogar do aplicativo:
            binding.cardSignOut.id -> {
                finish()
                Firebase.auth.signOut()
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }
    }

    // Para ouvir quais são os status do usuário, se está logado ou não no Firebase/aplicativo:
    private fun authListener() {
        Firebase.auth.addAuthStateListener {
            if (it.currentUser != null) {
                Toast.makeText(this, "Login efetuado", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Logout efetuado", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Para solicitar as permissões de funcionamento do app ao usuário:
    private fun permission() {
        val permissions = arrayOf<String>(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
        )

        Util.permissao(this, 100, permissions)
    }
}