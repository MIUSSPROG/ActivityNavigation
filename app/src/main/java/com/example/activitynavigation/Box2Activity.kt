package com.example.activitynavigation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.activitynavigation.databinding.ActivityBox2Binding

class Box2Activity : AppCompatActivity() {

    private lateinit var binding: ActivityBox2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_box2)
        (findViewById<Button>(R.id.btnToMain)).setOnClickListener { onToMainMenuPressed() }
//        binding = ActivityBox2Binding.inflate(layoutInflater).also { setContentView(binding.root) }
    }

    private fun onToMainMenuPressed() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(intent)
    }
}