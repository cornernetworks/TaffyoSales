package com.taffyosales.taffyosales.viewmodel;

import android.net.Uri;

import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.FieldValue;
import com.taffyosales.taffyosales.model.Product;
import com.taffyosales.taffyosales.model.Store;
import com.taffyosales.taffyosales.repo.ProductRepository;

public class ProductViewModel extends ViewModel {
    private ProductRepository productRepository =new ProductRepository();
//
    public void add_Product(String uid, Product product,String store_id) {
        productRepository.addProduct(uid,product,store_id);

    }
}
