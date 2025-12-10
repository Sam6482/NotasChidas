package com.example.notaschidas.ui.nav

sealed class Routes(val route: String) {
    object List : Routes("list")
    object Add : Routes("add")
    object Edit : Routes("edit/{id}") {
        fun createRoute(id: Int) = "edit/$id"
    }
}