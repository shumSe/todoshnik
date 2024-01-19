package ru.shumikhin.todoshnik.presentation.fragments.todoInfoFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import ru.shumikhin.todoshnik.R
import ru.shumikhin.todoshnik.databinding.FragmentMainBinding
import ru.shumikhin.todoshnik.databinding.FragmentTodoInfoBinding


class TodoInfoFragment : Fragment() {

    private var _binding: FragmentTodoInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTodoInfoBinding.inflate(inflater, container, false)
        val imp = arrayOf("LOW","ST","HIGH")
        val aa = ArrayAdapter<String>(requireContext(), R.layout.spinner_importance_item,  imp)
        aa.setDropDownViewResource(R.layout.spinner_importance_dropdown_item)


        val spinner = binding.spinnerImportance
        spinner.adapter = aa
        return binding.root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}