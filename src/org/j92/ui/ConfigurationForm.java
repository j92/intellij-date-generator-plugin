package org.j92.ui;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import org.j92.date.generator.Settings;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.format.DateTimeFormatter;

public class ConfigurationForm implements Configurable {
    private final Project project;
    private JTextField formatTextField;
    private JLabel formatLabel;
    private JPanel formatPanel;
    private JLabel formatInfoLabel;
    private JButton formatResetButton;
    private JButton formatHelpButton;

    public ConfigurationForm(@NotNull final Project project) {
        this.project = project;
    }

    @Nls
    @Override
    public String getDisplayName() {
        return "Date generator";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        formatResetButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                formatTextField.setText(getSettings().DEFAULT_FORMAT);
            }
        });
        formatHelpButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                IdeHelper.openUrl("https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html");
            }
        });
        formatTextField.setText(getSettings().format);
        formatTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                validateFormat();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                validateFormat();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                validateFormat();
            }
        });
        return formatPanel;
    }

    @Override
    public boolean isModified() {
        return !formatTextField.getText().equals(getSettings().format);
    }

    private Settings getSettings() {
        return Settings.getInstance(this.project);
    }

    @Override
    public void apply() throws ConfigurationException {
        String format = validateFormat();
        if (format == null) return;

        this.formatInfoLabel.setText("");
        getSettings().format = format;
    }

    @Nullable
    private String validateFormat() {
        String format = formatTextField.getText();
        try {
            DateTimeFormatter.ofPattern(format);
            this.formatInfoLabel.setText("");
        } catch (IllegalArgumentException e) {
            this.formatInfoLabel.setText("<html><font color='red'>Invalid format.</font></html>");
            return null;
        }
        return format;
    }

    @Override
    public void reset() {
        formatTextField.setText(getSettings().format);
    }
}
