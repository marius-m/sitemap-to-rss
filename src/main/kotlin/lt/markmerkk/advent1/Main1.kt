package lt.markmerkk.advent1

import java.io.File

fun main(args: Array<String>) {
    val inputFile = File("input1.txt")
    if (!inputFile.exists())
        throw IllegalArgumentException("Must provide an input file!")

    // Reading lines from kotlin
    val inputLines = inputFile
        .bufferedReader(Charsets.UTF_8)
        .readLines()
    println(inputLines)
}