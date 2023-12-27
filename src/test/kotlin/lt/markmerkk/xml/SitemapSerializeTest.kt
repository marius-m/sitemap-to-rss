package lt.markmerkk.xml

import com.google.common.truth.Truth.assertThat
import lt.markmerkk.xml.sitemap.Sitemap
import nl.adaptivity.xmlutil.serialization.XML
import org.junit.jupiter.api.Test

class SitemapSerializeTest {
    @Test
    fun test1() {
        val customXml = Sitemap.UrlSet(
            urls = listOf(
                Sitemap.Url(
                    loc = Sitemap.Loc("https://www.google.com"),
                    lastmod = "2019-01-01",
                    changefreq = "daily",
                    priority = "0.5"
                ),
                Sitemap.Url(
                    loc = Sitemap.Loc("https://www.google.com"),
                    lastmod = "2019-01-01",
                    changefreq = "daily",
                    priority = "0.5"
                )
            )
        )
        val result = XML.encodeToString(Sitemap.UrlSet.serializer(), customXml)

        val expectNoWhiteSpace =
            """
               <urlset xmlns="http://www.sitemaps.org/schemas/sitemap/0.9"><url lastmod="2019-01-01" changefreq="daily" priority="0.5"><loc>https://www.google.com</loc></url><url lastmod="2019-01-01" changefreq="daily" priority="0.5"><loc>https://www.google.com</loc></url></urlset>
            """
            .trimIndent()
        assertThat(result).isEqualTo(expectNoWhiteSpace)
    }
}