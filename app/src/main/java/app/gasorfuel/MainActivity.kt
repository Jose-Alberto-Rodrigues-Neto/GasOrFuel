package app.gasorfuel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
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
    var userInput by remember { mutableStateOf("") }
    GasOrFuelTheme {
        Column {
            Text(
                modifier = Modifier,
                text = "Gas or Fuel?",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
            )
            OutlinedTextField(value = "text", onValueChange = { userInput = it}, label = { Text("label") })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainPagePreview() {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        MainPage(
            modifier = Modifier
                .padding(innerPadding)
        )
    }
}