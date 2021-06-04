package com.instechrx.gitnabilapp.helper

import android.database.Cursor
import com.instechrx.gitnabilapp.database.UserContract.UserColumns.Companion.AVATAR
import com.instechrx.gitnabilapp.database.UserContract.UserColumns.Companion.HTML
import com.instechrx.gitnabilapp.database.UserContract.UserColumns.Companion.ID
import com.instechrx.gitnabilapp.database.UserContract.UserColumns.Companion.USERNAME
import com.instechrx.gitnabilapp.model.user.User

object MappingHelper {

    fun mapCursorToList(cursor: Cursor?) : List<User>{
        val userList = ArrayList<User>()

        cursor?.apply {
            while (moveToNext()){
                val id = getInt(getColumnIndexOrThrow(ID))
                val username = getString(getColumnIndexOrThrow(USERNAME))
                val avatar = getString(getColumnIndexOrThrow(AVATAR))
                val html = getString(getColumnIndexOrThrow(HTML))
                userList.add(User(username, avatar, html, id))
            }
        }

        return userList
    }
}