package com.example.login.data.local

import com.example.login.data.local.TokenEntity

interface TokenDao {

    fun upsert(token: TokenEntity)

    fun getTokenOnce(): String?

    fun clear()

    fun getUser(): TokenEntity?
}
