package com.hackathon.markdownnotetakingapp.util;

import org.commonmark.node.AbstractVisitor;
import org.commonmark.node.Node;
import org.commonmark.node.Text;

public class MarkdownTextExtractor {

    public static String extractText(Node node) {
        TextContentVisitor visitor = new TextContentVisitor();
        node.accept(visitor);
        return visitor.getTextContent();
    }

    private static class TextContentVisitor extends AbstractVisitor {
        private final StringBuilder buffer = new StringBuilder();

        public String getTextContent() {
            // Use trim() to remove any leading/trailing whitespace.
            return buffer.toString().trim();
        }

        @Override
        public void visit(Text text) {
            // Append the literal text from a text node.
            buffer.append(text.getLiteral());
            // Add a space to ensure words from different nodes are separated.
            buffer.append(" ");
        }
    }
}