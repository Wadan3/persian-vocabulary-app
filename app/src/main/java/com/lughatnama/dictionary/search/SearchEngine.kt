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
                val priority = when {
                    entry.normalizedWord == normalizedQuery -> 0
                    entry.normalizedWord.startsWith(normalizedQuery) -> 1
                    entry.normalizedWord.contains(normalizedQuery) -> 2
                    else -> return@mapNotNull null
                }
                RankedEntry(entry, priority)
            }
            .sortedWith(
                compareBy<RankedEntry> { it.priority }
                    .thenBy { it.entry.normalizedWord.length }
                    .thenBy { it.entry.id },
            )
            .take(limit)
            .map { it.entry }
            .toList()
    }

    private data class RankedEntry(
        val entry: DictionaryEntry,
        val priority: Int,
    )
}
