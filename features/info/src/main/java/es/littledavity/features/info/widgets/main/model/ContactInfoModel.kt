/*
 * Copyright 2021 dev.id
 */
package es.littledavity.features.info.widgets.main.model

import es.littledavity.domain.contacts.entities.Info

internal data class ContactInfoModel(
    val id: Int,
    val headerModel: ContactInfoHeaderModel,
    val info: List<Info>
)
