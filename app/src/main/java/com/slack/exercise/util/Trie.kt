package com.slack.exercise.util

// A tree-like data structure used to store a dynamic set of strings. Each node of the trie typically represents a
// single character of a string. Strings are stored by branching out from the root node, character by character.
// This efficient operations related to string prefix lookup and storage. Lookup, insertion, and deletion operations run in
// O(n) time, where n is the length of the string. Tries are notably useful for scenarios such as filtering results.
class Trie {
    private data class Node(
        val children: MutableMap<Char, Node> = mutableMapOf(),
        var word: String? = null,
    )

    private val root = Node()

    // Inserts a word into the trie.
    fun insert(word: String) {
        if (word.isBlank()) {
            return
        }

        word.fold(root) { node, char ->
            node.children[char] ?: Node().also { node.children[char] = it }
        }.word = word
    }

    // Searches for an exact match of the word in the trie.
    fun searchExact(word: String): String? {
        if (word.isBlank()) {
            return null
        }

        return word.fold(root) { node, char ->
            node.children[char] ?: return null
        }.word
    }

    // Searches for an exact match of the word in the trie or if
    // the trie contains any word that starts with the given prefix
    fun contains(prefix: String): Boolean {
        return searchExact(prefix) != null
    }
}