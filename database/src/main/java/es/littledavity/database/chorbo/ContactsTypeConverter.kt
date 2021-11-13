/*
 * Copyright 2021 dalodev
 */
package es.littledavity.database.chorbo

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.paulrybitskyi.hiltbinder.BindType
import es.littledavity.core.JsonConverter
import es.littledavity.database.chorbo.entities.CreationDate
import es.littledavity.database.chorbo.entities.CreationDateCategory
import es.littledavity.database.chorbo.entities.Image
import es.littledavity.database.chorbo.entities.Info
import es.littledavity.database.commons.RoomTypeConverter
import javax.inject.Inject

@ProvidedTypeConverter
@BindType(contributesTo = BindType.Collection.SET)
internal class ContactsTypeConverter @Inject constructor(
    private val jsonConverter: JsonConverter
) : RoomTypeConverter {

    @TypeConverter
    fun fromInfoList(infoList: List<Info>?): String = jsonConverter.toJson(infoList)

    @TypeConverter
    fun toInfoList(json: String): List<Info> = jsonConverter.fromJson(json) ?: emptyList()

    @TypeConverter
    fun fromImage(image: Image?): String = jsonConverter.toJson(image)

    @TypeConverter
    fun toImage(json: String): Image? = jsonConverter.fromJson(json)

    @TypeConverter
    fun fromImages(images: MutableList<Image>): String = jsonConverter.toJson(images)

    @TypeConverter
    fun toImages(json: String): MutableList<Image> = jsonConverter.fromJson(json) ?: mutableListOf()

    @TypeConverter
    fun fromCreationDate(creationDate: CreationDate): String = jsonConverter.toJson(creationDate)

    @TypeConverter
    fun toReleaseDates(json: String): CreationDate? = jsonConverter.fromJson(json)

    @TypeConverter
    fun fromCreationDateCategory(category: CreationDateCategory): String =
        jsonConverter.toJson(category)

    @TypeConverter
    fun toCreationDateCategory(json: String): CreationDateCategory =
        jsonConverter.fromJson(json) ?: CreationDateCategory.UNKNOWN
}
