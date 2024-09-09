package com.zzy.nasaapod.ui.component

import android.content.Context
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter.State.Empty.painter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.zzy.nasaapod.R
import com.zzy.nasaapod.data.model.APOD
import com.zzy.nasaapod.ui.theme.NasaAPODTheme
import com.zzy.nasaapod.util.BitmapUtil

@Composable
fun APODDetail(
    modifier: Modifier = Modifier,
    apod: APOD?
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Column(modifier = modifier
        .verticalScroll(rememberScrollState())
        .fillMaxSize()
    ) {
        if (apod != null) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                model = apod.url,
//            placeholder = painterResource(id = android.R.drawable.picture_frame),
                contentScale = ContentScale.FillWidth,
                contentDescription = apod.title
            )
            Row(
                modifier = Modifier.padding(
                    top = 6.dp,
                    start = 12.dp,
                    end = 12.dp,
                    bottom = 24.dp
                )
            ) {
                Column(Modifier.weight(1f)) {
                    Text(text = apod.title)
                    Text(text = apod.date)
                    Text(text = apod.explanation)
                    Text(text = apod.copyright)
                }
                IconButton(
                    onClick = {

                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_download),
                        contentDescription = "download"
                    )
                }
            }
        }
    }
}

//@Composable
//private fun downloadHdImage(context: Context, url: String) {
//    val painter = rememberAsyncImagePainter(
//        ImageRequest.Builder(LocalContext.current)
//            .data(url)
//            .placeholder(android.R.drawable.picture_frame)
//            .size(Size.ORIGINAL)
//            .build()
//    )
//
//    BitmapUtil.downloadHDImageToExternalStorage()
//}

@Preview
@Composable
fun PreviewAPODDetail() {
    NasaAPODTheme {
        APODDetail(
            apod = APOD(
                title = "picture title",
                date = "2024-01-01",
                explanation = "explanationexplanationexplanationexplanationexplanationexplanationexplanationexplanationexplanationexplanationexplanationexplanationexplanation",
                copyright = "copyright"
            )
        )
    }
}