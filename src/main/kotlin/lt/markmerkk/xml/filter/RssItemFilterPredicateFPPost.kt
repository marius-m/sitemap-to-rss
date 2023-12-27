package lt.markmerkk.xml.filter

import lt.markmerkk.xml.removeEmpty
import lt.markmerkk.xml.rss.Rss
import org.slf4j.LoggerFactory
import java.net.URI

class RssItemFilterPredicateFPPost: RssItemPredicate {
    override fun isValid(rssItem: Rss.Item): Boolean {
        val linkSanitized = rssItem.link.content
            .trim()
            .replace("\n", "")
        val uri = URI.create(linkSanitized)
        val pathSegments = uri
            .path
            .split("/")
            .removeEmpty()
        return pathSegments.contains(PATH_SEGMENT_POST) &&
                uri.host.contains(PATH_SEGMENT_DOMAIN)
    }

    companion object {
        private val l = LoggerFactory.getLogger(RssItemFilterPredicateFPPost::class.java)
        const val PATH_SEGMENT_DOMAIN = "finansaipaprastai.lt"
        const val PATH_SEGMENT_POST = "post"
    }
}