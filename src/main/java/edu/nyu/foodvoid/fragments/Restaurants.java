package edu.nyu.foodvoid.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import edu.nyu.foodvoid.FoodDetail;
import edu.nyu.foodvoid.HomeScreen;
import edu.nyu.foodvoid.R;
import edu.nyu.foodvoid.adapters.RestaurantAdapter;
import edu.nyu.foodvoid.database.RestaurantDatabase;
import edu.nyu.foodvoid.pojo.RestaurantModel;
import edu.nyu.foodvoid.utilities.CameraUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class Restaurants extends Fragment {

    private RecyclerView rvRestaurantList;
    private ImageView ivFoodImage;
    private RestaurantAdapter mAdapter;
    private String[] restaurants = new String[]{"Mc Donalds","Burger King","Popeye","Starbucks","Chipotle"};
    private LinearLayoutManager linearLayoutManager;

    ArrayList<RestaurantModel> restaurantModels;


    public Restaurants() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = LayoutInflater.from(getContext()).inflate(R.layout.fragment_restaurants,container,false);

        rvRestaurantList = v.findViewById(R.id.rvRestaurantList);
       // ivFoodImage = v.findViewById(R.id.ivFoodImage);

//        Bitmap bitmap = CameraUtils.optimizeBitmap(8,imagePath);
//        ivFoodImage.setImageBitmap(bitmap);

        SharedPreferences preferences =  getContext().getSharedPreferences(HomeScreen.PREF_NAME,Context.MODE_PRIVATE);
        String foodName = preferences.getString(HomeScreen.FOOD_NAME,"none");
        foodName = foodName.substring(0,1).toUpperCase() + foodName.substring(1);

        RestaurantDatabase restaurantDatabase = new RestaurantDatabase(getContext());
        restaurantDatabase.open();
        restaurantModels = restaurantDatabase.getRestaurantData(foodName);
        Toast.makeText(getContext(),"Size: " + restaurantModels.size(),Toast.LENGTH_SHORT).show();
        restaurantDatabase.close();


        rvRestaurantList.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext());
        rvRestaurantList.setLayoutManager(linearLayoutManager);
        mAdapter = new RestaurantAdapter(getContext(),restaurantModels);
        rvRestaurantList.setAdapter(mAdapter);

        return v;
    }

}
