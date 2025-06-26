package com.example.hotcoffee.ui.welcome

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hotcoffee.MainActivity
import com.example.hotcoffee.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startButton.setOnClickListener {
            // Butona basınca MainActivity'ye geç
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Welcome ekranı geri gelmesin
        }
    }
}
