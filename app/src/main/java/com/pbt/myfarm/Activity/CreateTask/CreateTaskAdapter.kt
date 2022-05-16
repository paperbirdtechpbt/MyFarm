package com.pbt.myfarm.Activity.CreateTask

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Context
import android.content.res.ColorStateList
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.internal.LinkedTreeMap
import com.pbt.myfarm.Activity.Home.MainActivity.Companion.ExpAmtArray
import com.pbt.myfarm.Activity.Home.MainActivity.Companion.ExpAmtArrayKey
import com.pbt.myfarm.Activity.Home.MainActivity.Companion.ExpName
import com.pbt.myfarm.Activity.Home.MainActivity.Companion.ExpNameKey
import com.pbt.myfarm.Activity.Home.MainActivity.Companion.privilegeListName
import com.pbt.myfarm.Activity.Home.MainActivity.Companion.privilegeListNameOffline
import com.pbt.myfarm.HttpResponse.Field
import com.pbt.myfarm.R
import com.pbt.myfarm.Service.ConfigFieldList
import com.pbt.myfarm.Util.AppUtils
import kotlinx.android.synthetic.main.activity_create_task.*
import kotlinx.android.synthetic.main.itemcongiffeildlist.view.*
import java.text.SimpleDateFormat
import java.util.*
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


    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") positionn: In) {

        val fieldList: ArrayList<String>? = ArrayList()
        val fieldListid: ArrayList<String>? = ArrayList()
        holder.name.setTag(positionn)
        holder.date.setTag(positionn)
        holder.spinner.setTag(positionn)


        val getrow: Any = this.list.get(positionn)
        AppUtils.logDebug(TAG, "VALUES  for is nULL  ${this.list.get(positionn)}")

        val t: LinkedTreeMap<Any, Any> = getrow as LinkedTreeMap<Any, Any>
        val fieldtype = t["field_type"].toString()
        val field_id = t["field_id"].toString()
        val namee = t["field_description"].toString()
        val editable = t["editable"].toString().toDouble().toInt()
//        val value = t["field_value"].toString()
        val field: ArrayList<Field> = t["field_list"] as ArrayList<Field>


