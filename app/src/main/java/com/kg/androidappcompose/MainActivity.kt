@file:OptIn(ExperimentalMaterial3Api::class)

package com.kg.androidappcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            MyTheme {
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    // Создаём NavHost и определяем маршруты
    NavHost(navController = navController, startDestination = "main_screen") {
//        composable("main_screen") { MainScreen(navController) }
//        composable("detail_screen") { DetailScreen(navController) }


        composable("main_screen") { MainScreen(navController) }
        composable(
            route = "detail_screen/{message}",
            arguments = listOf(navArgument("message") { defaultValue = "Нет сообщения" })
        ) { backStackEntry ->
            // Получаем значение аргумента
            val message = backStackEntry.arguments?.getString("message") ?: "Нет данных"
            DetailScreen(navController, message)
        }
    }
}

@Composable
fun MainScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Главный экран") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.Blue)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Добро пожаловать на главный экран!")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                navController.navigate("detail_screen/Привет из MainScreen!")
            }) {
                Text("Перейти на следующий экран")
            }
        }
    }
}

@Composable
fun DetailScreen(navController: NavHostController, message: String) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Детальный экран") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.Red)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Сообщение: $message")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                navController.popBackStack()
            }) {
                Text("Вернуться назад")
            }
        }
    }
}

@Composable
fun MyTheme(content: @Composable () -> Unit) {
    MaterialTheme {
        content()
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {

}