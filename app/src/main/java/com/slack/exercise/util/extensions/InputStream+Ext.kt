package com.slack.exercise.util.extensions

import java.io.BufferedReader
import java.io.InputStream

// this assumes that values in denylist.txt is all lowercase
fun InputStream.toStringList(): List<String> {
	return bufferedReader().use(BufferedReader::readLines)
}