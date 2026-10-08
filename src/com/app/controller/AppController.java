package com.app.controller;

import com.app.model.NumericToken;
import com.app.model.RegexAnalyzer;
import com.app.view.MainView;

import java.util.List;

public class AppController {
    private final MainView view;
    private final RegexAnalyzer model;

    public AppController(MainView view, RegexAnalyzer model) {
        this.view = view;
        this.model = model;

        this.view.setAnalyzeButtonListener(_ -> executeAnalysis());
    }

    private void executeAnalysis() {
        view.clearResults();

        String text = view.getInputText();

        if (text == null || text.trim().isEmpty()) {
            view.displayWarning("The input text is empty.", "Warning");
            return;
        }

        List<NumericToken> results = model.analyzeText(text);

        for (NumericToken token : results) {
            view.addResultRow(new Object[] {
                    token.id(),
                    token.lineNumber(),
                    token.token(),
                    token.type()
            });
        }
    }
}
