package com.rsschool.quiz.fragments

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.rsschool.quiz.ITransitFragment
import com.rsschool.quiz.R
import com.rsschool.quiz.databinding.FragmentQuestionsBinding
import com.rsschool.quiz.objects.QuestionsAndAnswersObject
import com.rsschool.quiz.objects.ThemesObject

class QuestionsFragment : Fragment() {
    // there variables needs for using comfort thing as binding!
    private var _binding: FragmentQuestionsBinding? = null
    private val binding get() = requireNotNull(_binding)
    // initialization
    private var position: Int = 0
    private var answersArray: IntArray = IntArray(1)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        position = arguments?.getInt(POSITION_KEY) ?: 0
        answersArray = arguments?.getIntArray(ARRAY_ANSWER_KEY) ?: IntArray(1)
        // Set individual theme for every question
        context?.setTheme(ThemesObject.themes[position])
        // Inflate binding
        _binding = FragmentQuestionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // filling answer variants
        val variantsSize = QuestionsAndAnswersObject.questionAndAnswers[position].second.size
        val variants: Array<Pair<RadioButton, Int>> = Array(variantsSize){
            val idQuestion = QuestionsAndAnswersObject.questionAndAnswers[position].second[it]
            val radioButton = createRadioButton(idQuestion)
            binding.radioGroup.addView(radioButton)
            radioButton to idQuestion
        }
        // Previous button become NOT active if it's first question
        binding.previousButton.isEnabled = position != 0

        // Set color of status bar
        setStatusBarColor()
        // Show question on textview
        setQuestion()
        // Set some cosmetic changes (with icon "back" and nextButton)
        someChangeMapping()
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
            clickNextOrSubmit()
        }
    }

    private fun createRadioButton(idText: Int): RadioButton{
        val radioButton = RadioButton(context)
        radioButton.setPaddingRelative(8,50,0,50)
        radioButton.layoutParams = RadioGroup.LayoutParams(
            RadioGroup.LayoutParams.MATCH_PARENT,
            RadioGroup.LayoutParams.WRAP_CONTENT
        )
        radioButton.text = resources.getString(idText)
        return radioButton
    }

    private fun setStatusBarColor(){
        with(TypedValue()){
            context?.theme?.resolveAttribute(R.attr.colorPrimaryDark, this, true)
            activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), resourceId)
        }
    }

    private fun setQuestion(){
        binding.questionToolBar.subtitle = "Question: ${position + 1}"
        binding.questionTV.text = resources
            .getString(QuestionsAndAnswersObject.questionAndAnswers[position].first)
    }

    private fun someChangeMapping(){
        if (position != 0)
            binding.questionToolBar.setNavigationIcon(R.drawable.ic_baseline_chevron_left_24)
        if (position == answersArray.size - 1)
            binding.nextButton.text = resources.getString(R.string.submit)
    }

    private fun checkIfAnswered(variants: Array<Pair<RadioButton, Int>>){
        if (answersArray[position] != 0){
            for (v in variants)
                if (v.second == answersArray[position]) {
                    v.first.isChecked = true
                    binding.nextButton.isEnabled = true
                }
        } else binding.nextButton.isEnabled = false
    }

    private fun chooseAnswerVariant(variants: Array<Pair<RadioButton, Int>>, checkedId: Int){
        for (v in variants)
            if (v.first.id == checkedId)
                answersArray[position] = v.second

        binding.nextButton.isEnabled = true
    }

    private fun clickPrevious(){
        if (position > 0)
            (activity as ITransitFragment)
                .transitFragment(newInstance(answersArray, --position))
    }

    private fun clickNextOrSubmit(){
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
                arguments = bundleOf(ARRAY_ANSWER_KEY to answersArray,
                    POSITION_KEY to positionQuestion)
            }

        private const val ARRAY_ANSWER_KEY = "AA_KEY"
        private const val POSITION_KEY = "P_KEY"
    }
}