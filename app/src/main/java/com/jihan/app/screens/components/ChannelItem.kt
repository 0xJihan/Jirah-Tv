package com.jihan.app.screens.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import com.jihan.app.R
import com.jihan.app.local.Channel
import com.jihan.app.screens.CenterBox
import com.jihan.app.util.generateRandomMaterialColor

@Composable
fun ChannelItem(modifier: Modifier = Modifier, channel: Channel, onClick: (Channel) -> Unit) {

    Column(
        Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {


        Card(
            onClick = { onClick(channel) },
            modifier = modifier,
            colors = CardDefaults.elevatedCardColors(
                  containerColor = generateRandomMaterialColor()
            )
        ) {
            SubcomposeAsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(.8f),
                model = channel.image.trim(),
                contentDescription = null,
                loading = { CenterBox(modifier) { CircularProgressIndicator() } },
                error = {
                    Image(painter = painterResource(R.drawable.error_image), null)
                }

            )


        }

        Text(
            channel.name,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold
        )
    }
}
