package es.littledavity.dynamicfeatures.chorbo_list.detail.model

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