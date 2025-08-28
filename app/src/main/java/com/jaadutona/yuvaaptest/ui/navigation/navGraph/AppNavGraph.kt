package com.jaadutona.yuvaaptest.ui.navigation.navGraph

import DashboardScreen
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jaadutona.yuvaaptest.ui.navigation.Route
import com.jaadutona.yuvaaptest.ui.screen.about.AboutScreen
import com.jaadutona.yuvaaptest.ui.screen.completeyourprofile.CompleteProfileScreen
import com.jaadutona.yuvaaptest.ui.screen.enternumber.EnterYourNumber
import com.jaadutona.yuvaaptest.ui.screen.enterotp.OtpScreen
import com.jaadutona.yuvaaptest.ui.screen.home.HomeScreen
import com.jaadutona.yuvaaptest.ui.screen.login.LoginScreen

@Composable
fun AppNavigation() {
    val isLoggedIn = remember { mutableStateOf(true) }

    if (isLoggedIn.value) {
        DashboardNavHost()
    } else {
        AuthNavHost(
            onLoginSuccess = {
                isLoggedIn.value = true
            }
        )
    }
}

@Composable
fun AuthNavHost(
    onLoginSuccess: () -> Unit
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.LOGIN.toString()
    ) {
        composable(Route.LOGIN.toString()) {
            LoginScreen(
                modifier = Modifier.fillMaxSize(),
                onMobileLoginClick = {
                    navController.navigate(Route.ENTER_YOUR_NUMBER.toString())
                },
                onGoogleLoginClick = {
                    onLoginSuccess()
                },
                onFacebookLoginClick = {
                    onLoginSuccess()
                }
            )
        }

        composable(Route.ENTER_YOUR_NUMBER.toString()) {
            EnterYourNumber(
                modifier = Modifier.fillMaxSize(),
                navController = navController,
                onNextClick = { number ->
                    navController.navigate("${Route.OTP_SCREEN}/$number")
                }
            )
        }

        composable(
            route = "${Route.OTP_SCREEN}/{number}",
            arguments = listOf(navArgument("number") { type = NavType.StringType })
        ) { backStackEntry ->
            val number = backStackEntry.arguments?.getString("number") ?: ""

            OtpScreen(
                modifier = Modifier.fillMaxSize(),
                navController = navController,
                errorMessage = "",
                subtitle = "Enter the 4-digit code sent to +91-$number",
                onVerify = {
                    navController.navigate(Route.PROFILE.toString())
                },
                onResend = { /* resend logic */ }
            )
        }

        composable(Route.PROFILE.toString()) {
            CompleteProfileScreen(
                modifier = Modifier.fillMaxSize(),
                navController = navController,
                onNextClick = {
                    onLoginSuccess() // ✅ After profile complete -> Dashboard
                }
            )
        }
    }
}


@Composable
fun DashboardNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.DASHBOARD.toString()
    ) {
        composable(Route.DASHBOARD.toString()) {
            DashboardScreen()
        }
        composable(Route.HOME.toString()) {
            HomeScreen()
        }

        composable(Route.ABOUT.toString()) {
            AboutScreen()
        }
    }
}
