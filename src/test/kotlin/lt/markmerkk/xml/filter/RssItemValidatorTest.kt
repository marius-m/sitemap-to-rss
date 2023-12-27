package lt.markmerkk.xml.filter

import com.google.common.truth.Truth.assertThat
import lt.markmerkk.TimeProviderTest
import lt.markmerkk.xml.rss.Rss
import org.junit.jupiter.api.Test

class RssItemValidatorTest {

    private val rssItemValidator = RssItemValidator.createDefault()
    private val timeProvider = TimeProviderTest

    @Test
    fun valid() {
        // Assemble
        val link = "https://www.finansaipaprastai.lt/post/3-brangiausi-nft-pasaulyje"
        val item = Rss.Item(
            title = Rss.Title("title"),
            link = Rss.Link(link),
            guid = Rss.Guid("guid"),
            description = Rss.Description("description"),
            pubDate = Rss.PubDate.fromDT(timeProvider.now()),
        )

        // Act
        val result = rssItemValidator.isValid(item)

        // Assert
        assertThat(result).isTrue()
    }

    @Test
    fun nullInput() {
        // Assemble
        // Act
        val result = rssItemValidator.isValid(null)

        // Assert
        assertThat(result).isFalse()
    }

    @Test
    fun empty() {
        // Assemble
        val link = ""
        val item = Rss.Item(
            title = Rss.Title("title"),
            link = Rss.Link(link),
            guid = Rss.Guid("guid"),
            description = Rss.Description("description"),
            pubDate = Rss.PubDate.fromDT(timeProvider.now()),
        )

        // Act
        val result = rssItemValidator.isValid(item)

        // Assert
        assertThat(result).isFalse()
    }

    @Test
    fun invalidSection() {
        // Assemble
        val link = "https://www.finansaipaprastai.lt/analize/4-magiskas-skaicius"
        val item = Rss.Item(
            title = Rss.Title("title"),
            link = Rss.Link(link),
            guid = Rss.Guid("guid"),
            description = Rss.Description("description"),
            pubDate = Rss.PubDate.fromDT(timeProvider.now()),
        )

        // Act
        val result = rssItemValidator.isValid(item)

        // Assert
        assertThat(result).isFalse()
    }

    @Test
    fun invalidSection2() {
        // Assemble
        val link = "https://www.finansaipaprastai.lt/credits"
        val item = Rss.Item(
            title = Rss.Title("title"),
            link = Rss.Link(link),
            guid = Rss.Guid("guid"),
            description = Rss.Description("description"),
            pubDate = Rss.PubDate.fromDT(timeProvider.now()),
        )

        // Act
        val result = rssItemValidator.isValid(item)

        // Assert
        assertThat(result).isFalse()
    }

    @Test
    fun invalidDomain() {
        // Assemble
        val link = "https://www.invaliddomain.lt/post/3-brangiausi-nft-pasaulyje"
        val item = Rss.Item(
            title = Rss.Title("title"),
            link = Rss.Link(link),
            guid = Rss.Guid("guid"),
            description = Rss.Description("description"),
            pubDate = Rss.PubDate.fromDT(timeProvider.now()),
        )

        // Act
        val result = rssItemValidator.isValid(item)

        // Assert
        assertThat(result).isFalse()
    }

    @Test
    fun invalidUrl() {
        // Assemble
        val link = "asdf1234"
        val item = Rss.Item(
            title = Rss.Title("title"),
            link = Rss.Link(link),
            guid = Rss.Guid("guid"),
            description = Rss.Description("description"),
            pubDate = Rss.PubDate.fromDT(timeProvider.now()),
        )

        // Act
        val result = rssItemValidator.isValid(item)

        // Assert
        assertThat(result).isFalse()
    }
}