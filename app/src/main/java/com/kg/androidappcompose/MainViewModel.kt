package com.kg.androidappcompose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    // Состояние хранится в MutableStateFlow
    private val _text = MutableStateFlow("")
    val text: StateFlow<String> = _text

    // Метод для обновления текста
    fun updateText(newText: String) {
        _text.value = newText
    }

    // Метод для изменения текста с использованием корутин
    fun changeTextWithDelay() {
        viewModelScope.launch {
            _text.value = "Загрузка..."
            kotlinx.coroutines.delay(2000)
            _text.value = "Готово!"
        }
    }
}