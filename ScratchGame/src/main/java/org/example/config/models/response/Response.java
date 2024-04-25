package org.example.config.models.response;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Response {
    private String[][] matrix;
    private BigDecimal reward;
    private Map<String, List<String>> applied_winning_combinations = new HashMap<>();
    private String applied_bonus_symbol;

    public String[][] getMatrix() {
        return matrix;
    }

    public BigDecimal getReward() {
        return reward;
    }

    public Map<String, List<String>> getApplied_winning_combinations() {
        return applied_winning_combinations;
    }

    public String getApplied_bonus_symbol() {
        return applied_bonus_symbol;
    }

    public void setMatrix(String[][] matrix) {
        this.matrix = matrix;
    }

    public void setReward(BigDecimal reward) {
        this.reward = reward;
    }

    public void setApplied_winning_combinations(Map<String, List<String>> applied_winning_combinations) {
        this.applied_winning_combinations = applied_winning_combinations;
    }

    public void setApplied_bonus_symbol(String applied_bonus_symbol) {
        this.applied_bonus_symbol = applied_bonus_symbol;
    }
}
