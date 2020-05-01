package es.littledavity.commons.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.test.core.app.ActivityScenario
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import es.littledavity.test_utils.TestCompatActivity
import es.littledavity.test_utils.TestFragmentActivity
import es.littledavity.test_utils.roboelectric.TestRobolectric
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.SpyK
import org.hamcrest.Matchers.instanceOf
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.Spy

class BaseFragmentTest  {


    lateinit var baseFragment: TestBaseFragment

    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }
    /*
        @Test
        fun initDependencyInjection_OnAttach_ShouldInvoke() {
            baseFragment.onAttach(context)

            verify(baseFragment).onInitDependencyInjection()
        }

        @Test
        fun initDataBiding_OnViewCreated_ShouldInvoke() {
            val view = mock<View>()
            val savedInstanceState = mock<Bundle>()
            baseFragment.onViewCreated(view, savedInstanceState)

            Mockito.verify(baseFragment).onInitDataBinding()
        }

         @Test
         fun requireCompactActivity_FromCompactActivity_ShouldReturnIt() {
             val scenario = ActivityScenario.launch(TestCompatActivity::class.java)
             scenario.onActivity {
                 it.supportFragmentManager
                     .beginTransaction()
                     .add(android.R.id.content, baseFragment)
                     .commitNow()
                 val compatActivity = baseFragment.requireCompatActivity()
                 assertNotNull(compatActivity)
                 assertThat(compatActivity, instanceOf(AppCompatActivity::class.java))
             }
         }

         @Test(expected = TypeCastException::class)
         fun requireCompactActivity_FromActivity_ShouldNotReturnIt() {
             val scenario = ActivityScenario.launch(TestFragmentActivity::class.java)
             scenario.onActivity {
                 it.supportFragmentManager
                     .beginTransaction()
                     .add(android.R.id.content, baseFragment)
                     .commitNow()
                 baseFragment.requireCompatActivity()
             }
         }*/
    class TestBaseFragment : BaseFragment<ViewDataBinding, ViewModel>(
        layoutId = 0
    ) {
        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? = null

        override fun onInitDependencyInjection() {}

        override fun onInitDataBinding() {}

        override fun onClear() {}
    }
}

