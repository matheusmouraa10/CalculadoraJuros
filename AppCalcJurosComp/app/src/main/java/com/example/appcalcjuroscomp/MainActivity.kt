package com.example.appcalcjuroscomp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlin.math.pow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorApp()
        }
    }
}

@Composable
fun CalculatorApp() {
    var principal by remember { mutableStateOf(0.0) }
    var rate by remember { mutableStateOf(0.0) }
    var time by remember { mutableStateOf(0) }
    var result by remember { mutableStateOf(0.0) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CalculatorInput("Valor Principal", principal) { value -> principal = value }
        CalculatorInput("Taxa de Juros Anual (%)", rate) { value -> rate = value }
        CalculatorInput("Tempo (Anos)", time) { value -> time = value.toInt() }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                result = calculateCompoundInterest(principal, rate, time)
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Calcular")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Valor Final: $%.2f".format(result),
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorInput(label: String, value: Any, onValueChange: (Double) -> Unit) {
    var text by remember { mutableStateOf(value.toString()) }

    OutlinedTextField(
        value = text,
        onValueChange = {
            text = it
            onValueChange(it.toDoubleOrNull() ?: 0.0)
        },
        label = { Text(text = label) },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        keyboardActions = KeyboardActions(
            onDone = { /* Realiza o calculo através do teclado se necessário */ }
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}

fun calculateCompoundInterest(principal: Double, rate: Double, time: Int): Double {
    val r = rate / 100
    return principal * (1 + r).pow(time)
}