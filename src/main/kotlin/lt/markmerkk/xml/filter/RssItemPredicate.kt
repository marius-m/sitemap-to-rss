package lt.markmerkk.xml.filter

import lt.markmerkk.xml.rss.Rss

/**
 * Assures that RSS link is valid
 */
interface RssItemPredicate {
    fun isValid(rssItem: Rss.Item): Boolean
}

class RssItemValidator(val predicates: List<RssItemPredicate>) {
    fun isValid(rssItem: Rss.Item?): Boolean {
        if (rssItem == null) {
            return false
        }
        return predicates.all { it.isValid(rssItem) }
    }

    companion object {
        fun createDefault(): RssItemValidator {
            return RssItemValidator(
                listOf(
                    RssItemFilterPredicateFPPost(),
                )
            )
        }
    }
}