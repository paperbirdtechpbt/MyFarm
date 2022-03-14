package com.pbt.myfarm.Activity.CreatePack

import android.app.DatePickerDialog
import android.content.Context
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
import com.pbt.myfarm.HttpResponse.*
import com.pbt.myfarm.PackViewModel.Companion.packconfigobject
import com.pbt.myfarm.R
import com.pbt.myfarm.Service.ConfigFieldList
import com.pbt.myfarm.Util.AppUtils
import java.text.SimpleDateFormat
import java.util.*


class CreatePackAdapter(
    var context: Context, var list: List<PackConfigFieldList>,
    var updateTaskIdBoolean: Boolean,
    var communityGroupList: List<PackCommunityList>,
    var communityGroupListname: ArrayList<String>,


    var callbacks: (ArrayList<String>, ArrayList<String>) -> Unit
) : RecyclerView.Adapter<CreatePackAdapter.ViewHolder>() {
    val ss = ""
    var item: ConfigFieldList? = null
    var TAG = "CreateTaskAdapter"
    val fieldId: ArrayList<String>? = ArrayList()
    var isOnTextChanged = false
    val myCalendar: Calendar = Calendar.getInstance()
    var boolean = true

    companion object {
        var desciptioncompanian: String = ""
    }


    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        var name: EditText = itemView.findViewById(R.id.pack_field_name)
        var date: EditText = itemView.findViewById(R.id.pack_field_expectedDate)
        var mysppinner: Spinner = itemView.findViewById(R.id.pack_field_spinner)
        var labelname: TextView = itemView.findViewById(R.id.pack_label_fieldname)
        var labeldate: TextView = itemView.findViewById(R.id.pack_label_field_expectedDate)
        var labelSpinner: TextView = itemView.findViewById(R.id.pack_label_field_spinner)
        var labelDesciption: TextView = itemView.findViewById(R.id.pack_label_desciption)

        var labelpackname: TextView = itemView.findViewById(R.id.pack_label_fieldname_name)
        var labelnameprefix: TextView = itemView.findViewById(R.id.pack_label_fieldname_nameprefix)
        var labelcommunityGroup: TextView = itemView.findViewById(R.id.pack_label_fieldname_spinner)
        var packname: EditText = itemView.findViewById(R.id.pack_field_name_name)
        var nameprefix: EditText = itemView.findViewById(R.id.pack_field_name_nameprefix)
        var communityGroup: Spinner = itemView.findViewById(R.id.pack_field_spinner_communityGroup)
        var desciption: EditText = itemView.findViewById(R.id.pack_field_desciption)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.itempackfeildlist, parent, false)
        return ViewHolder(view)
    }


    private fun updateLabel(date: EditText) {
        val myFormat = "yyyy-MM-dd"
        val dateFormat = SimpleDateFormat(myFormat, Locale.US)
        date.setText(dateFormat.format(myCalendar.time))
    }

    fun callBack() {

        callbacks.invoke(ExpAmtArray, ExpName)
    }


    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, positionn: Int) {

        val fieldList: ArrayList<String>? = ArrayList()
        val fieldListid: ArrayList<String>? = ArrayList()
        holder.name.setTag(positionn)
        holder.date.setTag(positionn)


        AppUtils.logDebug(TAG, "possition" + positionn.toString())


        val getrow: Any = this.list.get(positionn)
        val t: LinkedTreeMap<Any, Any> = getrow as LinkedTreeMap<Any, Any>
        val fieldtype = t["field_type"].toString()
        val field_id = t["field_id"].toString()
        val namee = t["field_name"].toString()

        val field: ArrayList<PackFieldList> = t["field_list"] as ArrayList<PackFieldList>

        val row: Any = this.list.get(positionn)

        val d: LinkedTreeMap<Any, Any> = row as LinkedTreeMap<Any, Any>
        val valued = d["field_value"].toString()


        val l = list.size
        val f = fieldId!!.size
        if (l != f) {
            fieldId.add(field_id)

        }


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

            for (i in 0 until fieldList.size) {
                if (valued == fieldListid.toString()) {
                    val spinnerPosition: Int = aa.getPosition(valued)
                    holder.mysppinner.setSelection(spinnerPosition)
                }
            }

        }
        if (fieldtype == "Numeric" ) {


            setSpinner(holder.nameprefix,holder.packname,holder.communityGroup,holder.labelpackname,
            holder.labelnameprefix,holder.labelcommunityGroup,holder.labelDesciption,holder.desciption)

            holder.date.visibility = View.GONE
            holder.labeldate.visibility = View.GONE
            holder.mysppinner.visibility = View.GONE
            holder.labelSpinner.visibility = View.GONE



            holder.labelname.visibility = View.VISIBLE
            holder.name.visibility = View.VISIBLE

            holder.labelname.setText(namee)
            if (namee == "QUANTITY") {
                holder.name.setInputType(InputType.TYPE_CLASS_NUMBER)
            }
//            Handler(Looper.getMainLooper()).postDelayed ({
            if (updateTaskIdBoolean) {
                if (valued == "null") {
                    holder.name.setText("")
                } else {
                    holder.name.setText(valued)
                }
            }
//            }, 3500)

        }
        else if ( fieldtype == "Text"){


            setSpinner(holder.nameprefix,holder.packname,holder.communityGroup,holder.labelpackname,
                holder.labelnameprefix,holder.labelcommunityGroup,holder.labelDesciption,holder.desciption)

            holder.date.visibility = View.GONE
            holder.labeldate.visibility = View.GONE
            holder.mysppinner.visibility = View.GONE
            holder.labelSpinner.visibility = View.GONE



            holder.labelname.visibility = View.VISIBLE
            holder.name.visibility = View.VISIBLE

            holder.labelname.setText(namee)
            if (namee == "QUANTITY") {
                holder.name.setInputType(InputType.TYPE_CLASS_NUMBER)
            }
//            Handler(Looper.getMainLooper()).postDelayed ({
            if (updateTaskIdBoolean) {
                if (valued == "null") {
                    holder.name.setText("")
                } else {
                    holder.name.setText(valued)
                }
            }
//            }, 3500)

        }
        else if (fieldtype == "List" || fieldtype == "Table") {
            val d = fieldtype
            holder.date.visibility = View.GONE
            holder.name.visibility = View.GONE
            holder.labeldate.visibility = View.GONE
            holder.labelname.visibility = View.GONE


            holder.labelSpinner.visibility = View.VISIBLE
            holder.mysppinner.visibility = View.VISIBLE

            holder.labelSpinner.setText(namee)
            setSpinner(holder.nameprefix,holder.packname,holder.communityGroup,holder.labelpackname,
                holder.labelnameprefix,holder.labelcommunityGroup,holder.labelDesciption,holder.desciption)


        } else if (fieldtype == "Date") {
            val d = fieldtype

            holder.name.visibility = View.GONE
            holder.labelname.visibility = View.GONE
            holder.mysppinner.visibility = View.GONE
            holder.labelSpinner.visibility = View.GONE
            setSpinner(holder.nameprefix,holder.packname,holder.communityGroup,holder.labelpackname,
                holder.labelnameprefix,holder.labelcommunityGroup,holder.labelDesciption,holder.desciption)

            if (updateTaskIdBoolean) {
                if (valued == "null") {
                    holder.date.setText("")
                } else {
                    holder.date.setText(valued)
                }
            }

        }


        holder.mysppinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {

                val selectedText: String
                val selectedId: String
                ExpAmtArray.add("0")
                ExpName.add("0")
                ExpAmtArrayKey.add("0")
                ExpNameKey.add("0")
                selectedText = fieldListid!![position]
//                        selectedText = fieldId!![position]
                selectedId = fieldId[positionn]
                ExpAmtArray[position] = selectedText
                ExpName[position] = selectedId
                ExpAmtArrayKey[position] = "f_id"
                ExpNameKey[position] = "f_value"

            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }



        holder.date.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence, i: Int, i1: Int, i2: Int
            ) {
            }

            override fun onTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
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
        holder.desciption.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence, i: Int, i1: Int, i2: Int
            ) {
            }

            override fun onTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
            ) {
                isOnTextChanged = true
            }

            override fun afterTextChanged(editable: Editable) {
                if (isOnTextChanged) {
                    isOnTextChanged = false
                    try {
desciptioncompanian=holder.desciption.text.toString()




                    } catch (e: NumberFormatException) {
                        AppUtils.logDebug("asdfEXCEPTION", e.message.toString())
                        run {
                            var i = 0
                            while (i <= positionn) {
                                Log.d("TimesRemoved", " : $i")
                                if (i == positionn) {
                                    desciptioncompanian=holder.desciption.text.toString()
                                }
                                i++
                            }
                        }
                    }
                }
            }
        })
        holder.date.setOnClickListener {

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



        holder.name.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence, i: Int, i1: Int, i2: Int
            ) {
            }

            override fun onTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
            ) {
                isOnTextChanged = true
                AppUtils.logDebug(TAG,"POsition"+positionn)
            }

            override fun afterTextChanged(editable: Editable) {


                if (isOnTextChanged) {
                    isOnTextChanged = false
                    try {

                        for (obj in list) {

                            ExpAmtArray.add("0")
                            ExpName.add("0")
                            ExpAmtArray[positionn] = editable.toString()
                            ExpName[positionn] = field_id
                            AppUtils.logDebug(TAG, "posittionnn" + positionn.toString())
                            ExpAmtArrayKey[positionn] = "f_id"
                            ExpNameKey[positionn] = "f_value"
                        }

                        for (i in 0..positionn) {
                            if (i != positionn) {
                                ExpAmtArray.add("0")
                                ExpName.add("0")
                                ExpAmtArrayKey.add("0")
                                ExpNameKey.add("0")
                            } else {
//                                 store user entered value to Array list (ExpAmtArray) at particular position
                                ExpAmtArray.add("0")
                                ExpName.add("0")
                                ExpAmtArray[positionn] = editable.toString()
                                ExpName[positionn] = field_id
                                AppUtils.logDebug(TAG, "posittionnn" + positionn.toString())
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


    }

    private fun setSpinner(
        nameprefix: EditText,
        packname: EditText,
        communityGroup: Spinner,
        labelpackname: TextView,
        labelnameprefix: TextView,
        labelcommunityGroup: TextView,
        labelDesciption: TextView,
        desciption: EditText
    ) {
        if (boolean) {


//            Handler(Looper.getMainLooper()).postDelayed({
            nameprefix.setText(packconfigobject!!.name_prefix)
            packname.setText(packconfigobject!!.name)
            val aa =
                ArrayAdapter(context, android.R.layout.simple_spinner_item, communityGroupListname)
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            communityGroup.setAdapter(aa)
            boolean = false
//            },1000)

        }
        else{
            labelcommunityGroup.visibility = View.GONE
            communityGroup.visibility = View.GONE
            labelpackname.visibility = View.GONE
            labelnameprefix.visibility = View.GONE
            packname.visibility = View.GONE
            nameprefix.visibility = View.GONE
            labelDesciption.visibility = View.GONE
            desciption.visibility = View.GONE
        }
    }

    fun callBackss() {
        callbacks.invoke(ExpAmtArray, ExpName)
    }

}


