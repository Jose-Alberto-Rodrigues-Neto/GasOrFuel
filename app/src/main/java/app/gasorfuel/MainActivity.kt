package app.gasorfuel

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.gasorfuel.ui.theme.AppTheme
import app.gasorfuel.ui.theme.AppTypography

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
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
    var namePost by rememberSaveable { mutableStateOf("") }
    var gasInput by rememberSaveable { mutableStateOf("") }
    var alcoholInput by rememberSaveable { mutableStateOf("") }
    var isChecked by rememberSaveable { mutableStateOf(false) }
    var result by rememberSaveable { mutableStateOf("") }
    val percent = if (isChecked) 75 else 70

    Scaffold(
        topBar = { MyTopBar() },
        content = {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth()
                ){
                    Image(
                        modifier = Modifier.fillMaxWidth(),
                        painter = painterResource(R.drawable.um_posto_de_gasolina_com_os_valores_da_gasolina_e_do__lcool_real_ados),
                        contentDescription = "Imagem do App"
                    )
                    if (result.isNotBlank()) {
                        Text(
                            text = result,
                            color = Color.White,
                            style = AppTypography.headlineLarge,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.W300,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth().padding(top = 300.dp)
                        )
                    }
                }
                OutlinedTextField(
                    value = alcoholInput,
                    onValueChange = { alcoholInput = it },
                    label = { Text("Insira o preço do álcool/litro", style = AppTypography.labelLarge) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )
                OutlinedTextField(
                    value = gasInput,
                    onValueChange = { gasInput = it },
                    label = { Text("Insira o preço da gasolina/litro", style = AppTypography.labelLarge) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )
                OutlinedTextField(
                    value = namePost,
                    onValueChange = { namePost = it },
                    label = { Text("Insira o nome do posto", style = AppTypography.labelLarge) }
                )
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "70%",
                        fontSize = 20.sp,
                        style = AppTypography.displayLarge
                    )
                    Switch(
                        checked = isChecked,
                        onCheckedChange = { isChecked = it }
                    )
                    Text(
                        text = "75%",
                        fontSize = 20.sp,
                        style = AppTypography.displayLarge
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
                    colors = ButtonDefaults.buttonColors(
                        contentColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                ) {
                    Text(text = "Calcular",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        style = AppTypography.bodyLarge
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
            Text(text = "Gasolina ou Álcool?", style = AppTypography.titleLarge)
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primaryContainer
        ),
    )
}

@Preview(showBackground = true)
@Composable
fun MainPagePreview() {
    AppTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MainPage(
                modifier = Modifier
                    .padding(innerPadding),
            )
        }
    }

}