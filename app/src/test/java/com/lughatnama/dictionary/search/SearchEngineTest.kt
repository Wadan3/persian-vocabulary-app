package com.lughatnama.dictionary.search

import com.lughatnama.dictionary.data.DictionaryEntry
import org.junit.Assert.assertEquals
import org.junit.Test

class SearchEngineTest {
    private val entries = listOf(
        entry(1, "فِراست", "فراست", "هوشمندی"),
        entry(2, "فراست‌مند", "فراست‌مند"),
        entry(3, "بی‌فراست", "بی‌فراست"),
        entry(4, "فِراست", "فراست", "زیرکی"),
    )

    @Test
    fun exactMatchesComeBeforePrefixAndContainsMatches() {
        val result = SearchEngine.search(entries, "فراست")
        assertEquals(listOf(1, 4, 2, 3), result.map { it.id })
    }

    @Test
    fun ArabicYehAndKafCanMatchPersianForms() {
        val yeh = entry(10, "ی", SearchNormalizer.normalize("ی"))
        val kaf = entry(11, "ک", SearchNormalizer.normalize("ک"))
        assertEquals(10, SearchEngine.search(listOf(yeh), "ي").single().id)
        assertEquals(11, SearchEngine.search(listOf(kaf), "ك").single().id)
    }

    @Test
    fun exactNormalizedDuplicatesCollapseToOneResult() {
        val duplicates = listOf(
            entry(20, "شقی", "شقی", "بدبخت"),
            entry(21, "شقی", "شقی", "بدبخت"),
            entry(22, "شقی", "شقی", "بدبخت"),
        )

        assertEquals(listOf(20), SearchEngine.search(duplicates, "شقی").map { it.id })
    }

    @Test
    fun differentMeaningsForTheSameWordArePreserved() {
        val definitions = listOf(
            entry(30, "word", "word", "meaning 1"),
            entry(31, "word", "word", "meaning 2"),
        )

        assertEquals(listOf(30, 31), SearchEngine.search(definitions, "word").map { it.id })
    }

    @Test
    fun surroundingWhitespaceDoesNotChangeTheMatch() {
        val item = entry(40, "شقی", "شقی", "بدبخت")
        assertEquals(40, SearchEngine.search(listOf(item), "  شقی  ").single().id)
    }

    @Test
    fun unknownWordReturnsNoResults() {
        assertEquals(emptyList<DictionaryEntry>(), SearchEngine.search(entries, "ناموجود"))
    }

    private fun entry(
        id: Int,
        word: String,
        normalized: String,
        meaning: String = "معنی نمونه",
    ) = DictionaryEntry(
        id = id,
        word = word,
        normalizedWord = normalized,
        meaning = meaning,
        sourcePage = 5,
    )
}
