package edu.nyu.foodvoid.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import edu.nyu.foodvoid.FoodDetail;
import edu.nyu.foodvoid.R;
import edu.nyu.foodvoid.pojo.RestaurantModel;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.AdapterHolder> {

        private Context context;
        private ArrayList<RestaurantModel> restaurantModels;


    public RestaurantAdapter(Context context, ArrayList<RestaurantModel> restaurantModels) {
        this.context = context;
        this.restaurantModels = restaurantModels;
    }

    @NonNull
    @Override
    public AdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_restaurant,parent,false);
        AdapterHolder adapterHolder = new AdapterHolder(view);
        return adapterHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHolder holder, int position) {
            RestaurantModel restaurantModel = restaurantModels.get(position);

            holder.tvRestaurantName.setText(restaurantModel.getrName());
    }

    @Override
    public int getItemCount() {
        return restaurantModels.size();
    }

    class AdapterHolder extends RecyclerView.ViewHolder{

        private TextView tvRestaurantName;

        public AdapterHolder(View itemView) {
            super(itemView);
            tvRestaurantName = itemView.findViewById(R.id.tvRestaurantName);
        }
    }
}
