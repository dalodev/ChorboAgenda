package es.littledavity.dynamicfeatures.home.model

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
                description = it.description
            )
        }

    override suspend fun reverseMap(from: List<ChorboItem>) =
        from.map {
            Chorbo(
                id = it.id,
                name = it.name,
                description = it.description
            )
        }
}