/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.chorboList.detail.model

import android.content.Context
import es.littledavity.core.database.chorbo.Chorbo
import es.littledavity.core.mapper.Mapper

class ChorboDetailMapper(private val context: Context) : Mapper<Chorbo, ChorboDetailItem> {

    override suspend fun map(from: Chorbo) = ChorboDetailItem(
        id = from.id,
        name = from.name,
        image = from.image,
        content = listOf(
            ChorboDetailContent.Content(
                ChorboDetailContent.ContentType.INFO,
                from.countryName,
                ChorboDetailContent.SectionContent.LOCATION
            ),
            ChorboDetailContent.Content(
                ChorboDetailContent.ContentType.SOCIAL,
                "+${from.countryCode} ${from.whatsapp}",
                ChorboDetailContent.SectionContent.WHATSAPP
            ),
            ChorboDetailContent.Content(
                ChorboDetailContent.ContentType.SOCIAL,
                from.instagram,
                ChorboDetailContent.SectionContent.INSTAGRAM
            )
        )
    )
}
