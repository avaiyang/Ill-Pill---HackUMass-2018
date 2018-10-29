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

public class HealthAdapter extends RecyclerView.Adapter<HealthAdapter.HealthHolder> {

    private Context context;
    private ArrayList<String> healthList;

    public HealthAdapter(Context context, ArrayList<String> healthList) {
        this.context = context;
        this.healthList = healthList;
    }

    @NonNull
    @Override
    public HealthHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.custom_recipe_details,parent,false);
        HealthHolder healthHolder = new HealthHolder(v);

        return healthHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HealthHolder holder, int position) {
        holder.tvRecipeDetail.setText(healthList.get(position));
    }

    @Override
    public int getItemCount() {
        return healthList.size();
    }

    class HealthHolder extends RecyclerView.ViewHolder{
        private TextView tvRecipeDetail;
        public HealthHolder(View itemView) {
            super(itemView);
            tvRecipeDetail = itemView.findViewById(R.id.tvRecipeDetails);
        }
    }
}
