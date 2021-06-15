package com.rsschool.quiz

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity(), ITransitFragment {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (checkSizeOnEqualling() != 0)
            transitFragment(QuestionsFragment
                .newInstance(IntArray(QuestionsAndAnswersObject.questions.size), 0))
    }

    override fun transitFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
    }

    /*
    According to the assignment, each screen should have:
    its own theme, its own color, its own question, its own answers, etc.
    Therefore i realized this method for avoid ANR!
     */
    private fun checkSizeOnEqualling() : Int =
        if (setOf(
                QuestionsAndAnswersObject.questions.size,
                QuestionsAndAnswersObject.rightAnswers.size,
                QuestionsAndAnswersObject.answers.size,
                ThemesAndColorsObject.themes.size,
                ThemesAndColorsObject.darkColors.size,
                ThemesAndColorsObject.mainColors.size
            ).size == 1 && QuestionsAndAnswersObject.checkCountAnswers()
        ) QuestionsAndAnswersObject.questions.size else 0
}