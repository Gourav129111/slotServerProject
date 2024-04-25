package org.example.config.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SymbolConfig {
    private double rewardMultiplier;
    private String type;
    private String impact;
    private Integer extra;

    @JsonCreator
    public SymbolConfig(@JsonProperty("reward_multiplier") double rewardMultiplier,
                  @JsonProperty("type") String type,
                  @JsonProperty("impact") String impact,
                  @JsonProperty("extra") Integer extra) {
        this.rewardMultiplier = rewardMultiplier;
        this.type = type;
        this.impact = impact;
        this.extra = extra;

    }

    public Integer getExtra() {
        return extra;
    }

    public void setExtra(Integer extra) {
        this.extra = extra;
    }
// Other properties


    @Override
    public String toString() {
        return "SymbolConfig{" +
                "rewardMultiplier=" + rewardMultiplier +
                ", type='" + type + '\'' +
                ", impact='" + impact + '\'' +
                ", extra=" + extra +
                '}';
    }

    public String getImpact() {
        return impact;
    }

    public void setImpact(String impact) {
        this.impact = impact;
    }

    public SymbolConfig() {
    }

    public double getRewardMultiplier() {
        return rewardMultiplier;
    }

    public void setRewardMultiplier(double rewardMultiplier) {
        this.rewardMultiplier = rewardMultiplier;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    // Getters and setters
}
