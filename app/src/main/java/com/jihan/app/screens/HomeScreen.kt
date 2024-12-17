package com.jihan.app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.jihan.app.local.Channel
import com.jihan.app.screens.components.ChannelItem
import com.jihan.app.util.Destination
import com.jihan.app.util.StateManager
import com.jihan.app.util.toTitleCase
import com.jihan.app.viewmodel.ChannelViewmodel
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: ChannelViewmodel = koinInject(),
    navController: NavController,
) {


    val channelList by viewModel.channelList.collectAsStateWithLifecycle()

    when (val state = channelList) {
        is StateManager.Failed -> {
            CenterBox {
                Text(
                    state.message,
                    fontSize = 30.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        StateManager.Loading -> {
            CenterBox { CircularProgressIndicator() }
        }

        is StateManager.Success -> {

            LaunchedEffect(Unit) {

            }

            val categories by viewModel.totalCategories.collectAsStateWithLifecycle()

            val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

            Scaffold(
                Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .nestedScroll(scrollBehavior.nestedScrollConnection),
                topBar = {
                    TopAppBar(
                        title = { Text("Jirah TV") },
                        scrollBehavior = scrollBehavior,
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.Blue.copy(alpha = 4f)
                        )
                    )
                }
            ) {

                Box(Modifier.padding(it)) {


                    val testChannel = remember {
                        Channel(
                            1,
                            "Test",
                            "https://i.imgur.com/SUPQAok.png",
                            "https://i.imgur.com/SUPQAok.png",
                            "Test"
                        )
                    }
                    val count = remember { 100 }
                    // val channelList by viewModel.channelList.collectAsStateWithLifecycle()


                    LazyColumn(
                        Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background)
                    ) {
                        items(categories){category->
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    category.toTitleCase(),
                                    style = MaterialTheme.typography.titleLarge
                                )
                                TextButton(onClick = {
                                    navController.navigate(
                                        Destination.CategoryDetail(
                                            category
                                        )
                                    )
                                }) {
                                    Text("View All")
                                }
                            }
                            LazyRow(
                                Modifier
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colorScheme.background)
                            ) {
                                items(count) {

                                    ChannelItem(
                                        channel = testChannel,
                                        modifier = Modifier.size(120.dp)
                                    ) {

                                    }
                                }
                            }
                        }
                    }

                }

            }
        }
    }
}

@Composable
fun CenterBox(modifier: Modifier = Modifier.fillMaxSize(), content: @Composable () -> Unit) {
    Column(
        modifier
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        content()
    }
}