package es.littledavity.dynamicfeatures.chorboList.detail.model

data class Info(val section: Section, val value: String)

sealed class InfoItem {
    data class Header(val category: Section) : InfoItem() {
        override val id = category.title
    }

    data class Item(val info: Info) : InfoItem() {
        override val id = info.value
    }

    abstract val id: String
}

enum class Section(val title: String) {
    INFO("Información"),
    SOCIAL("Redes sociales")
}