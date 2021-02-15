package com.mhelrigo.foodmanual.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

public class Category implements Parcelable {
    private String idCategory;
    private String strCategory;
    private String strCategoryThumb;
    private String strCategoryDescription;

    protected Category(Parcel in) {
        idCategory = in.readString();
        strCategory = in.readString();
        strCategoryThumb = in.readString();
        strCategoryDescription = in.readString();
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

    public String getIdCategory() {
        return idCategory;
    }

    public String getStrCategory() {
        return strCategory;
    }

    public String getStrCategoryThumb() {
        return strCategoryThumb;
    }

    public String getStrCategoryDescription() {
        return strCategoryDescription;
    }

    // Setter Methods

    public void setIdCategory( String idCategory ) {
        this.idCategory = idCategory;
    }

    public void setStrCategory( String strCategory ) {
        this.strCategory = strCategory;
    }

    public void setStrCategoryThumb( String strCategoryThumb ) {
        this.strCategoryThumb = strCategoryThumb;
    }

    public void setStrCategoryDescription( String strCategoryDescription ) {
        this.strCategoryDescription = strCategoryDescription;
    }

    @BindingAdapter({"categoryImage"})
    public static void categoryImage(ImageView view, String imageUrl){
        Glide.with(view.getContext()).load(imageUrl).into(view);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idCategory);
        dest.writeString(strCategory);
        dest.writeString(strCategoryThumb);
        dest.writeString(strCategoryDescription);
    }


}
