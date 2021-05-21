package es.littledavity.features.splash

import app.cash.turbine.test
import io.mockk.MockKAnnotations
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class SplashViewModelTest {

    private lateinit var viewModel: SplashViewModel

    @Before
    fun setup(){
        MockKAnnotations.init(this)
        viewModel = SplashViewModel()
    }

    @Test
    fun whenInit_shouldRoute() = runBlocking {

        viewModel.routeFlow.test {
            viewModel.init()
            assertThat(expectItem() is SplashRoute.Dashboard).isTrue
        }
    }
}