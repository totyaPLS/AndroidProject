package com.app.nutritionalsupplements.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.nutritionalsupplements.R;
import com.app.nutritionalsupplements.activities.MainActivity;
import com.app.nutritionalsupplements.models.Product;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import java.util.ArrayList;
import java.util.Locale;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> implements Filterable {
    private final boolean mUserIsLoggedIn;
    private ArrayList<Product> mProductsData;
    private ArrayList<Product> mProductsDataAll;
    private final Context mContext;
    private int mLastPosition = -1;

    public ProductAdapter(Context context, ArrayList<Product> products, boolean userIsLoggedIn) {
        this.mProductsData = products;
        this.mProductsDataAll = products;
        this.mContext = context;
        this.mUserIsLoggedIn = userIsLoggedIn;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.product_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ProductAdapter.ViewHolder holder, int position) {
        Product currentProduct = mProductsData.get(position);
        holder.bindTo(currentProduct, mUserIsLoggedIn);

        if (holder.getBindingAdapterPosition() > mLastPosition) {
            Animation slideAnimation = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_from_bottom_animation);
            Animation fadeAnimation = AnimationUtils.loadAnimation(mContext, R.anim.fade_animation);

            AnimationSet animationSet = new AnimationSet(true);
            animationSet.addAnimation(slideAnimation);
            animationSet.addAnimation(fadeAnimation);

            holder.itemView.startAnimation(animationSet);
            mLastPosition = holder.getBindingAdapterPosition();
        }
    }

    @Override
    public int getItemCount() {
        return mProductsData.size();
    }

    @Override
    public Filter getFilter() {
        return shoppingFilter;
    }

    private final Filter shoppingFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<Product> filteredList = new ArrayList<>();
            FilterResults results = new FilterResults();

            if (charSequence == null || charSequence.length() == 0) {
                results.count = mProductsDataAll.size();
                results.values = mProductsDataAll;
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (Product product : mProductsDataAll) {
                    if (product.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(product);
                    }
                }

                results.count = filteredList.size();
                results.values = filteredList;
            }

            return results;
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mProductsData = (ArrayList) filterResults.values;
            notifyDataSetChanged();
        }
    };

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView mProductImage;
        private final TextView mTitleTextView;
        private final RatingBar mRatingBar;
        private final TextView mPrice;
        private final LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mProductImage = itemView.findViewById(R.id.product_image);
            mTitleTextView = itemView.findViewById(R.id.product_name);
            mRatingBar = itemView.findViewById(R.id.rating_bar);
            mPrice = itemView.findViewById(R.id.price_text);
            linearLayout = itemView.findViewById(R.id.cart_linear_layout);
        }


        public void bindTo(Product currentProduct, boolean isUserLoggedIn) {
            mProductImage.setImageResource(currentProduct.getImage());
            mTitleTextView.setText(currentProduct.getName());
            mRatingBar.setRating(currentProduct.getRateValue());
            mPrice.setText(currentProduct.getPrice());

            Glide.with(mContext)
                    .load(currentProduct.getImage())
                    .transform(new RoundedCorners(50))
                    .into(mProductImage);

            linearLayout.setVisibility(isUserLoggedIn ? View.VISIBLE : View.GONE);

            if (isUserLoggedIn) {
                itemView.findViewById(R.id.add_to_cart_button).setOnClickListener(view ->
                        ((MainActivity) mContext).updateProduct(currentProduct)
                );

                itemView.findViewById(R.id.remove_from_cart_button).setOnClickListener(view ->
                        ((MainActivity) mContext).deleteProduct(currentProduct)
                );
            }
        }
    }
}
