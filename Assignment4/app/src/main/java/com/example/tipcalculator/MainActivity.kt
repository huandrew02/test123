package com.example.tipcalculator

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tipcalculator.ui.theme.TipCalculatorTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipCalculatorTheme {
                TipCalculator()
            }
        }
    }
}

@Composable
fun TipCalculator() {
    // Mutable state variables to hold the user input and calculated tip amounts
    var checkAmount by remember { mutableStateOf("") }
    var splitCount by remember { mutableStateOf("") }
    var tipsAndTotals by remember { mutableStateOf(emptyList<Pair<String, String>>()) }
    // Get the context to display Toast messages
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {

        Text(
            text = "Check amount:",
            fontSize = 18.sp,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        OutlinedTextField(
            value = checkAmount,
            onValueChange = { checkAmount = it },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = "Party Size:",
            fontSize = 18.sp,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        OutlinedTextField(
            value = splitCount,
            onValueChange = { splitCount = it },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                // Display a toast if either of the input fields is empty or contains invalid input
                if (checkAmount.isBlank() || splitCount.isBlank() ||
                    checkAmount.toFloatOrNull() == null || splitCount.toIntOrNull() == null) {
                    Toast.makeText(context, "Empty or incorrect value(s)", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                val tips = listOf(0.15f, 0.20f, 0.25f)

                val tipAmounts = tips.map { tip ->
                    (checkAmount.toFloat() * tip / splitCount.toInt()).roundToInt()
                }

                val totalAmounts = tips.map { tip ->
                    (checkAmount.toFloat() * (1 + tip) / splitCount.toInt()).roundToInt()
                }

                tipsAndTotals = listOf(
                    "15%" to "$${tipAmounts[0]}",
                    "20%" to "$${tipAmounts[1]}",
                    "25%" to "$${tipAmounts[2]}",
                    "15%" to "$${totalAmounts[0]}",
                    "20%" to "$${totalAmounts[1]}",
                    "25%" to "$${totalAmounts[2]}"
                )

            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Compute Tip")
        }

        Text(
            text = "Tips and Totals (per person)",
            fontSize = 30.sp
        )


        Row(
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            // Display tip amounts in a single row
            Text(
                text = "Tip:",
                fontSize = 18.sp,
            )
            tipsAndTotals.take(3).forEach { tip ->
                val formattedTip = "    ${tip.first} ${tip.second}"
                val spanStyle = SpanStyle(fontWeight = FontWeight.Bold)
                val annotatedString = AnnotatedString.Builder().apply {
                    append(formattedTip.substring(0, formattedTip.lastIndexOf(" ")))
                    pushStyle(spanStyle)
                    append(formattedTip.substring(formattedTip.lastIndexOf(" ")))
                    pop()
                }.toAnnotatedString()
                Text(
                    text = annotatedString,
                    fontSize = 18.sp,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Row(
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            // Display total amounts in a single row
            Text(
                text = "Total:",
                fontSize = 18.sp,
            )
            tipsAndTotals.takeLast(3).forEach { total ->
                val formattedTotal = "    ${total.first} ${total.second}"
                val spanStyle = SpanStyle(fontWeight = FontWeight.Bold)
                val annotatedString = AnnotatedString.Builder().apply {
                    append(formattedTotal.substring(0, formattedTotal.lastIndexOf(" ")))
                    pushStyle(spanStyle)
                    append(formattedTotal.substring(formattedTotal.lastIndexOf(" ")))
                    pop()
                }.toAnnotatedString()
                Text(
                    text = annotatedString,
                    fontSize = 18.sp,
                    modifier = Modifier.weight(1f)
                )
            }
        }

    }
}
