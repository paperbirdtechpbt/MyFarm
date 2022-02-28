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
import com.pbt.myfarm.Activity.CreatePack.CreatePackActivity
import com.pbt.myfarm.Activity.CreatePack.CreatePackAdapter
import com.pbt.myfarm.Activity.CreateTask.CreateTaskActivity.Companion.ExpAmtArrayKey
import com.pbt.myfarm.Activity.CreateTask.CreateTaskActivity.Companion.ExpNameKey
import com.pbt.myfarm.Activity.CreateTask.FieldModel
import com.pbt.myfarm.Activity.Pack.PackActivity
import com.pbt.myfarm.Activity.Pack.PackActivity.Companion.packList
import com.pbt.myfarm.Activity.ViewTask.ViewTaskActivity.Companion.updateTaskBoolen
import com.pbt.myfarm.HttpResponse.PackConfigFieldList
import com.pbt.myfarm.HttpResponse.testresponse
import com.pbt.myfarm.PackViewModel
import com.pbt.myfarm.PackViewModel.Companion.packCommunityList
import com.pbt.myfarm.PackViewModel.Companion.packCommunityListname
import com.pbt.myfarm.PackViewModel.Companion.packconfigList
import com.pbt.myfarm.R
import com.pbt.myfarm.Service.ApiClient
import com.pbt.myfarm.Service.ApiInterFace
import com.pbt.myfarm.Util.AppUtils
import com.pbt.myfarm.Util.MySharedPreference
import kotlinx.android.synthetic.main.activity_create_pack.*
import kotlinx.android.synthetic.main.activity_create_task.*
import kotlinx.android.synthetic.main.fragment_create_pack_frament.*
import kotlinx.android.synthetic.main.fragment_create_pack_frament.view.*
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import java.util.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class CreatePackFrament : Fragment(),
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_create_pack_frament, container, false)
//        quantity=view?.findViewById(R.id.fed_pack_quantity)
        initViewModel()

        val updateTask: Button = view.findViewById(R.id.btn_update_pack)
        updateTask.setOnClickListener {
            adapterr?.callBack()
            ApiClient.client.create(ApiInterFace::class.java)
                .updatePack(
                    packList?.pack_config_id.toString(), "Test",
                    packList?.com_group.toString(),
                    MySharedPreference.getUser(requireContext())?.id.toString(),
                    successObject.toString(), packList?.name_prefix!!,
                    packList?.id.toString()
                ).enqueue(this)

            AppUtils.logDebug(
                TAG, "configtye-" + packList?.pack_config_id.toString() + "\n" +
                        "communi-" + packList?.com_group + "\n" + "userid-" + MySharedPreference.getUser(
                    requireContext()
                )?.id.toString() + "\n" +
                        "fieldarr--" + successObject.toString() + "\n" + "taskid--" + packList?.id.toString()
            )

        }






        return view
    }

    private fun initViewModel() {
        viewmodel =
            ViewModelProvider(this).get(PackViewModel::class.java)
        viewmodel?.onConfigFieldList(requireContext(), updateTaskBoolen, "0")
        viewmodel?.configlist?.observe(viewLifecycleOwner, androidx.lifecycle.Observer { list ->
//            setCommunityGroup()
            val config =
                Gson().fromJson(Gson().toJson(list), ArrayList<PackConfigFieldList>()::class.java)
            AppUtils.logDebug(CreatePackActivity.TAG, "listofCreatePack" + config.toString())
            recycler_packFieldsFragment?.layoutManager = LinearLayoutManager(requireContext())
            adapterr =
                CreatePackAdapter(requireContext(), config, true, packCommunityList,
                    packCommunityListname
                ) { list, name ->
                    while (list.contains("0")) {
                        list.remove("0")
                    }
                    while (name.contains("0")) {
                        name.remove("0")
                    }
                    while (ExpNameKey.contains("0")) {
                        ExpNameKey.remove("0")
                    }
                    while (ExpAmtArrayKey.contains("0")) {
                        ExpAmtArrayKey.remove("0")
                    }




                    for (i in 0 until name.size) {
                        val jsonObject = JSONObject()
                        jsonObject.put(ExpAmtArrayKey.get(i), name.get(i))
                        jsonObject.put(ExpNameKey.get(i), list.get(i))

                        successObject.put(jsonObject)
                        fieldModel.add(FieldModel(name.get(i), list.get(i)))

                    }
                    AppUtils.logDebug(TAG, "SuccessObject" + successObject.toString())


                }
            recycler_packFieldsFragment.adapter = adapterr
        })

    }


    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CreatePackFrament().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onResponse(call: Call<testresponse>, response: Response<testresponse>) {
        Toast.makeText(requireContext(), "Pack Updated Successfully", Toast.LENGTH_SHORT).show()

        startActivity(Intent(requireContext(), PackActivity::class.java))
        activity?.finish()
    }

    override fun onFailure(call: Call<testresponse>, t: Throwable) {
        Toast.makeText(requireContext(), "Failed To Update", Toast.LENGTH_SHORT).show()
        AppUtils.logDebug(TAG, "Failur report-->" + t.message)

    }


}