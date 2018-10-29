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

public class DietAdapter extends RecyclerView.Adapter<DietAdapter.DietHolder> {

    private Context context;
    private ArrayList<String> dietList;
    public DietAdapter(Context context, ArrayList<String> dietList) {

        this.context = context;
        this.dietList = dietList;
    }

    @NonNull
    @Override
    public DietHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.custom_recipe_details,parent,false);
        DietHolder dietHolder = new DietHolder(v);
        return dietHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DietHolder holder, int position) {
        holder.tvRecipeDetail.setText(dietList.get(position));
    }

    @Override
    public int getItemCount() {
        return dietList.size();
    }

    class DietHolder extends RecyclerView.ViewHolder{
        private TextView tvRecipeDetail;
        public DietHolder(View itemView) {
            super(itemView);
            tvRecipeDetail = itemView.findViewById(R.id.tvRecipeDetails);
        }
    }
}
