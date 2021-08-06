/*
 * Copyright 2021 dev.id
 */
package es.littledavity.features.splash

import app.cash.turbine.test
import es.littledavity.testUtils.MainCoroutineRule
import io.mockk.MockKAnnotations
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class SplashViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: SplashViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = SplashViewModel()
    }

    @Test
    fun whenInit_shouldRoute() = mainCoroutineRule.runBlockingTest {

        viewModel.routeFlow.test {
            viewModel.init()
            assertThat(awaitItem() is SplashRoute.Dashboard).isTrue
        }
    }
}
