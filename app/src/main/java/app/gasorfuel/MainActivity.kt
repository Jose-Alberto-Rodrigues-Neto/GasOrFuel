package app.gasorfuel

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.collection.objectFloatMapOf
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.gasorfuel.ui.theme.GasOrFuelTheme
import kotlin.math.log

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

fun calcValue(gasPrice: Float, alcoholPrice: Float, percent: Int): String {
    val gasThreshold = gasPrice * (percent / 100.0f)
    return if (alcoholPrice <= gasThreshold) {
        "Álcool é mais rentável"
    } else {
        "Gasolina é mais rentável"
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainPage(modifier: Modifier) {
    var namePost by rememberSaveable { mutableStateOf<String>("") }
    var gasInput by rememberSaveable { mutableStateOf<String>("") }
    var alcoholInput by rememberSaveable { mutableStateOf<String>("") }
    var isChecked by rememberSaveable { mutableStateOf(false) }
    var result by rememberSaveable { mutableStateOf("") }
    val percent = if (isChecked) 75 else 70

    Scaffold(
        topBar = { MyTopBar() },
        content = { innerPadding ->
            Column(
                modifier = modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = alcoholInput,
                    onValueChange = { alcoholInput = it },
                    label = { Text("Insira o preço do álcool/litro") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )
                OutlinedTextField(
                    value = gasInput,
                    onValueChange = { gasInput = it },
                    label = { Text("Insira o preço da gasolina/litro") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )
                OutlinedTextField(
                    value = namePost,
                    onValueChange = { namePost = it },
                    label = { Text("Insira o nome do posto") }
                )
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {
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
                Button(
                    onClick = {
                        if (gasInput.isNotBlank() && alcoholInput.isNotBlank()) {
                            val gasPrice = gasInput.toFloatOrNull() ?: 0.0f
                            val alcoholPrice = alcoholInput.toFloatOrNull() ?: 0.0f
                            result = calcValue(gasPrice, alcoholPrice, percent)
                        } else {
                            result = ""
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                ) {
                    Text(text = "Calcular", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
                if (result.isNotBlank()) {
                    Text(
                        text = result,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
            }
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar() {
    TopAppBar(
        title = {
            Text(text = "Gasolina ou Álcool?")
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = Color(0xFF6200EE),
            titleContentColor = Color.White
        ),
    )
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