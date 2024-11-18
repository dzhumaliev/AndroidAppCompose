package com.kg.androidappcompose

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class DetailViewModel : ViewModel() {
    // Список сохраненных текстов
    private val _savedTexts = mutableStateListOf<String>()
    val savedTexts: List<String> = _savedTexts

    // Метод для добавления текста
    fun addText(text: String) {
        _savedTexts.add(text)
    }
}