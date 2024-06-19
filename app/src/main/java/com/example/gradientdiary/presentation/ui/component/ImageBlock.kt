package com.example.gradientdiary.presentation.ui.component

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.Uri
import android.os.Parcelable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.gradientdiary.data.database.entity.ContentBlockEntity
import com.example.gradientdiary.data.database.entity.ContentType
import com.example.gradientdiary.presentation.ui.localSnackBarManager
import com.example.gradientdiary.presentation.viewModel.ContentBlockViewModel
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.android.parcel.Parcelize
import kotlinx.parcelize.IgnoredOnParcel
import timber.log.Timber

@Parcelize
data class ImageBlock(
    override var seq: Long = 0,
    override var content: Uri?
) : ContentBlock<Uri?>(), Parcelable {
    @IgnoredOnParcel
    var imageState: MutableState<Bitmap?> = mutableStateOf(null)

    override fun isEmpty(): Boolean = content == null || content.toString().isBlank()


    override fun addNextBlock(viewModel: ContentBlockViewModel) {
        viewModel.insertBlock(TextBlock(content = ""))
    }

    @SuppressLint("CoroutineCreationDuringComposition")
    @Composable
    override fun DrawEditableContent(modifier: Modifier, viewModel: ContentBlockViewModel) {
        // 권한이 이미 부여되었는지 확인
        val context = LocalContext.current
        val persistedPermissions = context.contentResolver.persistedUriPermissions
        var hasShownSnackbar by remember { mutableStateOf(false) }
        val snackBarManager = localSnackBarManager.current

        Timber.d("Persisted URI Permissions on load: $persistedPermissions")
        // URI가 persistedPermissions에 포함되어 있는지 확인
        if (persistedPermissions.any { it.uri == content }) {
            // 이미지 로드
            Box(modifier = Modifier.padding(16.dp)) {
                GlideImage(
                    imageModel = content,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .aspectRatio(0.8f),
                    contentScale = ContentScale.FillHeight,
                    contentDescription = null
                )
            }
        } else {
            if (!hasShownSnackbar) {
                snackBarManager.showMessage("일부 파일을 앨범에서 찾을 수 없어요.") // 파일을 삭제하여 uri 접근이 불가한 경우
                content?.let {
                    viewModel.tobeDeletedBlockByUri(it)
                }
                Timber.e("Permission Denial: No persistable permission grants found for URI !! $content")
                hasShownSnackbar = true
            }
        }
    }

    override fun convertToContentBlockEntity() = ContentBlockEntity(
        type = ContentType.Image,
        seq = seq,
        content = content.toString()
    )


}