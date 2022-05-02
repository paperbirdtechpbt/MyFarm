package com.pbt.myfarm.Fragement.CreatePack


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.pbt.myfarm.Activity.CreatePack.CreatePackActivity.Companion.arrayID
import com.pbt.myfarm.Activity.CreatePack.CreatePackActivity.Companion.arrayIDKey
import com.pbt.myfarm.Activity.CreatePack.CreatePackActivity.Companion.arrayName
import com.pbt.myfarm.Activity.CreatePack.CreatePackActivity.Companion.arrayNameKey
import com.pbt.myfarm.Activity.CreatePack.CreatePackAdapter
import com.pbt.myfarm.Activity.CreateTask.FieldModel
import com.pbt.myfarm.Activity.Graph.GraphActivity
import com.pbt.myfarm.Activity.Home.MainActivity
import com.pbt.myfarm.Activity.Home.MainActivity.Companion.selectedCommunityGroup
import com.pbt.myfarm.Activity.Pack.PackActivity
import com.pbt.myfarm.Activity.Pack.PackActivity.Companion.desciptioncompanian
import com.pbt.myfarm.Activity.ViewTask.ViewTaskActivity.Companion.updateTaskBoolen
import com.pbt.myfarm.DataBase.DbHelper
import com.pbt.myfarm.HttpResponse.PackConfigFieldList
import com.pbt.myfarm.HttpResponse.testresponse
import com.pbt.myfarm.PackViewModel
import com.pbt.myfarm.PackViewModel.Companion.packCommunityList
import com.pbt.myfarm.PackViewModel.Companion.packCommunityListname
import com.pbt.myfarm.R
import com.pbt.myfarm.Service.ApiClient
import com.pbt.myfarm.Service.ApiInterFace
import com.pbt.myfarm.Util.AppConstant.Companion.PACK_LIST_PACKID
import com.pbt.myfarm.Util.AppUtils
import com.pbt.myfarm.Util.MySharedPreference
import kotlinx.android.synthetic.main.activity_create_pack.*
import kotlinx.android.synthetic.main.fragment_create_pack_frament.*
import kotlinx.android.synthetic.main.fragment_create_pack_frament.btn_update_pack
import kotlinx.android.synthetic.main.itemlist_collectdata.view.*
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import java.util.*
import com.pbt.myfarm.Activity.Pack.PackActivity.Companion.packList as packList1


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

//this fragement is for update pack

