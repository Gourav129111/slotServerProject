package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.config.Config;
import org.example.config.ConfigLoader;
import org.example.config.models.response.Response;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

public class Main {
    public final ObjectMapper objectMapper = new ObjectMapper();
    static int columns;
    static int rows;
    private static Config config;
    private static Random random = new Random();

    public static void main(String[] args) {
        String filePath;
        double bet = 0;
        if(args.length!=4) throw new RuntimeException("Error: Arguments not in proper format. --config <json-name> --betting-amount<amount>");
        if(args[0].equals("--config")) filePath = args[1];
        else throw new RuntimeException("--config file missing");
        if(args[2].equals("--betting-amount")) bet = Double.parseDouble(args[3]);
        else throw new RuntimeException("--betting-amount missing");
        try {
            config = ConfigLoader.loadConfig(filePath);
            // Now you have the config object, you can access its properties and use them in your game logic
        } catch (IOException e) {
            e.printStackTrace();
        }
        columns = config.getColumns();
        rows = config.getRows();

        String [][] grid = createMatrix();
        grid = addBonusSymbolsToMatrix(grid);
        Response response = new Response();
        response = checkWinningCombination(grid,response,bet);
        ObjectMapper mapper = new ObjectMapper();
        File responseFile = new File("response.json");
        try {
            mapper.writeValue(responseFile, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(response);
    }

    private static String[][] addBonusSymbolsToMatrix(String[][] grid) {
        int totalWeight = 0;
        int randomRow = random.nextInt(rows);
        int randomCol = random.nextInt(columns);
        Map<String, Integer> map = config.getProbabilities().getBonusSymbols().getSymbols();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            totalWeight += entry.getValue();
        }
        int i1 = random.nextInt(totalWeight);
        int pickerSum = 0;
        for(Map.Entry<String,Integer> entry : map.entrySet()){
            pickerSum+=entry.getValue();
            if(pickerSum>i1 && !entry.getKey().equals("MISS")){
                grid[randomRow][randomCol] = entry.getKey();
                return grid;
            }
        }
        return grid;
    }

    private static HashMap<String,Integer> symbolCount(String[][] grid){
        HashMap<String, Integer> elementCount = new HashMap<>();
        for (String[] row : grid) {
            for (String element : row) {
                elementCount.put(element, elementCount.getOrDefault(element, 0) + 1);
            }
        }
        return elementCount;
    }
    private static HashMap<String,Integer> horizontalPattern(String[][] grid){
        HashMap<String,Integer> horizontalPattern = new HashMap<>();
        for (int i = 0; i < rows; i++) {
            String first = grid[i][0];
            int cnt = 1;
            for (int j = 1; j < columns; j++) {
                if (!grid[i][j].equals(first)) {
                    break;
                }
                cnt++;
            }
            if (cnt == columns) {
                horizontalPattern.put(first,horizontalPattern.getOrDefault(first,0)+1);
            }
        }
        return horizontalPattern;
    }
    private static HashMap<String,Integer> verticalPattern(String[][] grid){
        HashMap<String,Integer> verticalPattern = new HashMap<>();
        for (int j = 0; j < columns; j++) {
            String first = grid[0][j];
            int cnt = 1;
            for (int i = 1; i < rows; i++) {
                if (!grid[i][j].equals(first)) {
                    break;
                }
                cnt++;
            }
            if (cnt == rows) {
                verticalPattern.put(first,verticalPattern.getOrDefault(first,0)+1);
            }
        }
        return verticalPattern;
    }
    private static HashMap<String,Integer> ltrPattern(String[][] grid){
        HashMap<String,Integer> ltrPattern = new HashMap<>();
        String firstDiag = grid[0][0];
        int cntDiag1 = 1;
        for (int i = 1; i < rows; i++) {
            if (!grid[i][i].equals(firstDiag)) {
                break;
            }
            cntDiag1++;
        }
        if (cntDiag1 == rows) {
            ltrPattern.put(firstDiag,1);
        }

        return ltrPattern;
    }
    private static HashMap<String,Integer> rtlPattern(String[][] grid){
        HashMap<String,Integer> rtlPattern = new HashMap<>();
        int j=columns-1;
        String secDiag = grid[0][j];
        int cntDiag2 = 1;
        j--;
        for (int i = 1; i < rows; i++) {
            if (!grid[i][j].equals(secDiag)) {
                break;
            }
            cntDiag2++;
            j--;
        }
        if (cntDiag2 == rows) {
            rtlPattern.put(secDiag,1);
        }
        return rtlPattern;
    }
    private static Response checkWinningCombination(String[][] grid, Response response, double bet) {
        
        double win = 0;
        response.setMatrix(grid);
        HashMap<String,Integer> symbolCount = symbolCount(grid);
        HashMap<String,Integer> horizontalPattern = horizontalPattern(grid);
        HashMap<String,Integer> verticalPattern = verticalPattern(grid);
        HashMap<String,Integer> ltrPattern = new HashMap<>();
        HashMap<String,Integer> rtlPattern = new HashMap<>();
        if(rows==columns){
            ltrPattern = ltrPattern(grid);
            rtlPattern = rtlPattern(grid);
        }
        for(Map.Entry<String,Integer> entry : symbolCount.entrySet()){
            String key = entry.getKey();
            double symbolMulti=1,horizontalMulti = 1, vertmulti=1, diamul=1,dia2mult =1;
            if(symbolCount.get(key)>2){
                symbolMulti = config.getWinCombinations().get("same_symbol_"+symbolCount.get(key)+"_times").getRewardMultiplier()*config.getSymbols().get(key).getRewardMultiplier();
                response.setApplied_winning_combinations(response.getApplied_winning_combinations());
                List<String> groups = new ArrayList<>();
                groups.add("same_symbol_"+symbolCount.get(key)+"_times");
                if(horizontalPattern.containsKey(key)) {
                    horizontalMulti = config.getWinCombinations().get("same_symbols_horizontally").getRewardMultiplier()*horizontalPattern.get(key);
                    groups.add("same_symbols_horizontally");
                }
                if(verticalPattern.containsKey(key)) {
                    vertmulti = config.getWinCombinations().get("same_symbols_vertically").getRewardMultiplier()*verticalPattern.get(key);
                    groups.add("same_symbols_vertically");
                }
                if(ltrPattern.containsKey(key)) {
                    diamul = config.getWinCombinations().get("same_symbols_diagonally_left_to_right").getRewardMultiplier();
                    groups.add("same_symbols_diagonally_left_to_right");
                }
                if(rtlPattern.containsKey(key)) {
                    dia2mult = config.getWinCombinations().get("same_symbols_diagonally_right_to_left").getRewardMultiplier();
                    groups.add("same_symbols_diagonally_right_to_left");
                }
                response.getApplied_winning_combinations().put(key,groups);
                win = win+(symbolMulti*horizontalMulti*vertmulti*diamul*dia2mult);
            }
        }
        if(win>0){
            win = win*bet;
            if(symbolCount.containsKey("10x")){
                win = win*10;
                response.setApplied_bonus_symbol("10x");
            }else if(symbolCount.containsKey("5x")){
                win = win*5;
                response.setApplied_bonus_symbol("5x");
            }else if(symbolCount.containsKey("+1000")){
                win = win+1000;
                response.setApplied_bonus_symbol("+1000");
            }else if(symbolCount.containsKey("+500")){
                win = win+500;
                response.setApplied_bonus_symbol("+500");
            }
        }
        response.setReward(BigDecimal.valueOf(win));
        return response;
    }

    private static String[][] createMatrix() {
        String [][] grid = new String[config.getRows()][config.getColumns()];
        for(int i=0;i<config.getRows();i++){
            for(int j=0;j<config.getColumns();j++){
                Map<String, Integer> map = config.getProbabilities().getStandardSymbols().get(config.getRows()*i+j).getSymbols();
                int totalWeight = 0;
                for(Map.Entry<String,Integer> entry : map.entrySet()){
                    totalWeight += entry.getValue();
                }
                int i1 = random.nextInt(totalWeight);
                int pickerSum = 0;
                for(Map.Entry<String,Integer> entry : map.entrySet()){
                    pickerSum+=entry.getValue();
                    if(pickerSum>i1){
                        grid[i][j] = entry.getKey();
                        break;
                    }
                }
            }
        }
        return grid;
    }


}