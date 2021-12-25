package com.example.activitynavigation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.activitynavigation.databinding.ActivityBoxSelectionBinding
import com.example.activitynavigation.databinding.ItemBoxBinding
import com.example.activitynavigation.model.Options
import java.lang.IllegalArgumentException
import kotlin.math.max
import kotlin.properties.Delegates
import kotlin.random.Random

class BoxSelectionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBoxSelectionBinding

    private lateinit var options: Options

    private lateinit var timer: CountDownTimer

    private var timerStartTimestamp by Delegates.notNull<Long>()
    private var boxIndex by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBoxSelectionBinding.inflate(layoutInflater).also { setContentView(it.root) }

        options = intent.getParcelableExtra(EXTRA_OPTIONS) ?:
            throw IllegalArgumentException("Can't launch BoxSelectionActivity without options")
        boxIndex = savedInstanceState?.getInt(KEY_INDEX) ?: Random.nextInt(options.boxCount)

        if (options.isTimerEnabled){
            timerStartTimestamp = savedInstanceState?.getLong(KEY_START_TIMESTAMP) ?: System.currentTimeMillis()

            setupTimer()
            updateTimerUi()
        }

        createBoxes()
    }

    private fun createBoxes() {
        val boxBindings = (0 until options.boxCount).map { index ->
            val boxBinding = ItemBoxBinding.inflate(layoutInflater)
            boxBinding.root.id = View.generateViewId()
            boxBinding.tvBoxTitle.text = "Box ${index+1}"
            boxBinding.root.setOnClickListener { view -> onBoxSelected(view) }
            boxBinding.root.tag = index
            binding.root.addView(boxBinding.root)
            boxBinding
        }

        binding.flow.referencedIds = boxBindings.map { it.root.id }.toIntArray()
    }

    private fun onBoxSelected(view: View){
        val answer = view.tag as Int
        if (answer == boxIndex){
            val intent = Intent(this, Box2Activity::class.java)
            startActivity(intent)
        } else{
          Toast.makeText(this, "This box is empty. Try another one.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupTimer(){
        timer = object : CountDownTimer(getRemainingSeconds() * 1000-1000, 1000){
            override fun onTick(p0: Long) {
                updateTimerUi()
            }

            override fun onFinish() {
                updateTimerUi()
                showTimerEndDialog()
            }

        }
    }

    private fun updateTimerUi(){
        if (getRemainingSeconds() > 0){
            binding.tvTimer.visibility = View.VISIBLE
            binding.tvTimer.text = "timer value: " + getRemainingSeconds()
        }else{
            binding.tvTimer.visibility = View.GONE
        }
    }

    override fun onStart() {
        super.onStart()
        if (options.isTimerEnabled){
            timer.start()
        }
    }

    override fun onStop() {
        super.onStop()
        if (options.isTimerEnabled){
            timer.cancel()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_INDEX, boxIndex)
        if (options.isTimerEnabled){
            outState.putLong(KEY_START_TIMESTAMP, timerStartTimestamp)
        }
    }

    private fun showTimerEndDialog(){
        val dialog = AlertDialog.Builder(this)
            .setTitle("The end")
            .setMessage("Oops^ there is no enough time, try again later.")
            .setCancelable(false)
            .setPositiveButton("Ok"){ dialog, which ->
//                dialog.dismiss()
                finish()
            }
            .create()
        dialog.show()
    }

    private fun getRemainingSeconds(): Long{
        val finishedAt = timerStartTimestamp + TIMER_DURATION
        return max(0, (finishedAt - System.currentTimeMillis()) / 1000)
    }

    companion object{
        val EXTRA_OPTIONS = "EXTRA_OPTIONS"
        private val KEY_INDEX = "KEY_INDEX"
        private val KEY_START_TIMESTAMP = "KEY_START_TIMESTAMP"

        private val TIMER_DURATION = 10000
    }
}