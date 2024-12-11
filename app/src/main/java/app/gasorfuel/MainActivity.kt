package app.gasorfuel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableFloatState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.gasorfuel.ui.theme.GasOrFuelTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GasOrFuelTheme {
                var namePost = remember { mutableStateOf("") }
                var gasInput by remember { mutableStateOf<String>("") }
                var alcoholInput by remember { mutableStateOf<String>("") }
                var isChecked by remember { mutableStateOf(false) }
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainPage(
                        modifier = Modifier
                            .padding(innerPadding)
                    )
                }
            }
        }
    }
}


@Composable
fun MainPage(modifier: Modifier){
    var namePost by rememberSaveable { mutableStateOf<String>("") }
    var gasInput by rememberSaveable { mutableStateOf<String>("") }
    var alcoholInput by rememberSaveable { mutableStateOf<String>("") }
    var isChecked by rememberSaveable { mutableStateOf(false) }
    Column {
            Text(
                modifier = Modifier,
                text = "Gasolina ou Álcool?",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
            )
            OutlinedTextField(
                value = namePost,
                onValueChange = { namePost = it },
                label = { Text("Insira o preço da gasolina/litro") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )
            OutlinedTextField(
                value = gasInput,
                onValueChange = { gasInput = it },
                label = { Text("Insira o preço da gasolina/litro") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )
            OutlinedTextField(
                value = alcoholInput,
                onValueChange = { alcoholInput = it},
                label = { Text("Insira o nome do posto") }
            )
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = "70%",
                    fontSize = 20.sp
                )
                Switch(
                    checked = isChecked,
                    onCheckedChange = { isChecked = it }
                )
                Text(
                    text = "75%",
                    fontSize = 20.sp
                )
            }
        }
}

@Preview(showBackground = true)
@Composable
fun MainPagePreview() {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        MainPage(
            modifier = Modifier
                .padding(innerPadding),
        )
    }
}