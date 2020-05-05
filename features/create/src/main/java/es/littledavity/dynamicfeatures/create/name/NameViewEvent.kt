package es.littledavity.dynamicfeatures.create.name

import es.littledavity.dynamicfeatures.create.model.CreateItem

/**
 * Different interaction events for [NameFragment].
 */
sealed class NameViewEvent {

    /**
     * Open chorbo image view.
     */
     object Next : NameViewEvent()
}