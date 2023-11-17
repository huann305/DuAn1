package com.example.duan1.user.fragment.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.R;
import com.example.duan1.model.Product;

import java.util.List;

public abstract class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {

    List<Product> list;

    public HomeAdapter(List<Product> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_page, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        Product product = list.get(position);

        if (product.getImage() != null) {
//            holder.imgProduct.setImageBitmap(product.getImage());
        }
        holder.tvNameProduct.setText(product.getName());
        holder.tvPrice.setText(product.getPrice() + " VND");
        holder.tvQuantitySold.setText(product.getQuantitySold() + " đã bán");

        holder.layout.setOnClickListener(v -> onItemClick(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public abstract void onItemClick(int position);

    public class HomeViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView tvNameProduct;
        TextView tvPrice;
        TextView tvQuantitySold;

        View layout;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imageView);
            tvNameProduct = itemView.findViewById(R.id.textView3);
            tvPrice = itemView.findViewById(R.id.textView5);
            tvQuantitySold = itemView.findViewById(R.id.textView7);
            layout = itemView.findViewById(R.id.layout_item);
        }
    }
}