class UpdatePackFragement : Fragment(),
    retrofit2.Callback<testresponse> {
    private var param1: String? = null
    private var param2: String? = null

    var name: EditText? = null
    var viewmodel: PackViewModel? = null
    var adapterr: CreatePackAdapter? = null
    val successObject = JSONArray()
    var configtype: EditText? = null
    var customer: EditText? = null
    val fieldModel = ArrayList<FieldModel>()
    val TAG = "CreatePackFragment"
    val packconfigListfrgm = ArrayList<PackConfigFieldList>()
    var recyclerView: RecyclerView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
//            param3=it.getParcelable(CONST_PACK_UPDATE_LIST)
//            AppUtils.logDebug(TAG,"param3----"+param3.toString())

        }
    }

    override fun onResume() {
        super.onResume()
//        progressbar_createpackfrgm.visibility = View.VISIBLE
        arrayID = ArrayList()
        arrayName = ArrayList()
        arrayIDKey = ArrayList()
        arrayNameKey = ArrayList()

        arrayID!!.clear()
        arrayName!!.clear()
        arrayIDKey!!.clear()
        arrayNameKey!!.clear()

//        initViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_create_pack_frament, container, false)
//        quantity=view?.findViewById(R.id.fed_pack_quantity)
//        AppUtils.logError(
//            TAG,
//            "onconfigfieldlist" + packList1?.pack_config_id.toString() + "\n" + packList1?.id!!.toString()
//        )
        arrayID = ArrayList()
        arrayName = ArrayList()
        arrayIDKey = ArrayList()
        arrayNameKey = ArrayList()

        arrayID!!.clear()
        arrayName!!.clear()
        arrayIDKey!!.clear()
        arrayNameKey!!.clear()
//        if (MainActivity.privilegeListName.contains("GraphChart")){
//            btn_viewgraph.visibility=View.VISIBLE
//        }

        initViewModel()

        val updateTask: Button = view.findViewById(R.id.btn_update_pack)
        val btnViewTask: Button = view.findViewById(R.id.btn_viewgraph)

        updateTask.setOnClickListener {

            adapterr?.callBack()


        }
        btnViewTask.setOnClickListener {
//            AppUtils.logDebug(TAG,"packlistttt"+packList1.toString())

            val intent = Intent(requireContext(), GraphActivity::class.java)
//            intent.putExtra(PACK_LIST_PACKID, packList1)
            intent.putExtra(PACK_LIST_PACKID, packList1?.pack_config_id.toString())
            startActivity(intent)

        }


        return view
    }

    private fun initViewModel() {
        viewmodel =
            ViewModelProvider(this).get(PackViewModel::class.java)
        AppUtils.logDebug(TAG,"packlistid--packlistconfig="+packList1?.pack_config_id.toString()+"="+
                packList1?.id.toString())

        viewmodel?.onConfigFieldList(
            requireContext(), true,
            packList1?.pack_config_id.toString(), packList1!!.id.toString()
        )
        viewmodel?.configlist?.observe(viewLifecycleOwner, androidx.lifecycle.Observer { list ->
//            setCommunityGroup()
            if (!list.isNullOrEmpty()) {
                progressbar_createpackfrgm.visibility = View.GONE
            }
            val config =
                Gson().fromJson(Gson().toJson(list), ArrayList<PackConfigFieldList>()::class.java)
            recycler_packFieldsFragment?.layoutManager = LinearLayoutManager(requireContext())
            adapterr =
                CreatePackAdapter(
                    requireContext(), config, true, packCommunityList,
                    packCommunityListname
                ) { list, name ->


                    if (list.isNotEmpty() && name.isNotEmpty()) {
                        AppUtils.logDebug(
                            TAG, "expname" + list.toString() +
                                    name.toString()
                        )
                        while (list.contains("0")) {
                            list.remove("0")
                        }
                        while (name.contains("0")) {
                            name.remove("0")
                        }
//                    while (ExpNameKey.contains("0")) {
//                        ExpNameKey.remove("0")
//                    }
//                    while (ExpAmtArrayKey.contains("0")) {
//                        ExpAmtArrayKey.remove("0")
//                    }


                        if (name.isNotEmpty()) {
//                            for (i in 0..name.size) {
//                                ExpAmtArrayKey!![i] = "f_id"
//                                ExpNameKey!![i] = "f_value"
//                            }
                            for (i in 0 until name.size) {
                                val jsonObject = JSONObject()

                                jsonObject.put(arrayIDKey!!.get(i), list.get(i))
                                jsonObject.put(arrayNameKey!!.get(i), name.get(i))

                                successObject?.put(jsonObject)
                                fieldModel.add(FieldModel(name.get(i), list.get(i)))
                                addPackValue(
                                    list.get(i),
                                    name.get(i),
                                    packList1?.id.toString(),
                                    true
                                )

                            }
                        }

                    }
                    if (successObject != null) {
                        updatePAck()

                    } else {
                        updatePAck()

                    }


                }
            recycler_packFieldsFragment.adapter = adapterr
        })
    }

    private fun addPackValue(list: String, name: String, idd: String, isUpdate: Boolean) {
        val db = DbHelper(requireContext(), null)
        db.addPackValues(list, name, idd, true)

    }

    private fun updatePAck() {
        progressbar_createpackfrgm.visibility = View.VISIBLE

        btn_update_pack.visibility = View.GONE
        if (desciptioncompanian.isNullOrEmpty()) {
            desciptioncompanian = "Desciption"
        }

//
        val db = DbHelper(requireContext(), null)
        val list = db.getAllPackConfig()
        var prefixname: String? = null
        for (i in 0 until list.size) {
            if (packList1?.pack_config_id == list.get(i).id.toString()) {
                if (list.get(i).name_prefix == null) {
                    prefixname = ""
                } else {
                    prefixname = list.get(i).name_prefix!!

                }
            }

        }

        AppUtils.logDebug(
            TAG, "update Task--" + prefixname +
                    desciptioncompanian +
                    selectedCommunityGroup +
                    MySharedPreference.getUser(requireContext())?.id.toString() +
                    successObject.toString() +
                    packList1?.id.toString()
        )
        if (desciptioncompanian!!.isEmpty()) {
            Toast.makeText(requireContext(), "Desciption is Required", Toast.LENGTH_SHORT).show()
            btn_create_pack.visibility = View.VISIBLE

        } else {
            if (prefixname.isNullOrEmpty()) {
                if (AppUtils().isInternet(requireContext())){
                    val db = DbHelper(requireContext(), null)
                    db.updatePackNew(packList1, desciptioncompanian!!, selectedCommunityGroup)
                }
                else{
                    ApiClient.client.create(ApiInterFace::class.java)
                        .updatePack(
                            packList1?.pack_config_id!!,
                            desciptioncompanian!!,
                            selectedCommunityGroup,
                            MySharedPreference.getUser(requireContext())?.id.toString(),
                            successObject.toString(),
                            "",
                            packList1?.id.toString()
                        ).enqueue(this)
                }




            } else {
                if (AppUtils().isInternet(requireContext())){
                    ApiClient.client.create(ApiInterFace::class.java)
                        .updatePack(
                            packList1?.pack_config_id!!,
                            desciptioncompanian!!,
                            selectedCommunityGroup,
                            MySharedPreference.getUser(requireContext())?.id.toString(),
                            successObject.toString(),
                            prefixname!!,
                            packList1?.id.toString()
                        ).enqueue(this)
                }
                else{
                    val db = DbHelper(requireContext(), null)
                    db.updatePackNew(packList1, desciptioncompanian!!, selectedCommunityGroup)
                }

            }

        }


    }


    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UpdatePackFragement().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onResponse(call: Call<testresponse>, response: Response<testresponse>) {
        if (response.body()?.error == false) {
            progressbar_createpackfrgm.visibility = View.GONE

            btn_update_pack.visibility = View.VISIBLE
            Toast.makeText(requireContext(), "Pack Updated Successfully", Toast.LENGTH_SHORT).show()


//            val db=DbHelper(requireContext(),null)
//            db.updatePackNew(packList1, desciptioncompanian, selectedCommunityGroup)
            startActivity(Intent(requireContext(), PackActivity::class.java))
            activity?.finish()
        } else {
            progressbar_createpackfrgm.visibility = View.GONE
            btn_update_pack.visibility = View.VISIBLE
            Toast.makeText(requireContext(), response.body()?.msg, Toast.LENGTH_SHORT).show()
        }

    }

    override fun onFailure(call: Call<testresponse>, t: Throwable) {
        try {
            progressbar_createpackfrgm.visibility = View.GONE

            btn_update_pack.visibility = View.VISIBLE
            Toast.makeText(requireContext(), "Failed To Update", Toast.LENGTH_SHORT).show()
            AppUtils.logDebug(TAG, "Failur report-->" + t.message)
        } catch (e: Exception) {
            progressbar_createpackfrgm.visibility = View.GONE

            btn_update_pack.visibility = View.VISIBLE
            Toast.makeText(requireContext(), "Failed To Update", Toast.LENGTH_SHORT).show()
            AppUtils.logDebug(TAG, "Failur report-->" + e.message)
        }


    }


}