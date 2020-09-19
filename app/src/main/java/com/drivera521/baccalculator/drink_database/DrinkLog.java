package com.example.daniel.riveradaniel_project1.drink_database;


public class DrinkLog {

    private String drinkName;
    private String stdDrink;
    private String consumedTime;

    public DrinkLog(String drinkName, String stdDrink, String consumedTime) {
        this.drinkName = drinkName;
        this.stdDrink = stdDrink;
        this.consumedTime = consumedTime;
    }

    public String getDrinkName() {
        return drinkName;
    }

    public String getStdDrink() {
        return stdDrink;
    }

    public String getConsumedTime() {
        return consumedTime;
    }

}
