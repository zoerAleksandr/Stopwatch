package com.gb.stopwatch.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gb.stopwatch.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModel()
    private val mainScope: CoroutineScope by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val textTimeOne = binding.stopwatchOneLayout.textTime
        val textTimeTwo = binding.stopwatchTwoLayout.textTime
        mainScope.launch {
            viewModel.tickerOne.collect {
                textTimeOne.text = it
            }
        }

        mainScope.launch {
            viewModel.tickerTwo.collect{
                textTimeTwo.text = it
            }
        }

        binding.stopwatchOneLayout.buttonStart.setOnClickListener {
            viewModel.start(NumberStopWatcher.ONE)
        }
        binding.stopwatchOneLayout.buttonPause.setOnClickListener {
            viewModel.pause(NumberStopWatcher.ONE)
        }
        binding.stopwatchOneLayout.buttonStop.setOnClickListener {
            viewModel.stop(NumberStopWatcher.ONE)
        }

        binding.stopwatchTwoLayout.buttonStart.setOnClickListener {
            viewModel.start(NumberStopWatcher.TWO)
        }
        binding.stopwatchTwoLayout.buttonPause.setOnClickListener {
            viewModel.pause(NumberStopWatcher.TWO)
        }
        binding.stopwatchTwoLayout.buttonStop.setOnClickListener {
            viewModel.stop(NumberStopWatcher.TWO)
        }
    }
}