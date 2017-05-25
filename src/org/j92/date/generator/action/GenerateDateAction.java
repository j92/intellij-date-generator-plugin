package org.j92.date.generator.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.project.Project;
import org.j92.date.generator.date.CurrentDateGenerator;

public class GenerateDateAction extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent event) {
        final Project project = event.getData(CommonDataKeys.PROJECT);
        final Editor editor = event.getData(CommonDataKeys.EDITOR);

        if (null == editor) {
            return;
        }

        Document document = editor.getDocument();

        final SelectionModel selectionModel = editor.getSelectionModel();

        String formattedDate = new CurrentDateGenerator().generate();

        final int selectionStart = selectionModel.getSelectionStart();
        final int selectionEnd = selectionModel.getSelectionEnd();

        Runnable runnable = () -> document.replaceString(selectionStart, selectionEnd, formattedDate);

        WriteCommandAction.runWriteCommandAction(project, runnable);

        editor.getCaretModel().moveToOffset(selectionStart + formattedDate.length());
    }
}
