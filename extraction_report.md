# Extraction report

## Summary

- Source PDF: `لغت نامه.pdf`
- Source SHA-256: `474995db83436d08ec259af214a7560b8d64fbb5c78bbe8e01ff9dcd9581560c`
- PDF pages inspected: 89
- Tagged dictionary tables inspected: 95
- Tagged table rows inspected: 1,862
- Dictionary entries extracted and verified: 3,403
- Normalized duplicate-word groups detected: 707
- Additional entries in those duplicate groups: 1,204
- Exact duplicate word-and-meaning groups: 195
- Entries requiring manual review: 0
- Entries with empty words: 0
- Entries with empty meanings: 0
- Unresolved malformed Persian extraction artifacts: 0

## Validation method

1. Rendered and inspected all 89 pages to identify dictionary tables, headings, column direction, merged final rows, and non-dictionary content.
2. Traversed the PDF's tagged structure tree to preserve table, row, cell, and source-page boundaries.
3. Detected both right-to-left and left-to-right table storage order from each visible `لغت / معنی` header, including continuation tables whose headers were not repeated.
4. Decoded the actual embedded-font glyph IDs to recover letters and diacritics that the PDF's semantic Unicode map mislabeled.
5. Fused semantic spacing with glyph-level text so neither missing letters nor glyph-order artifacts were silently accepted.
6. Compared every semantic/glyph disagreement and every combining-mark or mirrored-parenthesis edge case with the rendered source cell.
7. Ran integrity checks for sequential IDs, empty fields, replacement glyphs, duplicate normalized words, and source-page coverage.

## Duplicate handling

Duplicate words are retained because they occur as separate entries in the source PDF and can have different meanings or appearances. Search ranks every exact match before prefix and contains matches.

## Manual review

No entries remain uncertain after the second pass. `manual_review.json` is therefore an empty JSON array.
