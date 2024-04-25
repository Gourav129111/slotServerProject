package org.example.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.config.models.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Config {
    private int columns;
    private int rows;
    private LinkedHashMap<String, SymbolConfig> symbols;
    private ProbabilityConfig probabilities;
    private LinkedHashMap<String, WinCombination> winCombinations;
    @JsonCreator
    public Config(@JsonProperty("win_combinations") LinkedHashMap<String, WinCombination> winCombinations,
                  @JsonProperty("columns") int columns,
                  @JsonProperty("rows") int rows,
                  @JsonProperty("symbols") LinkedHashMap<String, SymbolConfig> symbols,
                  @JsonProperty("probabilities") ProbabilityConfig probabilities) {
        this.winCombinations = winCombinations;
        this.columns = columns;
        this.rows = rows;
        this.symbols = symbols;
        this.probabilities = probabilities;
    }
    public Config() {
    }

    public LinkedHashMap<String, WinCombination> getWinCombinations() {
        return winCombinations;
    }

    public void setWinCombinations(LinkedHashMap<String, WinCombination> winCombinations) {
        this.winCombinations = winCombinations;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public Map<String, SymbolConfig> getSymbols() {
        return symbols;
    }

    public void setSymbols(LinkedHashMap<String, SymbolConfig> symbols) {
        this.symbols = symbols;
    }

    public ProbabilityConfig getProbabilities() {
        return probabilities;
    }

    public void setProbabilities(ProbabilityConfig probabilities) {
        this.probabilities = probabilities;
    }

    @Override
    public String toString() {
        return "Config{" +
                "columns=" + columns +
                ", rows=" + rows +
                ", symbols=" + symbols +
                ", probabilities=" + probabilities +
                ", winCombinations=" + winCombinations +
                '}';
    }
}


