package com.example.fuelcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.fuelcalculator.ui.theme.FuelCalculatorTheme
import com.example.fuelcalculator.ui.theme.Typography
import kotlin.math.ceil

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FuelCalculatorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    FuelCalculatorApp(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(dimensionResource(R.dimen.outer_padding))
                    )
                }
            }
        }
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

private fun calculateNumberOfLaps(totalRaceTimeInMinutes: Int, lapTimeInSeconds: Double) : Double {
    val lapTimeInMinutes = lapTimeInSeconds / 60

    return totalRaceTimeInMinutes / lapTimeInMinutes
}

@Preview(showBackground = true)
@Composable
fun FuelCalculatorApp(modifier: Modifier = Modifier) {
    var raceHoursInput by remember { mutableStateOf("") }
    var raceMinutesInput by remember { mutableStateOf("") }
    var lapMinutesInput by remember { mutableStateOf("1") }
    var lapSecondsInput by remember { mutableStateOf("") }
    var fuelPerLapInput by remember { mutableStateOf("") }

    val raceHours = raceHoursInput.toIntOrNull() ?: 0
    val raceMinutes = raceMinutesInput.toIntOrNull() ?: 0

    val lapMinutes = lapMinutesInput.toIntOrNull() ?: 0
    val lapSeconds = lapSecondsInput.toDoubleOrNull() ?: 0.0

    val fuelPerLap  = fuelPerLapInput.toDoubleOrNull() ?: 0.0

    val totalRaceTimeInMinutes = raceHours * 60 + raceMinutes
    val lapTimeInSeconds = lapMinutes * 60 + lapSeconds

    val numberOfLaps = calculateNumberOfLaps(totalRaceTimeInMinutes, lapTimeInSeconds)
    val totalFuelNeeded = fuelPerLap * ceil(numberOfLaps)

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
                value = raceHoursInput,
                label = "Hours",
                onValueChange = { raceHoursInput = it },
                modifier = Modifier
                    .weight(1f)
            )

            Colon()

            NumberField(
                value = raceMinutesInput,
                label = "Minutes",
                onValueChange = { raceMinutesInput = it },
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
                value = lapMinutesInput,
                label = "Minutes",
                onValueChange = { lapMinutesInput = it },
                modifier = Modifier
                    .weight(1f)
            )

            Colon()

            NumberField(
                value = lapSecondsInput,
                label = "Seconds",
                onValueChange = { lapSecondsInput = it },
                modifier = Modifier
                    .weight(1f)
            )
        }

        NumberField(
            value = fuelPerLapInput,
            label = "Fuel Consumption Per Lap (L)",
            onValueChange = { fuelPerLapInput = it },
            modifier = Modifier
                .padding(dimensionResource(R.dimen.padding_small)),
            imeAction = ImeAction.Done
        )

        Spacer(Modifier.height(dimensionResource(R.dimen.padding_medium)))

        Text(
            "Number of laps ${numberOfLaps.format(1)}",
            style = Typography.labelLarge,
            modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_small))
        )

        Text(
            "Total Fuel Needed: ${totalFuelNeeded.format(1)} L",
            style = Typography.labelLarge,
            modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_small))
        )
    }
}
