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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fuelcalculator.ui.test.TestTags
import com.example.fuelcalculator.ui.theme.FuelCalculatorTheme
import com.example.fuelcalculator.ui.theme.Typography

@Composable
fun FuelScreen(
    modifier: Modifier = Modifier,
    fuelViewModel: FuelViewModel = viewModel()
) {
    val fuelUiState by fuelViewModel.uiState.collectAsState()

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        RowHeader("Race duration")

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            NumberField(
                value = fuelViewModel.raceHoursInput,
                label = "Hours",
                onValueChange = { fuelViewModel.updateRaceHours(it) },
                modifier = Modifier
                    .weight(1f)
                    .testTag(TestTags.RACE_HOURS_FIELD)
            )

            Colon()

            NumberField(
                value = fuelViewModel.raceMinutesInput,
                label = "Minutes",
                onValueChange = { fuelViewModel.updateRaceMinutes(it) },
                modifier = Modifier
                    .weight(1f)
                    .testTag(TestTags.RACE_MINUTES_FIELD)
            )
        }

        RowHeader("Lap Time")

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            NumberField(
                value = fuelViewModel.lapMinutesInput,
                label = "Minutes",
                onValueChange = { fuelViewModel.updateLapMinutes(it) },
                modifier = Modifier
                    .weight(1f)
                    .testTag(TestTags.LAP_MINUTES_FIELD)
            )

            Colon()

            NumberField(
                value = fuelViewModel.lapSecondsInput,
                label = "Seconds",
                onValueChange = { fuelViewModel.updateLapSeconds(it) },
                modifier = Modifier
                    .weight(1f)
                    .testTag(TestTags.LAP_SECONDS_FIELD)
            )
        }

        RowHeader("Fuel Calculation")

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(vertical = 8.dp)
        ) {
            NumberField(
                value = fuelViewModel.fuelPerLapInput,
                label = "Fuel Consumption",
                onValueChange = { fuelViewModel.updateFuelPerLap(it) },
                modifier = Modifier
                    .weight(1f)
                    .testTag(TestTags.FUEL_CONSUMPTION_FIELD),
                imeAction = ImeAction.Next,
                suffixText = "Litres/lap"
            )

            Spacer(modifier = Modifier.width(16.dp))

            NumberField(
                value = fuelViewModel.lapsMarginInput,
                label = "Safety margin",
                onValueChange = { fuelViewModel.updateLapsMargin(it) },
                modifier = Modifier
                    .weight(1f)
                    .testTag(TestTags.SAFETY_MARGIN_FIELD),
                imeAction = ImeAction.Done,
                suffixText = "Laps"
            )
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

        Text(
            buildAnnotatedString {
                append("Number of Laps: ")

                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(fuelUiState.numberOfLaps.format(1))
                }
            },
            style = Typography.labelLarge,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Text(
            buildAnnotatedString {
                append("Total Fuel Needed: ")

                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(fuelUiState.fuelNeeded.format(1))
                    append("L")
                }
            },
            style = Typography.labelLarge,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}

private fun Double.format(digits: Int) = "%.${digits}f".format(this)

@Composable
fun NumberField(
    value: String,
    label: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    imeAction: ImeAction = ImeAction.Next,
    suffixText: String? = null
) {
    TextField(
        value = value,
        singleLine = true,
        modifier = modifier,
        onValueChange = onValueChange,
        label = { Text(label, maxLines = 1) },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number,
            imeAction = imeAction
        ),
        suffix = { suffixText?.let { Text(it) } }
    )
}

@Composable
private fun Colon(modifier: Modifier = Modifier) {
    Text(
        ":",
        fontSize = 20.sp,
        fontWeight = FontWeight.ExtraBold,
        modifier = modifier.padding(8.dp)
    )
}

@Composable
private fun RowHeader(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        style = Typography.labelSmall,
        textAlign = TextAlign.Left,
        modifier = modifier.fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
private fun FuelScreenPreview() {
    FuelCalculatorTheme {
        FuelScreen()
    }
}
