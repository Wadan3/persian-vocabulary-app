package com.lughatnama.dictionary.search

import java.text.Normalizer

object SearchNormalizer {
    private val diacritics = Regex("[\\u0610-\\u061A\\u064B-\\u065F\\u0670\\u06D6-\\u06ED]")
    private val invisibleCharacters = Regex(
        "[\\u00AD\\u200B-\\u200F\\u202A-\\u202E\\u2060-\\u206F\\uFEFF]",
    )
    private val repeatedWhitespace = Regex("\\s+")

    fun normalize(value: String): String {
        val canonical = Normalizer.normalize(value, Normalizer.Form.NFC)
        val normalizedCharacters = buildString(canonical.length) {
            canonical.forEach { character ->
                when (character) {
                    'ي', 'ى', 'ئ' -> append('ی')
                    'ك' -> append('ک')
                    'ة', 'ۀ' -> append('ه')
                    'ؤ' -> append('و')
                    'أ', 'إ', 'ٱ' -> append('ا')
                    'ـ' -> Unit
                    else -> append(character)
                }
            }
        }
        return normalizedCharacters
            .replace(diacritics, "")
            .replace(invisibleCharacters, "")
            .replace(repeatedWhitespace, " ")
            .trim()
    }
}
