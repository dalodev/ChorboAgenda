/*
 * Copyright 2021 dalodev
 */
package es.littledavity.core.sharers

import android.content.Context

interface ContentSharer<Content : Any> {

    fun share(context: Context, content: Content)
}
