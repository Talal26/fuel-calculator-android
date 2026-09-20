package com.example.fuelcalculator

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.fuelcalculator.ui.FuelScreen
import com.example.fuelcalculator.ui.test.TestTags
import com.example.fuelcalculator.ui.theme.FuelCalculatorTheme
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@RunWith(AndroidJUnit4::class)
class FuelCalculatorUiTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setContent() {
        composeTestRule.setContent {
            FuelCalculatorTheme {
                FuelScreen(Modifier.fillMaxSize())
            }
        }
    }

    @Test
    fun inputValidation() {
        val integerFieldTags = listOf(
            TestTags.RACE_HOURS_FIELD,
            TestTags.LAP_MINUTES_FIELD,
            TestTags.SAFETY_MARGIN_FIELD
        )

        val decimalFieldTags = listOf(
            TestTags.RACE_MINUTES_FIELD,
            TestTags.LAP_SECONDS_FIELD,
            TestTags.FUEL_CONSUMPTION_FIELD
        )

        integerFieldTags.forEach {
            composeTestRule.onNodeWithTag(it).performTextClearance()

            // Rejects non-digit characters
            composeTestRule.onNodeWithTag(it).performTextInput(".")
            composeTestRule.onNodeWithTag(it).performTextInput(" ")
            composeTestRule.onNodeWithTag(it).performTextInput("a")
            assertEquals(
                "",
                getEditableTextFromTextField(composeTestRule.onNodeWithTag(it))
            )

            // Accepts digit characters
            composeTestRule.onNodeWithTag(it).performTextInput("1")
            assertEquals(
                "1",
                getEditableTextFromTextField(composeTestRule.onNodeWithTag(it))
            )
        }

        decimalFieldTags.forEach {
            composeTestRule.onNodeWithTag(it).performTextClearance()

            // Rejects decimal characters
            composeTestRule.onNodeWithTag(it).performTextInput(" ")
            composeTestRule.onNodeWithTag(it).performTextInput("a")
            assertEquals(
                "",
                getEditableTextFromTextField(composeTestRule.onNodeWithTag(it))
            )

            // Accepts digits and decimal point
            composeTestRule.onNodeWithTag(it).performTextInput("1.1")
            assertEquals(
                "1.1",
                getEditableTextFromTextField(composeTestRule.onNodeWithTag(it))
            )
        }
    }

    @Test
    fun correctCalculation() {
        val raceHoursInput = "0"
        val raceMinutesInput = "20"
        val lapMinutesInput = "1"
        val lapSecondsInput = "30.5"
        val fuelConsumptionInput = "2.6"
        val safetyMarginInput = "1"

        val lapResult = "14.3"
        val fuelResult = "39.0"

        // Entering inputs
        composeTestRule.onNodeWithTag(TestTags.RACE_HOURS_FIELD).performTextClearance()
        composeTestRule.onNodeWithTag(TestTags.RACE_HOURS_FIELD).performTextInput(raceHoursInput)

        composeTestRule.onNodeWithTag(TestTags.RACE_MINUTES_FIELD).performTextClearance()
        composeTestRule.onNodeWithTag(TestTags.RACE_MINUTES_FIELD).performTextInput(raceMinutesInput)

        composeTestRule.onNodeWithTag(TestTags.LAP_MINUTES_FIELD).performTextClearance()
        composeTestRule.onNodeWithTag(TestTags.LAP_MINUTES_FIELD).performTextInput(lapMinutesInput)

        composeTestRule.onNodeWithTag(TestTags.LAP_SECONDS_FIELD).performTextClearance()
        composeTestRule.onNodeWithTag(TestTags.LAP_SECONDS_FIELD).performTextInput(lapSecondsInput)

        composeTestRule.onNodeWithTag(TestTags.FUEL_CONSUMPTION_FIELD).performTextClearance()
        composeTestRule.onNodeWithTag(TestTags.FUEL_CONSUMPTION_FIELD).performTextInput(fuelConsumptionInput)

        composeTestRule.onNodeWithTag(TestTags.SAFETY_MARGIN_FIELD).performTextClearance()
        composeTestRule.onNodeWithTag(TestTags.SAFETY_MARGIN_FIELD).performTextInput(safetyMarginInput)

        // Checking output
        composeTestRule.onNodeWithTag(TestTags.LAP_CALCULATION_OUTPUT).assertTextContains(
            lapResult,
            substring = true
        )
        composeTestRule.onNodeWithTag(TestTags.FUEL_CALCULATION_OUTPUT).assertTextContains(
            fuelResult,
            substring = true
        )
    }
}

private fun getEditableTextFromTextField(node: SemanticsNodeInteraction): String {
    return node.fetchSemanticsNode().config[SemanticsProperties.EditableText].toString()
}
