package com.coooldoggy.shopably.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [FavoriteEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao

    companion object{
        private var instance: AppDatabase? = null
        private val TAG = AppDatabase::class.java.simpleName

        fun getDatabase(context: Context): AppDatabase{
            return instance ?: synchronized(this){
                instance ?: createDB(context)
            }
        }

        private fun createDB(context: Context): AppDatabase{
            val db = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "favoriteDB")
            db.apply {
                addCallback(object : RoomDatabase.Callback(){
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        Log.d(TAG, "FavoriteDB createDB")
                    }
                })
            }
            return db.build()
        }
    }
}