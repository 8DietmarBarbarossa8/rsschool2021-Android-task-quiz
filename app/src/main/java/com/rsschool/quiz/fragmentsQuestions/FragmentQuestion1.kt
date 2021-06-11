package com.rsschool.quiz.fragmentsQuestions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.rsschool.quiz.ITransitFragment
import com.rsschool.quiz.R
import com.rsschool.quiz.databinding.FragmentQuestion1Binding

class FragmentQuestion1 : Fragment() {
    // there variables needs for using comfort thing as binding!
    private var _binding: FragmentQuestion1Binding? = null
    private val binding get() = _binding!!
    // this variable using for getting access to click NEXT button!
    private var isMayNext = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuestion1Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setStatusBarColor()

        // initializing
        val answersArray = arguments?.getIntArray(ARRAY_ANSWER_KEY) ?: IntArray(1)
        var position = arguments?.getInt(POSITION_KEY) ?: 0
        val radioButtonsIDTextAnswersArray = arrayOf(
            binding.radioButton1 to R.string.answer11,
            binding.radioButton2 to R.string.answer12,
            binding.radioButton3 to R.string.answer13,
            binding.radioButton4 to R.string.answer14,
            binding.radioButton5 to R.string.answer15
        )

        // PREVIOUS button become NOT active!
        binding.previousButton.setBackgroundColor(binding.previousButton
            .context.resources.getColor(R.color.notAction))

        // check last radiobutton click, if it was
        if (answersArray[position] != 0){
            for (element in radioButtonsIDTextAnswersArray)
                if (element.second == answersArray[position]) {
                    element.first.isChecked = true
                    isMayNext = true
                    changeNextButtonClickActivity(isMayNext)
                }
        } else changeNextButtonClickActivity(false)

        // listen to events
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            for (element in radioButtonsIDTextAnswersArray)
                if (checkedId == element.first.id)
                    answersArray?.set(position, element.second)

            isMayNext = true
            changeNextButtonClickActivity(isMayNext)
        }

        binding.nextButton.setOnClickListener {
            if (isMayNext)
                (activity as ITransitFragment)
                    .transitFragment(FragmentQuestion2.newInstance(answersArray, ++position))
        }
    }

    private fun changeNextButtonClickActivity(isClickable: Boolean) =
        binding.nextButton.setBackgroundColor(binding.nextButton
            .context.resources.getColor(if (isClickable) R.color.colorQ1 else R.color.notAction))

    override fun onDestroyView() {
        // nulling a variable (_binding)!!!
        _binding = null
        super.onDestroyView()
    }

    private fun setStatusBarColor(){
        val window = activity?.window
        window?.statusBarColor =
            ResourcesCompat.getColor(resources, R.color.colorQ1_Dark, null)
    }

    companion object {
        @JvmStatic
        fun newInstance(answersArray: IntArray, positionQuestion: Int): FragmentQuestion1 =
            FragmentQuestion1().apply {
                arguments = Bundle().apply {
                    putIntArray(ARRAY_ANSWER_KEY, answersArray)
                    putInt(POSITION_KEY, positionQuestion)
                }
            }

        private const val ARRAY_ANSWER_KEY = "AA_KEY"
        private const val POSITION_KEY = "P_KEY"
    }
}