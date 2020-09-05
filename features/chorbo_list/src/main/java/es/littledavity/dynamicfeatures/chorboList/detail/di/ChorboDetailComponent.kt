package es.littledavity.dynamicfeatures.chorboList.detail.di

import dagger.Component
import es.littledavity.core.di.CoreComponent
import es.littledavity.core.di.scopes.FeatureScope
import es.littledavity.dynamicfeatures.chorboList.detail.ChorboDetailFragment
import es.littledavity.dynamicfeatures.chorboList.list.di.ChorboListModule

/**
 * Class for which a fully-formed, dependency-injected implementation is to
 * be generated from [ChorboListModule].
 *
 * @see Component
 */
@FeatureScope
@Component(
    modules = [ChorboDetailModule::class],
    dependencies = [CoreComponent::class]
)
interface ChorboDetailComponent {

    /**
     * Inject dependencies on component.
     *
     * @param chorboDetailFragment Chorbo detail component.
     */
    fun inject(chorboDetailFragment: ChorboDetailFragment)
}
