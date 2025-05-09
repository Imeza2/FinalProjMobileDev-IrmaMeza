package com.example.finalprojectirmameza.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.finalprojectirmameza.DataStoreManager
import com.example.finalprojectirmameza.screens.CreateNote
import com.example.finalprojectirmameza.screens.NotesScreen
import com.example.finalprojectirmameza.screens.SettingsScreen
import com.example.finalprojectirmameza.screens.WelcomeScreen
import androidx.navigation.navArgument
import com.example.finalprojectirmameza.DiaryViewModel




@Composable
fun ImgNav(dataStoreManager: DataStoreManager) {
    val navController = rememberNavController()
    val diaryViewModel: DiaryViewModel = viewModel()

    NavHost(navController = navController, startDestination = "enterScreen") {
        composable("enterScreen") {
            WelcomeScreen(navController = navController, dataStoreManager = dataStoreManager)
        }
        composable("notesScreen") {
            NotesScreen(navController = navController, diaryViewModel = diaryViewModel)
        }
        composable("createNote") {
            CreateNote(noteId = null, navController = navController, diaryViewModel = diaryViewModel)
        }
        composable(
            route = "createNote/{noteId}",
            arguments = listOf(navArgument("noteId") {
                type = NavType.LongType
                defaultValue = -1L
            })
        ) {backStackEntry ->
            val noteId = backStackEntry.arguments?.getLong("noteId") ?: -1L
            CreateNote(noteId = noteId, navController = navController, diaryViewModel = diaryViewModel)
        }
        composable("settings"){
            SettingsScreen(navController = navController, dataStoreManager = dataStoreManager)
        }
    }
}

