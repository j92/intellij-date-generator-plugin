package org.j92.date.generator.date

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class CurrentDateGenerator(val pattern: String = "yyyy-MM-dd'T'HH:mm:ssXXX") : DateGenerator {
    override fun generate(zonedDateTime: ZonedDateTime): String {
        val formatter = DateTimeFormatter.ofPattern(pattern)
        return formatter.format(zonedDateTime)
    }
}
