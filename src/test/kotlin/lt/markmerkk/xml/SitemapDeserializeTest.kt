package lt.markmerkk.xml

import com.google.common.truth.Truth.assertThat
import lt.markmerkk.xml.sitemap.Sitemap
import nl.adaptivity.xmlutil.serialization.XML
import org.junit.jupiter.api.Test

class SitemapDeserializeTest {
    @Test
    fun test1() {
        val customXmlStr =
            """
               <urlset xmlns="http://www.sitemaps.org/schemas/sitemap/0.9"><url lastmod="2019-01-01" changefreq="daily" priority="0.5"><loc>https://www.google.com</loc></url><url lastmod="2019-01-01" changefreq="daily" priority="0.5"><loc>https://www.google.com</loc></url></urlset>
            """.trimIndent()
        val result = XML.decodeFromString(Sitemap.UrlSet.serializer(), customXmlStr)

        assertThat(result).isEqualTo(
            Sitemap.UrlSet(
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
        )
    }

    @Test
    fun test2() {
        val customXmlStr =
            """
<?xml version="1.0" encoding="UTF-8"?>
<urlset xmlns="http://www.sitemaps.org/schemas/sitemap/0.9" xmlns:xhtml="http://www.w3.org/1999/xhtml">
    <url>
        <loc>
            https://www.finansaipaprastai.lt
        </loc>
    </url>
    <url>
        <loc>
            https://www.finansaipaprastai.lt/analize/4-magiskas-skaicius
        </loc>
    </url>
</urlset>
            """.trimIndent()
        val result = XML.decodeFromString(Sitemap.UrlSet.serializer(), customXmlStr)

        assertThat(result.urls[0].loc.content).isEqualTo("\n" +
                "            https://www.finansaipaprastai.lt\n" +
                "        ")
        assertThat(result.urls[1].loc.content).isEqualTo("\n" +
                "            https://www.finansaipaprastai.lt/analize/4-magiskas-skaicius\n" +
                "        ")
        assertThat(result).isEqualTo(
            Sitemap.UrlSet(
                urls = listOf(
                    Sitemap.Url(
                        loc = Sitemap.Loc(
                            "\n" +
                                    "            https://www.finansaipaprastai.lt\n" +
                                    "        "
                        ),
                    ),
                    Sitemap.Url(
                        loc = Sitemap.Loc(
                            "\n" +
                                    "            https://www.finansaipaprastai.lt/analize/4-magiskas-skaicius\n" +
                                    "        "
                        ),
                    )
                )
            )
        )
    }
}