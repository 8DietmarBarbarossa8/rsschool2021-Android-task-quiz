package com.rsschool.quiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.rsschool.quiz.fragmentsQuestions.FragmentQuestion1

class MainActivity : AppCompatActivity(), ITransitFragment {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        transitFragment(FragmentQuestion1.newInstance())
    }

    override fun transitFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
    }
}