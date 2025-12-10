package com.example.notaschidas.ui.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.notaschidas.ui.screens.AddNoteScreen
import com.example.notaschidas.ui.screens.EditNoteScreen
import com.example.notaschidas.ui.screens.NoteScreen
import com.example.notaschidas.viewmodel.NoteViewModel

@Composable
fun NavGraph(viewModel: NoteViewModel) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.List.route
    ) {

        composable(Routes.List.route) {
            NoteScreen(
                viewModel = viewModel,
                onAgregar = {
                    navController.navigate(Routes.Add.route)
                },
                onEditar = { id ->
                    navController.navigate(Routes.Edit.createRoute(id))
                }
            )
        }

        composable(Routes.Add.route) {
            AddNoteScreen(
                onGuardar = { titulo, descripcion, uri ->
                    viewModel.insertar(
                        navController.context,
                        titulo,
                        descripcion,
                        uri
                    )
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Routes.Edit.route,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStack ->
            val id = backStack.arguments?.getInt("id")!!
            EditNoteScreen(
                id = id,
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}