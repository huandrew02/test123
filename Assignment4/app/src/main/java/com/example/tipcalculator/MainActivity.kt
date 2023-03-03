package com.example.tipcalculator

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
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

                val tips = listOf(0.15f, 0.2f, 0.25f)

                val tipAmounts = tips.map { tip ->
                    ((checkAmount.toFloatOrNull() ?: 0f) * tip / (splitCount.toIntOrNull()
                        ?: 1)).roundToInt()
                }

                val totalAmounts = tips.map { tip ->
                    ((checkAmount.toFloatOrNull() ?: 0f) * (1 + tip) / (splitCount.toIntOrNull()
                        ?: 1)).roundToInt()
                }

                tipsAndTotals = listOf(
                    "Tip: 15%" to "$${tipAmounts[0]}",
                    "Tip: 20%" to "$${tipAmounts[1]}",
                    "Tip: 25%" to "$${tipAmounts[2]}",
                    "Total: 15%" to "$${totalAmounts[0]}",
                    "Total: 20%" to "$${totalAmounts[1]}",
                    "Total: 25%" to "$${totalAmounts[2]}"
                )

            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Compute Tip")
        }

        LazyColumn {
            items(tipsAndTotals) { (tip, total) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = tip,
                        fontSize = 18.sp,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Start
                    )
                    Text(
                        text = total,
                        fontSize = 18.sp,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.End
                    )
                }
            }
        }
    }
}
