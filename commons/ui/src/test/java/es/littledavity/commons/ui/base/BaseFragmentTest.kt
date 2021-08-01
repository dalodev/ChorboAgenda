/*
 * Copyright 2021 dev.id
 */
package es.littledavity.commons.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.nhaarman.mockitokotlin2.mock
import es.littledavity.commons.ui.base.navigation.Navigator
import org.mockito.MockitoAnnotations

class BaseFragmentTest {

    lateinit var baseFragment: TestBaseFragment

    fun setUp() {
        MockitoAnnotations.openMocks(this)
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
    class TestBaseFragment : BaseFragment<ViewDataBinding, BaseViewModel, Navigator>(
        layoutId = 0
    ) {
        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? = null

        override val viewBinding: ViewDataBinding
            get() = mock()
        override val viewModel: BaseViewModel
            get() = mock()
    }
}
