package app.gasorfuel

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import app.gasorfuel.manager.GasStationManager
import app.gasorfuel.model.GasStation
import app.gasorfuel.ui.theme.AppTheme

class PostDetailActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                val context = LocalContext.current
                val id = intent.getIntExtra("POST_ID", -1)
                val station = GasStationManager.loadStations(context).find { it.id == id }
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("Editar Posto") }
                        )
                    },
                    content = { paddingValues ->
                        if (station != null) {
                            PostDetailScreen(
                                context = context,
                                station = station,
                                modifier = Modifier.padding(paddingValues),
                                onSave = { updatedStation ->
                                    editStation(context, updatedStation.id, updatedStation)
                                    setResult(RESULT_OK)
                                    finish()
                                },
                            )
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun PostDetailScreen(context: Context, modifier: Modifier, station: GasStation, onSave: (GasStation) -> Unit) {
    var name by remember { mutableStateOf(TextFieldValue(station.name)) }
    var gasPrice by remember { mutableStateOf(TextFieldValue(station.gasPrice.toString())) }
    var alcoholPrice by remember { mutableStateOf(TextFieldValue(station.alcoholPrice.toString())) }
    val id = station.id.toString()
    val registrationDate = station.registrationDate




    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "ID: $id",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        Text(
            text = "Data de criação: $registrationDate",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nome") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = gasPrice,
            onValueChange = { gasPrice = it },
            label = { Text("Preço Gasolina") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )

        OutlinedTextField(
            value = alcoholPrice,
            onValueChange = { alcoholPrice = it },
            label = { Text("Preço Álcool") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )

        Button(
            onClick = {
                val updatedStation = station.copy(
                    name = name.text,
                    gasPrice = gasPrice.text.toFloatOrNull() ?: station.gasPrice,
                    alcoholPrice = alcoholPrice.text.toFloatOrNull() ?: station.alcoholPrice,
                )
                onSave(updatedStation)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Salvar")
        }
    }
}


fun navigateToPostDetail(context: Context, postId: Int) {
    val intent = Intent(context, PostDetailActivity::class.java).apply {
        putExtra("POST_ID", postId)
    }
    context.startActivity(intent)
}

fun editStation(context: Context, id: Int, updatedStation: GasStation) {
    GasStationManager.updateStation(context, updatedStation)
}
