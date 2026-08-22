package com.lughatnama.dictionary.search

import com.lughatnama.dictionary.data.DictionaryEntry
import org.junit.Assert.assertEquals
import org.junit.Test

class SearchEngineTest {
    private val entries = listOf(
        entry(1, "فِراست", "فراست"),
        entry(2, "فراست‌مند", "فراست‌مند"),
        entry(3, "بی‌فراست", "بی‌فراست"),
        entry(4, "فِراست", "فراست"),
    )

    @Test
    fun exactMatchesComeBeforePrefixAndContainsMatches() {
        val result = SearchEngine.search(entries, "فراست")
        assertEquals(listOf(1, 4, 2, 3), result.map { it.id })
    }

    @Test
    fun ArabicYehAndKafCanMatchPersianForms() {
        val item = entry(10, "یک", SearchNormalizer.normalize("یک"))
        assertEquals(10, SearchEngine.search(listOf(item), "يك").single().id)
    }

    @Test
    fun unknownWordReturnsNoResults() {
        assertEquals(emptyList<DictionaryEntry>(), SearchEngine.search(entries, "ناموجود"))
    }

    private fun entry(id: Int, word: String, normalized: String) = DictionaryEntry(
        id = id,
        word = word,
        normalizedWord = normalized,
        meaning = "معنی نمونه",
        sourcePage = 5,
    )
}
