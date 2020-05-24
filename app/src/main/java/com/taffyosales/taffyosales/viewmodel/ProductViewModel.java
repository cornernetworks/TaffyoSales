package com.taffyosales.taffyosales.viewmodel;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.FieldValue;
import com.taffyosales.taffyosales.model.Product;
import com.taffyosales.taffyosales.model.Store;
import com.taffyosales.taffyosales.repo.ProductRepository;

import java.util.List;

public class ProductViewModel extends ViewModel {
    private ProductRepository productRepository =new ProductRepository();
    MutableLiveData<String> store_id=new MutableLiveData<>();


    //    private ProductRepository productRepository =new ProductRepository(new ProductRepository.OnTaskComplete() {
//        @Override
//        public void allProductList(List<Product> productList) {
//
//        }
//
//        @Override
//        public void onError(Exception e) {
//
//        }
//    });
//
    public void add_Product(String uid, Product product,String store_id,String imgUri) {
        productRepository.addProduct(uid,product,store_id,imgUri);
    }


    public void setStoerId(String store_id){
        this.store_id.setValue(store_id);
    }
    public LiveData<String> getStoreId(){
        return store_id;
    }
}
