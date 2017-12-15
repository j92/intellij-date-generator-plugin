package org.j92.date.generator.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import org.j92.date.generator.date.CurrentDateGenerator;

public class GenerateDateAction extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent event) {
        final Project project = event.getData(CommonDataKeys.PROJECT);
        final Editor editor = event.getData(CommonDataKeys.EDITOR);

        if (null == editor || project == null) {
            return;
        }

        String formattedDate = new CurrentDateGenerator(project).generate();

        Runnable runnable = () -> {
            for (Caret caret : editor.getCaretModel().getAllCarets()) {
                insertTextAtCaret(editor.getDocument(), caret, formattedDate);
            }
        };

        WriteCommandAction.runWriteCommandAction(project, runnable);
    }

    private static void insertTextAtCaret(Document document, Caret caret, CharSequence text) {
        document.replaceString(caret.getSelectionStart(), caret.getSelectionEnd(), text);
        if (caret.hasSelection()) {
            caret.setSelection(caret.getSelectionStart(),  caret.getSelectionStart() + text.length());
        }
        caret.moveToOffset(caret.getSelectionStart() + text.length());
    }
}
