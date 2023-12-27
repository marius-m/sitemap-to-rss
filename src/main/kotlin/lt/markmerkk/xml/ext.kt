package lt.markmerkk.xml

fun List<String>.removeEmpty(): List<String> {
    return this
        .filter { it.isNotEmpty() }
}
