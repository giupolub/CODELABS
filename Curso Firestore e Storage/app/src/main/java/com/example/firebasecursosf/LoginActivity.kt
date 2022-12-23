package com.example.firebasecursosf

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.br.jafapps.bdfirestore.util.DialogProgress
import com.example.firebasecursosf.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        currentUser()

        binding.buttonLogin.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            binding.buttonLogin.id -> {
                buttonLogin()
            }
        }
    }

    // Para realizar o login no aplicativo:
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

    // Para receber as informações do firebase:
    private fun handleLogin(email: String, password: String) {
        // Método para pegar as informações do firebase:

        /*auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(this, "Login efetuado", Toast.LENGTH_SHORT).show()
            } else {
                val error = it.exception.toString()
                errorFirebase(error)
            }
        }*/

        // Outro método para pegar as informações do firebase (melhor que o anterior pq
        // não precisa fazer o tratamento do sucesso ou falha da resposta do firebase):

        // Para fornecer o sinal de que o aplicativo está carregando ao fazer o login:
        val dialogProgress = DialogProgress()
        dialogProgress.show(supportFragmentManager, "1")

        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            finish()
            dialogProgress.dismiss()
            startActivity(Intent(this, MainActivity::class.java))

        }.addOnFailureListener {
            dialogProgress.dismiss()
            val error = it.message.toString()
            errorFirebase(error)
        }

    }

    //Exibição dos erros recebidos do Firebase:
    private fun errorFirebase(error: String) {
        if (error.contains("There is no user record corresponding to this identifier")) {
            Toast.makeText(this, "E-mail não cadastrado", Toast.LENGTH_SHORT).show()
        } else if (error.contains("The password is invalid")) {
            Toast.makeText(this, "Senha inválida", Toast.LENGTH_SHORT).show()
        } else if (error.contains("The email address is badly formatted")) {
            Toast.makeText(this, "Email inválido", Toast.LENGTH_SHORT).show()
        }
    }

    // Se o usuário já ter efetuado o login, não precisará realizar novamente, ao menos se deslogar:
    private fun currentUser() {
        val user = auth.currentUser

        if (user != null) {
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}