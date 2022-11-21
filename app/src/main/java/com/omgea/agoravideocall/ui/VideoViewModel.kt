package com.omgea.agoravideocall.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class VideoViewModel : ViewModel() {
    private val _hasAudioPermission = mutableStateOf(false)
    val hasAudioPermission: State<Boolean> = _hasAudioPermission

    private val _hasCameraPermission = mutableStateOf(false)
    val hasCameraPermission: State<Boolean> = _hasCameraPermission

    fun onPermissionResult(
        acceptAudioPermission: Boolean,
        acceptCameraPermission: Boolean
    ) {
        _hasAudioPermission.value = acceptAudioPermission
        _hasCameraPermission.value = acceptCameraPermission
    }
}