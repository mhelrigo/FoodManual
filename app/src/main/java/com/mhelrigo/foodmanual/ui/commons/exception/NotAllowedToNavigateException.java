package com.mhelrigo.foodmanual.ui.commons.exception;

/**
 * This exception class is cheap enough to be used regularly
 * */
public class NotAllowedToNavigateException extends Throwable {
    public NotAllowedToNavigateException() {
        super("Not allowed to navigate", null, false, false);
    }

    public static void checkIfAllowedToNavigate(Boolean isTablet) throws NotAllowedToNavigateException {
        if (isTablet) {
            throw new NotAllowedToNavigateException();
        }

        return;
    }
}
