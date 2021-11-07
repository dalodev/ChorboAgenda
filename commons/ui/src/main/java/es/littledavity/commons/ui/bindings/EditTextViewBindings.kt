/*
 * Copyright 2021 dalodev
 */
package es.littledavity.commons.ui.bindings

import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.databinding.BindingAdapter
import es.littledavity.commons.ui.extensions.orZero

/**
 * Set edit text change listener.
 *
 * @param listener lambda passed to invoke custom event.
 */
@BindingAdapter("onTextChange")
fun EditText.onTextChange(listener: (String) -> Unit) {
    this.addTextChangedListener { text ->
        listener(text.toString())
        this.setSelection(text?.length.orZero())
    }
}
