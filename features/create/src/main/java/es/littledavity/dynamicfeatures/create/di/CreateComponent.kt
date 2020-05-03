package es.littledavity.dynamicfeatures.create.di

import dagger.Component
import es.littledavity.core.di.CoreComponent
import es.littledavity.core.di.scopes.FeatureScope
import es.littledavity.dynamicfeatures.create.CreateFragment

/**
 * Class for which a fully-formed, dependency-injected implementation is to
 * be generated from [CreateModule].
 *
 * @see Component
 */
@FeatureScope
@Component(
    modules = [CreateModule::class],
    dependencies = [CoreComponent::class])
interface CreateComponent {

    /**
     * Inject dependencies on component.
     *
     * @param createFragment Chorbo list component.
     */
    fun inject(createFragment: CreateFragment)
}