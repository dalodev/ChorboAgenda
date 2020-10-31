/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.chorboList.detail.model

data class ChorboDetailItem(
    val id: Long,
    val name: String,
    val image: String,
    val content: List<ChorboDetailContent>
)

sealed class ChorboDetailContent(
    val section: ContentType? = null,
    val content: String? = null
) {
    data class Content(
        val category: ContentType?,
        val data: String,
        val sectionContent: SectionContent
    ) : ChorboDetailContent(category, data)

    data class Section(
        val category: ContentType?
    ) : ChorboDetailContent(category)

    enum class ContentType {
        INFO,
        SOCIAL
    }

    enum class SectionContent {
        WHATSAPP,
        INSTAGRAM,
        LOCATION
    }
}



