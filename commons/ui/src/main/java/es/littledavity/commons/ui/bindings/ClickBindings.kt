package es.littledavity.commons.ui.bindings

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("onLongClick")
fun View.onLongClick(listener: () -> Unit) {
    setOnLongClickListener {
        listener()
        true
    }
}
