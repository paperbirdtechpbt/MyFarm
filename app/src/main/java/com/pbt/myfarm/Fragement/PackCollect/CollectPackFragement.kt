package com.pbt.myfarm.Fragement.PackCollect

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.pbt.myfarm.Activity.UpDatePack.UpdatePackActivity
import com.pbt.myfarm.CollectDataFieldListItem
import com.pbt.myfarm.CollectDataModel
import com.pbt.myfarm.Fragement.CollectNewData.ResponseCollectAcitivityResultList
import com.pbt.myfarm.HttpResponse.PackConfigFieldList
import com.pbt.myfarm.HttpResponse.testresponse
import com.pbt.myfarm.PackViewModel
import com.pbt.myfarm.R
import com.pbt.myfarm.Service.ApiClient
import com.pbt.myfarm.Service.ApiInterFace
import com.pbt.myfarm.Util.AppUtils
import kotlinx.android.synthetic.main.fragment_collect_pack_fragement.*
import retrofit2.Call
import retrofit2.Response
import java.util.ArrayList
import javax.security.auth.callback.Callback


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class CollectPackFragement : Fragment() ,retrofit2.Callback<ResponseCollectAcitivityResultList> {
    private var param1: String? = null
    private var param2: String? = null
    var recylerview:RecyclerView?=null
    var viewmodel: CollectDataViewModel? = null
    var adapter:CollectDataAdapter?=null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onResume() {
        super.onResume()
        progressbar_collectpack.visibility=View.VISIBLE

        initViewModel()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view:View= inflater.inflate(R.layout.fragment_collect_pack_fragement, container, false)

        initViewModel()


        return view
    }

    private fun initViewModel() {
        viewmodel =
            ViewModelProvider(this).get(CollectDataViewModel::class.java)
//        viewmodel?.progressBar=progressbar_collectpack
        viewmodel?.onCollectDataList(requireContext())


        viewmodel?.collectdatalist?.observe(viewLifecycleOwner,androidx.lifecycle.Observer{list ->



            val mylist =
                Gson().fromJson(Gson().toJson(list), ArrayList<CollectDataFieldListItem>()::class.java)

            recyclerview_collectdata?.layoutManager = LinearLayoutManager(requireContext())

            adapter  = CollectDataAdapter(mylist,requireContext()){  id ,boolean->
                collectDataId=id
                if (boolean){
                    showAlertDialog(id)
                }
                else{
                    val intent=Intent(activity,UpdatePackActivity::class.java)
                    intent.putExtra("fragment","2")
                    startActivity(intent)
                }



            }

            recyclerview_collectdata?.adapter=adapter
            if (list.isNullOrEmpty()){
                layout_nodata.visibility=View.VISIBLE
                progressbar_collectpack.visibility=View.GONE
            }
            else{
                layout_nodata.visibility=View.GONE
            }


        })
    }

    private fun showAlertDialog(id: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete")
            .setMessage("Are you sure you want to Delete")
            .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->

                progressbar_collectpack.visibility=View.VISIBLE
                ApiClient.client.create(ApiInterFace::class.java).deletecollectdata(id).enqueue(this)

                })
            .setNegativeButton(android.R.string.no, null)
            .setIcon(android.R.drawable.ic_delete)
            .show()
    }


    companion object {
        val TAG="CollectPackFragement"
        var collectDataId=""
    }

    override fun onResponse(
        call: Call<ResponseCollectAcitivityResultList>,
        response: Response<ResponseCollectAcitivityResultList>
    ) {
        if (response.body()?.error==false){
            Toast.makeText(requireContext(),"${response.body()?.msg}",Toast.LENGTH_LONG).show()
            viewmodel?.onCollectDataList(requireContext())
            adapter?.notifyDataSetChanged()
            progressbar_collectpack.visibility=View.GONE
        }
    }

    override fun onFailure(call: Call<ResponseCollectAcitivityResultList>, t: Throwable) {
        Toast.makeText(requireContext(),"Failed to Delete",Toast.LENGTH_LONG).show()

    }
}