//        val row: Any = this.list.get(positionn)

        val d: LinkedTreeMap<Any, Any> = getrow as LinkedTreeMap<Any, Any>
        val valued = d["field_value"].toString()

        val l = list.size
        val f = fieldId!!.size
        if (l != f) {
            fieldId.add(field_id)

        }


        if (fieldtype == "Table" || fieldtype == "List") {
            fieldList?.add("Select")
            fieldListid?.add("1")

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

        }
        if (fieldtype == "Date") {
            holder.name.visibility = View.GONE
            holder.spinner.visibility = View.GONE
            holder.labelFieldSpiiner.visibility = View.GONE
            holder.labelname.visibility = View.GONE
            holder.mysppinner.visibility = View.GONE
            holder.labelSpinner.visibility = View.GONE

            checkFoucusable(holder.itemView.field_expectedDate, null, editable)


            Handler(Looper.getMainLooper()).postDelayed({
                if (updateTaskIdBoolean) {
                    if (valued == "null") {
                        holder.date.setText("")
                    } else {
                        holder.date.setText(valued)
                    }

                }
            }, 1500)
            holder.labeldate.setText(namee)
        } else if (fieldtype == "Numeric") {

            holder.date.visibility = View.GONE
            holder.spinner.visibility = View.GONE
            holder.labelFieldSpiiner.visibility = View.GONE
            holder.labeldate.visibility = View.GONE
            holder.mysppinner.visibility = View.GONE
            holder.labelSpinner.visibility = View.GONE

            if (field_id == "155") {
                holder.name.inputType =
                    InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED

            }

            Handler(Looper.getMainLooper()).postDelayed({
                if (updateTaskIdBoolean) {
                    if (valued == "null") {
                        holder.name.setText("")
                    } else {
                        holder.name.setText(valued)
                    }
                }
            }, 1500)
            checkFoucusable(holder.itemView.field_name, null, editable)



            holder.labelname.setText(namee)

        } else if (fieldtype == "List" || fieldtype == "Multilist") {

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
            }, 500)
            checkFoucusable(null,holder.itemView.field_spinner, editable)



        } else if (fieldtype == "Table") {

            holder.date.visibility = View.GONE
            holder.labeldate.visibility = View.GONE
            holder.name.visibility = View.GONE
            holder.labelname.visibility = View.GONE
            holder.spinner.visibility = View.GONE
            holder.labelFieldSpiiner.visibility = View.GONE
            holder.labelSpinner.setText(namee)
            Handler(Looper.getMainLooper()).postDelayed({
                if (updateTaskIdBoolean) {
                    if (valued.isNullOrEmpty()) {

                        holder.name.setText("")
                    } else {
                        for (i in 0 until fieldListid!!.size) {
                            AppUtils.logDebug(
                                TAG,
                                "VALUES FOR SET SLECT#E ITEM =$valued.0 == $fieldListid.get(i)"
                            )
                            if (valued + ".0" == fieldListid.get(i)) {
                                AppUtils.logDebug(TAG, "VALUES FOR SET SLECT#E ITEM =$valued")
                                holder.mysppinner.setSelection(i)

                            }
                        }
                    }
                }
            }, 500)
            checkFoucusable(null,holder.itemView.field_spinner, editable)



        } else if (fieldtype == "DateTime") {
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
            }, 1000)
            holder.labeldate.setText(namee)
            checkFoucusable(holder.itemView.field_expectedDate,null, editable)





        } else if (fieldtype == "Text") {

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
            checkFoucusable(holder.itemView.field_name,null, editable)


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
//                ExpAmtArray[position] = selectedText
//                ExpName[position] = selectedId
                try {
                    ExpName[positionn] = selectedId
                    ExpAmtArray[positionn] = selectedText
                } catch (e: Exception) {
                    AppUtils.logDebug(TAG, "Exception in itemselect" + e.message.toString())
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
        if (!valued.isEmpty()) {
            for (i in 0 until fieldList!!.size) {
                if (valued == fieldListid?.get(i)) {
                    AppUtils.logDebug(TAG, "fieldlist" + fieldListid.get(i) + valued)
                    holder.mysppinner.setSelection(i)
                }
            }
        }




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

                                ExpName[positionn] = field_id

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

            var settime = ""

            val c = Calendar.getInstance()
            val mHour = c[Calendar.HOUR_OF_DAY]
            val mMinute = c[Calendar.MINUTE]
            val mSecond = c[Calendar.SECOND]
            if (fieldtype == "Date") {
                val date = DatePickerDialog.OnDateSetListener { v, year, month, day ->

                    myCalendar.set(Calendar.YEAR, year)
                    myCalendar.set(Calendar.MONTH, month)
                    myCalendar.set(Calendar.DAY_OF_MONTH, day)
                    updateLabel(holder.date, settime)
                }
                DatePickerDialog(
                    context, date, myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            } else {
                val timePickerDialog =
                    TimePickerDialog(context, OnTimeSetListener { view, hourOfDay, minute ->
                        settime = "T$hourOfDay:$minute"
                        AppUtils.logDebug(TAG, "settime" + settime)
//                    txtTime.setText("$hourOfDay:$minute")
                        updateLabel(holder.date, settime)

                    }, mHour, mMinute, false)

                timePickerDialog.show()

                val date = DatePickerDialog.OnDateSetListener { v, year, month, day ->

                    myCalendar.set(Calendar.YEAR, year)
                    myCalendar.set(Calendar.MONTH, month)
                    myCalendar.set(Calendar.DAY_OF_MONTH, day)
                    updateLabel(holder.date, settime)
                }
                DatePickerDialog(
                    context, date, myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            }

        }


    }

    private fun checkFoucusable(myview: EditText?, spinner: Spinner?, iseditable: In) {
        if (AppUtils().isInternet(context)) {
            if (!privilegeListName.contains("CanOverideEditTask")) {
                if (iseditable == 0) {

                        if (spinner == null) {
                            myview?.isEnabled = false
                            myview?.isFocusable = false
                            myview?.setBackgroundTintList(
                                ColorStateList.valueOf(
                                    context.resources.getColor(
                                        R.color.grey
                                    )
                                )
                            )

                        } else {
                            spinner.isEnabled = false
                            spinner.isFocusable = false
                            spinner.setBackgroundTintList(
                                ColorStateList.valueOf(
                                    context.resources.getColor(
                                        R.color.grey
                                    )
                                )
                            )
                        }


                }
            }


        } else {
            if (!privilegeListNameOffline.contains("CanOverideEditTask")) {
                if (iseditable == 0) {

                        if (spinner == null) {
                            myview?.isEnabled = false
                            myview?.isFocusable = false
                            myview?.setBackgroundTintList(
                                ColorStateList.valueOf(
                                    context.resources.getColor(
                                        R.color.grey
                                    )
                                )
                            )

                        } else {
//                            spinner?.isEnabled = false
//                            spinner?.isFocusable = false
                            spinner.isClickable = false
                            spinner.setBackgroundTintList(
                                ColorStateList.valueOf(
                                    context.resources.getColor(
                                        R.color.grey
                                    )
                                )
                            )
                        }

                }
            }


        }

    }


    private fun updateLabel(date: EditText, time: String) {
        val myFormat = "yyyy-MM-dd"
        val dateFormat = SimpleDateFormat(myFormat, Locale.US)
        if (time.isEmpty()) {
            date.setText(dateFormat.format(myCalendar.time))
        } else {
            date.setText(dateFormat.format(myCalendar.time) + time)

        }
    }

    fun callBack() {

        callbacks.invoke(ExpAmtArray, ExpName)
    }


    override fun getItemCount(): In {
        return list.size
    }
    override fun getItemViewType(position: In): In {
        return position
    }


}


