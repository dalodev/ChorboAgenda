/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.chorboList.detail.model

data class ChorboDetailItem(
    val id: Long,
    val name: String,
    val image: String,
    val countryCode: String,
    val countryName: String,
    val flag: String,
    val whatsapp: String,
    val instagram: String
)
