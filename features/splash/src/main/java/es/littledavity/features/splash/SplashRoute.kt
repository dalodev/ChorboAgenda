/*
 * Copyright 2021 dev.id
 */
package es.littledavity.features.splash

import es.littledavity.commons.ui.base.events.Route

internal sealed class SplashRoute: Route {
    object Dashboard: SplashRoute()
    object Exit : SplashRoute()
}
