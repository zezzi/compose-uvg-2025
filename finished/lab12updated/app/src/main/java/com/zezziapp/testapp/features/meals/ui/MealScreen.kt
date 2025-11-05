package com.zezziapp.testapp.features.meals.ui

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.ui.Alignment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealScreen(
    vm: MealViewModel = hiltViewModel(),
    onNavigateDetail: (String) -> Unit = {}
) {
    val s = vm.state
    val ctx = LocalContext.current

    LaunchedEffect(Unit) {
        vm.effects.collect { e ->
            when (e) {
                is MealContract.Effect.NavigateToDetail -> onNavigateDetail(e.id)
                is MealContract.Effect.ShowMessage -> Toast.makeText(ctx, e.text, Toast.LENGTH_SHORT).show()
            }
        }
    }

    LaunchedEffect(Unit) {
        vm.onIntent(MealContract.Intent.Load)
    }

    Scaffold(topBar = { TopAppBar(title = { Text("Meal Categories") }) }) { p ->
        Column(Modifier.padding(p).fillMaxSize()) {
            OutlinedTextField(
                value = s.query,
                onValueChange = { vm.onIntent(MealContract.Intent.SearchChanged(it)) },
                modifier = Modifier.fillMaxWidth().padding(12.dp),
                label = { Text("Search") }
            )
            when {
                s.isLoading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
                s.error != null -> ErrorBox(s.error!!) {
                    vm.onIntent(MealContract.Intent.Retry)
                }
                else -> LazyColumn(Modifier.fillMaxSize()) {
                    items(s.filtered, key = { it.id }) { cat ->
                        ListItem(
                            headlineContent = { Text(cat.name) },
                            supportingContent = { Text(cat.description, maxLines = 2, overflow = TextOverflow.Ellipsis) },
                            leadingContent = { AsyncImage(model = cat.thumbnailUrl, contentDescription = null, modifier = Modifier.size(56.dp)) },
                            modifier = Modifier.clickable { vm.onIntent(MealContract.Intent.CategoryClicked(cat.id)) }
                        )
                        Divider()
                    }
                }
            }
        }
    }
}

@Composable
private fun ErrorBox(message: String, onRetry: () -> Unit) {
    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text("Error: $message"); Spacer(Modifier.height(8.dp)); Button(onClick = onRetry) { Text("Retry") }
    }
}