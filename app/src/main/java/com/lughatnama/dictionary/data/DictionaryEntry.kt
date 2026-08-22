package com.lughatnama.dictionary.data

data class DictionaryEntry(
    val id: Int,
    val word: String,
    val normalizedWord: String,
    val meaning: String,
    val sourcePage: Int,
)
