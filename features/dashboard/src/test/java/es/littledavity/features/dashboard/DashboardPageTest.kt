/*
 * Copyright 2021 dalodev
 */
package es.littledavity.features.dashboard

import es.littledavity.features.dashboard.fragment.DashboardPage
import es.littledavity.features.dashboard.fragment.DashboardPage.Companion.toDashboardPageFromMenuItemId
import es.littledavity.features.dashboard.fragment.DashboardPage.Companion.toDashboardPageFromPosition
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

class DashboardPageTest {

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun toDashboardPageFromPosition_shouldReturnContactsPage() {

        val page = 0.toDashboardPageFromPosition()
        assertThat(page).isEqualTo(DashboardPage.CONTACTS)
    }

    @Test
    fun toDashboardPageFromPosition_shouldReturnAddContactPage() {

        val page = 1.toDashboardPageFromPosition()
        assertThat(page).isEqualTo(DashboardPage.ADD_CONTACT)
    }

    @Test
    fun toDashboardPageFromPosition_shouldReturnLikesPage() {

        val page = 2.toDashboardPageFromPosition()
        assertThat(page).isEqualTo(DashboardPage.LIKES)
    }

    @Test
    fun totoDashboardPageFromMenuItemId_shouldReturnContactPage() {
        val page = R.id.dashboard_bottom_navigation_item_contacts.toDashboardPageFromMenuItemId()
        assertThat(page).isEqualTo(DashboardPage.CONTACTS)
    }
    @Test
    fun totoDashboardPageFromMenuItemId_shouldReturnAddContactPage() {
        val page = R.id.dashboard_bottom_navigation_item_add.toDashboardPageFromMenuItemId()
        assertThat(page).isEqualTo(DashboardPage.ADD_CONTACT)
    }

    @Test
    fun totoDashboardPageFromMenuItemId_shouldReturnLikesContactPage() {
        val page = R.id.dashboard_bottom_navigation_item_likes.toDashboardPageFromMenuItemId()
        assertThat(page).isEqualTo(DashboardPage.LIKES)
    }
}
