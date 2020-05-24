package com.taffyosales.taffyosales.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.taffyosales.taffyosales.R;
import com.taffyosales.taffyosales.model.Product;

import java.util.List;


public class ShowProductAdapter extends RecyclerView.Adapter<ShowProductAdapter.ProductHolder> {


    private List<Product> productList;
    Context context;

    public ShowProductAdapter(List<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_product, parent, false);
        return new ProductHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        Product product=productList.get(position);
        Glide.with(context).load(product.getImageUri()).into(holder.productImage);
        holder.productName.setText(product.getProduc_name());
        holder.quantity.setText(product.getQuantity());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductHolder extends RecyclerView.ViewHolder {

        ImageView productImage;
        TextView productName;
        TextView quantity;

        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            productName = itemView.findViewById(R.id.product_name);
            quantity = itemView.findViewById(R.id.quantity);
        }
    }
}
