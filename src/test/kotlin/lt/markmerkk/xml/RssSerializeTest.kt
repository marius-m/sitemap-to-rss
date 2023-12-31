package lt.markmerkk.xml

import com.google.common.truth.Truth.assertThat
import lt.markmerkk.TimeProviderTest
import lt.markmerkk.xml.rss.Rss
import nl.adaptivity.xmlutil.serialization.XML
import org.junit.jupiter.api.Test

class RssSerializeTest {
    @Test
    fun test1() {
        val now = TimeProviderTest.now()
        val customXmlStr = Rss.Rss(
            channel = Rss.Channel(
                title = Rss.Title("title"),
                link = Rss.Link("link"),
                description = Rss.Description("description"),
                pubDate = Rss.PubDate.fromDT(now),
                item = listOf(
                    Rss.Item(
                        title = Rss.Title("title"),
                        link = Rss.Link("link"),
                        guid = Rss.Guid("guid"),
                        description = Rss.Description("description"),
                        pubDate = Rss.PubDate.fromDT(now),
                    ),
                    Rss.Item(
                        title = Rss.Title("title"),
                        link = Rss.Link("link"),
                        guid = Rss.Guid("guid"),
                        description = Rss.Description("description"),
                        pubDate = Rss.PubDate.fromDT(now),
                    )
                ),
            )
        )

        val result = XML.encodeToString(Rss.Rss.serializer(), customXmlStr)

        val expectNoWhiteSpace =
            """
<rss version="2.0" xmlns:atom="http://www.w3.org/2005/Atom" xmlns:media="http://search.yahoo.com/mrss/"><channel><title>title</title><link>link</link><description>description</description><pubDate>Thu, 1 Jan 1970 00:00:00 GMT</pubDate><item><title>title</title><link>link</link><guid>guid</guid><description>description</description><pubDate>Thu, 1 Jan 1970 00:00:00 GMT</pubDate></item><item><title>title</title><link>link</link><guid>guid</guid><description>description</description><pubDate>Thu, 1 Jan 1970 00:00:00 GMT</pubDate></item></channel></rss>
            """
            .trimIndent()
        assertThat(result).isEqualTo(expectNoWhiteSpace)
    }

    @Test
    fun test1_valid() {
        val now = TimeProviderTest.now()
        val link1 = "https://www.finansaipaprastai.lt/post/investavimas-i-akcijas"
        val customXmlStr = Rss.Rss(
            channel = Rss.Channel(
                title = Rss.Title("Title1"),
                link = Rss.Link("https://www.finansaipaprastai.lt/"),
                description = Rss.Description.asEmpty(),
                pubDate = Rss.PubDate.fromDT(now),
                item = listOf(
                    Rss.Item(
                        title = Rss.Title.fromLink(link1),
                        link = Rss.Link(link1),
                        guid = Rss.Guid(link1),
                        description = Rss.Description.asEmpty(),
                        pubDate = Rss.PubDate.fromDT(now),
                    ),
                ),
            )
        )

        val result = XML.encodeToString(Rss.Rss.serializer(), customXmlStr)

        val expectNoWhiteSpace =
            """
<rss version="2.0" xmlns:atom="http://www.w3.org/2005/Atom" xmlns:media="http://search.yahoo.com/mrss/"><channel><title>Title1</title><link>https://www.finansaipaprastai.lt/</link><description></description><pubDate>Thu, 1 Jan 1970 00:00:00 GMT</pubDate><item><title>investavimas i akcijas</title><link>https://www.finansaipaprastai.lt/post/investavimas-i-akcijas</link><guid>https://www.finansaipaprastai.lt/post/investavimas-i-akcijas</guid><description></description><pubDate>Thu, 1 Jan 1970 00:00:00 GMT</pubDate></item></channel></rss>
            """.trimIndent()
        assertThat(result).isEqualTo(expectNoWhiteSpace)
    }
}