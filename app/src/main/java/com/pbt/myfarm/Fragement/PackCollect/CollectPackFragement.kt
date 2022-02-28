package com.pbt.myfarm.Fragement.PackCollect

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.pbt.myfarm.CollectDataFieldListItem
import com.pbt.myfarm.CollectDataModel
import com.pbt.myfarm.HttpResponse.PackConfigFieldList
import com.pbt.myfarm.PackViewModel
import com.pbt.myfarm.R
import com.pbt.myfarm.Util.AppUtils
import kotlinx.android.synthetic.main.fragment_collect_pack_fragement.*
import java.util.ArrayList


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class CollectPackFragement : Fragment() {
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
        viewmodel?.onCollectDataList(requireContext())

        viewmodel?.collectdatalist?.observe(viewLifecycleOwner,androidx.lifecycle.Observer{list ->

            val mylist =
                Gson().fromJson(Gson().toJson(list), ArrayList<CollectDataFieldListItem>()::class.java)
            AppUtils.logDebug(TAG,"Observare Collect Pack "+Gson().toJson(list))
            AppUtils.logDebug(TAG,"Observare Collect Pack my list "+Gson().toJson(mylist))

            recyclerview_collectdata?.layoutManager = LinearLayoutManager(requireContext())

            adapter  = CollectDataAdapter(mylist,requireContext())

            recyclerview_collectdata?.adapter=adapter

        })
    }


    companion object {
val TAG="CollectPackFragement"
//        fun newInstance(param1: String, param2: String) =
//            CollectPackFragement().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
    }
}