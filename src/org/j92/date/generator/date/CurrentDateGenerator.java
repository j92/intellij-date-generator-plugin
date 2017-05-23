package org.j92.date.generator.date;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class CurrentDateGenerator implements DateGenerator {
    @Override
    public String generate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX ");
        return formatter.format(ZonedDateTime.now());
    }
}
