package com.app.main;

import com.app.controller.AppController;
import com.app.model.RegexAnalyzer;
import com.app.view.MainView;
import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {
    static void main() {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize FlatLaf. Using fallback UI.");
        }

        SwingUtilities.invokeLater(() -> {
            RegexAnalyzer model = new RegexAnalyzer();
            MainView view = new MainView();
            new AppController(view, model);

            view.setVisible(true);
        });
    }
}
