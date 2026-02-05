package com.example.fuelcalculator.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.math.ceil

class FuelViewModel : ViewModel() {
    var raceHoursInput by mutableStateOf("")
        private set

    var raceMinutesInput by mutableStateOf("")
        private set

    var lapMinutesInput by mutableStateOf("1")
        private set

    var lapSecondsInput by mutableStateOf("")
        private set

    var fuelPerLapInput by mutableStateOf("")
        private set

    var lapsMarginInput by mutableStateOf("1")
        private set

    private val _uiState = MutableStateFlow(FuelUiState())
    val uiState: StateFlow<FuelUiState> = _uiState.asStateFlow()

    private fun calculate() {
        val raceHours = raceHoursInput.toIntOrNull() ?: 0
        val raceMinutes = raceMinutesInput.toIntOrNull() ?: 0
        val lapMinutes = lapMinutesInput.toIntOrNull() ?: 0
        val lapSeconds = lapSecondsInput.toDoubleOrNull() ?: 0.0
        val fuelPerLap = fuelPerLapInput.toDoubleOrNull() ?: 0.0
        val lapsMargin = lapsMarginInput.toIntOrNull() ?: 0

        val raceTimeInMinutes = raceHours * 60 + raceMinutes
        val lapTimeInMinutes = lapMinutes + lapSeconds / 60

        val numberOfLaps = (raceTimeInMinutes / lapTimeInMinutes) + lapsMargin
        val fuelNeeded = fuelPerLap * ceil(numberOfLaps)

        _uiState.update { currentState ->
            currentState.copy(
                numberOfLaps = numberOfLaps,
                fuelNeeded = fuelNeeded
            )
        }
    }

    fun updateRaceHours(input: String) {
        raceHoursInput = input
        calculate()
    }

    fun updateRaceMinutes(input: String) {
        raceMinutesInput = input
        calculate()
    }

    fun updateLapMinutes(input: String) {
        lapMinutesInput = input
        calculate()
    }

    fun updateLapSeconds(input: String) {
        lapSecondsInput = input
        calculate()
    }

    fun updateFuelPerLap(input: String) {
        fuelPerLapInput = input
        calculate()
    }

    fun updateLapsMargin(input: String) {
        lapsMarginInput = input
        calculate()
    }
}