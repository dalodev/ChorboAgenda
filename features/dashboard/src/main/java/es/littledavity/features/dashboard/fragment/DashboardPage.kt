/*
 * Copyright 2021 dalodev
 */
package es.littledavity.features.dashboard.fragment

import es.littledavity.features.dashboard.R

internal enum class DashboardPage(
    val position: Int,
    val menuItemId: Int
) {

    CONTACTS(
        position = 0,
        menuItemId = R.id.dashboard_bottom_navigation_item_contacts
    ),
    ADD_CONTACT(
        position = 1,
        menuItemId = R.id.dashboard_bottom_navigation_item_add
    ),
    LIKES(
        position = 2,
        menuItemId = R.id.dashboard_bottom_navigation_item_likes
    );

    internal companion object {

        fun Int.toDashboardPageFromPosition() = values().find { it.position == this }
            ?: throw IllegalArgumentException(
                "Could not find the dashboard page for the specified position: $this."
            )

        fun Int.toDashboardPageFromMenuItemId() = values().find { it.menuItemId == this }
            ?: throw IllegalArgumentException(
                "Could not find the dashboard page for the specified menu item ID: $this."
            )
    }
}
