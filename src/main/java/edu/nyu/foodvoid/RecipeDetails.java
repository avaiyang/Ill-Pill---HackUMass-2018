package edu.nyu.foodvoid;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import edu.nyu.foodvoid.adapters.DietAdapter;
import edu.nyu.foodvoid.adapters.HealthAdapter;
import edu.nyu.foodvoid.adapters.IngredientsAdapter;

public class RecipeDetails extends AppCompatActivity {

    private RecyclerView rvDietLabels,rvHealthLabels,rvIngredients;
    private LinearLayoutManager layoutDiet,layoutHealth,layoutIngredients;
    private DietAdapter dietAdapter;
    private HealthAdapter healthAdapter;
    private IngredientsAdapter ingredientsAdapter;

    private Button btGotoRecipe;
    private ArrayList<String> dietList,healthList,ingredients;
    private String shareUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent receiver = getIntent();
        dietList = receiver.getStringArrayListExtra("DIETLIST");
        healthList = receiver.getStringArrayListExtra("HEALTHLIST");
        ingredients = receiver.getStringArrayListExtra("INGREDIENTS");
        shareUrl = receiver.getStringExtra("SHAREURL");

        rvDietLabels = findViewById(R.id.rvDietLabels);
        rvHealthLabels  =findViewById(R.id.rvHealthLabels);
        rvIngredients = findViewById(R.id.rvIngredients);

        btGotoRecipe = findViewById(R.id.btGoToRecipe);




        btGotoRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(shareUrl));
                startActivity(i);
            }
        });


        rvDietLabels.setHasFixedSize(true);
        layoutDiet = new LinearLayoutManager(RecipeDetails.this);
        rvDietLabels.setLayoutManager(layoutDiet);
        dietAdapter = new DietAdapter(RecipeDetails.this,dietList);
        rvDietLabels.setAdapter(dietAdapter);

        rvHealthLabels.setHasFixedSize(true);
        layoutHealth = new LinearLayoutManager(RecipeDetails.this);
        rvHealthLabels.setLayoutManager(layoutHealth);
        healthAdapter = new HealthAdapter(RecipeDetails.this,healthList);
        rvHealthLabels.setAdapter(healthAdapter);

        rvIngredients.setHasFixedSize(true);
        layoutIngredients = new LinearLayoutManager(RecipeDetails.this);
        rvIngredients.setLayoutManager(layoutIngredients);
        ingredientsAdapter = new IngredientsAdapter(RecipeDetails.this,ingredients);
        rvIngredients.setAdapter(ingredientsAdapter);







    }

}
