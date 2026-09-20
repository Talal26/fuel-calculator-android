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
import com.example.fuelcalculator.ui.theme.FuelCalculatorTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@RunWith(AndroidJUnit4::class)
class FuelCalculatorUiTest {
    @get:Rule val composeTestRule = createComposeRule()

    @Test
    fun inputValidation() {
        composeTestRule.setContent {
            FuelCalculatorTheme {
                FuelScreen(Modifier.fillMaxSize())
            }
        }

        val integerFieldTags = listOf(
            "raceHoursField",
            "lapMinutesField",
            "safetyMarginField"
        )

        val decimalFieldTags = listOf(
            "raceMinutesField",
            "lapSecondsField",
            "fuelConsumptionField"
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

            // Rejects non-digit characters
            composeTestRule.onNodeWithTag(it).performTextInput(" ")
            composeTestRule.onNodeWithTag(it).performTextInput("a")
            assertEquals(
                "",
                getEditableTextFromTextField(composeTestRule.onNodeWithTag(it))
            )

            // Accepts digits and decimal point characters
            composeTestRule.onNodeWithTag(it).performTextInput("1.1")
            assertEquals(
                "1.1",
                getEditableTextFromTextField(composeTestRule.onNodeWithTag(it))
            )
        }
    }
}

private fun getEditableTextFromTextField(node: SemanticsNodeInteraction): String {
    return node.fetchSemanticsNode().config[SemanticsProperties.EditableText].toString()
}
