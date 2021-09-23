package com.mhelrigo.foodmanual.model.meal;

import android.util.Log;

import androidx.room.Ignore;

import com.mhelrigo.foodmanual.R;

public class MealModel {
    public static final String ID = "ID";

    private int id;
    private String idMeal;
    private String strMeal;
    private String strDrinkAlternate = null;
    private String strCategory;
    private String strArea;
    private String strInstructions;
    private String strMealThumb;
    private String strTags = null;
    private String strYoutube;
    private String strIngredient1;
    private String strIngredient2;
    private String strIngredient3;
    private String strIngredient4;
    private String strIngredient5;
    private String strIngredient6;
    private String strIngredient7;
    private String strIngredient8;
    private String strIngredient9;
    private String strIngredient10;
    private String strIngredient11;
    private String strIngredient12;
    private String strIngredient13;
    private String strIngredient14;
    private String strIngredient15;
    private String strIngredient16;
    private String strIngredient17;
    private String strIngredient18;
    private String strIngredient19;
    private String strIngredient20;
    private String strMeasure1;
    private String strMeasure2;
    private String strMeasure3;
    private String strMeasure4;
    private String strMeasure5;
    private String strMeasure6;
    private String strMeasure7;
    private String strMeasure8;
    private String strMeasure9;
    private String strMeasure10;
    private String strMeasure11;
    private String strMeasure12;
    private String strMeasure13;
    private String strMeasure14;
    private String strMeasure15;
    private String strMeasure16;
    private String strMeasure17;
    private String strMeasure18;
    private String strMeasure19;
    private String strMeasure20;
    private String strSource;
    private String dateModified = null;
    private boolean isFavorite;
    private int viewHolderIndex;

    public MealModel(String idMeal, String strMeal, String strDrinkAlternate, String strCategory, String strArea, String strInstructions, String strMealThumb, String strTags, String strYoutube, String strIngredient1, String strIngredient2, String strIngredient3, String strIngredient4, String strIngredient5, String strIngredient6, String strIngredient7, String strIngredient8, String strIngredient9, String strIngredient10, String strIngredient11, String strIngredient12, String strIngredient13, String strIngredient14, String strIngredient15, String strIngredient16, String strIngredient17, String strIngredient18, String strIngredient19, String strIngredient20, String strMeasure1, String strMeasure2, String strMeasure3, String strMeasure4, String strMeasure5, String strMeasure6, String strMeasure7, String strMeasure8, String strMeasure9, String strMeasure10, String strMeasure11, String strMeasure12, String strMeasure13, String strMeasure14, String strMeasure15, String strMeasure16, String strMeasure17, String strMeasure18, String strMeasure19, String strMeasure20, String strSource, String dateModified) {
        this.idMeal = idMeal;
        this.strMeal = strMeal;
        this.strDrinkAlternate = strDrinkAlternate;
        this.strCategory = strCategory;
        this.strArea = strArea;
        this.strInstructions = strInstructions;
        this.strMealThumb = strMealThumb;
        this.strTags = strTags;
        this.strYoutube = strYoutube;
        this.strIngredient1 = strIngredient1;
        this.strIngredient2 = strIngredient2;
        this.strIngredient3 = strIngredient3;
        this.strIngredient4 = strIngredient4;
        this.strIngredient5 = strIngredient5;
        this.strIngredient6 = strIngredient6;
        this.strIngredient7 = strIngredient7;
        this.strIngredient8 = strIngredient8;
        this.strIngredient9 = strIngredient9;
        this.strIngredient10 = strIngredient10;
        this.strIngredient11 = strIngredient11;
        this.strIngredient12 = strIngredient12;
        this.strIngredient13 = strIngredient13;
        this.strIngredient14 = strIngredient14;
        this.strIngredient15 = strIngredient15;
        this.strIngredient16 = strIngredient16;
        this.strIngredient17 = strIngredient17;
        this.strIngredient18 = strIngredient18;
        this.strIngredient19 = strIngredient19;
        this.strIngredient20 = strIngredient20;
        this.strMeasure1 = strMeasure1;
        this.strMeasure2 = strMeasure2;
        this.strMeasure3 = strMeasure3;
        this.strMeasure4 = strMeasure4;
        this.strMeasure5 = strMeasure5;
        this.strMeasure6 = strMeasure6;
        this.strMeasure7 = strMeasure7;
        this.strMeasure8 = strMeasure8;
        this.strMeasure9 = strMeasure9;
        this.strMeasure10 = strMeasure10;
        this.strMeasure11 = strMeasure11;
        this.strMeasure12 = strMeasure12;
        this.strMeasure13 = strMeasure13;
        this.strMeasure14 = strMeasure14;
        this.strMeasure15 = strMeasure15;
        this.strMeasure16 = strMeasure16;
        this.strMeasure17 = strMeasure17;
        this.strMeasure18 = strMeasure18;
        this.strMeasure19 = strMeasure19;
        this.strMeasure20 = strMeasure20;
        this.strSource = strSource;
        this.dateModified = dateModified;
    }

