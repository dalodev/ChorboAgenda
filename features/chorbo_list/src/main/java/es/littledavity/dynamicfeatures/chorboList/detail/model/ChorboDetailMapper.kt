/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.chorboList.detail.model

import android.content.Context
import android.telephony.PhoneNumberUtils
import es.littledavity.core.database.chorbo.Chorbo
import es.littledavity.core.mapper.Mapper

class ChorboDetailMapper(private val context: Context) : Mapper<Chorbo, ChorboDetailItem> {

    override suspend fun map(from: Chorbo) = ChorboDetailItem(
        id = from.id,
        name = from.name,
        image = from.image,
        info = listOf(
            Info(Section.INFO, from.countryName),
            Info(
                Section.SOCIAL,
                PhoneNumberUtils.formatNumber(from.whatsapp, "ES")
            ),
            Info(Section.SOCIAL, from.instagram)
        ),
    )
}
