package com.jihan.app.screens

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.jihan.app.local.Channel
import com.jihan.app.screens.components.ChannelItem
import com.jihan.app.util.Constants.TAG
import com.jihan.app.util.Destination
import com.jihan.app.util.toTitleCase
import com.jihan.app.viewmodel.CategoryDetailViewmodel
import com.jihan.app.viewmodel.ChannelViewmodel
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDetailScreen(
    category: String,
    navController: NavController,
    viewmodel: CategoryDetailViewmodel = koinInject()
) {

    val channelList by viewmodel.channelList.collectAsStateWithLifecycle()
    val loading by viewmodel.loading.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewmodel.getChannelList(category)
    }


     if (loading)
        CenterBox {
            CircularProgressIndicator()
            Text("Loading...")
        }

    else
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(category.toTitleCase())
            },
            navigationIcon = {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
                }
            }
        )
    }) {
        Box(Modifier.padding(it)) {

            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 128.dp)
            ) {

                items(channelList, key = {it.id}) {
                      ChannelItem(Modifier.size(120.dp), it) {channel ->
                          navController.navigate(Destination.StreamPlayer(
                              url = channel.url,
                              title = channel.name,
                              image = channel.image
                          ))
                      }
                }
            }

        }
    }

}

