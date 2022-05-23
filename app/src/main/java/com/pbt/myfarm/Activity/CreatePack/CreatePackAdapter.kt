package com.pbt.myfarm.Activity.CreatePack

import android.app.DatePickerDialog
import android.content.Context
import android.content.res.ColorStateList
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
import com.pbt.myfarm.Activity.CreatePack.CreatePackActivity.Companion.arrayID
import com.pbt.myfarm.Activity.CreatePack.CreatePackActivity.Companion.arrayName
import com.pbt.myfarm.Activity.CreateTask.CreateTaskActivity
import com.pbt.myfarm.Activity.Home.MainActivity
import com.pbt.myfarm.Activity.Home.MainActivity.Companion.ExpAmtArrayKey
import com.pbt.myfarm.Activity.Home.MainActivity.Companion.ExpNameKey
import com.pbt.myfarm.Activity.Home.MainActivity.Companion.selectedCommunityGroup
import com.pbt.myfarm.Activity.Pack.PackActivity.Companion.desciptioncompanian
import com.pbt.myfarm.Activity.Pack.PackActivity.Companion.packList
import com.pbt.myfarm.Activity.Pack.PackActivity.Companion.selectedcom_Group_companian
import com.pbt.myfarm.HttpResponse.*
import com.pbt.myfarm.PackViewModel.Companion.labelPackConfigName
import com.pbt.myfarm.PackViewModel.Companion.labelPackConfigPrefix
import com.pbt.myfarm.PackViewModel.Companion.packCommunityList
import com.pbt.myfarm.PackViewModel.Companion.packconfigobject
import com.pbt.myfarm.R
import com.pbt.myfarm.Service.ConfigFieldList
import com.pbt.myfarm.Util.AppUtils
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


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

        callbacks.invoke(arrayID!!, arrayName!!)
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
        val editable = t["editable"].toString().toDouble().toInt()

        val field: ArrayList<PackFieldList> = t["field_list"] as ArrayList<PackFieldList>

        val row: Any = this.list.get(positionn)

        val d: LinkedTreeMap<Any, Any> = row as LinkedTreeMap<Any, Any>
        val valued = d["field_value"].toString()
        AppUtils.logDebug(TAG, "values" + valued)


        val l = list.size
        val f = fieldId!!.size
        if (l != f) {
            fieldId.add(field_id)

        }


        if (fieldtype == "Table" || fieldtype == "List") {
            fieldList?.add("Select")
            fieldListid?.add("0")

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
        if (fieldtype == "Numeric" || fieldtype == "Text") {


            setSpinner(
                holder.nameprefix,
                holder.packname,
                holder.communityGroup,
                holder.labelpackname,
                holder.labelnameprefix,
                holder.labelcommunityGroup,
                holder.labelDesciption,
                holder.desciption
            )

            holder.date.visibility = View.GONE
            holder.labeldate.visibility = View.GONE
            holder.mysppinner.visibility = View.GONE
            holder.labelSpinner.visibility = View.GONE



            holder.labelname.visibility = View.VISIBLE
            holder.name.visibility = View.VISIBLE

            holder.labelname.setText(namee)
            checkFoucable(holder.name, null, editable)
            if (namee == "QUANTITY") {
                holder.name.setInputType(InputType.TYPE_CLASS_NUMBER)
            }
//            Handler(Looper.getMainLooper()).postDelayed ({
            if (updateTaskIdBoolean) {
                if (valued == "null") {
                    holder.desciption.setText(packList?.description)
                    desciptioncompanian = packList?.description

                    holder.name.setText("")
                } else {
                    holder.desciption.setText(packList?.description)
                    desciptioncompanian = packList?.description
                    holder.name.setText(valued)
                }
            } else {
                holder.desciption.setText("")

            }
//            }, 3500)

        } else if (fieldtype == "List" || fieldtype == "Table") {
            holder.desciption.setText(packList?.description)


            holder.date.visibility = View.GONE
            holder.name.visibility = View.GONE
            holder.labeldate.visibility = View.GONE
            holder.labelname.visibility = View.GONE


            holder.labelSpinner.visibility = View.VISIBLE
            holder.mysppinner.visibility = View.VISIBLE

            checkFoucable(null, holder.mysppinner, editable)


            holder.labelSpinner.setText(namee)
            if (!valued.isEmpty()) {
                for (i in 0 until fieldList!!.size) {
                    if (valued == fieldListid?.get(i)) {
                        AppUtils.logDebug(TAG, "fieldlist" + fieldListid.get(i) + valued)
                        holder.mysppinner.setSelection(i)
                    }
                }
            }
            setSpinner(
                holder.nameprefix,
                holder.packname,
                holder.communityGroup,
                holder.labelpackname,
                holder.labelnameprefix,
                holder.labelcommunityGroup,
                holder.labelDesciption,
                holder.desciption
            )


        } else if (fieldtype == "Date") {
            holder.desciption.setText(packList?.description)
            desciptioncompanian = packList?.description


            holder.name.visibility = View.GONE
            holder.labelname.visibility = View.GONE
            holder.mysppinner.visibility = View.GONE
            holder.labelSpinner.visibility = View.GONE
            setSpinner(
                holder.nameprefix,
                holder.packname,
                holder.communityGroup,
                holder.labelpackname,
                holder.labelnameprefix,
                holder.labelcommunityGroup,
                holder.labelDesciption,
                holder.desciption
            )

            checkFoucable(holder.date, null, editable)

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
                if (position != 0) {
                    var spinnername: String? = null
                    var spinnerId: String? = null
                    spinnerId = fieldId[positionn].toString()
//                        selectedText = fieldId!![position]
                    spinnername = fieldListid!![position]
                    AppUtils.logError(
                        TAG, "spinnername" + spinnername + "\nid" +
                                spinnerId + "\n" + positionn + "size" + list.size
                    )
                    try {
                        arrayID!![positionn] = spinnerId
                        arrayName!![positionn] = spinnername
                    } catch (e: Exception) {
                        AppUtils.logDebug(TAG, "Exception in itemselect" + e.message.toString())
                    }

                }


            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        holder.communityGroup.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {

                selectedCommunityGroup = communityGroupList.get(position).id

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

                        for (i in 0..list.size) {
                            if (i != positionn) {
                                arrayID!!.add("0")
                                arrayName!!.add("0")
                                ExpAmtArrayKey.add("0")
                                ExpNameKey.add("0")
                            } else {
                                arrayID!!.add("0")
                                arrayName!!.add("0")
                                arrayName!![positionn] = editable.toString()
                                arrayID!![positionn] = field_id

//                                ExpAmtArrayKey[i] = "f_id"
//                                ExpNameKey[i] = "f_value"
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
                                    arrayID!![positionn] = "0"
                                    arrayName!![positionn] = "0"
//                                    ExpAmtArrayKey[positionn] = "0"
//                                    ExpNameKey[positionn] = "0"
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
                        desciptioncompanian = holder.desciption.text.toString()

                    } catch (e: NumberFormatException) {
                        AppUtils.logDebug("asdfEXCEPTION", e.message.toString())
                        run {
                            var i = 0
                            while (i <= positionn) {
                                Log.d("TimesRemoved", " : $i")
                                if (i == positionn) {
                                    desciptioncompanian = holder.desciption.text.toString()
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
            }

            override fun afterTextChanged(editable: Editable) {

                if (isOnTextChanged) {
                    isOnTextChanged = false
                    try {

                        for (i in 0..list.size) {

                            if (i != positionn) {
                                arrayID!!.add("0")
                                arrayName!!.add("0")
//                                ExpAmtArrayKey.add("0")
//                                ExpNameKey.add("0")
                            } else {
//                                 store user entered value to Array list (ExpAmtArray) at particular position

                                arrayName!![positionn] = editable.toString()
                                arrayID!![positionn] = field_id

                                AppUtils.logDebug(TAG, "posittionnn" + positionn.toString())
//                                ExpAmtArrayKey[i] = "f_id"
//                                ExpNameKey[i] = "f_value"
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
                                    arrayID!![i] = "0"
                                    arrayName!![i] = "0"
//                                    ExpAmtArrayKey[i] = "0"
//                                    ExpNameKey[i] = "0"
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

            if (AppUtils().isInternet(context)) {
                nameprefix.setText(packconfigobject!!.name_prefix)
                packname.setText(packconfigobject!!.name)
            } else {
                nameprefix.setText(labelPackConfigPrefix)
                packname.setText(labelPackConfigName)
            }

            val aa =
                ArrayAdapter(context, android.R.layout.simple_spinner_item, communityGroupListname)
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            communityGroup.setAdapter(aa)

            if (!selectedcom_Group_companian.isNullOrEmpty()) {
                for (i in packCommunityList.indices) {
                    val item = packCommunityList.get(i)
                    if (selectedcom_Group_companian!!.toDouble().toInt() == item.id.toDouble()
                            .toInt()
                    ) {
                        communityGroup.setSelection(i)
                    }
                }

            }

            boolean = false
//            },1000)
            checkFoucable(packname,communityGroup,0)
            checkFoucable(nameprefix,null,0)

        }
        else {
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
        AppUtils.logDebug(TAG, arrayName.toString() + "dsd" + arrayID)
        callbacks.invoke(arrayID!!, arrayName!!)
    }

    fun checkFoucable(editext: EditText?, spinner: Spinner?, iseditable: Int) {
        if (updateTaskIdBoolean) {
//            if (CreateTaskActivity.checkFieldStatus?.status == "completed" || CreateTaskActivity.checkFieldStatus?.status == null) {
//                editext?.isEnabled = false
//                editext?.isFocusable = false
//                editext?.setBackgroundTintList(
//                    ColorStateList.valueOf(
//                        context.resources.getColor(
//                            R.color.grey
//                        )
//                    )
//                )
//                spinner?.isEnabled = false
//                spinner?.isFocusable = false
//                spinner?.setBackgroundTintList(
//                    ColorStateList.valueOf(
//                        context.resources.getColor(
//                            R.color.grey
//                        )
//                    )
//                )
//            } else {
                if (AppUtils().isInternet(context)) {
//                    if (!MainActivity.privilegeListName.contains("CanOverideEditTask")) {
                        if (iseditable == 0) {

                            if (spinner == null) {
                                editext?.isEnabled = false
                                editext?.isFocusable = false
                                editext?.setBackgroundTintList(
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


                        } else {
                            editext?.isEnabled = true
                            editext?.isFocusable = true
                            spinner?.isEnabled = true
                            spinner?.isFocusable = true
                        }
//                    } else {
//                        editext?.isEnabled = true
//                        editext?.isFocusable = true
//                        spinner?.isEnabled = true
//                        spinner?.isFocusable = true
//                    }


                }
//                else {
//                    if (!privilegeListNameOffline.contains("CanOverideEditTask")) {
//                        if (iseditable == 0) {
//
//                            if (spinner == null) {
//                                myview?.isEnabled = false
//                                myview?.isFocusable = false
//                                myview?.setBackgroundTintList(
//                                    ColorStateList.valueOf(
//                                        context.resources.getColor(
//                                            R.color.grey
//                                        )
//                                    )
//                                )
//
//                            } else {
////                            spinner?.isEnabled = false
////                            spinner?.isFocusable = false
//                                spinner.isClickable = false
//                                spinner.setBackgroundTintList(
//                                    ColorStateList.valueOf(
//                                        context.resources.getColor(
//                                            R.color.grey
//                                        )
//                                    )
//                                )
//                            }
//
//                        }
//                    }
//                }

//                myview?.isEnabled = true
//                myview?.isFocusable = true
//                myview?.setBackgroundTintList(
//                    ColorStateList.valueOf(
//                        context.resources.getColor(
//                            R.color.grey
//                        )
//                    )
//                )
//                spinner?.isEnabled = true
//                spinner?.isFocusable = true
//                spinner?.setBackgroundTintList(
//                    ColorStateList.valueOf(
//                        context.resources.getColor(
//                            R.color.grey
//                        )
//                    )
//                )
            }

        }
    }




