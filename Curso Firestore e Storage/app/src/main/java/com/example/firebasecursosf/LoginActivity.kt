package com.example.firebasecursosf

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.util.Util
import com.example.firebasecursosf.databinding.ActivityAberturaBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityAberturaBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAberturaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.buttonLogin.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            binding.buttonLogin.id -> {
                buttonLogin()
            }
        }
    }

    private fun buttonLogin() {
        val email = binding.editEmail.text.toString()
        val password = binding.editPassword.text.toString()

        if (email.trim() != "" && password.trim() != "") {
            if (com.br.jafapps.bdfirestore.util.Util.statusInternet(this)) {
                handleLogin(email, password)
            } else {
                Toast.makeText(this, "Não há conexão com a internet", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
        }

    }

    private fun handleLogin(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(this, "Login efetuado", Toast.LENGTH_SHORT).show()
            } else {
                val error = it.exception.toString()
                errorFirebase(error)
            }
        }
    }

    private fun errorFirebase(error: String) {
        if (error.contains("There is no user record corresponding to this identifier")) {
            Toast.makeText(this, "E-mail não cadastrado", Toast.LENGTH_SHORT).show()
        } else if (error.contains("The password is invalid")) {
            Toast.makeText(this, "Senha inválida", Toast.LENGTH_SHORT).show()
        } else if (error.contains("The email address is badly formatted")) {
            Toast.makeText(this, "Email inválido", Toast.LENGTH_SHORT).show()
        }
    }
}