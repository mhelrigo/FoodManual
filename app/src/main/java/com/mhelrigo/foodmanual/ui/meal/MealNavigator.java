package com.mhelrigo.foodmanual.ui.meal;

import static com.mhelrigo.foodmanual.ui.commons.exception.NotAllowedToNavigateException.checkIfAllowedToNavigate;

import android.os.Bundle;

import androidx.navigation.NavController;

import com.mhelrigo.foodmanual.ui.commons.exception.NotAllowedToNavigateException;

import javax.annotation.Nullable;

import timber.log.Timber;

/**
 * Not all fragments needs to navigate to @MealDetailFragment,
 * only the ones implementing this interface will.
 */
public interface MealNavigator {
    default void navigateToMealDetail(int action, @Nullable Bundle bundle, NavController navController, boolean isTablet) {
        try {
            checkIfAllowedToNavigate(isTablet);
        } catch (NotAllowedToNavigateException e) {
            Timber.e("You're not allowed to navigate when device is tablet");
            return;
        }

        if (bundle == null) {
            navController.navigate(action);
        } else {
            navController.navigate(action, bundle);
        }
    }
}
