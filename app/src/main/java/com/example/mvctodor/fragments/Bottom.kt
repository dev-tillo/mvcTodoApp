package com.example.mvctodor.fragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.example.mvctodor.R
import com.example.mvctodor.adapters.FirstAdapter
import com.example.mvctodor.databinding.FragmentBottomBinding
import com.example.mvctodor.models.AppDatabase
import com.example.mvctodor.models.ListModel
import kotlin.math.roundToInt

private const val ARG_PARAM1 = "box"
private const val ARG_PARAM2 = "param2"

class Bottom : SuperBottomSheetFragment() {

    private var _binding: FragmentBottomBinding? = null
    private val binding get() = _binding!!
    private var param1: ListModel? = null
    private var param2: String? = null
    private lateinit var appDatabase: AppDatabase
    private lateinit var firstAdapter: FirstAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getSerializable(ARG_PARAM1) as ListModel?
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentBottomBinding.inflate(inflater, container, false)
        appDatabase = AppDatabase.getInstance(requireContext())

        firstAdapter =
            FirstAdapter(appDatabase.serviceDao().getAllListName(param1!!.name), requireContext())

        binding.apply {
            titleTv.text = param1?.name
            layout.setBackgroundColor(param1!!.color)
            taskRv.adapter = firstAdapter
            addBtn.setOnClickListener {
                val bundle = Bundle()
                bundle.putSerializable("listModel", param1)
                findNavController(requireParentFragment()).navigate(R.id.addTask, bundle)
            }

        }

        return binding.root
    }

    override fun getCornerRadius() =
        requireContext().resources.getDimension(R.dimen.demo_sheet_rounded_corner)

    override fun getStatusBarColor() = Color.RED

    override fun getPeekHeight(): Int {
        return requireContext().resources.getDimension(R.dimen.super_bottom_sheet_peek_height)
            .roundToInt()
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: ListModel) =
            Bottom().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, param1)
                }
            }
    }
}