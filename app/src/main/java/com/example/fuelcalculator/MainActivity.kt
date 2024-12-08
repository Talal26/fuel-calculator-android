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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fuelcalculator.ui.theme.FuelCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FuelCalculatorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    FuelCalculatorApp(
                        modifier = Modifier.fillMaxSize()
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
    val totalFuelNeeded = fuelPerLap * numberOfLaps

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            "Total Race Time",
            textAlign = TextAlign.Left,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp)
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            NumberField(
                value = raceHoursInput,
                label = "Hours",
                onValueChange = { raceHoursInput = it },
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f)
            )

            Text(":")

            NumberField(
                value = raceMinutesInput,
                label = "Minutes",
                onValueChange = { raceMinutesInput = it },
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f)
            )
        }

        Text(
            "Lap Time",
            textAlign = TextAlign.Left,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp)
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            NumberField(
                value = lapMinutesInput,
                label = "Minutes",
                onValueChange = { lapMinutesInput = it },
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f)
            )

            Text(":")

            NumberField(
                value = lapSecondsInput,
                label = "Seconds",
                onValueChange = { lapSecondsInput = it },
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f)
            )

        }

        NumberField(
            value = fuelPerLapInput,
            label = "Fuel Consumption Per Lap (L)",
            onValueChange = { fuelPerLapInput = it },
            modifier = Modifier
                .padding(8.dp),
            imeAction = ImeAction.Done
        )

        Spacer(Modifier.height(16.dp))

        Text(
            "Number of laps ${numberOfLaps.format(1)}",
            fontSize = 24.sp,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Text(
            "Total Fuel Needed: ${totalFuelNeeded.format(1)} L",
            fontSize = 24.sp
        )
    }
}
