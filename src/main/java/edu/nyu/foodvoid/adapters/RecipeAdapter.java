package edu.nyu.foodvoid.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import edu.nyu.foodvoid.R;
import edu.nyu.foodvoid.RecipeDetails;
import edu.nyu.foodvoid.pojo.RecipeModel;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeHolder> {

    private Context context;
    private ArrayList<RecipeModel> recipeModels;

    public RecipeAdapter(Context context, ArrayList<RecipeModel> recipeModels) {
        this.context = context;
        this.recipeModels = recipeModels;
    }

    @NonNull
    @Override
    public RecipeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.card_recipes,parent,false);
        RecipeHolder recipeHolder = new RecipeHolder(v);
        return recipeHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeHolder holder, int position) {

        final RecipeModel recipeModel = recipeModels.get(position);

        holder.tvRecipeLabel.setText(recipeModel.getRecipeName());

        final ArrayList<String> dietlabelList,healthLabelList,ingredientList;

        String dietLabels = recipeModel.getDietLabel().replace("[","").replace("]","").replace("\"","");
        if (!dietLabels.contains(",")){
            dietlabelList = new ArrayList<>();
            if (dietLabels.isEmpty()){

                dietlabelList.add("Diet Not Available");
            }

            dietlabelList.add(dietLabels);
        }else {
            dietlabelList = new ArrayList<>(Arrays.asList(dietLabels.split(",")));
        }

        String healthLabels = recipeModel.getHealthLabel().replace("[","").replace("]","").replace("\"","");


        if (!healthLabels.contains(",")){
            healthLabelList = new ArrayList<>();
            healthLabelList.add(healthLabels);
        }else {
            healthLabelList = new ArrayList<>(Arrays.asList(healthLabels.split(",")));
            Log.i("HEALTH_LABELS_SIZE",String.valueOf(healthLabelList.size()));
        }

        String ingredients = recipeModel.getIngredients().replace("[","").replace("]","").replace("\"","");


        if (!ingredients.contains(",")){
            ingredientList = new ArrayList<>();
            ingredientList.add(ingredients);
        }else {
            ingredientList = new ArrayList<>(Arrays.asList(ingredients.split(",")));
            Log.i("IngredientsSIZE",String.valueOf(ingredientList.size()));
        }

        holder.ibRecipeDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context,RecipeDetails.class);
                intent.putStringArrayListExtra("DIETLIST",dietlabelList);
                intent.putStringArrayListExtra("HEALTHLIST",healthLabelList);
                intent.putStringArrayListExtra("INGREDIENTS",ingredientList);
                intent.putExtra("SHAREURL",recipeModel.getShareUrl());
                context.startActivity(intent);
            }
        });





    }

    @Override
    public int getItemCount() {
        return recipeModels.size();
    }

    class RecipeHolder extends RecyclerView.ViewHolder{

        private TextView tvRecipeLabel;
        private ImageButton ibRecipeDetails;

        public RecipeHolder(View itemView) {
            super(itemView);
            tvRecipeLabel = itemView.findViewById(R.id.tvRecipeName);
            ibRecipeDetails = itemView.findViewById(R.id.ibRecipeDetail);
        }
    }
}
