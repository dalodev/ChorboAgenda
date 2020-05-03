package es.littledavity.dynamicfeatures.create.name.di

import dagger.Component
import es.littledavity.core.di.CoreComponent
import es.littledavity.core.di.scopes.FeatureScope
import es.littledavity.dynamicfeatures.create.name.NameFragment

/**
 * Class for which a fully-formed, dependency-injected implementation is to
 * be generated from [CreateModule].
 *
 * @see Component
 */
@FeatureScope
@Component(
    modules = [NameModule::class],
    dependencies = [CoreComponent::class])
interface NameComponent {

    /**
     * Inject dependencies on component.
     *
     * @param nameFragment Chorbo name component.
     */
    fun inject(nameFragment: NameFragment)
}