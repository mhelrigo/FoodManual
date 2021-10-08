package com.mhelrigo.foodmanual.ui.commons.base;

/**
 * By wrapping any class with these class, we are making it easy to handle the showing of Loading and Error.
 */
public final class ViewStateWrapper<T> {
    private final T result;
    private final Throwable throwable;
    private final ViewState viewState;

    private ViewStateWrapper(T result, Throwable throwable, ViewState viewState) {
        this.result = result;
        this.throwable = throwable;
        this.viewState = viewState;
    }

    public static ViewStateWrapper init() {
        return new ViewStateWrapper(null, null, ViewState.INIT);
    }

    public static ViewStateWrapper loading() {
        return new ViewStateWrapper(null, null, ViewState.LOADING);
    }

    public static <T> ViewStateWrapper<T> success(T result) {
        return new ViewStateWrapper(result, null, ViewState.SUCCESS);
    }

    public static <T> ViewStateWrapper<T> error(Throwable error) {
        return new ViewStateWrapper(null, error, ViewState.ERROR);
    }

    public T getResult() {
        return result;
    }

    public Throwable getThrowable() {
        return throwable;
    }


    public ViewState getViewState() {
        return viewState;
    }

    public Boolean noResultYet() {
        return getViewState().equals(ViewState.LOADING) ||
                getViewState().equals(ViewState.ERROR) ||
                getViewState().equals(ViewState.INIT);
    }
}
