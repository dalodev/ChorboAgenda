/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.chorboList.detail.model

import android.content.Context
import android.net.Uri
import android.telephony.PhoneNumberUtils
import android.view.View
import es.littledavity.core.database.chorbo.Chorbo
import es.littledavity.core.mapper.Mapper
import es.littledavity.core.utils.ImageUtils
import es.littledavity.dynamicfeatures.chorbo_list.R
import java.io.File

class ChorboDetailMapper(private val context: Context) : Mapper<Chorbo, ChorboDetailItem> {

    override suspend fun map(from: Chorbo) = ChorboDetailItem(
        id = from.id,
        name = from.name,
        image = Uri.parse(from.image),
        info = listOf(
            Info(Section.INFO, InfoData.Simple(from.countryName), R.drawable.ic_location),
            Info(
                Section.SOCIAL,
                InfoData.Simple(PhoneNumberUtils.formatNumber(from.whatsapp, "ES").orEmpty()),
                R.drawable.ic_whatsapp
            ),
            Info(
                Section.SOCIAL,
                InfoData.Simple(from.instagram),
                R.drawable.ic_instagram
            ),
            Info(
                Section.PREVIEW,
                InfoData.Preview(createList(from)),
                View.NO_ID
            )
        ),
    )

    private fun createList(chorbo: Chorbo): List<Uri> {
        val list = arrayListOf<Uri>()
        repeat(6) {
            list.add(Uri.parse(chorbo.image))
        }
        return list
    }
}
