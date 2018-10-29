package edu.nyu.foodvoid;

import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.common.Image;
import com.here.android.mpa.common.OnEngineInitListener;
import com.here.android.mpa.common.ViewObject;
import com.here.android.mpa.mapping.Map;
import com.here.android.mpa.mapping.MapFragment;
import com.here.android.mpa.mapping.MapGesture;
import com.here.android.mpa.mapping.MapMarker;
import com.here.android.mpa.mapping.MapObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MapView extends AppCompatActivity {


    private Map map = null;
    private MapFragment mapFragment = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initialize();
    }

    private void initialize(){

//        final com.here.android.mpa.common.Image myImage =
//                new com.here.android.mpa.common.Image();
//
//        try {
//            myImage.setImageResource(R.drawable.ic_map_black_24dp);
//        } catch (IOException e) {
//            finish();
//        }

        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapfragment);
        mapFragment.init(new OnEngineInitListener() {
            @Override
            public void onEngineInitializationCompleted(Error error) {
                if (error == Error.NONE){


                    map = mapFragment.getMap();
                    map.setCenter(new GeoCoordinate(42.390916,-72.526376,0.0),Map.Animation.LINEAR);

                    map.setZoomLevel((map.getMaxZoomLevel() + map.getMinZoomLevel())/1.5);

                    Image myImage = new Image();


                    try {
                        myImage.setImageResource(R.drawable.marker);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    List<MapObject> list = new ArrayList<>();
                    list.add(new MapMarker(new GeoCoordinate(42.390916,-72.526376),myImage));
                    list.add(new MapMarker(new GeoCoordinate(42.390437,-72.521556),myImage));
                    list.add(new MapMarker(new GeoCoordinate(42.3890408,-72.5393862),myImage));
                    list.add(new MapMarker(new GeoCoordinate(42.3830023,-72.5240225),myImage));

                    if (map != null)
                     map.addMapObjects(list);







//                    List<MapObject> list = new ArrayList<>();
//                    list.add(new MapMarker(new GeoCoordinate(40.729511,-73.996460),null));
//                    list.add(new MapMarker(new GeoCoordinate(40.918960,-73.049010),null));

//                    MapMarker mapMarker = new MapMarker(new GeoCoordinate(40.729511,-73.996460),myImage);
//
//                    map.addMapObject(mapMarker);


                }else {
                    Log.i("Error","Cannot Initialise Map");
                }
            }
        });


       MapGesture.OnGestureListener listener = new MapGesture.OnGestureListener.OnGestureListenerAdapter() {
           @Override
           public boolean onMapObjectsSelected(List<ViewObject> list) {
               for (ViewObject viewObj : list) {
                   if (viewObj.getBaseType() == ViewObject.Type.USER_OBJECT) {
                       if (((MapObject)viewObj).getType() == MapObject.Type.MARKER) {
                           // At this point we have the originally added
                           // map marker, so we can do something with it
                           // (like change the visibility, or more
                           // marker-specific actions)
                           ((MapObject)viewObj).setVisible(false);

                       }
                   }
               }
               // return false to allow the map to handle this callback also
               return false;
           }
       };




    }

}
