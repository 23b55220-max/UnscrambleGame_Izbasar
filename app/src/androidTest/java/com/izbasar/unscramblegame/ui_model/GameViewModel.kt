package com.izbasar.unscramblegame.ui_model

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.izbasar.unscramblegame.data.GameUiState
import com.izbasar.unscramblegame.allWords

class GameViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(GameUiState())

    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    private lateinit var currentWord: String

    private var usedWords: MutableSet<String> = mutableSetOf()

    init {
        resetGame()
    }

    // Перемешивание букв в слове
    private fun shuffleCurrentWord(word: String): String {
        val tempWord = word.toCharArray()

        tempWord.shuffle()

        while (String(tempWord).equals(word, ignoreCase = true)) {
            tempWord.shuffle()
        }

        return String(tempWord)
    }

    // Выбор случайного слова и перемешивание
    private fun pickRandomWordAndShuffle(): String {
        currentWord = allWords.random()

        while (usedWords.contains(currentWord)) {
            currentWord = allWords.random()
        }

        usedWords.add(currentWord)

        return shuffleCurrentWord(currentWord)
    }

    // Сброс игры
    private fun resetGame() {
        usedWords.clear()

        _uiState.value = GameUiState(
            currentScrambledWord = pickRandomWordAndShuffle()
        )
    }
}