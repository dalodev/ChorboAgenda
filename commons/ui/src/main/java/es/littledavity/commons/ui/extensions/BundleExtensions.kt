/*
 * Copyright 2021 dalodev
 */
package es.littledavity.commons.ui.extensions

import android.os.Bundle
import android.os.Parcelable

fun Bundle.getBundle(key: String, default: Bundle): Bundle = getBundle(key) ?: default

fun Bundle.getBundleOrThrow(key: String): Bundle = checkNotNull(getBundle(key)) {
    "The bundle does not contain a bundle value with the key: $key."
}

@SuppressWarnings("unchecked")
fun <T> Bundle.getSerializable(key: String, default: T): T = getSerializable(key) as? T ?: default

@SuppressWarnings("unchecked")
fun <T : Any> Bundle.getSerializableOrThrow(key: String): T =
    checkNotNull(getSerializable(key) as? T) {
        "The bundle does not contain a serializable value with the key: $key."
    }

fun <T : Parcelable> Bundle.getParcelable(key: String, default: T): T =
    getParcelable(key) ?: default

fun <T : Parcelable> Bundle.getParcelableOrThrow(key: String): T =
    checkNotNull(getParcelable(key)) {
        "The bundle does not contain a parcelable value with the key: $key."
    }