    @Ignore
    public MealModel(String idMeal, String strMeal, String strDrinkAlternate, String strCategory, String strArea, String strInstructions, String strMealThumb, String strTags, String strYoutube, String strIngredient1, String strIngredient2, String strIngredient3, String strIngredient4, String strIngredient5, String strIngredient6, String strIngredient7, String strIngredient8, String strIngredient9, String strIngredient10, String strIngredient11, String strIngredient12, String strIngredient13, String strIngredient14, String strIngredient15, String strIngredient16, String strIngredient17, String strIngredient18, String strIngredient19, String strIngredient20, String strMeasure1, String strMeasure2, String strMeasure3, String strMeasure4, String strMeasure5, String strMeasure6, String strMeasure7, String strMeasure8, String strMeasure9, String strMeasure10, String strMeasure11, String strMeasure12, String strMeasure13, String strMeasure14, String strMeasure15, String strMeasure16, String strMeasure17, String strMeasure18, String strMeasure19, String strMeasure20, String strSource, String dateModified, boolean isFavorite) {
        this.idMeal = idMeal;
        this.strMeal = strMeal;
        this.strDrinkAlternate = strDrinkAlternate;
        this.strCategory = strCategory;
        this.strArea = strArea;
        this.strInstructions = strInstructions;
        this.strMealThumb = strMealThumb;
        this.strTags = strTags;
        this.strYoutube = strYoutube;
        this.strIngredient1 = strIngredient1;
        this.strIngredient2 = strIngredient2;
        this.strIngredient3 = strIngredient3;
        this.strIngredient4 = strIngredient4;
        this.strIngredient5 = strIngredient5;
        this.strIngredient6 = strIngredient6;
        this.strIngredient7 = strIngredient7;
        this.strIngredient8 = strIngredient8;
        this.strIngredient9 = strIngredient9;
        this.strIngredient10 = strIngredient10;
        this.strIngredient11 = strIngredient11;
        this.strIngredient12 = strIngredient12;
        this.strIngredient13 = strIngredient13;
        this.strIngredient14 = strIngredient14;
        this.strIngredient15 = strIngredient15;
        this.strIngredient16 = strIngredient16;
        this.strIngredient17 = strIngredient17;
        this.strIngredient18 = strIngredient18;
        this.strIngredient19 = strIngredient19;
        this.strIngredient20 = strIngredient20;
        this.strMeasure1 = strMeasure1;
        this.strMeasure2 = strMeasure2;
        this.strMeasure3 = strMeasure3;
        this.strMeasure4 = strMeasure4;
        this.strMeasure5 = strMeasure5;
        this.strMeasure6 = strMeasure6;
        this.strMeasure7 = strMeasure7;
        this.strMeasure8 = strMeasure8;
        this.strMeasure9 = strMeasure9;
        this.strMeasure10 = strMeasure10;
        this.strMeasure11 = strMeasure11;
        this.strMeasure12 = strMeasure12;
        this.strMeasure13 = strMeasure13;
        this.strMeasure14 = strMeasure14;
        this.strMeasure15 = strMeasure15;
        this.strMeasure16 = strMeasure16;
        this.strMeasure17 = strMeasure17;
        this.strMeasure18 = strMeasure18;
        this.strMeasure19 = strMeasure19;
        this.strMeasure20 = strMeasure20;
        this.strSource = strSource;
        this.dateModified = dateModified;
        this.isFavorite = isFavorite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public void setViewHolderIndex(int viewHolderIndex) {
        this.viewHolderIndex = viewHolderIndex;
    }

    public String getIdMeal() {
        return idMeal;
    }

    public String getStrMeal() {
        return strMeal;
    }

    public String getStrDrinkAlternate() {
        return strDrinkAlternate;
    }

    public String getStrCategory() {
        return strCategory;
    }

    public String getStrArea() {
        return strArea;
    }

    public String getStrInstructions() {
        return strInstructions;
    }

    public String getStrMealThumb() {
        return strMealThumb;
    }

    public String getStrTags() {
        return strTags;
    }

    public String getStrYoutube() {
        return strYoutube;
    }

    public String getStrIngredient1() {
        return strIngredient1;
    }

    public String getStrIngredient2() {
        return strIngredient2;
    }

    public String getStrIngredient3() {
        return strIngredient3;
    }

    public String getStrIngredient4() {
        return strIngredient4;
    }

    public String getStrIngredient5() {
        return strIngredient5;
    }

    public String getStrIngredient6() {
        return strIngredient6;
    }

    public String getStrIngredient7() {
        return strIngredient7;
    }

    public String getStrIngredient8() {
        return strIngredient8;
    }

    public String getStrIngredient9() {
        return strIngredient9;
    }

    public String getStrIngredient10() {
        return strIngredient10;
    }

    public String getStrIngredient11() {
        return strIngredient11;
    }

    public String getStrIngredient12() {
        return strIngredient12;
    }

    public String getStrIngredient13() {
        return strIngredient13;
    }

    public String getStrIngredient14() {
        return strIngredient14;
    }

    public String getStrIngredient15() {
        return strIngredient15;
    }

    public String getStrIngredient16() {
        return strIngredient16;
    }

    public String getStrIngredient17() {
        return strIngredient17;
    }

    public String getStrIngredient18() {
        return strIngredient18;
    }

    public String getStrIngredient19() {
        return strIngredient19;
    }

    public String getStrIngredient20() {
        return strIngredient20;
    }

    public String getStrMeasure1() {
        return strMeasure1;
    }

    public String getStrMeasure2() {
        return strMeasure2;
    }

    public String getStrMeasure3() {
        return strMeasure3;
    }

    public String getStrMeasure4() {
        return strMeasure4;
    }

    public String getStrMeasure5() {
        return strMeasure5;
    }

    public String getStrMeasure6() {
        return strMeasure6;
    }

    public String getStrMeasure7() {
        return strMeasure7;
    }

    public String getStrMeasure8() {
        return strMeasure8;
    }

    public String getStrMeasure9() {
        return strMeasure9;
    }

    public String getStrMeasure10() {
        return strMeasure10;
    }

    public String getStrMeasure11() {
        return strMeasure11;
    }

    public String getStrMeasure12() {
        return strMeasure12;
    }

    public String getStrMeasure13() {
        return strMeasure13;
    }

    public String getStrMeasure14() {
        return strMeasure14;
    }

    public String getStrMeasure15() {
        return strMeasure15;
    }

    public String getStrMeasure16() {
        return strMeasure16;
    }

    public String getStrMeasure17() {
        return strMeasure17;
    }

    public String getStrMeasure18() {
        return strMeasure18;
    }

    public String getStrMeasure19() {
        return strMeasure19;
    }

    public String getStrMeasure20() {
        return strMeasure20;
    }

    public String getStrSource() {
        return strSource;
    }

    public String getDateModified() {
        return dateModified;
    }

    public int getViewHolderIndex() {
        return viewHolderIndex;
    }

    public static boolean checkIfMeasurementIsEmpty(String measurement){
        return measurement == null || measurement.equals("") || measurement.equals(" ");
    }

    public static boolean checkIfIngredientIsEmpty(String ingredient){
        return ingredient == null || ingredient.equals("") || ingredient.equals(" ");
    }

    public String doMeasurementAndIngredients() {
        return (this.checkIfMeasurementIsEmpty(this.getStrMeasure1()) ? "" : this.getStrMeasure1() + " of ") + (this.checkIfIngredientIsEmpty(this.getStrIngredient1()) ? "" : this.getStrIngredient1() + "\n") +
                (this.checkIfMeasurementIsEmpty(this.getStrMeasure2()) ? "" : this.getStrMeasure2() + " of ") + (this.checkIfIngredientIsEmpty(this.getStrIngredient2()) ? "" : this.getStrIngredient2() + "\n") +
                (this.checkIfMeasurementIsEmpty(this.getStrMeasure3()) ? "" : this.getStrMeasure3() + " of ") + (this.checkIfIngredientIsEmpty(this.getStrIngredient3()) ? "" : this.getStrIngredient3() + "\n") +
                (this.checkIfMeasurementIsEmpty(this.getStrMeasure4()) ? "" : this.getStrMeasure4() + " of ") + (this.checkIfIngredientIsEmpty(this.getStrIngredient4()) ? "" : this.getStrIngredient4() + "\n") +
                (this.checkIfMeasurementIsEmpty(this.getStrMeasure5()) ? "" : this.getStrMeasure5() + " of ") + (this.checkIfIngredientIsEmpty(this.getStrIngredient5()) ? "" : this.getStrIngredient5() + "\n") +
                (this.checkIfMeasurementIsEmpty(this.getStrMeasure6()) ? "" : this.getStrMeasure6() + " of ") + (this.checkIfIngredientIsEmpty(this.getStrIngredient6()) ? "" : this.getStrIngredient6() + "\n") +
                (this.checkIfMeasurementIsEmpty(this.getStrMeasure7()) ? "" : this.getStrMeasure7() + " of ") + (this.checkIfIngredientIsEmpty(this.getStrIngredient7()) ? "" : this.getStrIngredient7() + "\n") +
                (this.checkIfMeasurementIsEmpty(this.getStrMeasure8()) ? "" : this.getStrMeasure8() + " of ") + (this.checkIfIngredientIsEmpty(this.getStrIngredient8()) ? "" : this.getStrIngredient8() + "\n") +
                (this.checkIfMeasurementIsEmpty(this.getStrMeasure9()) ? "" : this.getStrMeasure9() + " of ") + (this.checkIfIngredientIsEmpty(this.getStrIngredient9()) ? "" : this.getStrIngredient9() + "\n") +
                (this.checkIfMeasurementIsEmpty(this.getStrMeasure10()) ? "" : this.getStrMeasure10() + " of ") + (this.checkIfIngredientIsEmpty(this.getStrIngredient10()) ? "" : this.getStrIngredient10() + "\n") +
                (this.checkIfMeasurementIsEmpty(this.getStrMeasure11()) ? "" : this.getStrMeasure11() + " of ") + (this.checkIfIngredientIsEmpty(this.getStrIngredient11()) ? "" : this.getStrIngredient11() + "\n") +
                (this.checkIfMeasurementIsEmpty(this.getStrMeasure12()) ? "" : this.getStrMeasure12() + " of ") + (this.checkIfIngredientIsEmpty(this.getStrIngredient12()) ? "" : this.getStrIngredient12() + "\n") +
                (this.checkIfMeasurementIsEmpty(this.getStrMeasure13()) ? "" : this.getStrMeasure13() + " of ") + (this.checkIfIngredientIsEmpty(this.getStrIngredient13()) ? "" : this.getStrIngredient13() + "\n") +
                (this.checkIfMeasurementIsEmpty(this.getStrMeasure14()) ? "" : this.getStrMeasure14() + " of ") + (this.checkIfIngredientIsEmpty(this.getStrIngredient14()) ? "" : this.getStrIngredient14() + "\n") +
                (this.checkIfMeasurementIsEmpty(this.getStrMeasure15()) ? "" : this.getStrMeasure15() + " of ") + (this.checkIfIngredientIsEmpty(this.getStrIngredient15()) ? "" : this.getStrIngredient15() + "\n") +
                (this.checkIfMeasurementIsEmpty(this.getStrMeasure16()) ? "" : this.getStrMeasure16() + " of ") + (this.checkIfIngredientIsEmpty(this.getStrIngredient16()) ? "" : this.getStrIngredient16() + "\n") +
                (this.checkIfMeasurementIsEmpty(this.getStrMeasure17()) ? "" : this.getStrMeasure17() + " of ") + (this.checkIfIngredientIsEmpty(this.getStrIngredient17()) ? "" : this.getStrIngredient17() + "\n") +
                (this.checkIfMeasurementIsEmpty(this.getStrMeasure18()) ? "" : this.getStrMeasure18() + " of ") + (this.checkIfIngredientIsEmpty(this.getStrIngredient18()) ? "" : this.getStrIngredient18() + "\n") +
                (this.checkIfMeasurementIsEmpty(this.getStrMeasure19()) ? "" : this.getStrMeasure19() + " of ") + (this.checkIfIngredientIsEmpty(this.getStrIngredient19()) ? "" : this.getStrIngredient19() + "\n") +
                (this.checkIfMeasurementIsEmpty(this.getStrMeasure20()) ? "" : this.getStrMeasure20() + " of ") + (this.checkIfIngredientIsEmpty(this.getStrIngredient20()) ? "" : this.getStrIngredient20() + "\n");
    }

    public Integer returnIconForFavorite() {
        if (isFavorite) {
            return R.drawable.ic_favorite_red;
        } else if (!isFavorite) {
            return R.drawable.ic_favorite_filled;
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MealModel)) {
            Log.e("equals", "equals");
            return false;
        }

        MealModel mealModel = (MealModel) o;
        Log.e("equals", "not equals");
        return idMeal.equals(mealModel.idMeal);
    }

    @Override
    public int hashCode() {
        return Integer.parseInt(idMeal);
    }
}
