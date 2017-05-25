package org.j92.date.generator.date

import java.time.ZonedDateTime

interface DateGenerator {
    fun generate(zonedDateTime: ZonedDateTime = ZonedDateTime.now()): String
}
