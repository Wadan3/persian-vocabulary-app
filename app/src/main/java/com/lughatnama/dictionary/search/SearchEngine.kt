package com.lughatnama.dictionary.search

import com.lughatnama.dictionary.data.DictionaryEntry

object SearchEngine {
    fun search(
        entries: List<DictionaryEntry>,
        query: String,
        limit: Int = 20,
    ): List<DictionaryEntry> {
        val normalizedQuery = SearchNormalizer.normalize(query)
        if (normalizedQuery.isEmpty()) return emptyList()

        return entries
            .asSequence()
            .mapNotNull { entry ->
                if (entry.word.isBlank() || entry.meaning.isBlank()) return@mapNotNull null

                val normalizedWord = SearchNormalizer.normalize(entry.normalizedWord)
                val priority = when {
                    normalizedWord == normalizedQuery -> 0
                    normalizedWord.startsWith(normalizedQuery) -> 1
                    normalizedWord.contains(normalizedQuery) -> 2
                    else -> return@mapNotNull null
                }
                RankedEntry(
                    entry = entry,
                    normalizedWord = normalizedWord,
                    normalizedMeaning = SearchNormalizer.normalize(entry.meaning),
                    priority = priority,
                )
            }
            .sortedWith(
                compareBy<RankedEntry> { it.priority }
                    .thenBy { it.normalizedWord.length }
                    .thenBy { it.entry.id },
            )
            .distinctBy { it.normalizedWord to it.normalizedMeaning }
            .take(limit)
            .map { it.entry }
            .toList()
    }

    private data class RankedEntry(
        val entry: DictionaryEntry,
        val normalizedWord: String,
        val normalizedMeaning: String,
        val priority: Int,
    )
}
