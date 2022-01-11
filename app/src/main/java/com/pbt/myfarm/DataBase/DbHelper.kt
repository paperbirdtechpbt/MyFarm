package com.pbt.myfarm.DataBase

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.pbt.myfarm.Activity.ViewTask.ViewTaskActivity
import com.pbt.myfarm.ModelClass.ViewTaskModelClass
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_DATABASE_NAME
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_DATABASE_VERSION
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_EXPECTED_END_DATE
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_EXPECTED_EXP_END_DATE
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_EXPECTED_EXP_STR_DATE
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_EXPECTED_STR_DATE
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
import com.pbt.myfarm.Util.AppUtils


class DbHelper(var context: Context,  factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, CONST_DATABASE_NAME, factory, CONST_DATABASE_VERSION) {

    private  val TAG = "DbHelper"


    override fun onCreate(db: SQLiteDatabase?) {
        val query = ("CREATE TABLE " + CONST_USERS_TABLE + " ("
                + CONST_ID + " INTEGER PRIMARY KEY, " +
                CONST_USERNAME + " TEXT," +
                CONST_USERROLE + " TEXT," + CONST_USERPASS + " TEXT" + ")")
        db?.execSQL(query)

        val userTaskTable = ("CREATE TABLE " + CONST_NEW_TASK + " ("
                + CONST_ID + " INTEGER PRIMARY KEY, " +
                CONST_USER_ID + " TEXT," +
                CONST_USERNAME + " TEXT," +
                CONST_TASK_NAME + " TEXT," +
                CONST_TASK_TYPE + " TEXT," +
                CONST_EXPECTED_EXP_STR_DATE + " TEXT," +
                CONST_EXPECTED_EXP_END_DATE + " TEXT," +
                CONST_EXPECTED_STR_DATE + " TEXT," +
                CONST_EXPECTED_END_DATE + " TEXT," +
                CONST_TASK_DETAIL + " TEXT" + ")")

        db?.execSQL(userTaskTable)


    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + CONST_USERS_TABLE)
        onCreate(db)
    }

    fun addUser() {
        val values = ContentValues()
        values.put(CONST_USERNAME, "pbt@admin")
        values.put(CONST_USERROLE, "admin")
        values.put(CONST_USERPASS, "pbt@admin")
        val db = this.writableDatabase
        db.insert(CONST_USERS_TABLE, null, values)
        db.close()

    }

    fun addTask(username: String, viewtask: ViewTaskModelClass) {

        try {
            val values = ContentValues()
            values.put(CONST_USERNAME, username)
            values.put(CONST_TASK_NAME, viewtask.ENTRYNAME+ CONST_ID)
            values.put(CONST_TASK_TYPE, viewtask.ENTRYTYPE)
            values.put(CONST_EXPECTED_EXP_STR_DATE, viewtask.ExpectedStartDate)
            values.put(CONST_EXPECTED_EXP_END_DATE, viewtask.ExpectedEndDate)
            values.put(CONST_EXPECTED_STR_DATE, viewtask.StartDate)
            values.put(CONST_EXPECTED_END_DATE, viewtask.EndDate)
            values.put(CONST_TASK_DETAIL, viewtask.ENTRYDETAIL)
            val db = this.writableDatabase
            val result = db.insert(CONST_NEW_TASK, null, values)
            db.close()
            AppUtils.logDebug(TAG, result.toString())
            if (result >= 0) {
                Toast.makeText(context, "Added", Toast.LENGTH_SHORT).show()
                val intent = Intent(context, ViewTaskActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)

                Activity().finish()


            } else {
                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()

            }
        }catch (e:Exception){
            AppUtils.logError(TAG,e.message!!)
        }



    }
    fun updateTask(task: ViewTaskModelClass, entryname: String):Int{
        val db=this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(CONST_TASK_NAME, task.ENTRYNAME)
        contentValues.put(CONST_TASK_TYPE, task.ENTRYTYPE)
        contentValues.put(CONST_TASK_DETAIL, task.ENTRYDETAIL)
        contentValues.put(CONST_EXPECTED_EXP_STR_DATE, task.ExpectedStartDate)
        contentValues.put(CONST_EXPECTED_EXP_END_DATE, task.ExpectedEndDate)
        contentValues.put(CONST_EXPECTED_STR_DATE, task.StartDate)
        contentValues.put(CONST_EXPECTED_END_DATE, task.EndDate)
//        val success=db.update(CONST_NEW_TASK,contentValues,"$CONST_TASK_NAME="+task.ENTRYNAME,
//    null)
      val success=  db.update(CONST_NEW_TASK, contentValues,"$CONST_TASK_NAME = ?", arrayOf(entryname))
        db.close()
        return success
    }

    @SuppressLint("Range")
    fun readData(): ArrayList<ViewTaskModelClass> {
        val list: ArrayList<ViewTaskModelClass> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from $CONST_NEW_TASK"
        val cursor: Cursor?
        try {
            cursor = db.rawQuery(query, null)
        } catch (e: Exception) {
            AppUtils.logError(TAG, e.message!!)
            db.execSQL(query)
            return ArrayList()
        }
        var taskname: String
        var tasktype: String
        var taskdetail: String
        var expectedStartDate:String
        var expectedEndDate:String
        var startdate:String
        var endDate:String
        if (cursor.moveToFirst()) {
            do {
                taskname = cursor.getString(cursor.getColumnIndex(CONST_TASK_NAME))
                tasktype = cursor.getString(cursor.getColumnIndex(CONST_TASK_TYPE))
                taskdetail = cursor.getString(cursor.getColumnIndex(CONST_TASK_DETAIL))
                expectedStartDate = cursor.getString(cursor.getColumnIndex(
                    CONST_EXPECTED_EXP_STR_DATE))
                expectedEndDate = cursor.getString(cursor.getColumnIndex(CONST_EXPECTED_EXP_END_DATE))
                startdate = cursor.getString(cursor.getColumnIndex(CONST_EXPECTED_STR_DATE))
                endDate = cursor.getString(cursor.getColumnIndex(CONST_EXPECTED_END_DATE))
                val lst = ViewTaskModelClass(
                    ENTRYDETAIL = taskdetail, ENTRYNAME = taskname,
                    ENTRYTYPE = tasktype,ExpectedStartDate = expectedStartDate,
                    ExpectedEndDate = expectedEndDate,StartDate = startdate,
                    EndDate = endDate
                )
                list.add(lst)
            } while (cursor.moveToNext())
        }
        return list

    }

    fun deleteTask(taskname: String) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(CONST_TASK_NAME, taskname)
        db.delete(CONST_NEW_TASK, "taskname=?", arrayOf(taskname))
        db.close()

    }


}