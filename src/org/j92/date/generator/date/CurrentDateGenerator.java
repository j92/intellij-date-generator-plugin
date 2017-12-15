package org.j92.date.generator.date;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.project.Project;
import org.j92.date.generator.Settings;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class CurrentDateGenerator implements DateGenerator {
    Project project;

    public CurrentDateGenerator(Project project) {
        this.project = project;
    }

    @Override
    public String generate() {
        String format = Settings.getInstance(this.project).format;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return formatter.format(ZonedDateTime.now());
    }
}
