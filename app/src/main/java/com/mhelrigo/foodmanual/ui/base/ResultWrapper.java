package com.mhelrigo.foodmanual.ui.base;

/**
 * By wrapping any class with these class, we are making it easy to handle the showing of Loading and Error.
 * */
public final class ResultWrapper<T> {
    private final T result;
    private final Throwable throwable;
    private final ViewState viewState;

    private ResultWrapper(T result, Throwable throwable, ViewState viewState) {
        this.result = result;
        this.throwable = throwable;
        this.viewState = viewState;
    }

    public static ResultWrapper loading() {
        return new ResultWrapper(null, null, ViewState.LOADING);
    }

    public static <T> ResultWrapper<T> success(T result) {
        return new ResultWrapper(result, null, ViewState.SUCCESS);
    }

    public static <T> ResultWrapper<T> error(Throwable error) {
        return new ResultWrapper(null, error, ViewState.ERROR);
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
}
