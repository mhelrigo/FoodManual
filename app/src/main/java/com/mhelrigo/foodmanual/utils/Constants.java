package com.mhelrigo.foodmanual.utils;

import com.mhelrigo.foodmanual.BuildConfig;

public class Constants {
    public static final String PACKAGE_NAME = "com.mhelrigo.foodmanual.utils";
    public static final String BASE_URL = "https://www.themealdb.com/api/json/v2/" + BuildConfig.API_KEY + "/";

    public static final String RANDOM_MEALS_URL = "randomselection.php";
    public static final String CATEGORIES_URL = "categories.php";
    public static final String MEAL_DETAILS_URL = "lookup.php?i=";
    public static final String INGREDIENTS_URL = "list.php?i=list";
    public static final String LATEST_MEALS_URL = "latest.php";
    public static final String FILTERED_MEALS_URL = "filter.php";
    public static final String AREAS_URL = "list.php?a=list";
    public static final String SEARCH_MEALS_URL = "search.php?s=";

    public static final String ID = "id";
    public static final String CATEGORY_NAME = "categoryName";
    public static final String CATEGORY = "category";

    public static final String EMPTY = "";

    public static class HTTPResponse {
        public static final int RESPONSE_200 = 200;
        public static final int RESPONSE_400 = 400;
        public static final int RESPONSE_404 = 404;

        public static final int SOCKET_TIMEOUT = 10001;
        public static final int CONNECTION_ERROR = 10002;
        public static final int IO_ERROR = 10003;
    }

    public static class FireBaseAnalyticsEvent{
        public static final String MEAL_CATEGORY = "MEAL_CATEGORY_";
        public static final String MEAL = "MEAL_";
        public static final String INGREDIENT = "INGREDIENT_";

        public static final String MEAL_ADDED_TO_FAV = "MEAL_FAVORITE_";
        public static final String MEAL_REMOVED_FROM_FAV = "MEAL_NOT_FAVORITE_";
    }

    public static class View{
        public static final int VIEW_TYPE_ITEM = 0;
        public static final int VIEW_TYPE_LOADING = 1;
    }
}
