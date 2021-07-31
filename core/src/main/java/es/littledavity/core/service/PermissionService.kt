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
import com.paulrybitskyi.hiltbinder.BindType
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface PermissionService {
    fun requestPermissions(
        permissions: List<String>,
        listener: MultiplePermissionsListener
    )

    fun requestPermission(permission: String, listener: PermissionListener)
    fun checkPermissions(permission: List<String>): Boolean
}

@BindType
class PermissionServiceImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : PermissionService {
    override fun requestPermissions(
        permissions: List<String>,
        listener: MultiplePermissionsListener
    ) {
        Dexter.withContext(context).withPermissions(permissions).withListener(listener).check()
    }

    override fun requestPermission(permission: String, listener: PermissionListener) {
        Dexter.withContext(context).withPermission(permission).withListener(listener).check()
    }

    override fun checkPermissions(permission: List<String>) = !permission.any {
        ContextCompat.checkSelfPermission(context, it) != PackageManager.PERMISSION_GRANTED
    }
}
