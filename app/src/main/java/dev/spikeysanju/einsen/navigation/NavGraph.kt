package dev.spikeysanju.einsen.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.HiltViewModelFactory
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dev.spikeysanju.einsen.model.Priority
import dev.spikeysanju.einsen.view.add.AddTaskScreen
import dev.spikeysanju.einsen.view.details.TaskDetailsScreen
import dev.spikeysanju.einsen.view.edit.EditTaskScreen
import dev.spikeysanju.einsen.view.home.HomeScreen
import dev.spikeysanju.einsen.view.settings.SettingsScreen
import dev.spikeysanju.einsen.view.task.AllTaskScreen
import dev.spikeysanju.einsen.view.viewmodel.MainViewModel

object EndPoints {
    const val ID = "id"
}

@Composable
fun NavGraph(toggleTheme: () -> Unit) {
    val navController = rememberAnimatedNavController()
    val actions = remember(navController) { MainActions(navController) }

    AnimatedNavHost(navController, startDestination = Screen.Home.route) {
        // Home
        composable(
            Screen.Home.route
        ) {
            val viewModel: MainViewModel = viewModel(
                factory = HiltViewModelFactory(LocalContext.current, it)
            )
            HomeScreen(viewModel, actions)
        }

        // Add Task
        composable(
            Screen.AddTask.route
        ) {
            val viewModel = hiltViewModel<MainViewModel>(it)
            AddTaskScreen(viewModel, actions)
        }

        // All Task
        composable(Screen.AllTask.route) {
            val viewModel = hiltViewModel<MainViewModel>(it)
            viewModel.getTaskByPriority(Priority.URGENT)
            AllTaskScreen(viewModel, actions)
        }

        // Task Details
        composable(
            "${Screen.TaskDetails.route}/{id}",
            arguments = listOf(navArgument(EndPoints.ID) { type = NavType.IntType })
        ) {
            val viewModel = hiltViewModel<MainViewModel>(it)
            val taskID = it.arguments?.getInt(EndPoints.ID)
                ?: throw IllegalStateException("'task ID' shouldn't be null")

            viewModel.findTaskByID(taskID)
            TaskDetailsScreen(viewModel, actions)
        }

        // Edit Task
        composable(
            "${Screen.EditTask.route}/{id}",
            arguments = listOf(navArgument(EndPoints.ID) { type = NavType.IntType })
        ) {
            val viewModel = hiltViewModel<MainViewModel>(it)
            val taskID = it.arguments?.getInt(EndPoints.ID)
                ?: throw IllegalStateException("'task ID' shouldn't be null")

            viewModel.findTaskByID(taskID)
            EditTaskScreen(viewModel, actions)
        }

        // Settings
        composable(Screen.Settings.route) {
            val viewModel = hiltViewModel<MainViewModel>(it)
            SettingsScreen(viewModel, actions)
        }

    }
}

class MainActions(navController: NavController) {

    val upPress: () -> Unit = {
        navController.navigateUp()
    }

    val gotoAddTask: () -> Unit = {
        navController.navigate(Screen.AddTask.route)
    }

    val gotoAllTask: () -> Unit = {
        navController.navigate(Screen.AllTask.route)
    }

    val gotoTaskDetails: (id: Int) -> Unit = { id ->
        navController.navigate("${Screen.TaskDetails.route}/$id")
    }

    val gotoEditTask: (id: Int) -> Unit = { id ->
        navController.navigate("${Screen.EditTask.route}/$id")
    }

    val gotoSettings: () -> Unit = {
        navController.navigate(Screen.Settings.route)
    }
}