/*
 * Copyright 2021 dalodev
 */
package es.littledavity.features.dashboard

import app.cash.turbine.test
import es.littledavity.features.dashboard.fragment.DashboardRoute
import es.littledavity.features.dashboard.fragment.DashboardViewModel
import es.littledavity.testUtils.MainCoroutineRule
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DashboardViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: DashboardViewModel

    @Before
    fun setup() {
        viewModel = DashboardViewModel()
    }

    @Test
    fun onClickSearch_shouldGoToSearchRoute() = mainCoroutineRule.runBlockingTest {
        viewModel.routeFlow.test {
            viewModel.onToolbarRightButtonClicked()
            assertThat(awaitItem() is DashboardRoute.Search).isTrue
        }
    }
}
