package com.rsschool.quiz

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.rsschool.quiz.fragments.QuestionsFragment
import com.rsschool.quiz.objects.QuestionsAndAnswersObject
import com.rsschool.quiz.objects.ThemesObject

class MainActivity : AppCompatActivity(), ITransitFragment {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // fix bag with changing system theme
        if (savedInstanceState == null){
            // Count of question == Count of screen!
            val size = QuestionsAndAnswersObject.questionAndAnswers.size
            // Check some conditions (described below)
            if (isMayTransit())
                transitFragment(QuestionsFragment.newInstance(IntArray(size), 0))
        }
    }

    private fun isMayTransit(): Boolean {
        /*
        Every screen should to contain own:
        1) question;
        2) variants of answers (not less than 5);
        3) theme.
        Also, count of right answers should equal count of questions
         */
        val sizes = Triple(
            QuestionsAndAnswersObject.questionAndAnswers.size,
            QuestionsAndAnswersObject.rightAnswers.size,
            ThemesObject.themes.size
        )
        return sizes.first == sizes.second
                && sizes.second == sizes.third
                && QuestionsAndAnswersObject.checkCountOfVariantsAnswers()
    }

    override fun transitFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
    }
}