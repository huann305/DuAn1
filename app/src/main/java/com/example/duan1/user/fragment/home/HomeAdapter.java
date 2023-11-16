package com.example.duan1.user.fragment.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.R;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_page, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder {

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
