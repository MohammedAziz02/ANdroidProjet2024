package com.projetjava.Models;


public class Recipe {
    private int id;
    private String name_recipe;
    private String description_recipe;
    private String ingredients_recipe;

    private byte[]  imageBytes;
    public Recipe(int id, String name, String description, String ingredients,byte[] coded) {
        this.id=id;
        this.name_recipe=name;
        this.description_recipe=description;
        this.imageBytes=coded;
        this.ingredients_recipe=ingredients;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName_recipe() {
        return name_recipe;
    }

    public void setName_recipe(String name_recipe) {
        this.name_recipe = name_recipe;
    }

    public String getDescription_recipe() {
        return description_recipe;
    }

    public void setDescription_recipe(String description_recipe) {
        this.description_recipe = description_recipe;
    }

    public String getIngredients_recipe() {
        return ingredients_recipe;
    }

    public void setIngredients_recipe(String ingredients_recipe) {
        this.ingredients_recipe = ingredients_recipe;
    }

    public byte[] getImageBytes() {
        return imageBytes;
    }

    public void setImageBytes(byte[] image_base64) {
        this.imageBytes = image_base64;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "name_recipe='" + name_recipe + '\'' +
                ", description_recipe='" + description_recipe + '\'' +
                ", ingredients_recipe='" + ingredients_recipe + '\'' +
                '}';
    }
}