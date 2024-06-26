package com.example.gradientdiary.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.Query
import com.example.gradientdiary.data.database.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertCategoryEntity(category: CategoryEntity)

    @Query("select * from CategoryEntity")
    abstract fun getAllCategory(): Flow<List<CategoryEntity>>

    @Delete
    abstract fun deleteCategory(categoryEntity: CategoryEntity)

    @Query("UPDATE CategoryEntity SET categoryName = :newName WHERE id = :id")
    abstract fun updateCategoryName(id: Long, newName: String)
}