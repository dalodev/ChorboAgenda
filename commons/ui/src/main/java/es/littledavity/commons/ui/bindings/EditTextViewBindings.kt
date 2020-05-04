package es.littledavity.commons.ui.bindings

import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.databinding.BindingAdapter

/**
 * Set edit text change listener.
 *
 * @param listener lambda passed to invoke custom event.
 */
@BindingAdapter("app:onTextChange")
fun EditText.onTextChange(listener: (String) -> Unit) {
    this.addTextChangedListener { text ->
        listener(text.toString())
    }
}