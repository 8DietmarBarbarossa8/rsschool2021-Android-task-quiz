package com.rsschool.quiz.fragmentsQuestions

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.rsschool.quiz.ITransitFragment
import com.rsschool.quiz.R
import com.rsschool.quiz.databinding.FragmentResultInfoBinding
import kotlin.system.exitProcess

class FragmentResultInfo : Fragment() {
    private var _binding: FragmentResultInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResultInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setStatusBarColor()

        val answersArray = arguments?.getIntArray(ARRAY_ANSWER_KEY) ?: IntArray(1)
        val rightAnswersArray = intArrayOf(
            R.string.rightAnswer1,
            R.string.rightAnswer2,
            R.string.rightAnswer3,
            R.string.rightAnswer4,
            R.string.rightAnswer5
        )
        val result = checkAnswers(answersArray, rightAnswersArray)
        val messageTV = getResult(result, rightAnswersArray.size)
        binding.resultTextView.text = messageTV

        binding.shareImageView.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, generateReportAnswers(messageTV, answersArray))
            val chooserTitle = getString(R.string.chooser)
            val chosenIntent = Intent.createChooser(intent, chooserTitle)
            startActivity(chosenIntent)
        }
        binding.backImageView.setOnClickListener {
            (activity as ITransitFragment)
                .transitFragment(FragmentQuestion1.newInstance(IntArray(5), 0))
        }
        binding.exitImageView.setOnClickListener {
            exitProcess(0)
        }
    }

    private fun checkAnswers(array: IntArray, rightArray: IntArray): Int {
        var countRightScore = 0
        for (i in array.indices)
            if (resources.getString(array[i]) == resources.getString(rightArray[i]))
                countRightScore++
        return countRightScore
    }

    private fun getResult(score: Int, total: Int): String =
        "Your result: $score of $total\n(${(score.toFloat() / total * 100).toInt()}%)"

    private fun generateReportAnswers(messageResult: String, answerArray: IntArray): String {
        var text = "$messageResult\n"
        val questionsArray = arrayOf(
            R.string.question1,
            R.string.question2,
            R.string.question3,
            R.string.question4,
            R.string.question5
        )

        for (i in questionsArray.indices)
            text += "\n${i + 1}) ${resources.getString(questionsArray[i])}" +
                    "\nYour answer: ${resources.getString(answerArray[i])}\n"

        return text
    }

    private fun setStatusBarColor(){
        val window = activity?.window
        window?.statusBarColor =
            ResourcesCompat.getColor(resources, R.color.deep_orange_100_dark, null)
    }

    companion object {
        @JvmStatic
        fun newInstance(answersArray: IntArray): FragmentResultInfo {
            val fragment = FragmentResultInfo()
            val args = Bundle()
            args.putIntArray(ARRAY_ANSWER_KEY, answersArray)
            fragment.arguments = args
            return fragment
        }

        private const val ARRAY_ANSWER_KEY = "AA_KEY"
    }
}