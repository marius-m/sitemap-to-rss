package lt.markmerkk.xml.rss

import kotlinx.serialization.Serializable
import lt.markmerkk.DateTimeUtils
import nl.adaptivity.xmlutil.serialization.XmlNamespaceDeclSpec
import nl.adaptivity.xmlutil.serialization.XmlSerialName
import nl.adaptivity.xmlutil.serialization.XmlValue
import java.net.URI
import java.time.OffsetDateTime

class Rss {
    @Serializable
    @XmlSerialName("rss")
    data class Rss(
        val version: String = "2.0",
        @XmlSerialName("xmlns:atom") val xmlnsAtom: String = "http://www.w3.org/2005/Atom",
        @XmlSerialName("xmlns:media") val xmlnsMedia: String = "http://search.yahoo.com/mrss/",
        val channel: Channel,
    )

    @Serializable
    @XmlSerialName("channel")
    data class Channel(
        val title: Title,
        val link: Link,
        val description: Description,
        val pubDate: PubDate,
        val item: List<Item>,
    )

    @Serializable
    @XmlSerialName("title")
    data class Title(@XmlValue val content: String) {
        companion object {

            @Throws(IllegalArgumentException::class)
            fun fromLink(link: String): Title {
                val linkSanitized = link
                    .trim()
                    .replace("\n", "")
                val uri = URI.create(linkSanitized)
                val pathSegments = uri
                    .path
                    .split("/")
                    .rmEmpty()
                val lastSegment = pathSegments
                    .lastOrNull() ?: throw IllegalArgumentException("Invalid link: $link")
                val titleSanitized = lastSegment
                    .replace("-", " ")
                return Title(titleSanitized)
            }

            private fun List<String>.rmEmpty(): List<String> {
                return this
                    .filter { it.isNotEmpty() }
            }
        }
    }

    @Serializable
    @XmlSerialName("link")
    data class Link(@XmlValue val content: String)

    @Serializable
    @XmlSerialName("description")
    data class Description(@XmlValue val content: String) {
        companion object {
            fun asEmpty(): Description {
                return Description("")
            }
        }
    }

    @Serializable
    @XmlSerialName("pubDate")
    data class PubDate(@XmlValue val content: String) {
        companion object {
            fun fromDT(dateTime: OffsetDateTime): PubDate {
                return PubDate(DateTimeUtils.formatToStringAsRss(dateTime))
            }
        }
    }

    @Serializable
    @XmlSerialName("guid")
    data class Guid(@XmlValue val content: String)

    @Serializable
    @XmlSerialName("item")
    data class Item(
        val title: Title,
        val link: Link,
        val guid: Guid,
        val description: Description,
        val pubDate: PubDate,
    )
}