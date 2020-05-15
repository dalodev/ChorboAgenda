/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.create.image.di

import dagger.Component
import es.littledavity.core.di.CoreComponent
import es.littledavity.core.di.scopes.FeatureScope
import es.littledavity.dynamicfeatures.create.image.ImageFragment

/**
 * Class for which a fully-formed, dependency-injected implementation is to
 * be generated from [ImageModule].
 *
 * @see Component
 */
@FeatureScope
@Component(
    modules = [ImageModule::class],
    dependencies = [CoreComponent::class])
interface ImageComponent {

    /**
     * Inject dependencies on component.
     *
     * @param imageFragment Chorbo name component.
     */
    fun inject(imageFragment: ImageFragment)
}
