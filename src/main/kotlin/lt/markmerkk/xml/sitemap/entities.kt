package lt.markmerkk.xml.sitemap

import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlSerialName
import nl.adaptivity.xmlutil.serialization.XmlValue

class Sitemap {

    @Serializable
    @XmlSerialName("urlset", namespace = "http://www.sitemaps.org/schemas/sitemap/0.9")
    data class UrlSet(
        @XmlSerialName("url") val urls: List<Url>,
    )

    @Serializable
    @XmlSerialName("url")
    data class Url(
        val loc: Loc,
        val lastmod: String = "",
        val changefreq: String = "",
        val priority: String = "",
    )

    @Serializable
    @XmlSerialName("loc")
    data class Loc(
        @XmlValue val content: String,
    )
}
