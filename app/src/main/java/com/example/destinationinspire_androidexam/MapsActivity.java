package com.example.destinationinspire_androidexam;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.destinationinspire_androidexam.Model.CityModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<CityModel> cityModelList = new ArrayList<>();
    private TextView cityTextview;
    private String collection;
    private List<String> countries = new ArrayList<>(); //Ugly way im doing it, but apparently u cant fetch all collection names in android.
    //Would have to rearrange my database


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Subject to improvement if further devoloped :)
        countries.add("Argentina");
        countries.add("Australia");
        countries.add("Canada");
        countries.add("Denmark");
        countries.add("Ecuador");
        countries.add("Estonia");
        countries.add("Faroe Islands");
        countries.add("Jamaica");
        countries.add("Macedonia");






        //Assigning
        cityTextview = findViewById(R.id.cityTextview);

        //If a country is selected from the choose country activity it will be passed as extra
        //check if its not null/if there is something, if so, set the collection to look in from fb to this country name
        if (getIntent().getStringExtra("country") != null){
            collection = getIntent().getStringExtra("country");
        } else collection = countries.get(generateRandom(countries.size())); //pick a random country from list





    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getCityFromDb(collection);

    }

    public void notForMeBtnPressed(View view){
        cityPicker(); //Just execute the cityPicker() method again to get a new city in the same country
    }

    public void googleSearchBtnPressed(View view){ //Method will search on google for city name
            Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
            intent.putExtra(SearchManager.QUERY, cityTextview.getText() + " " +  collection); //search for city name and country
            startActivity(intent);
    }


    private void cityPicker() {
        mMap.clear(); //clear map first

        int index = generateRandom(cityModelList.size()); //pick a random index based on size of the retrieved list.
        String city = cityModelList.get(index).getCity(); //use it to pick city and get the info on that city
        double lat = cityModelList.get(index).getLat();
        double len = cityModelList.get(index).getLen();

        LatLng latLng = new LatLng(lat,len); //LatLng to be used to create the marker

        mMap.addMarker(new MarkerOptions().position(latLng).title(city)); //add the marker with city name
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng)); //move camera to the marker

        cityTextview.setText(city); //sets the text on textview to the city name
    }


    public void getCityFromDb(String collection) { //Method waits for the custom callback so that the list is populated
        readData(new FirestoreCallback() {
            @Override
            public void onCallback(List<CityModel> list) {
                cityPicker(); //calls the cityPicker() method when the list is ready.
            }
        }, collection);

    }
    //method to read data
    private void readData(final FirestoreCallback firestoreCallback, String collection) { //Is using firestorecallback and
        // takes a string to know which collection to read
        db.collection(collection)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                CityModel cityModel = documentSnapshot.toObject(CityModel.class); //Automatically creates object and fills corresponding fields
                                cityModelList.add(cityModel); //adds them to list
                            }
                            firestoreCallback.onCallback(cityModelList);
                        } else {
                            System.out.println("Error ->" + task.getException());
                        }
                    }
                });

    }

    public int generateRandom(int max) { //to get a random index based on length of array
        Random random = new Random();
        return random.nextInt(max);
    }

    private interface FirestoreCallback { //Interface, creating a custom callback to wait for the data from firebase
        void onCallback(List<CityModel> list);
    }
}
