package com.zzy.nasaapod.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.zzy.nasaapod.R
import com.zzy.nasaapod.data.model.APOD
import com.zzy.nasaapod.ui.component.APODDetail
import com.zzy.nasaapod.ui.theme.NasaAPODTheme

internal const val EXTRA_KEY_APOD = "com.zzy.nasaapod.extra_key_apod"

fun MainActivity.launchDetail(apod: APOD) {
    val intent = Intent(this, DetailActivity::class.java).apply {
        putExtra(EXTRA_KEY_APOD, apod)
    }
    this.startActivity(intent)
}

class DetailActivity: ComponentActivity() {

    private var apod: APOD? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        apod = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            savedInstanceState?.getParcelable(EXTRA_KEY_APOD, APOD::class.java)?:intent.getParcelableExtra(EXTRA_KEY_APOD, APOD::class.java)
        } else {
            savedInstanceState?.getParcelable(EXTRA_KEY_APOD)?:intent.getParcelableExtra(EXTRA_KEY_APOD)

        }
        setContent {
            NasaAPODTheme {
                Scaffold { paddingValues ->
                    APODDetail(
                        modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding()),
                        apod = apod
                    ) {
                        Toast.makeText(this, getString(R.string.image_saved, it), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        apod?.let {  outState.putParcelable(EXTRA_KEY_APOD, apod) }
    }
}