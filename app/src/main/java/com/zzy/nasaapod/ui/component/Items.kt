package com.zzy.nasaapod.ui.component

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.zzy.nasaapod.data.model.APOD
import com.zzy.nasaapod.ui.theme.NasaAPODTheme
import com.zzy.nasaapod.util.BitmapUtil.saveImageToAppDirectory
import kotlinx.coroutines.launch
import java.io.File

@Composable
fun ImageItem(
    modifier: Modifier = Modifier,
    apod: APOD,
    onItemClick: (APOD) -> Unit = {},
    onLikeTapped: (APOD, Boolean) -> Unit
) {
//    var isLike by remember { mutableStateOf(apod.localPath != null) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val painter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current)
            .data(apod.url)
            .placeholder(android.R.drawable.picture_frame)
            .size(Size.ORIGINAL)
            .build()
    )


    fun saveOrDeleteImage(isSave: Boolean) {
        scope.launch {
            var path: String? = null
            if (isSave) {
                path = saveImage(context, apod.date, painter)
            } else {
                apod.localPath?.let { localPath -> deleteImage(localPath) }
            }
            apod.updateLocalPath(path)
            onLikeTapped(apod, isSave)
        }
    }

    Column(modifier = modifier.fillMaxWidth().clickable {
        onItemClick(apod)
    }) {
        Image(
            modifier = Modifier
                .fillMaxWidth(),
            painter = painter,
            contentScale = ContentScale.FillWidth,
            contentDescription = apod.title)
        Row(modifier = Modifier.padding(top = 6.dp, start = 12.dp, end = 12.dp, bottom = 24.dp)) {
            Column(Modifier.weight(1f)) {
                Text(text = apod.title)
                Text(text = apod.date)
            }
            IconToggleButton(
                checked = apod.mutableLocalPath != null,
                colors = IconButtonDefaults.iconToggleButtonColors(
                    checkedContentColor = MaterialTheme.colorScheme.error
                ),
                onCheckedChange = {
                    saveOrDeleteImage(it)
                }) {
                Icon(
                    imageVector = if (apod.mutableLocalPath != null) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Like button"
                )
            }
        }
    }
}

@Composable
fun LoadingItem() {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(6.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        CircularProgressIndicator(modifier = Modifier.size(48.dp))
    }
}

private fun saveImage(context: Context, name: String, painter: AsyncImagePainter): String? {
    val state = painter.state as? AsyncImagePainter.State.Success
    val drawable = state?.result?.drawable
    return if (drawable != null) {
        saveImageToAppDirectory(context, name, drawable)
    } else {
        null
    }
}

private fun deleteImage(path: String): Boolean {
    val file = File(path)
    file.delete()
    return !file.exists()
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
        ) { apod, isLike ->

        }

    }
}