package com.example.activitynavigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewbinding.BuildConfig
import com.example.activitynavigation.databinding.ActivityAboutBinding

class AboutActivity : BaseActivity() {

    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvAppName2.text = BuildConfig.LIBRARY_PACKAGE_NAME
        binding.tvVerCode2.text = BuildConfig.BUILD_TYPE
        binding.btnOk.setOnClickListener { onOkPressed() }
    }

    private fun onOkPressed(){
        finish()
    }
}