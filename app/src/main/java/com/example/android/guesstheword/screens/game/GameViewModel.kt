package com.example.android.guesstheword.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    companion object {
        // These represent different important times
        // This is when the game is over
        const val DONE = 0L
        // This is the number of milliseconds in a second
        const val ONE_SECOND = 1000L
        // This is the total time of the game
        const val COUNTDOWN_TIME = 60000L
    }

    private val timer : CountDownTimer

    private val _currTime = MutableLiveData<Long>()
    val currTime : LiveData<Long>
        get() = _currTime


    // The current word
    private val _word = MutableLiveData<String>()
    //Gotta encapsulate this boi
    val word : LiveData<String>
        get() = _word

    private val _eventGameFinished = MutableLiveData<Boolean>()
    val eventGameFinished : LiveData<Boolean>
        get() = _eventGameFinished

    // The current score
    private val _score = MutableLiveData<Int>()
    //Gotta encapsulate this boi too
    val score : LiveData<Int>
        get() = _score

    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>

    init {
        Log.i("GameViewModel", "GameViewModel created")

        _score.value = 0
       _word.value = ""
        _eventGameFinished.value = false
        resetList()
        nextWord()
        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND){
            override fun onTick(millisUntilFinished : Long) {
                _currTime.value = (millisUntilFinished/ ONE_SECOND)
            }
            override fun onFinish() {
                _eventGameFinished.value = true
                _currTime.value = DONE
            }

        }
        timer.start()
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel", "GameViewModel destroyed")
    }

    /**
     * Resets the list of words and randomizes the order
     */
    private fun resetList() {
        wordList = mutableListOf(
            "queen",
            "hospital",
            "basketball",
            "cat",
            "change",
            "snail",
            "soup",
            "calendar",
            "sad",
            "desk",
            "guitar",
            "home",
            "railway",
            "zebra",
            "jelly",
            "car",
            "crow",
            "trade",
            "bag",
            "roll",
            "bubble"
        )
        wordList.shuffle()
    }

    /**
     * Moves to the next word in the list
     */
    private fun nextWord() {
        //Select and remove a word from the list
        if (wordList.isEmpty()) {
            resetList()
        }
        _word.value = wordList.removeAt(0)
    }

    /** Methods for buttons presses **/

     fun onSkip() {
        _score.value = (score.value)?.minus(1)
        nextWord()

    }

     fun onCorrect() {
         _score.value = (score.value)?.plus(1)
         nextWord()
    }


    fun onGameFinishedComplete (){
        _eventGameFinished.value = false
    }


}