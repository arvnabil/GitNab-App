package com.instechrx.gitnabilapp.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import androidx.constraintlayout.widget.Constraints
import com.instechrx.gitnabilapp.database.UserContract.UserColumns.Companion.ID
import com.instechrx.gitnabilapp.database.UserContract.UserColumns.Companion.TABLE_USER
import com.instechrx.gitnabilapp.database.UserContract.UserColumns.Companion.USERNAME
import java.sql.SQLException

class UserHelper(context: Context) {

    companion object{
        private const val DATABASE_TABLE = TABLE_USER
        private lateinit var databaseHelper: DatabaseHelper
        private var userHelper : UserHelper? = null

        fun getDatabase(context: Context) : UserHelper =
            userHelper ?: synchronized(this){
                userHelper ?: UserHelper(context)
            }

        private lateinit var database : SQLiteDatabase

    }

    init {
        databaseHelper = DatabaseHelper(context)
    }
    @Throws(SQLException::class)
    fun open(){
        database = databaseHelper.writableDatabase
    }

    fun queryAll() : Cursor{
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$ID ASC")
    }

    fun queryById(id: String) : Cursor{
        return database.query(
            DATABASE_TABLE,
            null,
            "$ID = ?",
            arrayOf(id),
            null,
            null,
            null,
            null)
    }

    fun insert(contentValues: ContentValues?) : Long{
        return database.insert(DATABASE_TABLE, null, contentValues)
    }

    fun update(id: String, contentValues: ContentValues?) : Int{
        return database.update(DATABASE_TABLE, contentValues, "$ID = ?", arrayOf(id))
    }

    fun delete(id: String): Int{
        return database.delete(DATABASE_TABLE, "$ID = '$id'", null)
    }


    fun check(login: String): Boolean {
        database = databaseHelper.writableDatabase
        val selectUsername =
            "SELECT * FROM $DATABASE_TABLE WHERE $USERNAME =?"
        val cursor =
            database.rawQuery(selectUsername, arrayOf(login))
        var check = false
        if (cursor.moveToFirst()) {
            check = true
            var i = 0
            while (cursor.moveToNext()) {
                i++
            }
            Log.d(Constraints.TAG, String.format("%d records found", i))
        }
        cursor.close()
        return check
    }
}