package com.rsschool.quiz

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.rsschool.quiz.fragments.QuestionsFragment
import com.rsschool.quiz.objects.QuestionsAndAnswersObject

class MainActivity : AppCompatActivity(), ITransitFragment {
    override val arrayParameter: IntArray
        get() = IntArray(QuestionsAndAnswersObject.questionsAndAnswers.size)
    override val position: Int
        get() = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        transitFragment(QuestionsFragment.newInstance(arrayParameter, position))
    }

    override fun transitFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
    }
}