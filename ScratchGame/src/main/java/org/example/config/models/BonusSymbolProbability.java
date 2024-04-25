package org.example.config.models;

import java.util.Map;

public class BonusSymbolProbability {
    private Map<String, Integer> symbols;

    // Getters and setters

    @Override
    public String toString() {
        return "BonusSymbolProbability{" +
                "symbols=" + symbols +
                '}';
    }

    public Map<String, Integer> getSymbols() {
        return symbols;
    }

    public void setSymbols(Map<String, Integer> symbols) {
        this.symbols = symbols;
    }
}
