package com.example.fuelcalculator.ui.test

import com.example.fuelcalculator.ui.FuelViewModel
import org.junit.Test
import org.junit.Assert.assertEquals

class FuelViewModelTest {
    private val viewModel = FuelViewModel()

    @Test
    fun fuelViewModel_CorrectOutput() {
        var currentUiState = viewModel.uiState.value

        viewModel.updateRaceMinutes("25")
        viewModel.updateLapSeconds("35")
        viewModel.updateFuelPerLap("2.66")
        viewModel.updateLapsMargin("")

        currentUiState = viewModel.uiState.value

        assertEquals(currentUiState.numberOfLaps, 15.8, 0.1)
        assertEquals(currentUiState.fuelNeeded, 42.6, 0.1)
    }
}
