package com.mhelrigo.foodmanual.model.ingredient;

public class IngredientModel {
    private String idIngredient;
    private String strIngredient;
    private String strDescription;
    private String strType = null;

    public String getIdIngredient() {
        return idIngredient;
    }

    public String getStrIngredient() {
        return strIngredient;
    }

    public String getStrDescription() {
        return strDescription;
    }

    public String getStrType() {
        return strType;
    }

    // Setter Methods

    public void setIdIngredient( String idIngredient ) {
        this.idIngredient = idIngredient;
    }

    public void setStrIngredient( String strIngredient ) {
        this.strIngredient = strIngredient;
    }

    public void setStrDescription( String strDescription ) {
        this.strDescription = strDescription;
    }

    public void setStrType( String strType ) {
        this.strType = strType;
    }
}
