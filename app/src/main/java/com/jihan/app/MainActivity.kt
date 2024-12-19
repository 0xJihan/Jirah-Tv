package com.jihan.app

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.media3.common.Player
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.jihan.app.screens.CategoryDetailScreen
import com.jihan.app.screens.HomeScreen
import com.jihan.app.screens.PlayerScreen
import com.jihan.app.ui.theme.AppTheme
import com.jihan.app.util.Constants.TAG
import com.jihan.app.util.Destination
import com.jihan.app.viewmodel.ChannelViewmodel
import org.koin.android.ext.android.inject
import org.koin.compose.koinInject


class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val channelViewModel : ChannelViewmodel by inject()

        setContent {
            AppTheme {
                MainApp(channelViewModel)
            }
        }
    }


    @Composable
    fun MainApp(viewmodel: ChannelViewmodel) {
        val navController = rememberNavController()


        NavHost(navController, Destination.Home) {

            composable<Destination.Home>{ HomeScreen(viewmodel,navController) }

            composable<Destination.StreamPlayer> {
                val route = it.toRoute<Destination.StreamPlayer>()
               PlayerScreen(
                   url = route.url,
                   title = route.title,
                   onBackPressed = navController::navigateUp
               )
            }

            composable<Destination.CategoryDetail> {
                val route = it.toRoute<Destination.CategoryDetail>()

                CategoryDetailScreen(route.category,navController)

            }

        }


    }

}






