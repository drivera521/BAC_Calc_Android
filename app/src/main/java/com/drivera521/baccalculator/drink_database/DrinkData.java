package com.example.daniel.riveradaniel_project1.drink_database;

public class DrinkData {

    private final String drinkName;
    private final String standardDrink;
    private final String drinkVolume;
    private final String drinkPercentage;
    private final String drinkImage;

    public DrinkData(String drinkName, String standardDrink, String drinkVolume, String drinkPercentage, String drinkImage) {
        this.drinkName = drinkName;
        this.standardDrink = standardDrink;
        this.drinkVolume = drinkVolume;
        this.drinkPercentage = drinkPercentage;
        this.drinkImage = drinkImage;
    }

    public String getDrinkName() {
        return drinkName;
    }

    public String getStandardDrink() {
        return standardDrink;
    }

    public String getDrinkVolume() {
        return drinkVolume;
    }

    public String getDrinkPercentage() {
        return drinkPercentage;
    }

    public String getDrinkImage() {
        return drinkImage;
    }
}
