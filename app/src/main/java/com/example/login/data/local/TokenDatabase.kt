package com.example.login.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [TokenEntity::class],
    version = 1,
    exportSchema = false
)
abstract class TokenDatabase : RoomDatabase() {
    abstract fun tokenDao(): TokenDao

    companion object {
        @Volatile private var INSTANCE: TokenDatabase? = null

        fun getInstance(context: Context): TokenDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    TokenDatabase::class.java,
                    "token.db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
