package edu.nyu.foodvoid.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import edu.nyu.foodvoid.R;
import edu.nyu.foodvoid.RecipeDetails;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsHolder> {

    private Context context;
    private ArrayList<String> ingredients;

    public IngredientsAdapter(Context context, ArrayList<String> ingredients) {
        this.context = context;
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public IngredientsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.custom_recipe_details,parent,false);
        IngredientsHolder ingredientsHolder = new IngredientsHolder(v);
        return ingredientsHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsHolder holder, int position) {

        holder.tvRecipeDetail.setText(ingredients.get(position));

    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    class IngredientsHolder extends RecyclerView.ViewHolder{
        private TextView tvRecipeDetail;
        public IngredientsHolder(View itemView) {
            super(itemView);
            tvRecipeDetail = itemView.findViewById(R.id.tvRecipeDetails);
        }
    }
}
