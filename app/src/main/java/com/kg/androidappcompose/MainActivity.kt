@file:OptIn(ExperimentalMaterial3Api::class)

package com.kg.androidappcompose

import android.os.Bundle
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyTheme {
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp() {

    val detailViewModel: DetailViewModel = viewModel()
    val navController = rememberNavController()
    // Создаём NavHost и определяем маршруты
    NavHost(
        navController = navController,
        startDestination = "main_screen",
    ) {

        composable("main_screen") { MainScreen(navController, detailViewModel = detailViewModel) }
        composable(
            route = "detail_screen",
//            arguments = listOf(navArgument("message") { defaultValue = "Нет сообщения" }),
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(300, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(300, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
        ) { backStackEntry ->
            // Получаем значение аргумента
            val message = backStackEntry.arguments?.getString("message") ?: "Нет данных"
            DetailScreen(navController, viewModel = detailViewModel)
        }
    }
}

@Composable
fun MainScreen(
    navController: NavHostController,
    viewModel: MainViewModel = viewModel(),
    detailViewModel: DetailViewModel
) {

    var enterText by remember { mutableStateOf("") }
    val text by viewModel.text.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Главный экран") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = text)
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = enterText,
                onValueChange = { newText ->
                    enterText = newText
                    viewModel.updateText(newText)
                },
                label = { Text("Текстовое поле") },
                placeholder = { Text("Введите что-нибудь...") },
                singleLine = true,

                )

            Button(onClick = {
                detailViewModel.addText(enterText)
                navController.navigate("detail_screen")
                viewModel.changeTextWithDelay()
            }) {
                Text("Перейти на следующий экран")
            }
        }
    }
}

@Composable
fun DetailScreen(navController: NavHostController, viewModel: DetailViewModel) {

    val savedTexts = viewModel.savedTexts

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Детальный экран") })
        }
    ) { paddingValues ->

//        Box(modifier = Modifier.fillMaxSize()) {
//
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top
        ) {

            LazyColumn(
                modifier = Modifier
                    .background(Color.Red)
                    .size(400.dp)
            ) {
                items(savedTexts) { text ->
                    Text(
                        text = text,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    navController.popBackStack()
                }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Вернуться назад")
            }
        }
    }
}
//}

@Composable
fun MyTheme(content: @Composable () -> Unit) {
    MaterialTheme {
        content()
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApp()
}