package com.example.activitynavigation

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.activitynavigation.OptionsActivity.Companion.EXTRA_OPTIONS
import com.example.activitynavigation.databinding.ActivityMainBinding
import com.example.activitynavigation.model.Options
import java.lang.IllegalArgumentException

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var options: Options

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        binding.btnOpenBox.setOnClickListener { onOpenBoxPressed() }
        binding.btnOptions.setOnClickListener { onOptionsPressed() }
        binding.btnAbout.setOnClickListener { onAboutPressed() }
        binding.btnExit.setOnClickListener { onExitPressed() }

        options = savedInstanceState?.getParcelable(KEY_OPTIONS) ?: Options.DEFAULT
        Log.d("MY_TAG", "onCreate(${javaClass.simpleName}): $savedInstanceState")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == OPTIONS_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            options = data?.getParcelableExtra(EXTRA_OPTIONS) ?:
            throw IllegalArgumentException("Can't get the updated data from options activity")
        }
        Log.d("MY_TAG", "onActivityResult: $options")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_OPTIONS, options)
        Log.d("MY_TAG", "onSaveInstanceState(${javaClass.simpleName}): $outState")
    }

    private fun onOpenBoxPressed() {
        val intent = Intent(this, BoxSelectionActivity::class.java)
        intent.putExtra(BoxSelectionActivity.EXTRA_OPTIONS, options)
        startActivity(intent)
    }

    private fun onAboutPressed(){
        val intent = Intent(this, AboutActivity::class.java)
        startActivity(intent)
    }

    private fun onExitPressed(){
        finish()
    }
    private fun onOptionsPressed(){
        val intent = Intent(this, OptionsActivity::class.java)
        intent.putExtra(EXTRA_OPTIONS, options)
        startActivityForResult(intent, OPTIONS_REQUEST_CODE)
    }

    companion object{
        private val KEY_OPTIONS = "KEY_OPTIONS"
        private val OPTIONS_REQUEST_CODE = 1
    }

}