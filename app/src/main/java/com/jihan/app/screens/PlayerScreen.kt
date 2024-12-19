package com.jihan.app.screens

import android.app.Activity
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LOCKED
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_USER
import android.media.metrics.PlaybackErrorEvent
import android.widget.ImageButton
import androidx.activity.compose.BackHandler
import androidx.annotation.OptIn
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.hls.DefaultHlsDataSourceFactory
import androidx.media3.exoplayer.hls.HlsMediaSource
import androidx.media3.ui.PlayerView
import com.jihan.app.MainActivity
import com.jihan.app.R

@OptIn(UnstableApi::class)
@Composable
fun PlayerScreen(
    url: String,
    title:String,
    onBackPressed: () -> Unit={}
) {
    Column {
        val context = LocalContext.current
        val activity = remember { context as Activity }

        val lockScreen = {activity.requestedOrientation = SCREEN_ORIENTATION_LOCKED}

        val enterFullScreen = {activity.requestedOrientation= SCREEN_ORIENTATION_LANDSCAPE}
        val exitFullScreen = {activity.requestedOrientation = SCREEN_ORIENTATION_USER}


        var buffering by remember { mutableStateOf(true) }
        var errorMsg by remember { mutableStateOf<String?>(null) }



        val mediaItem = remember { MediaItem.fromUri(url) }
        val hlsFactory =
            remember { DefaultHlsDataSourceFactory(DefaultHttpDataSource.Factory()) }
        val mediaSource =
            remember { HlsMediaSource.Factory(hlsFactory).createMediaSource(mediaItem) }


        val player = remember {


            ExoPlayer.Builder(context).build().apply {
                setMediaSource(mediaSource)
                prepare()
                playWhenReady = true

            }
        }

        var controllerVisible by remember { mutableStateOf(false) }
        Box(Modifier.fillMaxWidth()) {

            AndroidView(
                modifier = Modifier.fillMaxWidth().aspectRatio(16f/9f),
                factory = {
                    PlayerView(
                        it
                    ).apply {
                        this.player = player
                        controllerVisible=isControllerFullyVisible
                    }
                },

            )




            IconButton(onClick = {
                if (activity.requestedOrientation== SCREEN_ORIENTATION_LANDSCAPE)
                exitFullScreen()else enterFullScreen()
            }) { Icon(
                painter = painterResource(R.drawable.baseline_screen_rotation_alt_24),
                contentDescription = null,
                modifier = Modifier.align(Alignment.BottomEnd)
            ) }


            this@Column.AnimatedVisibility(
                buffering,
                Modifier.align(Alignment.Center)
            ) { CircularProgressIndicator() }

            errorMsg?.let {
                Text(
                    "Error: $it",
                    Modifier.align(Alignment.Center),
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.error
                )
            }



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
                        PlaybackErrorEvent.ERROR_DECODING_FORMAT_UNSUPPORTED -> "Unsupported Media Type"
                        PlaybackErrorEvent.ERROR_DECODING_FAILED -> "Invalid Media Type"
                        else -> "Unknown error"
                    }
                }


            }

            player.addListener(listener)


            onDispose {
                player.pause()
                player.stop()
                player.release()
                player.removeListener(listener)

            }



        }

        BackHandler {
            exitFullScreen()
            onBackPressed()
        }

    }
}

