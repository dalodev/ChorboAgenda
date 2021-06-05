package es.littledavity.features.image.viewer.widgets

import android.content.Context
import es.littledavity.commons.ui.base.rv.AbstractRecyclerViewAdapter
import es.littledavity.commons.ui.base.rv.NoDependencies

internal class ImageViewerAdapter(
    context: Context
): AbstractRecyclerViewAdapter<ImageViewerItem, NoDependencies>(context)