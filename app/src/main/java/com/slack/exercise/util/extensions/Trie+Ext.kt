package com.slack.exercise.util.extensions

import com.slack.exercise.util.Trie

/**
 * Converts an Iterable of Strings into a Trie data structure.
 */
fun Iterable<String>.toTrie(): Trie {
	return Trie().apply {
		forEach { word ->
			insert(word)
		}
	}
}
