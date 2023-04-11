package com.app.nutritionalsupplements.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.nutritionalsupplements.R;
import com.app.nutritionalsupplements.models.Product;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> implements Filterable {
    private boolean mUserIsLoggedIn;
    private final ArrayList<Product> mProductsData;
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
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_from_bottom_animation);
            holder.itemView.startAnimation(animation);
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
            // TODO
            return null;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            // TODO
        }
    };

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView mProductImage;
        private final TextView mTitleTextView;
        private final RatingBar mRatingBar;
        private final TextView mPrice;
        private final RelativeLayout mSecondRelativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mProductImage = itemView.findViewById(R.id.product_image);
            mTitleTextView = itemView.findViewById(R.id.product_name);
            mRatingBar = itemView.findViewById(R.id.rating_bar);
            mPrice = itemView.findViewById(R.id.price_text);
            itemView.findViewById(R.id.add_to_cart_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("Activity", "Item added to cart.");
                }
            });
            mSecondRelativeLayout = itemView.findViewById(R.id.add_to_cart_relative_layout);
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

            mSecondRelativeLayout.setVisibility(isUserLoggedIn ? View.VISIBLE : View.GONE);
        }
    }
}
