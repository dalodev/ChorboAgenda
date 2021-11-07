/*
 * Copyright 2021 dalodev
 */
package es.littledavity.commons.ui.widgets.toolbar

import android.view.Gravity

enum class TitleGravity(
    internal val id: Int,
    internal val gravity: Int
) {

    LEFT(
        id = 1,
        gravity = Gravity.START
    ),
    CENTER(
        id = 2,
        gravity = Gravity.CENTER
    ),
    RIGHT(
        id = 3,
        gravity = Gravity.END
    );

    companion object {

        @JvmName("forId")
        @JvmStatic
        internal fun Int.asTitleGravity(): TitleGravity = values().find { it.id == this }
            ?: throw IllegalArgumentException("Could not find the title gravity for the specified ID: $this.")
    }
}
