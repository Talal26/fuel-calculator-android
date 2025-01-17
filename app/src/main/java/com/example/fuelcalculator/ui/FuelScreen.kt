package com.example.fuelcalculator.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fuelcalculator.R
import com.example.fuelcalculator.ui.theme.Typography

@Preview(showBackground = true)
@Composable
fun FuelScreen(
    fuelViewModel: FuelViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val fuelUiState by fuelViewModel.uiState.collectAsState()

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        RowHeader("Total Race Time")

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_small))
        ) {
            NumberField(
                value = fuelViewModel.raceHoursInput,
                label = "Hours",
                onValueChange = { fuelViewModel.updateRaceHours(it) },
                modifier = Modifier
                    .weight(1f)
            )

            Colon()

            NumberField(
                value = fuelViewModel.raceMinutesInput,
                label = "Minutes",
                onValueChange = { fuelViewModel.updateRaceMinutes(it) },
                modifier = Modifier
                    .weight(1f)
            )
        }

        RowHeader("Lap Time")

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_small))
        ) {
            NumberField(
                value = fuelViewModel.lapMinutesInput,
                label = "Minutes",
                onValueChange = { fuelViewModel.updateLapMinutes(it) },
                modifier = Modifier
                    .weight(1f)
            )

            Colon()

            NumberField(
                value = fuelViewModel.lapSecondsInput,
                label = "Seconds",
                onValueChange = { fuelViewModel.updateLapSeconds(it) },
                modifier = Modifier
                    .weight(1f)
            )
        }

        RowHeader("Fuel Calculation")

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(vertical = dimensionResource(R.dimen.padding_small))
        ) {
            NumberField(
                value = fuelViewModel.fuelPerLapInput,
                label = "Fuel Consumption Per Lap (L)",
                onValueChange = { fuelViewModel.updateFuelPerLap(it) },
                modifier = Modifier
                    .weight(1f)
            )

            Spacer(modifier = Modifier.width(16.dp))

            NumberField(
                value = fuelViewModel.lapsMarginInput,
                label = "Number of Laps Margin",
                onValueChange = { fuelViewModel.updateLapsMargin(it) },
                modifier = Modifier
                    .weight(1f),
                imeAction = ImeAction.Done
            )
        }

        HorizontalDivider(modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)))

        Text(
            "Number of laps ${fuelUiState.numberOfLaps.format(1)}",
            style = Typography.labelLarge,
            modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_small))
        )

        Text(
            "Total Fuel Needed: ${fuelUiState.fuelNeeded.format(1)} L",
            style = Typography.labelLarge,
            modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_small))
        )
    }
}

fun Double.format(digits: Int) = "%.${digits}f".format(this)

@Composable
fun NumberField(
    value: String,
    label: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    imeAction: ImeAction = ImeAction.Next
) {
    TextField(
        value = value,
        singleLine = true,
        modifier = modifier,
        onValueChange = onValueChange,
        label = { Text(label) },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number,
            imeAction = imeAction
        )
    )
}

@Composable
fun Colon(modifier: Modifier = Modifier) {
    Text(
        ":",
        fontSize = 20.sp,
        fontWeight = FontWeight.ExtraBold,
        modifier = modifier.padding(dimensionResource(R.dimen.padding_small))
    )
}

@Composable
fun RowHeader(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        style = Typography.labelSmall,
        textAlign = TextAlign.Left,
        modifier = modifier.fillMaxWidth()
    )
}
