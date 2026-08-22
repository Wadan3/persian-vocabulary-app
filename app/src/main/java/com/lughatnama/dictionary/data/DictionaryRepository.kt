package com.lughatnama.dictionary.data

import android.content.Context
import org.json.JSONArray

class DictionaryRepository(private val context: Context) {
    fun loadEntries(): List<DictionaryEntry> {
        val json = context.assets.open(DICTIONARY_ASSET).bufferedReader(Charsets.UTF_8).use {
            it.readText()
        }
        val array = JSONArray(json)
        return buildList(array.length()) {
            repeat(array.length()) { index ->
                val item = array.getJSONObject(index)
                add(
                    DictionaryEntry(
                        id = item.getInt("id"),
                        word = item.getString("word"),
                        normalizedWord = item.getString("normalizedWord"),
                        meaning = item.getString("meaning"),
                        sourcePage = item.getInt("sourcePage"),
                    ),
                )
            }
        }
    }

    private companion object {
        const val DICTIONARY_ASSET = "dictionary.json"
    }
}
