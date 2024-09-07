package com.zzy.nasaapod.ui.component

import android.widget.ProgressBar
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.zzy.nasaapod.data.model.APOD
import com.zzy.nasaapod.ui.theme.NasaAPODTheme

@Composable
fun ImageItem(
    modifier: Modifier = Modifier,
    apod: APOD,
    onLikeTapped: (APOD, Boolean) -> Unit
) {
    var isFavorite by remember { mutableStateOf(apod.localPath != null) }

    Column(modifier = modifier.fillMaxWidth()) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth(),
            contentScale = ContentScale.FillWidth,
            model = apod.localPath?:apod.url,
            contentDescription = apod.title
        )
        Row(modifier = Modifier.padding(top = 6.dp, start = 12.dp, end = 12.dp, bottom = 24.dp)) {
            Column(Modifier.weight(1f)) {
                Text(text = apod.title)
                Text(text = apod.date)
            }
            IconToggleButton(
                checked = isFavorite,
                colors = IconButtonDefaults.iconToggleButtonColors(
                    checkedContentColor = MaterialTheme.colorScheme.error
                ),
                onCheckedChange = {
                    isFavorite = it
                    onLikeTapped(apod, it)
                }) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Like button"
                )
            }
        }
    }
}

@Composable
fun LoadingItem() {
    Column(modifier = Modifier.fillMaxWidth().padding(6.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        CircularProgressIndicator(modifier = Modifier.size(48.dp))
    }
}

@Preview
@Composable
fun PreviewImageItem() {
    NasaAPODTheme {
        ImageItem(
            apod = APOD(
                title = "picture title",
                date = "2024-01-01"
            )
        ) { apod, isFavorite ->

        }

    }
}