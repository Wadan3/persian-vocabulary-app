package com.lughatnama.dictionary.data

import java.io.File
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class DictionaryDataIntegrityTest {
    private val json: String by lazy {
        val direct = File("src/main/assets/dictionary.json")
        val fromRoot = File("app/src/main/assets/dictionary.json")
        (if (direct.isFile) direct else fromRoot).readText(Charsets.UTF_8)
    }

    @Test
    fun containsTheValidatedNumberOfEntries() {
        assertEquals(3_403, Regex("\\\"id\\\"\\s*:").findAll(json).count())
        assertEquals(3_403, Regex("\\\"word\\\"\\s*:").findAll(json).count())
        assertEquals(3_403, Regex("\\\"meaning\\\"\\s*:").findAll(json).count())
    }

    @Test
    fun hasNoEmptyWordsOrMeanings() {
        assertFalse(Regex("\\\"word\\\"\\s*:\\s*\\\"\\s*\\\"").containsMatchIn(json))
        assertFalse(Regex("\\\"meaning\\\"\\s*:\\s*\\\"\\s*\\\"").containsMatchIn(json))
    }

    @Test
    fun everyEntryRetainsAPdfSourcePage() {
        val pages = Regex("\\\"sourcePage\\\"\\s*:\\s*(\\d+)")
            .findAll(json)
            .map { it.groupValues[1].toInt() }
            .toList()
        assertEquals(3_403, pages.size)
        assertTrue(pages.all { it in 5..89 })
    }

    @Test
    fun containsNoReplacementGlyphsOrNetworkSources() {
        assertFalse(json.contains('�'))
        assertFalse(json.contains('□'))
        assertFalse(json.contains("http://", ignoreCase = true))
        assertFalse(json.contains("https://", ignoreCase = true))
    }
}
