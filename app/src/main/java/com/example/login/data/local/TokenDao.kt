package com.example.login.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao //Data Access Object
interface TokenDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(token: TokenEntity) //ingresa o remplaza

    //Se observa toda la fila (token + firstname)
    @Query("SELECT token FROM token WHERE id = 1 LIMIT 1")
    fun observeToken(): Flow<String?> // observa y s cambia devuelve un flow que emite el valor del token

    //Lectura puntual
    @Query("SELECT token FROM token WHERE id = 1 LIMIT 1")
    suspend fun getTokenOnce(): String?

    @Query("DELETE FROM token WHERE id = 1")
    suspend fun clear()

    //Recupera el fistName
    @Query("SELECT * FROM token WHERE id = 1")
    suspend fun getUser(): TokenEntity?
}















//hola soy un comentario
