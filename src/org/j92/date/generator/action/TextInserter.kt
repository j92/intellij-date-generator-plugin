package org.j92.date.generator.action

import com.intellij.openapi.editor.Caret
import com.intellij.openapi.editor.Document

class TextInserter(val carets: List<Caret>, val document: Document, val text: CharSequence) : Runnable {
    override fun run() {
        carets.forEach { it.insertTextAtCaret(text) }
    }

    private fun Caret.insertTextAtCaret(text: CharSequence) {
        document.replaceString(selectionStart, selectionEnd, text)
        adjustSelection(text.length)
        moveToOffset(selectionStart + text.length)
    }

    private fun Caret.adjustSelection(length: Int) {
        if (hasSelection()) {
            setSelection(selectionStart, selectionStart + length)
        }
    }
}
