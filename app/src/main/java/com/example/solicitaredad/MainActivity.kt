package com.example.solicitaredad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.solicitaredad.ui.theme.SolicitarEdadTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SolicitarEdadTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun FirstScreen(onNavigate: (Int) -> Unit) {
    var age by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = age,
            onValueChange = { age = it },
            label = { Text("Ingresa tu edad") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            val ageInt = age.toIntOrNull()
            if (ageInt != null) {
                onNavigate(ageInt)
            }
        }) {
            Text("Enviar")
        }
    }
}

@Composable
fun SecondScreen(age: Int) {
    val message = if (age >= 18) "A buena hora eres un adulto" else "A buena hora eres menor de edad"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = message)
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "first_screen") {
        composable("first_screen") {
            FirstScreen { age ->
                navController.navigate("second_screen/$age")
            }
        }
        composable(
            "second_screen/{age}",
            arguments = listOf(navArgument("age") { type = NavType.IntType })
        ) { backStackEntry ->
            val age = backStackEntry.arguments?.getInt("age") ?: 0
            SecondScreen(age)
        }
    }
}