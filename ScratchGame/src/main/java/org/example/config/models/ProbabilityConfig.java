package org.example.config.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ProbabilityConfig {
    private List<StandardSymbolProbability> standardSymbols;
    private BonusSymbolProbability bonusSymbols;

    @JsonCreator
    public ProbabilityConfig(@JsonProperty("standard_symbols") List<StandardSymbolProbability> standardSymbols,
                        @JsonProperty("bonus_symbols") BonusSymbolProbability bonusSymbols) {
        this.standardSymbols = standardSymbols;
        this.bonusSymbols = bonusSymbols;
    }

    @Override
    public String toString() {
        return "ProbabilityConfig{" +
                "standardSymbols=" + standardSymbols +
                ", bonusSymbols=" + bonusSymbols +
                '}';
    }


    public List<StandardSymbolProbability> getStandardSymbols() {
        return standardSymbols;
    }

    public void setStandardSymbols(List<StandardSymbolProbability> standardSymbols) {
        this.standardSymbols = standardSymbols;
    }

    public BonusSymbolProbability getBonusSymbols() {
        return bonusSymbols;
    }

    public void setBonusSymbols(BonusSymbolProbability bonusSymbols) {
        this.bonusSymbols = bonusSymbols;
    }
}
