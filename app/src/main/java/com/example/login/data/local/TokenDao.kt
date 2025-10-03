package com.example.login.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TokenDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(token: TokenEntity)


    @Query("SELECT token FROM token WHERE id = 1 LIMIT 1")
    fun observeToken(): Flow<String?>

    //Lectura puntual
    @Query("SELECT token FROM token WHERE id = 1 LIMIT 1")
    suspend fun getTokenOnce(): String?

    @Query("DELETE FROM token WHERE id = 1")
    suspend fun clear()

    //Recupera todo dentro de la DB TOKEN
    @Query("SELECT * FROM token WHERE id = 1 LIMIT 1")
    suspend fun getUser(): TokenEntity?
}
