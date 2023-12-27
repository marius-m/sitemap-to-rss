package lt.markmerkk.xml.sitemap

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

class SitemapLocContentTrimmedTest {
    @Test
    fun valid() {
        val input = "\n" +
                "            https://www.finansaipaprastai.lt\n" +
                "        "
        val result = Sitemap.Loc(input).contentSanitized()
        assertThat(result).isEqualTo("https://www.finansaipaprastai.lt")
    }

    @Test
    fun noSpaces() {
        val input = "https://www.finansaipaprastai.lt"
        val result = Sitemap.Loc(input).contentSanitized()
        assertThat(result).isEqualTo("https://www.finansaipaprastai.lt")
    }
}