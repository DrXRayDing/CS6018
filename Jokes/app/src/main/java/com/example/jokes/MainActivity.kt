package com.example.jokes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel


class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp(viewModel)
        }
    }
}

// MyApp() composable function
@Composable
fun MyApp(viewModel: MainViewModel) {
    val jokes = viewModel.jokes.collectAsState().value

    // Layout for displaying jokes
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Button to fetch new joke
        Button(onClick = { viewModel.fetchJoke() }) {
            Text(text = "Get Joke")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // LazyColumn to display all jokes
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(jokes) { joke ->
                Text(text = joke)
            }
        }
    }
}

// Optional Preview for design-time
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApp(viewModel())
}