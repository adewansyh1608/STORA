package com.example.stora.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.stora.screens.AddItemScreen
import com.example.stora.screens.DetailScreen
import com.example.stora.screens.InventoryScreen
import com.example.stora.screens.AuthScreen
import com.example.stora.screens.HomeScreen
import com.example.stora.screens.LoansScreen
import androidx.compose.animation.fadeOut

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.AUTH_SCREEN
    ) {

        composable(
            route = Routes.AUTH_SCREEN,

            exitTransition = {

                if (targetState.destination.route == Routes.HOME_SCREEN) {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Up,
                        animationSpec = tween(1000)
                    )
                } else {

                    fadeOut(animationSpec = tween(1000))
                }
            }
        ) {
            AuthScreen(navController = navController)
        }

        composable(
            route = Routes.HOME_SCREEN,

            enterTransition = {

                if (initialState.destination.route == Routes.AUTH_SCREEN) {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Up,
                        animationSpec = tween(1000)
                    )
                } else {

                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(500)
                    )
                }
            },

            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(500)
                )
            }
        ) {
            HomeScreen(navController = navController)
        }

        composable(
            route = Routes.INVENTORY_SCREEN,

            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(500)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(500)
                )
            }
        ) {
            InventoryScreen(navController = navController)
        }

        composable(
            route = Routes.DETAIL_SCREEN,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(500)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(500)
                )
            }
        ) { backStackEntry ->

            val itemId = backStackEntry.arguments?.getString("itemId")
            DetailScreen(navController = navController, itemId = itemId)
        }

        composable(
            route = Routes.ADD_ITEM_SCREEN,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Up,
                    animationSpec = tween(500)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Down,
                    animationSpec = tween(500)
                )
            }
        ) {
            AddItemScreen(navController = navController)
        }

        composable(
            route = Routes.LOANS_SCREEN,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(500)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(500)
                )
            }
        ) {
            LoansScreen(navController = navController)
        }
    }
}