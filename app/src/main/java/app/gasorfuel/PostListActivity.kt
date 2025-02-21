package app.gasorfuel

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.gasorfuel.manager.GasStationManager
import app.gasorfuel.model.GasStation
import app.gasorfuel.ui.theme.AppTheme

class PostListActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(stringResource(R.string.stations_list)) }
                        )
                    },
                    content = { innerPadding ->
                        PostListScreen(modifier = Modifier.padding(innerPadding))
                    }
                )
            }
        }
    }
}

@Composable
fun PostListScreen(modifier: Modifier) {
    val context = LocalContext.current
    val postList = remember { mutableStateListOf<GasStation>().apply { addAll(loadPostList(context)) } }

    val editLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            postList.clear()
            postList.addAll(loadPostList(context))
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn {
            items(postList) { post ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "${stringResource(R.string.txt_nome)}: ${post.name}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = "${stringResource(R.string.txt_gas)}: R$${post.gasPrice}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "${stringResource(R.string.txt_alc)}: R$${post.alcoholPrice}",
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                            ) {
                                Button(
                                    onClick = {
                                        val intent = Intent(context, PostDetailActivity::class.java).apply {
                                            putExtra("POST_ID", post.id)
                                        }
                                        editLauncher.launch(intent)
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    Text(text = stringResource(R.string.txt_edit))
                                }

                                Button(
                                    onClick = { deleteStationList(context, post.id, postList) },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 8.dp)
                                ) {
                                    Text(text = stringResource(R.string.txt_deletar))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


fun loadPostList(context: Context): List<GasStation> {
    return GasStationManager.loadStations(context)
}
fun deleteStationList(context: Context, id: Int, postList: MutableList<GasStation>) {
    GasStationManager.deleteStation(context, id)
    postList.removeAll { it.id == id }
}