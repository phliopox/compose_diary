package com.example.gradientdiary.presentation.ui.component

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.Uri
import android.os.Parcelable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.gradientdiary.data.database.entity.ContentBlockEntity
import com.example.gradientdiary.data.database.entity.ContentType
import com.example.gradientdiary.presentation.PersistedPermissionsCheck
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
        Timber.e("add next Block")
        viewModel.insertBlock(TextBlock(content = ""))
    }

    @SuppressLint("CoroutineCreationDuringComposition")
    @Composable
    override fun DrawEditableContent(modifier: Modifier, textAlign: TextAlign?,viewModel: ContentBlockViewModel) {
        // 권한이 이미 부여되었는지 확인

        PersistedPermissionsCheck(
            content = content,
            havePermission = {
                Box(modifier = modifier.padding(16.dp)) {
                    GlideImage(
                        imageModel = content,
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .heightIn(50.dp, 500.dp),
                        contentScale = ContentScale.Crop,
                        contentDescription = null
                    )
                }
            },
            notFound = {
                viewModel.tobeDeletedBlockByUri(it)

            })
        // URI가 persistedPermissions에 포함되어 있는지 확인
        /*        if (persistedPermissions.any { it.uri == content }) {
                    // 이미지 로드
                    Box(modifier = modifier.padding(16.dp)) {
                        GlideImage(
                            imageModel = content,
                            modifier = Modifier
                                .align(Alignment.TopCenter)
                                .heightIn(50.dp, 500.dp),
                            contentScale = ContentScale.Crop,
                            contentDescription = null
                        )
                    }
                } else {
                    if (!hasShownSnackbar) {
                        PersistedPermissionsCheck(content){
                            viewModel.tobeDeletedBlockByUri(it)
                        }
                        hasShownSnackbar = true
                    }
                }*/
    }

    override fun convertToContentBlockEntity() = ContentBlockEntity(
        type = ContentType.Image,
        seq = seq,
        content = content.toString()
    )
}
