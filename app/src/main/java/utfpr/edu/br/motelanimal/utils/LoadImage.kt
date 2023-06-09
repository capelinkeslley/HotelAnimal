package utfpr.edu.br.motelanimal.utils

import android.widget.ImageView
import coil.load
import utfpr.edu.br.motelanimal.R

fun ImageView.tryLoadImage(url: String) {
    load(url) {
        fallback(R.drawable.baseline_image_search_24)
        error(R.drawable.baseline_pets_24)
        placeholder(R.drawable.baseline_refresh_24)
    }
}