package com.rsschool.quiz

import androidx.fragment.app.Fragment

interface ITransitFragment {
    val arrayParameter: IntArray
    val position: Int
    fun transitFragment(fragment: Fragment)
}