package com.gb.stopwatch.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import com.gb.stopwatch.R
import com.gb.stopwatch.data.StopwatchStateHolder
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.java.KoinJavaComponent.inject

class MainViewModel(
    private val stopwatchStateHolderOne: StopwatchStateHolder,
    private val stopwatchStateHolderTwo: StopwatchStateHolder,
    private val scope: CoroutineScope
) : ViewModel() {
    private val context: Application by inject(Application::class.java)
    private val defaultValueTime = context.getString(R.string.default_time)
    private var jobOne: Job? = null
    private var jobTwo: Job? = null
    private val mutableTickerOne = MutableStateFlow(defaultValueTime)
    private val mutableTickerTwo = MutableStateFlow(defaultValueTime)
    val tickerOne: StateFlow<String> = mutableTickerOne
    val tickerTwo: StateFlow<String> = mutableTickerTwo

    fun start(number: NumberStopWatcher) {
        startJob(number)
    }

    private fun startJob(number: NumberStopWatcher) {
        when (number) {
            NumberStopWatcher.ONE -> {
                stopwatchStateHolderOne.start()
                println(stopwatchStateHolderOne.toString())
                jobOne = scope.launch {
                    while (isActive) {
                        mutableTickerOne.value =
                            stopwatchStateHolderOne.getStringTimeRepresentation()
                        delay(20)
                    }
                }
            }
            NumberStopWatcher.TWO -> {
                stopwatchStateHolderTwo.start()
                println(stopwatchStateHolderTwo.toString())
                jobTwo = scope.launch {
                    while (isActive) {
                        mutableTickerTwo.value =
                            stopwatchStateHolderTwo.getStringTimeRepresentation()
                        delay(20)
                    }
                }
            }
        }

    }

    fun pause(number: NumberStopWatcher) {
        when (number) {
            NumberStopWatcher.ONE -> {
                stopwatchStateHolderOne.pause()
            }
            NumberStopWatcher.TWO -> {
                stopwatchStateHolderTwo.pause()
            }
        }
        stopJob(number)
    }

    fun stop(number: NumberStopWatcher) {
        when (number) {
            NumberStopWatcher.ONE -> {
                stopwatchStateHolderOne.stop()
            }
            NumberStopWatcher.TWO -> {
                stopwatchStateHolderTwo.stop()
            }
        }
        stopJob(number)
        clearValue(number)
    }

    private fun stopJob(number: NumberStopWatcher) {
        when (number) {
            NumberStopWatcher.ONE -> {
                jobOne = null
            }
            NumberStopWatcher.TWO -> {
                jobTwo = null
            }
        }
    }

    private fun clearValue(number: NumberStopWatcher) {
        when (number) {
            NumberStopWatcher.ONE -> {
                mutableTickerOne.value = defaultValueTime
            }
            NumberStopWatcher.TWO -> {
                mutableTickerTwo.value = defaultValueTime
            }
        }
    }

    override fun onCleared() {
        scope.coroutineContext.cancel()
        super.onCleared()
    }
}