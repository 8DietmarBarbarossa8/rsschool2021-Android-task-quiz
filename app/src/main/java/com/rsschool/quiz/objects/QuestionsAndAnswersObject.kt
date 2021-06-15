package com.rsschool.quiz.objects

import com.rsschool.quiz.R

object QuestionsAndAnswersObject {
    val questions = arrayOf(
        R.string.question1,
        R.string.question2,
        R.string.question3,
        R.string.question4,
        R.string.question5
    )

    val answers = arrayOf(
        arrayOf(
            R.string.answer11,
            R.string.answer12,
            R.string.answer13,
            R.string.answer14,
            R.string.answer15
        ),
        arrayOf(
            R.string.answer21,
            R.string.answer22,
            R.string.answer23,
            R.string.answer24,
            R.string.answer25
        ),
        arrayOf(
            R.string.answer31,
            R.string.answer32,
            R.string.answer33,
            R.string.answer34,
            R.string.answer35
        ),
        arrayOf(
            R.string.answer41,
            R.string.answer42,
            R.string.answer43,
            R.string.answer44,
            R.string.answer45
        ),
        arrayOf(
            R.string.answer51,
            R.string.answer52,
            R.string.answer53,
            R.string.answer54,
            R.string.answer55
        ),
    )

    val rightAnswers = arrayOf(
        R.string.answer12,
        R.string.answer23,
        R.string.answer33,
        R.string.answer44,
        R.string.answer55,
    )

    fun checkCountAnswers(): Boolean {
        for (i in 0..answers.size - 2)
            if (answers[i].size != answers[i + 1].size)
                return false
        return true
    }
}