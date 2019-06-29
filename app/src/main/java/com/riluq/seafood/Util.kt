package com.riluq.seafood

import android.Manifest
import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

fun visible() = View.VISIBLE
fun gone() = View.GONE

fun cameraPermission(context: Context, activity: Activity) {
    Dexter.withActivity(activity)
        .withPermission(Manifest.permission.CAMERA)
        .withListener(object: PermissionListener {
            override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
            }

            override fun onPermissionRationaleShouldBeShown(
                permission: PermissionRequest?,
                token: PermissionToken?
            ) {
                AlertDialog.Builder(context)
                    .setTitle(R.string.camera_permission_rationale_title)
                    .setMessage(R.string.camera_permission_rationale_message)
                    .setNegativeButton(android.R.string.cancel) { dialogInterface, i ->
                        dialogInterface.dismiss()
                        token?.cancelPermissionRequest()
                    }
                    .setPositiveButton(android.R.string.ok) { dialogInterface, i ->
                        dialogInterface.dismiss()
                        token?.continuePermissionRequest()
                    }
                    .show()
            }

            override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                Toast.makeText(context, R.string.camera_permission_denied_message, Toast.LENGTH_SHORT).show()
            }

        }).check()
}