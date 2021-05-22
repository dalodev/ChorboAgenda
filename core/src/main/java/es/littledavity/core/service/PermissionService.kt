/*
 * Copyright 2021 dev.id
 */
package es.littledavity.core.service

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.karumi.dexter.Dexter
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import javax.inject.Inject

class PermissionService @Inject constructor(
    internal val context: Context
) {
    fun requestPermissions(
        permissions: List<String>,
        listener: MultiplePermissionsListener
    ) {
        Dexter.withContext(context).withPermissions(permissions).withListener(listener).check()
    }

    fun requestPermission(permission: String, listener: PermissionListener) {
        Dexter.withContext(context).withPermission(permission).withListener(listener).check()
    }

    fun checkPermissions(permission: List<String>) = !permission.any {
        ContextCompat.checkSelfPermission(context, it) != PackageManager.PERMISSION_GRANTED
    }
}
