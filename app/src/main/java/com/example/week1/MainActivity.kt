package com.example.week1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.week1.ui.theme.Week1Theme
import com.example.week1.view.CalendarScreen
import com.example.week1.view.HomeScreen
import com.example.week1.view.SettingsScreen
import com.example.week1.viewmodel.TaskViewModel

const val ROUTE_HOME = "home"
const val ROUTE_CALENDAR = "calendar"
const val ROUTE_SETTINGS = "settings"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Week1Theme {
                App()
            }
        }
    }
}

@Composable
private fun App() {
    val navController = rememberNavController()
    val vm: TaskViewModel = viewModel()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = currentRoute == ROUTE_HOME,
                    onClick = { navController.navigateTopLevel(ROUTE_HOME) },
                    icon = { Box {} },
                    label = { Text("Home") }
                )
                NavigationBarItem(
                    selected = currentRoute == ROUTE_CALENDAR,
                    onClick = { navController.navigateTopLevel(ROUTE_CALENDAR) },
                    icon = { Box {} },
                    label = { Text("Calendar") }
                )
                NavigationBarItem(
                    selected = currentRoute == ROUTE_SETTINGS,
                    onClick = { navController.navigateTopLevel(ROUTE_SETTINGS) },
                    icon = { Box {} },
                    label = { Text("Settings") }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = ROUTE_HOME,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(ROUTE_HOME) { HomeScreen(vm = vm) }
            composable(ROUTE_CALENDAR) { CalendarScreen(vm = vm) }
            composable(ROUTE_SETTINGS) { SettingsScreen() }
        }
    }
}

private fun androidx.navigation.NavHostController.navigateTopLevel(route: String) {
    navigate(route) {
        popUpTo(graph.startDestinationId) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}
