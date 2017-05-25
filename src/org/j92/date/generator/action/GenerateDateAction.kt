package org.j92.date.generator.action

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys.PROJECT
import com.intellij.openapi.actionSystem.CommonDataKeys.EDITOR
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import org.j92.date.generator.date.CurrentDateGenerator

class GenerateDateAction : AnAction() {
    override fun actionPerformed(event: AnActionEvent) {
        val project = event.getData(PROJECT)
        val editor = event.getData(EDITOR)

        editor?.let {
            val formattedDate = CurrentDateGenerator().generate()

            val inserter = buildTextInserter(editor, formattedDate)

            project?.runCommand(inserter)
        }
    }

    private fun buildTextInserter(editor: Editor, formattedDate: String): TextInserter {
        return TextInserter(editor.caretModel.allCarets, editor.document, formattedDate)
    }

    private fun Project.runCommand(runnable: TextInserter) {
        WriteCommandAction.runWriteCommandAction(this, runnable)
    }
}
