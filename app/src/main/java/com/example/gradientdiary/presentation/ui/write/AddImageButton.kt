package com.example.gradientdiary.presentation.ui.write

import android.net.Uri
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
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File

@ExperimentalAnimationApi
@Composable
fun AddImageButton(
    iconModifier: Modifier = Modifier,
    handleAddImage: (Uri?) -> Unit
) {

    var imageUri by rememberSaveable { mutableStateOf<Uri?>(null) }
    var showImageSelectDialog by remember { mutableStateOf(false) }
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



    val handlePickFromGalleryIconButton = {
        showImageSelectDialog = false
        getImageFromGalleryLauncher.launch("image/*")
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

/* val permissionDeniedAction = {
     snackScope.launch {
         snackState.showSnackbar(
             message = "카메라 권한이 거부 되었습니다. 사진 찍기 기능을 사용할 수 없습니다. 설정에서 권한을 허용 해주세요.",
             duration = SnackbarDuration.Short
         )
     }
 }*/

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
