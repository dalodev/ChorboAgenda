package es.littledavity.dynamicfeatures.home.di

import dagger.Component
import es.littledavity.core.di.CoreComponent
import es.littledavity.core.di.scopes.FeatureScope
import es.littledavity.dynamicfeatures.home.HomeFragment

/**
 * Class for which a fully-formed, dependency-injected implementation is to
 * be generated from [HomeModule].
 *
 * @see Component
 */
@FeatureScope
@Component(
    modules = [HomeModule::class],
    dependencies = [CoreComponent::class])
interface HomeComponent {

    /**
     * Inject dependencies on component.
     *
     * @param homeFragment Home component.
     */
    fun inject(homeFragment: HomeFragment)
}