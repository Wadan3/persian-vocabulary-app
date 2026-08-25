package com.lughatnama.dictionary.search

import org.junit.Assert.assertEquals
import org.junit.Test

class SearchNormalizerTest {
    @Test
    fun normalizesArabicAndPersianVariants() {
        assertEquals("یک", SearchNormalizer.normalize("  يــك  "))
    }

    @Test
    fun removesDiacriticsOnlyForComparison() {
        assertEquals("فراست", SearchNormalizer.normalize("فِراست"))
    }

    @Test
    fun collapsesRepeatedWhitespace() {
        assertEquals("ایده آلیست", SearchNormalizer.normalize("ایده   آلیست"))
    }

    @Test
    fun removesInvisibleFormattingCharacters() {
        assertEquals("فراستمند", SearchNormalizer.normalize("فراست‌مند\u200F"))
    }
}
