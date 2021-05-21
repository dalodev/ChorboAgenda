/*
 * Copyright 2021 dev.id
 */
package es.littledavity.features.dashboard

import app.cash.turbine.test
import es.littledavity.features.dashboard.fragment.DashboardRoute
import es.littledavity.features.dashboard.fragment.DashboardViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before

class DashboardViewModelTest {

    private lateinit var viewModel: DashboardViewModel

    @Before
    fun setup() {
        viewModel = DashboardViewModel()
    }

    @Test
    fun onClickSearch_shouldGoToSearchRoute() = runBlocking {
        viewModel.routeFlow.test {
            viewModel.onToolbarRightButtonClicked()
            assertThat(expectItem() is DashboardRoute.Search).isTrue
        }
    }

}
