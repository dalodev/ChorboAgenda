/*
 * Copyright 2021 dev.id
 */
package es.littledavity.commons.ui.base.events

sealed class GeneralCommand : Command {
    class ShowShortToast(val message: String): GeneralCommand()
    class ShowLongToast(val message: String): GeneralCommand()
}
