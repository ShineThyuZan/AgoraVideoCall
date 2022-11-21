package com.omgea.agoravideocall.ui

import android.Manifest
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.omgea.agoravideocall.APP_ID
import com.omgea.agoravideocall.ui.VideoViewModel
import io.agora.agorauikit_android.AgoraConnectionData
import io.agora.agorauikit_android.AgoraVideoViewer


@OptIn(ExperimentalUnsignedTypes::class)
@Composable
fun VideoScreen(
    roomName: String,
    onNavigateUp: () -> Unit = {},
    viewModel: VideoViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    var agoraView: AgoraVideoViewer? = null
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { perms ->
            viewModel.onPermissionResult(
                acceptAudioPermission = perms[Manifest.permission.RECORD_AUDIO] == true,
                acceptCameraPermission = perms[Manifest.permission.CAMERA] == true,
            )

        })
    LaunchedEffect(key1 = true) {
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.CAMERA
            )
        )
    }

    BackHandler {
        agoraView?.leaveChannel()
        onNavigateUp()
    }

    if (viewModel.hasAudioPermission.value && viewModel.hasCameraPermission.value) {
        AndroidView(
            factory = {
                AgoraVideoViewer(
                    it,
                    connectionData = AgoraConnectionData(
                        appId = APP_ID
                    )
                ).also { agoraVideoView ->
                    agoraVideoView.join(roomName)
                    agoraView = agoraVideoView
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }

}