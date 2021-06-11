package com.rsschool.quiz.fragmentsQuestions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import com.rsschool.quiz.ITransitFragment
import com.rsschool.quiz.R
import com.rsschool.quiz.databinding.FragmentQuestion4Binding

class FragmentQuestion4 : Fragment() {
    private var _binding: FragmentQuestion4Binding? = null
    private val binding get() = _binding!!
    private var isMayNext = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQuestion4Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setStatusBarColor()

        val answersArray = arguments?.getIntArray(ARRAY_ANSWER_KEY) ?: IntArray(1)
        var position = arguments?.getInt(POSITION_KEY) ?: 0
        val radioButtonsIDTextAnswersArray = arrayOf(
            binding.radioButton1 to R.string.answer41,
            binding.radioButton2 to R.string.answer42,
            binding.radioButton3 to R.string.answer43,
            binding.radioButton4 to R.string.answer44,
            binding.radioButton5 to R.string.answer45
        )

        if (answersArray[position] != 0){
            for (element in radioButtonsIDTextAnswersArray)
                if (element.second == answersArray[position]) {
                    element.first.isChecked = true
                    isMayNext = true
                    changeNextButtonClickActivity(isMayNext)
                }
        } else changeNextButtonClickActivity(false)

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            for (element in radioButtonsIDTextAnswersArray)
                if (checkedId == element.first.id)
                    answersArray?.set(position, element.second)

            isMayNext = true
            changeNextButtonClickActivity(isMayNext)
        }

        // click PREVIOUS button!
        binding.previousButton.setOnClickListener {
            (activity as ITransitFragment)
                .transitFragment(FragmentQuestion3.newInstance(answersArray, --position))
        }

        binding.questionToolBar.setNavigationOnClickListener {
            (activity as ITransitFragment)
                .transitFragment(FragmentQuestion3.newInstance(answersArray, --position))
        }

        binding.nextButton.setOnClickListener {
            if (isMayNext)
                (activity as ITransitFragment)
                    .transitFragment(FragmentQuestion5.newInstance(answersArray, ++position))
        }
    }

    private fun changeNextButtonClickActivity(isClickable: Boolean) =
        binding.nextButton.setBackgroundColor(binding.nextButton
            .context.resources.getColor(if (isClickable) R.color.colorQ4 else R.color.notAction))

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun setStatusBarColor(){
        val window = activity?.window
        window?.statusBarColor =
            ResourcesCompat.getColor(resources, R.color.colorQ4_Dark, null)
    }

    companion object {
        @JvmStatic
        fun newInstance(answersArray: IntArray, positionQuestion: Int): FragmentQuestion4 =
            FragmentQuestion4().apply {
                arguments = Bundle().apply {
                    putIntArray(ARRAY_ANSWER_KEY, answersArray)
                    putInt(POSITION_KEY, positionQuestion)
                }
            }

        private const val ARRAY_ANSWER_KEY = "AA_KEY"
        private const val POSITION_KEY = "P_KEY"
    }
}