/*
 * Copyright 2021 dev.id
 */
package es.littledavity.database.chorbo

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.paulrybitskyi.hiltbinder.BindType
import es.littledavity.core.JsonConverter
import es.littledavity.database.chorbo.entities.CreationDate
import es.littledavity.database.chorbo.entities.CreationDateCategory
import es.littledavity.database.chorbo.entities.Image
import es.littledavity.database.commons.RoomTypeConverter
import javax.inject.Inject

@ProvidedTypeConverter
@BindType(contributesTo = BindType.Collection.SET)
internal class ContactsTypeConverter @Inject constructor(
    private val jsonConverter: JsonConverter
) : RoomTypeConverter {

    @TypeConverter
    fun fromImage(image: Image?): String {
        return jsonConverter.toJson(image)
    }

    @TypeConverter
    fun toImage(json: String): Image? {
        return jsonConverter.fromJson(json)
    }

    @TypeConverter
    fun fromImages(images: List<Image>): String {
        return jsonConverter.toJson(images)
    }

    @TypeConverter
    fun toImages(json: String): List<Image> {
        return (jsonConverter.fromJson(json) ?: emptyList())
    }

    @TypeConverter
    fun fromCreationeDate(creationDate: CreationDate): String {
        return jsonConverter.toJson(creationDate)
    }

    @TypeConverter
    fun toReleaseDates(json: String): CreationDate? {
        return jsonConverter.fromJson(json)
    }

    @TypeConverter
    fun fromCreationDateCategory(category: CreationDateCategory): String {
        return jsonConverter.toJson(category)
    }

    @TypeConverter
    fun toCreationDateCategory(json: String): CreationDateCategory {
        return (jsonConverter.fromJson(json) ?: CreationDateCategory.UNKNOWN)
    }
}
