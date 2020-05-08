package es.littledavity.dynamicfeatures.create.location.di

import dagger.Component
import es.littledavity.core.di.CoreComponent
import es.littledavity.core.di.scopes.FeatureScope
import es.littledavity.dynamicfeatures.create.location.LocationFragment
import es.littledavity.dynamicfeatures.create.name.di.NameModule

/**
 * Class for which a fully-formed, dependency-injected implementation is to
 * be generated from [NameModule].
 *
 * @see Component
 */
@FeatureScope
@Component(
    modules = [LocationModule::class],
    dependencies = [CoreComponent::class])
interface LocationComponent {

    /**
     * Inject dependencies on component.
     *
     * @param locationFragment Chorbo name component.
     */
    fun inject(locationFragment: LocationFragment)
}