package com.mhelrigo.foodmanual.ui.mealdetails;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.mhelrigo.foodmanual.R;
import com.mhelrigo.foodmanual.databinding.ItemTagBinding;

import java.util.ArrayList;
import java.util.List;

public class TagsRecyclerViewAdapter extends RecyclerView.Adapter<TagsRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "TagsRecyclerViewAdapter";

    private List<String> tags = new ArrayList<>();

    public TagsRecyclerViewAdapter() {
    }

    public void setData(List<String> tags){
        this.tags = tags;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TagsRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTagBinding binding =
                DataBindingUtil
                        .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_tag, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TagsRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.bind(tags.get(position));
    }

    @Override
    public int getItemCount() {
        return (tags.size() == 0) ? 0 : tags.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemTagBinding itemTagBinding;

        public ViewHolder(@NonNull ItemTagBinding itemTagBinding) {
            super(itemTagBinding.getRoot());
            this.itemTagBinding = itemTagBinding;
        }

        public void bind(String tagName){
            itemTagBinding.setTagName(tagName);
        }
    }
}
