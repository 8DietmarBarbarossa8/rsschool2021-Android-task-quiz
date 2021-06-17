package com.rsschool.quiz.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.rsschool.quiz.ITransitFragment
import com.rsschool.quiz.R
import com.rsschool.quiz.databinding.FragmentQuestionsBinding
import com.rsschool.quiz.objects.QuestionsAndAnswersObject
import com.rsschool.quiz.objects.ThemesAndColorsObject

class QuestionsFragment : Fragment() {
    // there variables needs for using comfort thing as binding!
    private var _binding: FragmentQuestionsBinding? = null
    private val binding get() = _binding!!
    // initialization
    private var position: Int = 0
    private var answersArray: IntArray = IntArray(1)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        position = arguments?.getInt(POSITION_KEY) ?: 0
        answersArray = arguments?.getIntArray(ARRAY_ANSWER_KEY) ?: IntArray(1)
        // Set individual theme (colors) for every question
        setTheme()
        _binding = FragmentQuestionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // filling answer variants
        val radioButtons = arrayOf(
            binding.radioButton1,
            binding.radioButton2,
            binding.radioButton3,
            binding.radioButton4,
            binding.radioButton5
        )
        val variants = Array(radioButtons.size){
            radioButtons[it].text = resources.getString(
                QuestionsAndAnswersObject.questionsAndAnswers[position].second[it])
            radioButtons[it] to QuestionsAndAnswersObject.questionsAndAnswers[position].second[it]
        }

        // Set color and subtitle
        setBarsSetting()
        // Show question on textview
        setQuestion()
        // Set some cosmetic changes (with icon "back" and nextButton)
        someChangeMapping(binding.nextButton)

        // Previous button become NOT active if it's first question
        changeButtonEnable(binding.previousButton, position != 0)

        // check last radiobutton click, if it was
        checkIfAnswered(variants)

        // Listen events
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            chooseAnswerVariant(variants, checkedId)
        }
        binding.previousButton.setOnClickListener {
            clickPrevious()
        }
        binding.questionToolBar.setNavigationOnClickListener {
            clickPrevious()
        }
        binding.nextButton.setOnClickListener {
            clickNextOfSubmit()
        }
    }

    private fun setTheme(){
        context?.setTheme(ThemesAndColorsObject.themes[position])
    }

    private fun setBarsSetting(){
        activity?.window?.statusBarColor = ResourcesCompat
            .getColor(resources, ThemesAndColorsObject.darkColors[position], null)
    }

    private fun setQuestion(){
        binding.questionToolBar.subtitle = "Question: ${position + 1}"
        binding.questionTV.text = resources
            .getString(QuestionsAndAnswersObject.questionsAndAnswers[position].first)
    }

    private fun changeButtonEnable(button: Button, isEnable: Boolean){
        button.isEnabled = isEnable
    }

    private fun someChangeMapping(button: Button){
        if (position != 0)
            binding.questionToolBar.setNavigationIcon(R.drawable.ic_baseline_chevron_left_24)
        if (position == answersArray.size - 1)
            button.text = resources.getString(R.string.submit)
    }

    private fun checkIfAnswered(variants: Array<Pair<RadioButton, Int>>){
        if (answersArray[position] != 0){
            for (v in variants)
                if (v.second == answersArray[position]) {
                    v.first.isChecked = true
                    changeButtonEnable(binding.nextButton,true)
                }
        } else changeButtonEnable(binding.nextButton,false)
    }

    private fun chooseAnswerVariant(variants: Array<Pair<RadioButton, Int>>, checkedId: Int){
        for (v in variants)
            if (v.first.id == checkedId)
                answersArray[position] = v.second

        changeButtonEnable(binding.nextButton,true)
    }

    private fun clickPrevious(){
        if (position > 0)
            (activity as ITransitFragment)
                .transitFragment(newInstance(answersArray, --position))
    }

    private fun clickNextOfSubmit(){
        if (position < answersArray.size - 1)
            (activity as ITransitFragment)
                .transitFragment(newInstance(answersArray, ++position))
        else
            (activity as ITransitFragment)
                .transitFragment(ResultInfoFragment.newInstance(answersArray))
    }

    override fun onDestroyView() {
        // nulling a variable (_binding)!!!
        _binding = null
        super.onDestroyView()
    }

    companion object {
        @JvmStatic
        fun newInstance(answersArray: IntArray, positionQuestion: Int): QuestionsFragment =
            QuestionsFragment().apply {
                arguments = Bundle().apply {
                    putIntArray(ARRAY_ANSWER_KEY, answersArray)
                    putInt(POSITION_KEY, positionQuestion)
                }
            }

        private const val ARRAY_ANSWER_KEY = "AA_KEY"
        private const val POSITION_KEY = "P_KEY"
    }
}