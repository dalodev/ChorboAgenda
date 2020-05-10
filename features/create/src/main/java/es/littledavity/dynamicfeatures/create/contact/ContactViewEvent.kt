package es.littledavity.dynamicfeatures.create.contact

/**
 * Different interaction events for [ContactViewEvent].
 */
sealed class ContactViewEvent {

    /**
     * Open chorbo image view.
     */
    object Next : ContactViewEvent()
}