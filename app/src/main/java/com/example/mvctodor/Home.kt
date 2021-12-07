package com.example.mvctodor

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import com.example.mvctodor.adapters.FirstAdapter
import com.example.mvctodor.adapters.HomeAdapterList
import com.example.mvctodor.databinding.FragmentHomeBinding
import com.example.mvctodor.fragments.Bottom
import com.example.mvctodor.models.AppDatabase
import com.example.mvctodor.models.ListModel
import java.lang.reflect.Method

class Home : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var listadapter: HomeAdapterList
    private lateinit var appDatabase: AppDatabase
    private lateinit var firstTaskAdapter: FirstAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        appDatabase = AppDatabase.getInstance(requireContext())

        firstTaskAdapter = FirstAdapter(appDatabase.serviceDao().getAllTask(), requireContext())

        listadapter = HomeAdapterList(appDatabase.listDao().getAllList(), requireContext(),
            object : HomeAdapterList.OnItemClickListener {
                override fun onItemClick(listModel: ListModel) {
                    val sheet = Bottom.newInstance(listModel)
                    sheet.show(childFragmentManager, "BottomFragment")
                }
            })

        binding.apply {
            rvTask.adapter = firstTaskAdapter
            rvCategory.adapter = listadapter
            add.setOnClickListener {
                findNavController().navigate(R.id.addList)
            }
        }

        return binding.root
    }
}