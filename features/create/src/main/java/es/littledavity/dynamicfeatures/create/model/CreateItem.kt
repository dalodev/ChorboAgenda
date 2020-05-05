package es.littledavity.dynamicfeatures.create.model

import java.io.Serializable

data class CreateItem(
    var name: String,
    var image: String
): Serializable