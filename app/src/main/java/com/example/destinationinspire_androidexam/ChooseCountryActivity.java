package com.example.destinationinspire_androidexam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.destinationinspire_androidexam.Adapter.ListViewAdapter;
import com.example.destinationinspire_androidexam.Model.CountryModel;

import java.util.ArrayList;
import java.util.List;

public class ChooseCountryActivity extends AppCompatActivity {

    List<CountryModel> countryModelList;
    ListView listView;
    String countryName; //Store the picked countryname in, will be passed


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_country);

        //Assign
        listView = findViewById(R.id.countryListView);

        //Initialize
        countryModelList = new ArrayList<>();
        //Add data
        countryModelList.add(new CountryModel(R.drawable.argentina,"Argentina"));
        countryModelList.add(new CountryModel(R.drawable.australia,"Australia"));
        countryModelList.add(new CountryModel(R.drawable.canada,"Canada"));
        countryModelList.add(new CountryModel(R.drawable.denmark,"Denmark"));
        countryModelList.add(new CountryModel(R.drawable.ecuador,"Ecuador"));
        countryModelList.add(new CountryModel(R.drawable.estonia,"Estonia"));
        countryModelList.add(new CountryModel(R.drawable.faroe,"Faroe Islands"));
        countryModelList.add(new CountryModel(R.drawable.jamaica,"Jamaica"));
        countryModelList.add(new CountryModel(R.drawable.macedonia,"Macedonia"));



        //Adapter creation, passing this as context, listView as layout and the arraylist.
        ListViewAdapter adapter = new
                ListViewAdapter(this,R.layout.country_list_layout,countryModelList);

        //Attachment of adapter to the listview
        listView.setAdapter(adapter);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // Will retrieve the countryname of the picked item.
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                countryName = countryModelList.get(position).getCountry(); //gets the country name of picked item

                goToMapsActivity(); //calls method to go to mapsactivity which also passes the picked country name.
            }
        });

    }
    private void goToMapsActivity() {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("country",countryName);
        startActivity(intent);
    }

}
