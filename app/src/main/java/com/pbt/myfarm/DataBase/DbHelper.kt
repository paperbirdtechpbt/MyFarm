package com.pbt.myfarm.DataBase

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_DATABASE_NAME
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_DATABASE_VERSION
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_ID
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_NEW_TASK
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_TASK_DETAIL
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_TASK_NAME
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_TASK_TYPE
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_USERNAME
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_USERPASS
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_USERROLE
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_USERS_TABLE
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_USER_ID

class DbHelper(context:Context,factory:SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context,CONST_DATABASE_NAME,factory, CONST_DATABASE_VERSION){


    override fun onCreate(db: SQLiteDatabase?) {
        val query=("CREATE TABLE " + CONST_USERS_TABLE + " ("
                + CONST_ID + " INTEGER PRIMARY KEY, " +
                CONST_USERNAME + " TEXT," +
                CONST_USERROLE + " TEXT," + CONST_USERPASS + " TEXT" + ")")
        db?.execSQL(query)

        val userTaskTable=("CREATE TABLE " + CONST_NEW_TASK + " ("
                + CONST_ID + " INTEGER PRIMARY KEY, " +
                CONST_USER_ID + " TEXT," +
                CONST_USERNAME + " TEXT," +
                CONST_TASK_NAME + " TEXT," +
                CONST_TASK_TYPE + " TEXT," +
                CONST_TASK_DETAIL+ " TEXT" + ")")

        db?.execSQL(userTaskTable)


    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + CONST_USERS_TABLE)
        onCreate(db)
    }
    fun addUser(name:String,password:String){
        val values=ContentValues()
        values.put(CONST_USERNAME,"pbt@admin")
        values.put(CONST_USERROLE,"admin")
        values.put(CONST_USERPASS,"pbt@admin")
        val db=this.writableDatabase
        db.insert(CONST_USERS_TABLE, null,values)
        db.close()

    }
    fun addTask(username:String,taskname:String,tasktype:String,taskdetail:String){
        val values=ContentValues()
        values.put(CONST_USERNAME,username)
        values.put(CONST_TASK_NAME,taskname)
        values.put(CONST_TASK_TYPE,tasktype)
        values.put(CONST_TASK_DETAIL,taskdetail)
        val db=this.writableDatabase
        db.insert(CONST_NEW_TASK, null,values)
        db.close()
    }
    fun getUser():Cursor?{
val db=this.readableDatabase
    return db.rawQuery("SELECT * FROM " + CONST_USERS_TABLE,null)


}
}