package es.littledavity.dynamicfeatures.chorbo_list.list.model

import es.littledavity.core.database.chorbo.Chorbo
import es.littledavity.core.mapper.Mapper

/**
 * Helper class to transforms network response to visual model, catching the necessary data.
 *
 * @see Mapper
 */
open class ChorboItemMapper : Mapper<List<Chorbo>, List<ChorboItem>> {

    override suspend fun map(from: List<Chorbo>) =
        from.map {
            ChorboItem(
                id = it.id,
                name = it.name,
                image = it.image,
                countryCode = it.countryCode,
                countryName = it.countryName,
                flag = it.flag,
                whatsapp = it.whatsapp,
                instagram = it.whatsapp
            )
        }

    override suspend fun reverseMap(from: List<ChorboItem>) =
        from.map {
            Chorbo(
                id = it.id,
                name = it.name,
                image = it.image,
                countryCode = it.countryCode,
                countryName = it.countryName,
                flag = it.flag,
                whatsapp = it.whatsapp,
                instagram = it.instagram
            )
        }
}