package com.example.fuelcalculator.ui.test

import com.example.fuelcalculator.ui.FuelViewModel
import org.junit.Assert.assertEquals
import org.junit.Test

class FuelViewModelTest {
    private val viewModel = FuelViewModel()

    @Test
    fun fuelViewModel_CorrectOutput() {
        viewModel.updateRaceMinutes("25")
        viewModel.updateLapSeconds("35")
        viewModel.updateFuelPerLap("2.66")
        viewModel.updateLapsMargin("1")

        val currentUiState = viewModel.uiState.value

        assertEquals(currentUiState.numberOfLaps, 16.8, 0.1)
        assertEquals(currentUiState.fuelNeeded, 45.2, 0.1)
    }
}
