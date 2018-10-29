package edu.nyu.foodvoid;

import android.Manifest;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.api.ClarifaiResponse;
import clarifai2.dto.input.ClarifaiInput;
import edu.nyu.foodvoid.utilities.CameraUtils;
import okhttp3.OkHttpClient;

public class HomeScreen extends AppCompatActivity implements View.OnClickListener {

//    private Button btUpload;
    private Button btTakePicture;

    private ImageView ivBackground1,ivBackground2;

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final String KEY_IMAGE_STORAGE_PATH = "image_path";
    public static final String IMAGE_EXTENSION = "jpg";
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;

    public static final int BITMAP_SAMPLE_SIZE = 8;

    private Uri currentImageUri;

    private static String imageStoragePath;
    private static final String API_KEY = "xxxxxxx";
    private ClarifaiClient client;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    
    public static final String FOOD_NAME = "foodname";
    public static final String PREF_NAME = "foodPref";
    public static final String IMAGE_PATH = "imagePath";





    private String imagePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home_screen);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        ivBackground1 = findViewById(R.id.ivbackground1);
        ivBackground2 = findViewById(R.id.ivbackground2);

        theAnimator(ivBackground1,ivBackground2);

      //  new GetRecipies().execute("pizza");
        client = new ClarifaiBuilder(API_KEY).buildSync();



        if (!CameraUtils.isDeviceSupportsCamera(getApplicationContext())) {
            Toast.makeText(getApplicationContext(),
                    "Sorry! Your device doesn't support camera",
                    Toast.LENGTH_LONG).show();
            // will close the app if the device doesn't have camera
            finish();
        }

//        btUpload = findViewById(R.id.btUpload);
        btTakePicture = findViewById(R.id.btTakeaPicture);

//        btUpload.setOnClickListener(this);
        btTakePicture.setOnClickListener(this);


    }


    private void theAnimator(final ImageView iv1, final ImageView iv2){
        final ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(60000L);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final float progress = (float) animation.getAnimatedValue();
                final float height = iv1.getHeight();
                final float translationY = height * progress;
                iv1.setTranslationY(translationY);
                iv2.setTranslationY(translationY - height);
            }
        });
        animator.start();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
//            case R.id.btUpload:
//
////                if (CameraUtils.checkPermissions(getApplicationContext()))
////                    getImageFromGallery();
////                else
////                    requestCameraPermission(2);
//
//                break;

            case R.id.btTakeaPicture:

                if (CameraUtils.checkPermissions(getApplicationContext()))
                    captureImage();
                else
                    requestCameraPermission(MEDIA_TYPE_IMAGE);


                break;
        }
    }

    private void requestCameraPermission(final int type){
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.ACCESS_WIFI_STATE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {

                            if (type == MEDIA_TYPE_IMAGE) {
                                // capture picture
                                captureImage();
                            }

                        } else if (report.isAnyPermissionPermanentlyDenied()) {
                            showPermissionsAlert();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }



    private void captureImage(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = CameraUtils.getOutputMediaFile(MEDIA_TYPE_IMAGE);
        if (file != null) {
            imageStoragePath = file.getAbsolutePath();
        }

        Uri fileUri = CameraUtils.getOutputMediaFileUri(getApplicationContext(), file);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        // start the image capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

        outState.putString(KEY_IMAGE_STORAGE_PATH, imageStoragePath);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        imageStoragePath = savedInstanceState.getString(KEY_IMAGE_STORAGE_PATH);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE){
            if (resultCode == RESULT_OK){

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(4000);

                            CameraUtils.refreshGallery(getApplicationContext(),imageStoragePath);
                            Log.i("ImagePath",imageStoragePath);
                            imagePath = imageStoragePath;
//                new GetPredictedResponse().execute(imageStoragePath);

                            Intent intent = new Intent(HomeScreen.this,MedicineDetail.class);
                            startActivity(intent);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });

                thread.start();



            }else if (resultCode == RESULT_CANCELED){
                makeToast("Cancelled");
            }else {
                makeToast("Failed");
            }
        }
    }



    public void makeToast(String str){
        Toast.makeText(getApplicationContext(),str,Toast.LENGTH_SHORT).show();
    }

    private void showPermissionsAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissions required!")
                .setMessage("Camera needs few permissions to work properly. Grant them in settings.")
                .setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        CameraUtils.openSettings(HomeScreen.this);
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }



    public class GetPredictedResponse extends AsyncTask<String,Void,ClarifaiResponse>{

        @Override
        protected ClarifaiResponse doInBackground(String... strings) {




            ClarifaiResponse response = client.getDefaultModels().foodModel().predict()
                        .withInputs(ClarifaiInput.forImage(new File(strings[0])))
                        .executeSync();



            return response;
        }

        @Override
        protected void onPostExecute(ClarifaiResponse clarifaiResponse) {
            super.onPostExecute(clarifaiResponse);
           // makeToast(clarifaiResponse.rawBody());
            Log.i("PREDICTIONS",clarifaiResponse.rawBody());
            String name = getResponseFromJson(clarifaiResponse.rawBody());




            preferences = getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
            editor = preferences.edit();
            editor.putString(FOOD_NAME,name);
            editor.putString(IMAGE_PATH,imagePath);
            editor.commit();



            Intent intent = new Intent(HomeScreen.this,FoodDetail.class);
//            intent.putExtra("IMAGEPATH",imagePath);
            startActivity(intent);


//            new GetRecipies().execute(name);
        }
    }

    private String getResponseFromJson(String jsonString){
        String result = "";

        try {


            JSONObject jsonObject = new JSONObject((jsonString));

            if (jsonObject.getJSONObject("status").getString("description").toLowerCase().equals("ok")){
                JSONArray jsonArray = jsonObject.getJSONArray("outputs");
                JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                JSONObject jsonObject2 = jsonObject1.getJSONObject("data");
                JSONArray jsonArray1 = jsonObject2.getJSONArray("concepts");
                result = jsonArray1.getJSONObject(0).getString("name");
                makeToast(jsonArray1.getJSONObject(0).getString("name"));


            }


            makeToast(jsonObject.getJSONObject("status").getString("description"));
            //Log.i("LENGTH_JSON",String.valueOf(jsonObject.length()));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return result;
    }




}
