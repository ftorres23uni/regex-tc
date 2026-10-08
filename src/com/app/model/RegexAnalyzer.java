package com.app.model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexAnalyzer {
    private static final String REGEX_NATURAL = "^(\\d{1,3}(,\\d{3})+|\\d+)$";
    private static final String REGEX_REAL = "^(\\d{1,3}(,\\d{3})+|\\d+)\\.\\d+$";
    private static final String REGEX_PERCENTAGE = "^(\\d{1,3}(,\\d{3})+|\\d+)(\\.\\d+)?%$";
    private static final String REGEX_MONETARY = "^\\$(\\d{1,3}(,\\d{3})+|\\d+)(\\.\\d+)?$";

    private static final Pattern CANDIDATE_PATTERN = Pattern.compile("\\S*\\d\\S*");

    public List<NumericToken> analyzeText(String text) {
        List<NumericToken> results = new ArrayList<>();

        if (text == null || text.trim().isEmpty()) {
            return results;
        }

        String[] lines = text.split("\\n");
        int resultCounter = 1;

        for (int i = 0; i < lines.length; i++) {
            int lineNumber = i + 1;
            String lineText = lines[i];

            Matcher matcher = CANDIDATE_PATTERN.matcher(lineText);

            while (matcher.find()) {
                String token = matcher.group();

                while (token.endsWith(".") || token.endsWith(",") || token.endsWith(";") || token.endsWith(":")) {
                    token = token.substring(0, token.length() - 1);
                }

                String type = "Invalid";
                if (token.matches(REGEX_NATURAL)) {
                    type = "Natural";
                } else if (token.matches(REGEX_REAL)) {
                    type = "Real";
                } else if (token.matches(REGEX_PERCENTAGE)) {
                    type = "Percentage";
                } else if (token.matches(REGEX_MONETARY)) {
                    type = "Monetary Value";
                }

                results.add(new NumericToken(resultCounter, lineNumber, token, type));
                resultCounter++;
            }
        }
        return results;
    }
}
