package com.example.newsappadm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import com.example.newsappadm.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity(), OnClickListener {

    lateinit var binding: ActivityMainBinding
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()

        binding.buttonPublish.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        if (v.id == R.id.button_publish) {
            val title = binding.editTitle.text.toString()
            val news = binding.editNews.text.toString()
            val date = binding.editDate.text.toString()
            val author = binding.editAuthor.text.toString()

            if (title.isEmpty() || news.isEmpty() || date.isEmpty() || author.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
            } else {
                saveNews(title, news, date, author)
            }
        }
    }

    private fun saveNews(title: String, news: String, date: String, author: String) {
        val mapNews = hashMapOf(
            "Title" to title,
            "News" to news,
            "Date" to date,
            "Author" to author
        )

        db.collection("News").document("News")
            .set(mapNews).addOnCompleteListener { task ->
                if (task.isSuccessful){
                    Toast.makeText(this, "Notícia publicada com sucesso!", Toast.LENGTH_SHORT).show()
                    clearData()
                }
            }.addOnFailureListener {

            }
    }

    private fun clearData() {
        binding.editTitle.setText("")
        binding.editNews.setText("")
        binding.editDate.setText("")
        binding.editAuthor.setText("")
    }
}