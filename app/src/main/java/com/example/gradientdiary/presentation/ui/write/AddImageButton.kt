package com.example.gradientdiary.presentation.ui.write

import android.Manifest
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview

import com.example.gradientdiary.R
import com.example.gradientdiary.presentation.theme.GradientDiaryTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File

@OptIn(ExperimentalPermissionsApi::class)
@ExperimentalAnimationApi
@Composable
fun AddImageButton(
    iconModifier: Modifier = Modifier,
    handleAddImage: (Uri?) -> Unit
) {

    var imageUri by rememberSaveable { mutableStateOf<Uri?>(null) }
    //  var showImageSelectDialog by remember { mutableStateOf(false) }
    val snackState = remember { SnackbarHostState() }
    val snackScope = rememberCoroutineScope()

    val context = LocalContext.current


    val getImageFromGalleryLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                imageUri = uri
                handleAddImage(imageUri)
            }
        }


    val state = rememberPermissionState(
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> Manifest.permission.READ_MEDIA_IMAGES
            else -> Manifest.permission.READ_EXTERNAL_STORAGE
        }
    )
    val permissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { wasGranted ->
            if (wasGranted) { // 권한체크
                getImageFromGalleryLauncher.launch("image/*")
            }
        }


    val handlePickFromGalleryIconButton = {
        //   showImageSelectDialog = false
        //getImageFromGalleryLauncher.launch("image/*")
        if (state.status != PermissionStatus.Granted) { // 권한 체크
            permissionLauncher.launch(
                when {
                    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> Manifest.permission.READ_MEDIA_IMAGES
                    else -> Manifest.permission.READ_EXTERNAL_STORAGE
                }
            )
        } else {
            getImageFromGalleryLauncher.launch("image/*")
        }

    }

    Icon(
        painterResource(id = R.drawable.ic_gallery),
        modifier = iconModifier.clickable {
            handlePickFromGalleryIconButton()
        },
        contentDescription = null
    )

    SnackbarHost(hostState = snackState)

}

@ExperimentalAnimationApi
@Preview
@Composable
fun AddImageButtonPreview() {
    GradientDiaryTheme {
        AddImageButton(
            handleAddImage = {}
        )
    }
}
