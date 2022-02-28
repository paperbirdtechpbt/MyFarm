package com.pbt.myfarm.Activity.CreateTask

import android.app.DatePickerDialog
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.internal.LinkedTreeMap
import com.pbt.myfarm.Activity.CreateTask.CreateTaskActivity.Companion.ExpAmtArray
import com.pbt.myfarm.Activity.CreateTask.CreateTaskActivity.Companion.ExpAmtArrayKey
import com.pbt.myfarm.Activity.CreateTask.CreateTaskActivity.Companion.ExpName
import com.pbt.myfarm.Activity.CreateTask.CreateTaskActivity.Companion.ExpNameKey
import com.pbt.myfarm.HttpResponse.Field
import com.pbt.myfarm.R
import com.pbt.myfarm.Service.ConfigFieldList
import com.pbt.myfarm.Util.AppUtils
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.Int as In


class CreateTaskAdapter(
    var context: Context, var list: List<ConfigFieldList>,
    var updateTaskIdBoolean: Boolean,
    var callbacks: (ArrayList<String>, ArrayList<String>) -> Unit
) : RecyclerView.Adapter<CreateTaskAdapter.ViewHolder>() {
    val ss = ""
    var item: ConfigFieldList? = null
    var TAG = "CreateTaskAdapter"
    val fieldId: ArrayList<String>? = ArrayList()
    var isOnTextChanged = false
    val myCalendar: Calendar = Calendar.getInstance()


    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        var name: EditText = itemView.findViewById(R.id.field_name)
        var date: EditText = itemView.findViewById(R.id.field_expectedDate)
        var spinner: EditText = itemView.findViewById(R.id.field_spinner_Grip)

        //var communityGroup: Spinner = itemView.findViewById(R.id.field_communityGroup)
        var mysppinner: Spinner = itemView.findViewById(R.id.field_spinner)
        var labelname: TextView = itemView.findViewById(R.id.label_fieldname)
        var labeldate: TextView = itemView.findViewById(R.id.label_field_expectedDate)
        var labelFieldSpiiner: TextView = itemView.findViewById(R.id.label_field_spinner_Grip)
        var labelSpinner: TextView = itemView.findViewById(R.id.label_field_spinner)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: In): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.itemcongiffeildlist, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, positionn: In) {

        val fieldList: ArrayList<String>? = ArrayList()
        val fieldListid: ArrayList<String>? = ArrayList()
        holder.name.setTag(positionn)
        holder.date.setTag(positionn)
        holder.spinner.setTag(positionn)


        val getrow: Any = this.list.get(positionn)
        val t: LinkedTreeMap<Any, Any> = getrow as LinkedTreeMap<Any, Any>
        val fieldtype = t["field_type"].toString()
        val field_id = t["field_id"].toString()
        val namee = t["field_name"].toString()
//        val value = t["field_value"].toString()
        val field: ArrayList<Field> = t["field_list"] as ArrayList<Field>


        val row: Any = this.list.get(positionn)

        val d: LinkedTreeMap<Any, Any> = row as LinkedTreeMap<Any, Any>
        val valued = d["field_value"].toString()


        AppUtils.logDebug(TAG, "Value--->" + valued)

        val l = list.size
        val f = fieldId!!.size
        if (l != f) {
            fieldId.add(field_id)
            AppUtils.logDebug(TAG, "size-->" + fieldId) }


        if (fieldtype == "Table" || fieldtype == "List") {

            for (i in 0 until field.size) {
                val row: Any = field.get(i)
                val rowmap: LinkedTreeMap<Any, Any> = row as LinkedTreeMap<Any, Any>
                val name = rowmap["name"].toString()
                val id = rowmap["id"].toString()

                fieldList?.add(name)
                fieldListid?.add(id)

            }
        }
        if (!fieldList.isNullOrEmpty()) {
            val aa = ArrayAdapter(context, android.R.layout.simple_spinner_item, fieldList)
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            holder.mysppinner.setAdapter(aa)

//            Handler(Looper.getMainLooper()).postDelayed({
//                for (i in 0 until field.size) {
//                    if (valued == fieldListid.toString()) {
//                        val pos = field.get(i).name
//                        AppUtils.logDebug(TAG, "posssssss----" + pos)
//                        val spinnerPosition: kotlin.Int = aa.getPosition(pos)
//                        holder.mysppinner.setSelection(i)
//                    }
//                }
//            }, 1500)


        }



        holder.mysppinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: In,
                id: Long
            ) {

                var selectedText = ""
                var selectedId = ""
                ExpAmtArray.add("0")
                ExpName.add("0")
                ExpAmtArrayKey.add("0")
                ExpNameKey.add("0")
                //selectedText contains spinner values ID
                selectedText = fieldListid!![position]
//                        selectedText = fieldId!![position]
                //selectedId contains values of the edittext postition
                selectedId = fieldId!![positionn]
                AppUtils.logDebug(TAG,"FieldList Id and Name"+selectedText+"-"+selectedId)
                ExpAmtArray[position] = selectedText
                ExpName[position] = selectedId
                ExpAmtArrayKey[position] = "f_id"
                ExpNameKey[position] = "f_value"

            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
                    Handler(Looper.getMainLooper()).postDelayed({
                for (i in 0 until fieldListid!!.size) {
                    if (valued == fieldListid.toString()) {

                        holder.mysppinner.setSelection(i)
                    }
                }
            }, 1500)


        holder.name.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence, i: kotlin.Int, i1: kotlin.Int, i2: kotlin.Int
            ) {
            }

            override fun onTextChanged(
                charSequence: CharSequence,
                i: kotlin.Int,
                i1: kotlin.Int,
                i2: kotlin.Int
            ) {
                isOnTextChanged = true
            }

            override fun afterTextChanged(editable: Editable) {

                //so this will trigger each time user enter value in editText box
                if (isOnTextChanged) {
                    isOnTextChanged = false
                    try {

                        for (i in 0..positionn) {
                            if (i != positionn) {
                                //store 0  where user select position in not equal/
                                ExpAmtArray.add("0")
                                ExpName.add("0")
                                ExpAmtArrayKey.add("0")
                                ExpNameKey.add("0")
                            } else {
                                // store user entered value to Array list (ExpAmtArray) at particular position
                                ExpAmtArray.add("0")
                                ExpName.add("0")
                                ExpAmtArray[positionn] = editable.toString()
                                ExpName[positionn] = field_id
                                ExpAmtArrayKey[positionn] = "f_id"
                                ExpNameKey[positionn] = "f_value"
                                break
                            }
                        }


                    } catch (e: NumberFormatException) {
                        AppUtils.logDebug("asdfEXCEPTION", e.message.toString())
                        run {
                            var i = 0
                            while (i <= positionn) {
                                Log.d("TimesRemoved", " : $i")
                                if (i == positionn) {
                                    ExpAmtArray[positionn] = "0"
                                    ExpName[positionn] = "0"
                                    ExpAmtArrayKey[positionn] = "0"
                                    ExpNameKey[positionn] = "0"
                                }
                                i++
                            }
                        }


                    }
                }
            }
        })
        holder.spinner.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence, i: kotlin.Int, i1: kotlin.Int, i2: kotlin.Int
            ) {
            }

            override fun onTextChanged(
                charSequence: CharSequence,
                i: kotlin.Int,
                i1: kotlin.Int,
                i2: kotlin.Int
            ) {
                //using this boolean because sometime when user enter value in edittxt
                //afterTextchanged runs twice to prevent this, i m making use of this variable.
                isOnTextChanged = true
            }

            override fun afterTextChanged(editable: Editable) {
                //so this will trigger each time user enter value in editText box

                if (isOnTextChanged) {
                    isOnTextChanged = false
                    try {

                        for (i in 0..positionn) {
                            if (i != positionn) {
                                //store 0  where user select position in not equal/
                                ExpAmtArray.add("0")
                                ExpName.add("0")
                                ExpAmtArrayKey.add("0")
                                ExpNameKey.add("0")
                            } else {
                                // store user entered value to Array list (ExpAmtArray) at particular position
                                ExpAmtArray.add("0")
                                ExpName.add("0")
                                ExpAmtArray[positionn] = editable.toString()
                                ExpName[positionn] = field_id
                                ExpAmtArrayKey[positionn] = "f_id"
                                ExpNameKey[positionn] = "f_value"
                                break
                            }
                        }


                    } catch (e: NumberFormatException) {
                        AppUtils.logDebug("asdfEXCEPTION", e.message.toString())
                        run {
                            var i = 0
                            while (i <= positionn) {
                                Log.d("TimesRemoved", " : $i")
                                if (i == positionn) {
                                    ExpAmtArray[positionn] = "0"
                                    ExpName[positionn] = "0"
                                    ExpAmtArrayKey[positionn] = "0"
                                    ExpNameKey[positionn] = "0"
                                }
                                i++
                            }
                        }


                    }
                }
            }
        })
        holder.date.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence, i: kotlin.Int, i1: kotlin.Int, i2: kotlin.Int
            ) {
            }

            override fun onTextChanged(
                charSequence: CharSequence,
                i: kotlin.Int,
                i1: kotlin.Int,
                i2: kotlin.Int
            ) {
                isOnTextChanged = true
            }

            override fun afterTextChanged(editable: Editable) {
                if (isOnTextChanged) {
                    isOnTextChanged = false
                    try {


                        for (i in 0..positionn) {
                            if (i != positionn) {
                                ExpAmtArray.add("0")
                                ExpName.add("0")
                                ExpAmtArrayKey.add("0")
                                ExpNameKey.add("0")
                            } else {
                                ExpAmtArray.add("0")
                                ExpName.add("0")
                                ExpAmtArray[positionn] = editable.toString()
                                AppUtils.logDebug(TAG, editable.toString())
                                ExpName[positionn] = field_id
                                AppUtils.logDebug(TAG, field_id)
                                ExpAmtArrayKey[positionn] = "f_id"
                                ExpNameKey[positionn] = "f_value"
                                break
                            }
                        }


                    } catch (e: NumberFormatException) {
                        AppUtils.logDebug("asdfEXCEPTION", e.message.toString())
                        run {
                            var i = 0
                            while (i <= positionn) {
                                Log.d("TimesRemoved", " : $i")
                                if (i == positionn) {
                                    ExpAmtArray[positionn] = "0"
                                    ExpName[positionn] = "0"
                                    ExpAmtArrayKey[positionn] = "0"
                                    ExpNameKey[positionn] = "0"
                                }
                                i++
                            }
                        }
                    }
                }
            }
        })
        holder.date.setOnClickListener {

            AppUtils.logDebug("TAG", "on CLick in date")
            val date = DatePickerDialog.OnDateSetListener { v, year, month, day ->

                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, month)
                myCalendar.set(Calendar.DAY_OF_MONTH, day)
                updateLabel(holder.date)
            }
            DatePickerDialog(
                context, date, myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }


        if (fieldtype == "Date") {
            holder.name.visibility = View.GONE
            holder.spinner.visibility = View.GONE
            holder.labelFieldSpiiner.visibility = View.GONE
            holder.labelname.visibility = View.GONE
            holder.mysppinner.visibility = View.GONE
            holder.labelSpinner.visibility = View.GONE



            Handler(Looper.getMainLooper()).postDelayed({
                if (updateTaskIdBoolean) {
                    if (valued == "null") {
                        holder.date.setText("")
                    } else {
                        holder.date.setText(valued)
                    }

                }
            }, 3500)
            holder.labeldate.setText(namee)
        } else if (fieldtype == "Numeric") {

            holder.date.visibility = View.GONE
            holder.spinner.visibility = View.GONE
            holder.labelFieldSpiiner.visibility = View.GONE
            holder.labeldate.visibility = View.GONE
            holder.mysppinner.visibility = View.GONE
            holder.labelSpinner.visibility = View.GONE

            Handler(Looper.getMainLooper()).postDelayed({
                if (updateTaskIdBoolean) {
                    if (valued == "null") {
                        holder.name.setText("")
                    } else {
                        holder.name.setText(valued)
                    }
                }
            }, 1500)


            holder.labelname.setText(namee)

        } else if (fieldtype == "List") {

            holder.date.visibility = View.GONE
            holder.spinner.visibility = View.GONE
            holder.name.visibility = View.GONE
            holder.labelFieldSpiiner.visibility = View.GONE
            holder.labeldate.visibility = View.GONE
            holder.labelname.visibility = View.GONE
            holder.labelSpinner.setText(namee)

            Handler(Looper.getMainLooper()).postDelayed({
                if (updateTaskIdBoolean) {
                    if (valued == "null") {
//                        holder.name.setText("")
                    } else {
                        for (i in 0 until fieldListid!!.size) {
                            if (valued == fieldListid.get(i)) {
                                holder.mysppinner.setSelection(i)

                            }

                        }
                    }
                }
            }, 1500)


        } else if (fieldtype == "Table") {

            holder.date.visibility = View.GONE
            holder.labeldate.visibility = View.GONE
            holder.name.visibility = View.GONE
            holder.labelname.visibility = View.GONE
            holder.spinner.visibility = View.GONE
            holder.labelFieldSpiiner.visibility = View.GONE
            holder.labelSpinner.setText(namee)
        }


    }


    private fun updateLabel(date: EditText) {
        val myFormat = "yyyy-MM-dd"
        val dateFormat = SimpleDateFormat(myFormat, Locale.US)
        date.setText(dateFormat.format(myCalendar.time))
    }

    fun callBack() {

        callbacks.invoke(ExpAmtArray, ExpName)
    }


    override fun getItemCount(): In {
        return list.size
    }


}


