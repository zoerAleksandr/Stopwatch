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

        val textView = binding.textTime
        mainScope.launch {
            viewModel.ticker.collect {
                textView.text = it
            }
        }

        binding.buttonStart.setOnClickListener {
            viewModel.start()
        }
        binding.buttonPause.setOnClickListener {
            viewModel.pause()
        }
        binding.buttonStop.setOnClickListener {
            viewModel.stop()
        }
    }
}