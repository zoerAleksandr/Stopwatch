package com.gb.stopwatch.di

import com.gb.stopwatch.data.StopwatchStateHolder
import com.gb.stopwatch.data.TimestampProviderImpl
import com.gb.stopwatch.domain.ElapsedTimeCalculator
import com.gb.stopwatch.domain.TimestampProvider
import com.gb.stopwatch.ui.MainViewModel
import com.gb.stopwatch.ui.StopwatchStateCalculator
import com.gb.stopwatch.ui.TimestampMillisecondsFormatter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val module = module {
    single {
        StopwatchStateHolder(
            stopwatchStateCalculator = get(),
            elapsedTimeCalculator = get(),
            timestampMillisecondsFormatter = get()
        )
    }
    single {
        StopwatchStateCalculator(
            timestampProvider = get(),
            elapsedTimeCalculator = get()
        )
    }

    single {
        ElapsedTimeCalculator(
            timestampProvider = get()
        )
    }

    single { TimestampMillisecondsFormatter() }

    factory<TimestampProvider> { TimestampProviderImpl() }

    viewModel {
        MainViewModel(
            stopwatchStateHolder = get(),
            scope = get()
        )
    }
}

val scope = module {
    factory { CoroutineScope(Dispatchers.Main + SupervisorJob()) }
}