package lt.markmerkk.xml

import lt.markmerkk.TimeProviderImpl
import lt.markmerkk.xml.filter.RssItemValidator
import lt.markmerkk.xml.rss.Rss
import lt.markmerkk.xml.sitemap.Sitemap
import nl.adaptivity.xmlutil.serialization.XML
import org.slf4j.LoggerFactory
import java.io.File
import java.time.OffsetDateTime

fun main(args: Array<String>) {
    Main1().run("sitemap.xml")
}

class Main1 {
    private val timeProvider = TimeProviderImpl()

    @Throws(IllegalArgumentException::class)
    fun run(inputFileName: String) {
        val now = timeProvider.now()
        val inputFile = File(inputFileName)
        if (!inputFile.exists())
            throw IllegalArgumentException("Must provide an input file!")

        l.info("Parsing input sitemap (${inputFile.absolutePath})")
        val sitemapUrlSet = parseSitemap(inputFile)

        l.info("Converting to rss items")
        val rssItems = sitemapToRssItems(sitemapUrlSet, now)

        l.info("Generating rss.xml")
        generateRss(now, rssItems)
    }

    private fun generateRss(now: OffsetDateTime, rssItems: List<Rss.Item>) {
        val rssXml = Rss.Rss(
            channel = Rss.Channel(
                title = Rss.Title("Finansai paprastai"),
                link = Rss.Link("https://www.finansaipaprastai.lt/"),
                description = Rss.Description.asEmpty(),
                pubDate = Rss.PubDate.fromDT(now),
                item = rssItems,
            )
        )
        l.debug(rssItems.toString())
        writeRssToFile("rss.xml", rssXml)
    }

    private fun sitemapToRssItems(
        sitemapUrlSet: Sitemap.UrlSet,
        now: OffsetDateTime,
        rssItemValidator: RssItemValidator = RssItemValidator.createDefault(),
    ): List<Rss.Item> {
        val rssItemResults: List<Result<Rss.Item>> = sitemapUrlSet.urls
            .map { url: Sitemap.Url ->
                val contentSanitized = url.loc.contentSanitized()
                runCatching {
                    Rss.Item(
                        title = Rss.Title.fromLink(contentSanitized),
                        link = Rss.Link(contentSanitized),
                        guid = Rss.Guid(contentSanitized),
                        description = Rss.Description.asEmpty(),
                        pubDate = Rss.PubDate.fromDT(now),
                    )
                }
            }
        val rssItemResultsFailures: List<Result<Rss.Item>> = rssItemResults
            .filter { it.isFailure }
        val rssItemResultsInvalid: List<Result<Rss.Item>> = rssItemResults
            .filter { it.isSuccess }
            .filter { !rssItemValidator.isValid(it.getOrNull()) }
        l.info("Parsed items: ${rssItemResults.size}")
        l.info("Parsed failures: ${rssItemResultsFailures.size}")
        if (rssItemResultsFailures.isNotEmpty()) {
            rssItemResultsFailures.forEach {
                l.warn("Failure: ${it.exceptionOrNull()?.message}")
            }
        }
        l.info("Parsed invalid items: ${rssItemResultsInvalid.size}")
        if (rssItemResultsInvalid.isNotEmpty()) {
            rssItemResultsInvalid.forEach {
                l.warn("Invalid item: ${it.getOrNull()}")
            }
        }
        val rssItemsSuccess = rssItemResults
            .minus(rssItemResultsFailures)
            .minus(rssItemResultsInvalid)
            .mapNotNull { it.getOrNull() }
        l.debug(rssItemsSuccess.toString())
        return rssItemsSuccess
    }

    private fun parseSitemap(inputFile: File): Sitemap.UrlSet {
        val sitemapUrlSet = XML
            .decodeFromString(Sitemap.UrlSet.serializer(), inputFile.readText(Charsets.UTF_8))
        l.debug(sitemapUrlSet.toString())
        return sitemapUrlSet
    }

    private fun writeRssToFile(
        outputFileName: String,
        output: Rss.Rss,
    ) {
        val outputFile = File(outputFileName)
        if (!outputFile.exists()) {
            outputFile.createNewFile()
        }
        outputFile.writeText(
            XML.Companion.encodeToString(Rss.Rss.serializer(), output)
        )
    }

    companion object {
        val l = LoggerFactory.getLogger(Main1::class.java)
    }
}