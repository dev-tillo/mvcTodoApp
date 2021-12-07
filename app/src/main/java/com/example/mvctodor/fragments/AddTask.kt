package com.example.mvctodor.fragments

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.mvctodor.R
import com.example.mvctodor.databinding.FragmentAddTaskBinding
import com.example.mvctodor.models.AppDatabase
import com.example.mvctodor.models.ListModel
import com.example.mvctodor.models.TaskModel
import com.example.mvctodor.reciver.MyReciver
import com.ozcanalasalvar.library.utils.DateUtils
import com.ozcanalasalvar.library.view.datePicker.DatePicker.MONTH_ON_FIRST
import com.ozcanalasalvar.library.view.popup.DatePickerPopup
import com.ozcanalasalvar.library.view.popup.TimePickerPopup
import java.text.SimpleDateFormat
import java.util.*

private const val ARG_PARAM1 = "box"
private const val ARG_PARAM2 = "param2"

class AddTask : Fragment() {
    private var _binding: FragmentAddTaskBinding? = null
    private val binding get() = _binding!!
    private var param1: ListModel? = null
    private var param2: String? = null
    private lateinit var pickerPopup: TimePickerPopup
    private lateinit var dateTimePickerPopup: DatePickerPopup
    private var datee: String = ""
    private lateinit var appDatabase: AppDatabase
    private var timee: String = ""
    var task_time: Long = 0
    var calendar_date: Long = 0

    @SuppressLint("SimpleDateFormat")
    private var simpleDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getSerializable("listModel") as ListModel?
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        appDatabase = AppDatabase.getInstance(requireContext())
        binding.apply {
            cancelBtn.setOnClickListener {
                findNavController().popBackStack()
            }
            tv.text = param1?.name
            image.setBackgroundColor(param1!!.color)
            doneBtn.setOnClickListener {
                if (isValidation()) {
                    findNavController().popBackStack()
                }
            }
            date.setOnClickListener {
                dateTimePickerPopup = DatePickerPopup.Builder()

                    .from(requireContext())
                    .offset(3)
                    .darkModeEnabled(true)
                    .pickerMode(MONTH_ON_FIRST)
                    .textSize(19)
                    .endDate(DateUtils.getTimeMiles(2050, 10, 25))
                    .currentDate(DateUtils.getCurrentTime())
                    .startDate(DateUtils.getTimeMiles(1995, 0, 1))
                    .listener { _, _, day, month, year ->
                        datee = "" + day + "/" + (month + 1) + "/" + year
                        if (datee == "") {
                            date.setImageResource(R.drawable.ic_calendar)
                        } else {
                            date.setImageResource(R.drawable.ic_calendar__on)
                        }
                    }
                    .build()

                dateTimePickerPopup.show()

            }
            time.setOnClickListener {
                val calendar = Calendar.getInstance()
                pickerPopup = TimePickerPopup.Builder()
                    .from(requireContext())
                    .offset(3)
                    .textSize(17)
                    .setTime(12, 12)
                    .listener { _, hour, minute ->
                        calendar.set(Calendar.SECOND, 1)
                        calendar.set(Calendar.HOUR_OF_DAY, hour)
                        calendar.set(Calendar.MINUTE, minute)
                        timee = "$hour : $minute"
                        calendar_date = calendar.timeInMillis
                        if (timee == "") {
                            time.setImageResource(R.drawable.ic_alarm)
                        } else {
                            time.setImageResource(R.drawable.ic_alarm__on)
                        }
                    }
                    .build()
                pickerPopup.show()
            }
        }
        return binding.root
    }


    @SuppressLint("MissingPermission")
    private fun isValidation(): Boolean {
        val name = binding.edTv.text.toString()
        if (name == "") {
            Toast.makeText(requireContext(), "Please Enter Description", Toast.LENGTH_SHORT).show()
            return false
        }
        if (datee == "") {
            Toast.makeText(requireContext(), "Please Enter Date", Toast.LENGTH_SHORT).show()
            return false
        }
        if (timee == "") {
            Toast.makeText(requireContext(), "Please Enter Time", Toast.LENGTH_SHORT).show()
            return false
        }
        appDatabase.serviceDao().addTask(
            TaskModel(
                description = name,
                listColor = param1!!.color,
                listName = param1!!.name,
                date = datee,
                time = timee,
                isHave = false
            )
        )
        val calendar =
            simpleDateFormat.format(Date(calendar_date)).substring(0, 11) + "00:00:00:0000"
        calendar_date = simpleDateFormat.parse(calendar).time
        var task_id = 1

        for (i in appDatabase.serviceDao().getAllTask()) {
            task_id = i.id
        }
        val intent = Intent(requireContext(), MyReciver::class.java)
        intent.putExtra("id", task_id)

        val pendingIntent =
            PendingIntent.getBroadcast(
                requireContext(),
                task_id,
                intent,
                0
            )
        val alarmManager =
            requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            calendar_date + task_time,
            pendingIntent
        )

        Toast.makeText(requireContext(), "Task added!", Toast.LENGTH_SHORT)
            .show()
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}