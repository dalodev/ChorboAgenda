/*
 * Copyright 2021 dalodev
 */
package es.littledavity.core.commons

import android.os.Build

object SdkInfo {

    @JvmField val SDK_VERSION = Build.VERSION.SDK_INT

    @JvmField val IS_AT_LEAST_KITKAT = SDK_VERSION >= Build.VERSION_CODES.KITKAT
    @JvmField val IS_AT_LEAST_LOLLIPOP = SDK_VERSION >= Build.VERSION_CODES.LOLLIPOP
    @JvmField val IS_AT_LEAST_LOLLIPOP_MR1 = SDK_VERSION >= Build.VERSION_CODES.LOLLIPOP_MR1
    @JvmField val IS_AT_LEAST_MARSHMALLOW = SDK_VERSION >= Build.VERSION_CODES.M
    @JvmField val IS_AT_LEAST_NOUGAT = SDK_VERSION >= Build.VERSION_CODES.N
    @JvmField val IS_AT_LEAST_NOUGAT_MR1 = SDK_VERSION >= Build.VERSION_CODES.N_MR1
    @JvmField val IS_AT_LEAST_OREO = SDK_VERSION >= Build.VERSION_CODES.O
    @JvmField val IS_AT_LEAST_OREO_MR1 = SDK_VERSION >= Build.VERSION_CODES.O_MR1
    @JvmField val IS_AT_LEAST_PIE = SDK_VERSION >= Build.VERSION_CODES.P
    @JvmField val IS_AT_LEAST_10 = SDK_VERSION >= Build.VERSION_CODES.Q
    @JvmField val IS_AT_LEAST_11 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.R
}
