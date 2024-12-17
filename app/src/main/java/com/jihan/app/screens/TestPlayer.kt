package com.jihan.app.screens

import androidx.annotation.OptIn
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.hls.DefaultHlsDataSourceFactory
import androidx.media3.exoplayer.hls.HlsMediaSource
import androidx.media3.ui.PlayerView

@OptIn(UnstableApi::class)
@Composable
fun TestPlayer(modifier: Modifier = Modifier) {

    Box {


        var buffering by remember { mutableStateOf(true) }
        var errorMsg by remember { mutableStateOf<String?>(null) }
        val context = LocalContext.current

        val hlsUrl = "https://ekusheyserver.com/etvlivesn.m3u8"


        val mediaItem = remember { MediaItem.fromUri(hlsUrl) }
        val hlsFactory = remember { DefaultHlsDataSourceFactory(DefaultHttpDataSource.Factory()) }
        val mediaSource =
            remember { HlsMediaSource.Factory(hlsFactory).createMediaSource(mediaItem) }


        val player = remember {


            ExoPlayer.Builder(context).build().apply {
                setMediaSource(mediaSource)
                prepare()
                playWhenReady = true

            }
        }


        AndroidView(
            factory = {
                PlayerView(it).apply {
                    this.player = player
                }
            }
        )


        AnimatedVisibility(
            buffering,
            Modifier.align(Alignment.Center)
        ) { CircularProgressIndicator() }

        errorMsg?.let {
            Text("Error: $it", Modifier.align(Alignment.Center))
        }


        DisposableEffect(Unit) {


            val listener = object : Player.Listener {
                override fun onPlaybackStateChanged(playbackState: Int) {
                    buffering = playbackState == Player.STATE_BUFFERING
                }


                override fun onPlayerError(error: PlaybackException) {
                    errorMsg = when (error.errorCode) {
                        PlaybackException.ERROR_CODE_IO_INVALID_HTTP_CONTENT_TYPE -> "Invalid content type"
                        PlaybackException.ERROR_CODE_IO_NETWORK_CONNECTION_FAILED -> "Network connection failed"
                        PlaybackException.ERROR_CODE_IO_NETWORK_CONNECTION_TIMEOUT -> "Network connection timeout"
                        PlaybackException.ERROR_CODE_TIMEOUT -> "Network connection timeout"
                        PlaybackException.ERROR_CODE_DISCONNECTED -> "Disconnected"
                        else -> "Unknown error"
                    }
                }


            }

            player.addListener(listener)


            onDispose {
                player.stop()
                player.release()
                player.removeListener(listener)
            }

        }


    }


}