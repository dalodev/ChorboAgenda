package es.littledavity.features.dashboard.fragment

import es.littledavity.features.dashboard.R

internal enum class DashboardPage(
    val position: Int,
    val menuItemId: Int
) {


    CONTACTS(
        position = 0,
        menuItemId = R.id.dashboard_bottom_navigation_item_contacts
    );


    internal companion object {

        fun Int.toDashboardPageFromPosition(): DashboardPage {
            return values().find { it.position == this }
                ?: throw IllegalArgumentException("Could not find the dashboard page for the specified position: $this.")
        }

        fun Int.toDashboardPageFromMenuItemId(): DashboardPage {
            return values().find { it.menuItemId == this }
                ?: throw IllegalArgumentException("Could not find the dashboard page for the specified menu item ID: $this.")
        }

    }


}