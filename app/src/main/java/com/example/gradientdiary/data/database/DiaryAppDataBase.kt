package com.example.gradientdiary.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.gradientdiary.data.database.entity.CategoryEntity
import com.example.gradientdiary.data.database.entity.DiaryEntity
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import java.io.File
import java.util.concurrent.Executors


@Database(
    entities = [
        DiaryEntity::class,
        CategoryEntity::class
    ],
    version = 4,
    exportSchema = false
)
@TypeConverters(com.example.gradientdiary.data.database.TypeConverters::class)
abstract class DiaryAppDataBase : RoomDatabase() {
    abstract fun diaryDao(): DiaryDao
    abstract fun categoryDao(): CategoryDao

    companion object {
        fun getInstance(context: Context): DiaryAppDataBase =
            Room.databaseBuilder(context, DiaryAppDataBase::class.java, "diary_app.db")
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        Timber.e("db onCreate ")

                        Executors.newSingleThreadExecutor().execute {
                            runBlocking {
                                getInstance(context).categoryDao().insertCategoryEntity(
                                    CategoryEntity(1,"일기"))
                                Timber.e("SQL 호출됨 ")
                            }
                        }
                    }
                })
                .fallbackToDestructiveMigration().build()
    }
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // 예: "diary_table"에 "new_column" 추가
        database.execSQL("ALTER TABLE DiaryEntity ADD COLUMN title TEXT")
    }
}

fun deleteDatabaseFile(context : Context ,databaseName: String?) {
    val databases: File = File(context.applicationInfo.dataDir + "/databases")
    val db = File(databases, databaseName)
    if (db.delete()) Timber.e("Database deleted successfully") else Timber.e("Failed to delete database")
}
