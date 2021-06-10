package com.rsschool.quiz

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.rsschool.quiz.fragmentsQuestions.FragmentQuestion1

class MainActivity : AppCompatActivity(), ITransitFragment {
    private val answersArray = IntArray(5)
    private val position = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        transitFragment(FragmentQuestion1.newInstance(answersArray, position))
    }

    override fun transitFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
    }
}