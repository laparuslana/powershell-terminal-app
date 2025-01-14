package com.example.terminal.WindowStyle;

import java.util.HashMap;
import java.util.Map;

public class ThemeManager {
    private static final Map<String, BackgroundTheme> factories = new HashMap<>();

    static {
        factories.put("dark", new DarkTheme());
        factories.put("light", new LightTheme());
        factories.put("highcontrast", new HighContrastTheme());
    }

    public static BackgroundTheme getFactory(String schemeName) {
        return factories.get(schemeName);
    }
}
