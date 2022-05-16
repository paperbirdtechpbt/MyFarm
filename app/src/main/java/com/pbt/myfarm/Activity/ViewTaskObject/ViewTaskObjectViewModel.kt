package com.pbt.myfarm.Activity.ViewTaskObject

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.pbt.myfarm.DataBase.DbHelper
import com.pbt.myfarm.TaskObject
import com.pbt.myfarm.Util.AppUtils

class ViewTaskObjectViewModel (val activity: Application) : AndroidViewModel(activity){


    var mylist = MutableLiveData<List<TaskObject>>()


    init {

        mylist = MutableLiveData<List<TaskObject>>()

    }

    fun getObjectsList(context: Context, list: com.pbt.myfarm.Task?) {

    }


}
