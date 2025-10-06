package com.example.login.data.local

import android.content.ContentValues
import android.database.Cursor
import com.example.login.data.local.AppDbHelper
import com.example.login.data.local.TokenEntity

class TokenDaoImpl(private val dbHelper: AppDbHelper) : TokenDao {

    override fun upsert(token: TokenEntity) {
        val db = dbHelper.writableDatabase
        db.beginTransaction()
        try {
            // Intentar UPDATE
            val values = ContentValues().apply {
                put("id", token.id)
                put("token", token.token)
                put("firstName", token.firstName)
            }
            val rows = db.update(
                "token",
                values,
                "id = ?",
                arrayOf(token.id.toString())
            )
            if (rows == 0) {
                db.insertOrThrow("token", null, values)
            }
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
    }

    override fun getTokenOnce(): String? {
        val db = dbHelper.readableDatabase
        var c: Cursor? = null
        return try {
            c = db.query(
                "token",
                arrayOf("token"),
                "id = 1",
                null, null, null, null,
                "1"
            )
            if (c.moveToFirst()) c.getString(0) else null
        } finally {
            c?.close()
        }
    }

    override fun getUser(): TokenEntity? {
        val db = dbHelper.readableDatabase
        var c: Cursor? = null
        return try {
            c = db.query(
                "token",
                arrayOf("id", "token", "firstName"),
                "id = 1",
                null, null, null, null,
                "1"
            )
            if (c.moveToFirst()) {
                TokenEntity(
                    id = c.getInt(0),
                    token = c.getString(1),
                    firstName = c.getString(2)
                )
            } else null
        } finally {
            c?.close()
        }
    }

    override fun clear() {
        val db = dbHelper.writableDatabase
        db.delete("token", "id = 1", null)
    }
}
