package com.example.gradientdiary.presentation.viewModel

import androidx.lifecycle.ViewModel
import com.example.gradientdiary.data.database.entity.ContentEntity
import com.example.gradientdiary.data.database.entity.ContentType
import com.example.gradientdiary.presentation.ui.component.ContentBlock
import dagger.hilt.android.lifecycle.HiltViewModel

class ContentViewModel(
    initialContentEntity: List<ContentEntity>
) :ViewModel(){
    private var contentBlockList: MutableList<ContentEntity> = mutableListOf()
    init{
        if(initialContentEntity.isEmpty()){
            contentBlockList.add(ContentEntity(ContentType.Text,""))
        }else{
            contentBlockList = initialContentEntity.toMutableList()
        }
    }
}