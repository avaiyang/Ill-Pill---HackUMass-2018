package edu.nyu.foodvoid;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import edu.nyu.foodvoid.adapters.RestaurantAdapter;
import edu.nyu.foodvoid.fragments.TabFragment;
import edu.nyu.foodvoid.utilities.CameraUtils;

public class FoodDetail extends AppCompatActivity {

    android.support.v4.app.FragmentManager fragmentManager;
    android.support.v4.app.FragmentTransaction fragmentTransaction;

    private ImageView ivFoodImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences preferences = getSharedPreferences(HomeScreen.PREF_NAME,Context.MODE_PRIVATE);
        String imagePath = preferences.getString(HomeScreen.IMAGE_PATH,"none");



        ivFoodImage = findViewById(R.id.ivFoodImage);
        Bitmap bitmap = CameraUtils.optimizeBitmap(8,imagePath);
        ivFoodImage.setImageBitmap(bitmap);


        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.foodItemContainer,new TabFragment()).commit();






    }

}
