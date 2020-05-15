/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.create.name

/**
 * Different interaction events for [NameFragment].
 */
sealed class NameViewEvent {

    /**
     * Open chorbo image view.
     */
    object Next : NameViewEvent()
}
