package com.example.activitynavigation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.activitynavigation.databinding.ActivityBoxBinding

class BoxActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBoxBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBoxBinding.inflate(layoutInflater).also { setContentView(binding.root) }

        binding.btnToMain.setOnClickListener { onToMainMenuPressed() }
    }

    private fun onToMainMenuPressed() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(intent)
    }
}