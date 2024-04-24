package com.example.gradientdiary.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.Query
import com.example.gradientdiary.data.database.entity.CategoryEntity
import kotlinx.coroutines.flow.StateFlow

@Dao
abstract class CategoryDao {
    @Insert(onConflict = IGNORE)
    abstract fun insertCategoryEntity(category: CategoryEntity)

    @Query("select * from CategoryEntity")
    abstract fun getAllCategory(): StateFlow<List<CategoryEntity>>

    @Delete
    abstract fun deleteCategory(categoryEntity: CategoryEntity)
}