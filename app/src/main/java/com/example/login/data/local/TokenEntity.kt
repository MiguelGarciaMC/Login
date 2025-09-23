package com.example.login.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "token")
data class TokenEntity(
    @PrimaryKey val id: Int = 1,      // siempre 1 para tener un único registro
    val token: String
)
