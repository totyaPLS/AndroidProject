package com.app.nutritionalsupplements.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.nutritionalsupplements.R;
import com.app.nutritionalsupplements.models.Product;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private ArrayList<Product> productsData;
    private ArrayList<Product> productsDataAll;
    private Context context;
    private int lastPosition = -1;

    public ProductAdapter(Context context, ArrayList<Product> products) {
        this.productsData = products;
        this.productsDataAll = products;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_main, parent, false));
    }

    @Override
    public void onBindViewHolder(ProductAdapter.ViewHolder holder, int position) {
        Product currentProduct = productsData.get(position);

        holder.bindTo(currentProduct);
    }

    @Override
    public int getItemCount() {
        return productsData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bindTo(Product currentProduct) {

        }
    }
}
