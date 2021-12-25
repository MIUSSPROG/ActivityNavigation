package com.example.activitynavigation

import android.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CheckBox
import com.example.activitynavigation.databinding.ActivityOptionsBinding
import com.example.activitynavigation.model.Options
import java.lang.IllegalArgumentException

class OptionsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOptionsBinding
    private lateinit var options: Options
    private lateinit var boxCountItems: List<BoxCountItem>
    private lateinit var adapter: ArrayAdapter<BoxCountItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOptionsBinding.inflate(layoutInflater).also { setContentView(it.root) }

        options = savedInstanceState?.getParcelable(KEY_OPTIONS) ?:
            intent.getParcelableExtra(EXTRA_OPTIONS) ?:
            throw IllegalArgumentException("You need to specify EXTRA_OPTIONS argument to launch this activity")

        setupSpinner()
        setupCheckBox()
        updateUi()

        binding.btnCancel.setOnClickListener { onCancelPressed() }
        binding.btnConfirm.setOnClickListener { onConfirmPressed() }
        Log.d("MY_TAG", "onCreate(${javaClass.simpleName}): ${savedInstanceState.toString()}")
        Log.d("MY_TAG", "onCreate(${javaClass.simpleName}): $options")
    }

    private fun onConfirmPressed() {
        val intent = Intent()
        intent.putExtra(EXTRA_OPTIONS, options)
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun onCancelPressed() {
        finish()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_OPTIONS, options)
        Log.d("MY_TAG", "onSaveInstanceState(${javaClass.simpleName}): ${outState.toString()}")
    }

    private fun setupCheckBox(){
        binding.cbEnableTimer.setOnClickListener{
            options = options.copy(isTimerEnabled = (it as CheckBox).isChecked)
        }
    }

    private fun updateUi(){
        binding.cbEnableTimer.isChecked = options.isTimerEnabled

        val currentIndex = boxCountItems.indexOfFirst { it.count == options.boxCount }
        binding.spBoxesCount.setSelection(currentIndex)
//        binding.spBoxesCount.setText(adapter.getItem(currentIndex).toString(), false)
//        with(binding.spinner){
//            listSelection = currentIndex
//            performCompletion()
//        }

//        binding.spinner.setText(options.boxCount)
    }

    private fun setupSpinner(){
        boxCountItems = (1..6).map { BoxCountItem(it, "$it boxes") }
        adapter = ArrayAdapter(this, R.layout.simple_dropdown_item_1line, boxCountItems)

        binding.spBoxesCount.adapter = adapter
        binding.spBoxesCount.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, p3: Long) {
                val count = boxCountItems[position].count
                options = options.copy(boxCount = count)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
//        binding.spinner.setAdapter(adapter)
//        binding.spinner.onItemClickListener =
//            AdapterView.OnItemClickListener { p0, view, position, p3 ->
//                val count = boxCountItems[position].count
//                options = options.copy(boxCount = count)
//            }

    }

    companion object{
        val EXTRA_OPTIONS = "EXTRA_OPTIONS"
        private val KEY_OPTIONS = "KEY_OPTIONS"
    }

    class BoxCountItem(
        val count: Int,
        private val optionTitle: String
    ){
        override fun toString(): String {
            return optionTitle
        }
    }


}