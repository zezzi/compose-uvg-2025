package com.zezziapp.testapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType.Companion.Text
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.zezziapp.testapp.features.auth.ui.AuthScreen
import com.zezziapp.testapp.features.auth.ui.AuthViewModel
import com.zezziapp.testapp.features.meals.ui.MealScreen
import com.zezziapp.testapp.model.CategoryUI
import com.zezziapp.testapp.model.MealUI
import com.zezziapp.testapp.ui.features.photos.views.PexelsScreen
import com.zezziapp.testapp.ui.theme.TestAppTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val authViewModel: AuthViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TestAppTheme {
                var isAuthenticated by remember { mutableStateOf(false) }

                if (isAuthenticated) {
                    MealScreen()
                } else {
                    AuthScreen(onAuthenticated = { isAuthenticated = true })
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComposeUiOnlyApp() {
    var selectedCategoryId by remember { mutableStateOf(sampleCategories.first().id) }

    val filteredMeals = remember(selectedCategoryId) {
        sampleMeals.filter { it.categoryId == selectedCategoryId }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Compose: remember + AsyncImage") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            val myTextFieldState = rememberTextFieldState("""
    Hello
    World
    Invisible
""".trimIndent())


            TextField(
                value = "test",
                onValueChange = {  }
            )
            Text(
                "Pick a category",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(sampleCategories) { cat ->
                    val isSelected = cat.id == selectedCategoryId
                    CategoryChip(
                        category = cat,
                        selected = isSelected,
                        onClick = { selectedCategoryId = cat.id }
                    )
                }
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(filteredMeals) { meal ->
                    MealCard(meal = meal)
                }
            }
        }
    }
}

@Composable
private fun MealCard(meal: MealUI) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 110.dp)
            .clickable { /* no-op for UI-only exercise */ }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = meal.thumbUrl,
                contentDescription = meal.name,
                modifier = Modifier
                    .size(96.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(
                    text = meal.name,
                    style = MaterialTheme.typography.titleSmall.copy(fontSize = 18.sp),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(6.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                )
            }
        }
    }
}

@Composable
private fun CategoryChip(
    category: CategoryUI,
    selected: Boolean,
    onClick: () -> Unit
) {
    val shape = RoundedCornerShape(16.dp)
    val bg = if (selected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant
    val content = if (selected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant

    ElevatedCard(
        modifier = Modifier
            .width(170.dp)
            .height(90.dp)
            .clickable(onClick = onClick),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
                .background(bg, shape)
                .clip(shape),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = category.thumbUrl,
                contentDescription = category.name,
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.width(10.dp))
            Text(
                text = category.name,
                color = content,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

private val sampleCategories = listOf(
    CategoryUI("beef", "Beef", "https://www.themealdb.com/images/category/beef.png"),
    CategoryUI("chicken", "Chicken", "https://www.themealdb.com/images/category/chicken.png"),
    CategoryUI("dessert", "Dessert", "https://www.themealdb.com/images/category/dessert.png"),
    CategoryUI("seafood", "Seafood", "https://www.themealdb.com/images/category/seafood.png"),
)

private val sampleMeals = listOf(
    // Use categoryId to relate to sampleCategories
    MealUI("m1","beef","Beef and Mustard Pie","https://www.themealdb.com/images/media/meals/sytuqu1511553755.jpg"),
    MealUI("m2","beef","Beef Lo Mein","https://www.themealdb.com/images/media/meals/1529444830.jpg"),
    MealUI("m3","chicken","Chicken Alfredo","https://www.themealdb.com/images/media/meals/syqypv1486981727.jpg"),
    MealUI("m4","chicken","Honey Balsamic Chicken","https://www.themealdb.com/images/media/meals/qtuwxu1468233098.jpg"),
    MealUI("m5","dessert","Chocolate Gateau","https://www.themealdb.com/images/media/meals/tqtywx1468317395.jpg"),
    MealUI("m6","dessert","Apple & Blackberry Crumble","https://www.themealdb.com/images/media/meals/xvsurr1511719182.jpg"),
    MealUI("m7","seafood","Salmon Teriyaki","https://www.themealdb.com/images/media/meals/xxyupu1468262513.jpg"),
    MealUI("m8","seafood","Fish Pie","https://www.themealdb.com/images/media/meals/ysxwuq1487323065.jpg"),
)
