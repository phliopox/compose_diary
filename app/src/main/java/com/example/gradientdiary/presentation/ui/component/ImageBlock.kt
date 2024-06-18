package com.example.gradientdiary.presentation.ui.component

import android.graphics.Bitmap
import android.icu.number.Scale
import android.net.Uri
import android.os.Parcelable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.gradientdiary.data.database.entity.ContentBlockEntity
import com.example.gradientdiary.data.database.entity.ContentType
import com.example.gradientdiary.presentation.viewModel.ContentBlockViewModel
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

    @Composable
    override fun DrawEditableContent(modifier: Modifier, viewModel: ContentBlockViewModel) {
        Timber.e("imageDraw : $content")
        Box(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest
                        .Builder(LocalContext.current)
                        .data(data = content)
                        .build()
                ),
                modifier = Modifier
                    .aspectRatio(0.8f),
                contentScale = ContentScale.FillHeight,
                contentDescription = null
            )
        }
    }

    override fun convertToContentBlockEntity() = ContentBlockEntity(
        type = ContentType.Image,
        seq = seq,
        content = content.toString()
    )


}