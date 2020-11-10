package es.littledavity.dynamicfeatures.chorboList.detail.model

import android.net.Uri
import androidx.annotation.DrawableRes

data class Info(
    val section: Section,
    val value: InfoData,
    @DrawableRes val icon: Int
)

sealed class InfoItem {
    data class Header(val category: Section) : InfoItem()

    data class Item(val info: Info) : InfoItem()
}

sealed class InfoData {
    data class Simple(val value: String) : InfoData()

    data class Preview(val list: List<Uri>) : InfoData()
}

enum class Section(val title: String) {
    INFO("Información"),
    SOCIAL("Redes sociales"),
    PREVIEW("Galería")
}