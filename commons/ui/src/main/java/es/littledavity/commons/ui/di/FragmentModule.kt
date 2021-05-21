package es.littledavity.commons.ui.di

import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import com.paulrybitskyi.commons.navigation.navController

@Module
@InstallIn(FragmentComponent::class)
internal object FragmentModule {

    @Provides
    fun provideNavController(fragment: Fragment): NavController {
        return fragment.navController
    }
}