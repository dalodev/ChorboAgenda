package es.littledavity.dynamicfeatures.create.location

/**
 * Different interaction events for [LocationFragment].
 */
sealed class LocationViewEvent {

    /**
     * Next screen event.
     */
    object Next : LocationViewEvent()

    /**
     * Show country picker
     */
    object CountryPicker: LocationViewEvent()
}