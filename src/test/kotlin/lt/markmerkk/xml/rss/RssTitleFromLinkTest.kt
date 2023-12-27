package lt.markmerkk.xml.rss

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class RssTitleFromLinkTest {

    @Test
    fun valid1() {
        val inputLink = "https://www.finansaipaprastai.lt/post/top-5-mitai-tiesos-apie-investavima"
        val result = Rss.Title.fromLink(inputLink)

        assertThat(result).isEqualTo(
            Rss.Title(content = "top 5 mitai tiesos apie investavima")
        )
    }

    @Test
    fun valid2() {
        val inputLink = "https://www.finansaipaprastai.lt/post/infliacija-lietuvoje-pavojus-ar-galimybe"
        val result = Rss.Title.fromLink(inputLink)

        assertThat(result).isEqualTo(
            Rss.Title(content = "infliacija lietuvoje pavojus ar galimybe")
        )
    }

    @Test
    fun valid3_withSpaces() {
        val inputLink = "\n" +
                "            https://www.finansaipaprastai.lt/analize/4-magiskas-skaicius\n" +
                "        "
        val result = Rss.Title.fromLink(inputLink)

        assertThat(result).isEqualTo(
            Rss.Title(content = "4 magiskas skaicius")
        )
    }

    @Test
    fun noSegments() {
        val inputLink = "https://www.finansaipaprastai.lt/"

        assertThrows<IllegalArgumentException> {
            Rss.Title.fromLink(inputLink)
        }
    }
}