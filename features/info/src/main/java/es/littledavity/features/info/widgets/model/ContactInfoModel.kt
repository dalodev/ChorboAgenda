/*
 * Copyright 2021 dalodev
 */
package es.littledavity.features.info.widgets.model

import es.littledavity.domain.contacts.entities.Info

internal data class ContactInfoModel(
    val id: Int,
    val headerModel: ContactInfoHeaderModel,
    val info: List<Info>
) {
    val hasDetails = info.isNotEmpty()
}
