package com.mhelrigo.foodmanual.ui.base;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.databinding.BindingAdapter;

public class BindingAdapters {
    @BindingAdapter({"bind:successState"})
    public static void successState(View view, ViewState viewState) {
        if (viewState.equals(ViewState.LOADING))  {
            view.setVisibility(View.GONE);
        } else if (viewState.equals(ViewState.SUCCESS)) {
            view.setVisibility(View.VISIBLE);
        } else if (viewState.equals(ViewState.ERROR)) {
            view.setVisibility(View.GONE);
        }
    }

    @BindingAdapter({"bind:loadingState"})
    public static void loadingState(ImageView view, ViewState viewState) {
        if (viewState.equals(ViewState.LOADING)) {
            view.setVisibility(View.VISIBLE);
        } else if (viewState.equals(ViewState.SUCCESS)) {
            view.setVisibility(View.GONE);
        } else if (viewState.equals(ViewState.ERROR)) {
            view.setVisibility(View.GONE);
        }
    }

    @BindingAdapter({"bind:errorState"})
    public static void errorState(TextView view, ViewState viewState) {
        switch (viewState) {
            case SUCCESS: {
                view.setVisibility(View.GONE);
            }
            case ERROR: {
                view.setVisibility(View.VISIBLE);
            }
            case LOADING: {
                view.setVisibility(View.GONE);
            }
        }
    }
}
