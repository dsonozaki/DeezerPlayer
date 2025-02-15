package com.sonozaki.core.presentation

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat




private fun hasToAskPermission(activity: Activity, permission: String) =
    ContextCompat.checkSelfPermission(
        activity,
        permission
    ) != PackageManager.PERMISSION_GRANTED

/**
 * Wrapper for asking permission.
 * @param activity Activity
 * @param permission Permission to request
 * @param showDialogWithExplanation Ask permission again if user denied it previously
 * @param requestPermission Ask permission
 * @param permissionProvided Called if permission was provided
 */
fun askPermission(
    activity: Activity,
    permission: String,
    showDialogWithExplanation: () -> Unit,
    requestPermission: () -> Unit,
    permissionProvided: () -> Unit
) {
    if (hasToAskPermission(activity, permission)) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
            showDialogWithExplanation()
        } else {
            requestPermission()
        }
    } else {
        permissionProvided()
    }
}
