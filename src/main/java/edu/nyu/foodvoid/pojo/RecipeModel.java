package edu.nyu.foodvoid.pojo;

public class RecipeModel {

    private String recipeName,dietLabel,healthLabel,ingredients,shareUrl;

    public String getDietLabel() {
        return dietLabel;
    }

    public String getHealthLabel() {
        return healthLabel;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setDietLabel(String dietLabel) {
        this.dietLabel = dietLabel;
    }

    public void setHealthLabel(String healthLabel) {
        this.healthLabel = healthLabel;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }


}
