package com.rsschool.quiz.fragmentsQuestions

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.rsschool.quiz.R
import com.rsschool.quiz.databinding.FragmentQuestion1Binding

class FragmentQuestion1 : Fragment() {
    private var _binding: FragmentQuestion1Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuestion1Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId){
                R.id.radioButton1 -> {}
                R.id.radioButton2 -> {}
                R.id.radioButton3 -> {
                    Toast.makeText(context, "LOL", Toast.LENGTH_LONG).show()
                }
                R.id.radioButton4 -> {}
                R.id.radioButton5 -> {}
            }
        }

        binding.previousButton.setBackgroundColor(Color.GRAY)
        binding.previousButton.isClickable = false

        binding.nextButton.setOnClickListener {
//            (activity as ITransitFragment).transitFragment(FragmentQuestion2.newInstance())
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        @JvmStatic
        fun newInstance(): FragmentQuestion1{
            return FragmentQuestion1()
        }
    }
}