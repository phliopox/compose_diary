package com.example.gradientdiary.presentation.ui.write

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.gradientdiary.R
import com.example.gradientdiary.presentation.theme.GradientDiaryTheme
import com.example.gradientdiary.presentation.ui.localSnackBarManager
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import timber.log.Timber

@OptIn(ExperimentalPermissionsApi::class)
@ExperimentalAnimationApi
@Composable
fun AddImageButton(
    iconModifier: Modifier = Modifier,
    handleAddImage: (Uri?) -> Unit
) {

    var imageUri by rememberSaveable { mutableStateOf<Uri?>(null) }
    val snackBarManager = localSnackBarManager.current
    val context = LocalContext.current


    val getImageFromGalleryLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.OpenDocument()) { uri ->
            uri?.let {
                try {
                    val flags =
                        Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                    context.contentResolver.takePersistableUriPermission(
                        it,
                        flags
                    )
                    // 권한 부여 확인 로그
                    val persistedPermissions = context.contentResolver.persistedUriPermissions
                    Timber.d("Persisted URI Permissions: $persistedPermissions")
                    imageUri = uri
                    handleAddImage(imageUri)
                } catch (e: SecurityException) {
                    Timber.e("URI 읽기 실패 :" + e.message)
                    snackBarManager.showMessage("URI 읽기 권한 실패")

                }
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
                getImageFromGalleryLauncher.launch(arrayOf("image/*"))
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
            getImageFromGalleryLauncher.launch(arrayOf("image/*"))
        }

    }

    Icon(
        painterResource(id = R.drawable.ic_gallery),
        modifier = iconModifier.clickable {
            handlePickFromGalleryIconButton()
        },
        contentDescription = null
    )

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
