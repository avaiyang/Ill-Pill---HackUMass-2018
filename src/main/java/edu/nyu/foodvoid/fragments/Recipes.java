package edu.nyu.foodvoid.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import edu.nyu.foodvoid.HomeScreen;
import edu.nyu.foodvoid.R;
import edu.nyu.foodvoid.adapters.RecipeAdapter;
import edu.nyu.foodvoid.pojo.RecipeModel;
import edu.nyu.foodvoid.utilities.CameraUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class Recipes extends Fragment {

    private RecyclerView rvRecipe;
    private RecipeAdapter mAdapter;
    private LinearLayoutManager linearLayoutManager;

    private SharedPreferences preferences;

    private String[] recipeLabels = {"Recipe 1","Recipe 2","Recipe 3","Recipe 4","Recipe 5"};

    private static final String EDAMAM_APP_ID = "xxxx";
    private static final String EDAMAM_APP_KEY = "xxxx";
    private static final String EDAMAM_TO = "10";
    private static final String EDAMAM_FROM = "0";
    private static final String EDAMAM_BASE_URL = "https://api.edamam.com/search";

    private ArrayList<RecipeModel> recipeModels = new ArrayList<>();
    private String foodName;


    public Recipes() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = LayoutInflater.from(getContext()).inflate(R.layout.fragment_recipes,container,false);

        rvRecipe = v.findViewById(R.id.rvRecipes);


        preferences = getContext().getSharedPreferences(HomeScreen.PREF_NAME,Context.MODE_PRIVATE);
        foodName = preferences.getString(HomeScreen.FOOD_NAME,"None");


        if (!foodName.equals("None")){

            new GetRecipies().execute(foodName);
        }







        return v;
    }



    public class GetRecipies extends AsyncTask<String,String,String>{

        HttpURLConnection connection;
        BufferedReader reader;
        InputStream inputStream;
        String res = "";
        StringBuilder sb;

        @Override
        protected String doInBackground(String... strings) {
            String querySearch = "q";
            String queryAppId = "app_id";
            String queryAppKey = "app_key";
            String queryFrom = "from";
            String queryTo = "to";
            Uri uri = Uri.parse(EDAMAM_BASE_URL).buildUpon().appendQueryParameter(querySearch,strings[0])
                    .appendQueryParameter(queryAppId,EDAMAM_APP_ID)
                    .appendQueryParameter(queryAppKey,EDAMAM_APP_KEY)
                    .appendQueryParameter(queryFrom,EDAMAM_FROM)
                    .appendQueryParameter(queryTo,EDAMAM_TO).build();



            try {
                URL url = new URL(uri.toString());
                Log.i("THE_URL",url.toString());
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                inputStream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
                String line = "";
                sb = new StringBuilder();

                while ((line = reader.readLine()) != null)
                    sb.append(line + "\n");

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (sb.length() != 0){
                res = getRecipeDataFromJson(sb.toString());
            }



            return res;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            rvRecipe.setHasFixedSize(true);
            linearLayoutManager = new LinearLayoutManager(getContext());
            rvRecipe.setLayoutManager(linearLayoutManager);
            mAdapter = new RecipeAdapter(getContext(),recipeModels);

            rvRecipe.setAdapter(mAdapter);



        }
    }

    private String getRecipeDataFromJson(String jsonString){
        String result = "";

        Log.i("RECIPE_JSON",jsonString);

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("hits");

            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject1 = jsonArray.getJSONObject(i).getJSONObject("recipe");
                RecipeModel recipeModel = new RecipeModel();
                recipeModel.setRecipeName(jsonObject1.getString("label"));
                recipeModel.setDietLabel(jsonObject1.getString("dietLabels"));
                recipeModel.setHealthLabel(jsonObject1.getString("healthLabels"));
                recipeModel.setIngredients(jsonObject1.getString("ingredientLines"));
                recipeModel.setShareUrl(jsonObject1.getString("shareAs"));

                recipeModels.add(recipeModel);
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }


        return result;
    }



}
