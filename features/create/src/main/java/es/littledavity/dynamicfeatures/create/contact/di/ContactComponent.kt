package es.littledavity.dynamicfeatures.create.contact.di

import dagger.Component
import es.littledavity.core.di.CoreComponent
import es.littledavity.core.di.scopes.FeatureScope
import es.littledavity.dynamicfeatures.create.contact.ContactFragment

/**
 * Class for which a fully-formed, dependency-injected implementation is to
 * be generated from [ContactModule].
 *
 * @see Component
 */
@FeatureScope
@Component(
    modules = [ContactModule::class],
    dependencies = [CoreComponent::class])
interface ContactComponent {

    /**
     * Inject dependencies on component.
     *
     * @param contactFragment Chorbo contact component.
     */
    fun inject(contactFragment: ContactFragment)
}