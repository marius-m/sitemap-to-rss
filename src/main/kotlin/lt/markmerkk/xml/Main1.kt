package lt.markmerkk.xml

import lt.markmerkk.xml.sitemap.Sitemap
import nl.adaptivity.xmlutil.serialization.XML
import java.io.File

fun main(args: Array<String>) {
    val inputFile = File("sitemap.xml")
    if (!inputFile.exists())
        throw IllegalArgumentException("Must provide an input file!")

    // Reading lines from kotlin
//    val inputLines = inputFile
//        .bufferedReader(Charsets.UTF_8)
//        .readLines()
//    println(inputLines)

    val sitemapUrlSet = XML.Companion
        .decodeFromString(Sitemap.UrlSet.serializer(), inputFile.readText(Charsets.UTF_8))
    println(sitemapUrlSet)

    
}