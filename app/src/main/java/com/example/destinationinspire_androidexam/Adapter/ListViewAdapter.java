package com.example.destinationinspire_androidexam.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.destinationinspire_androidexam.Model.CountryModel;
import com.example.destinationinspire_androidexam.R;

import java.util.List;

public class ListViewAdapter extends ArrayAdapter<CountryModel> { //needs to extend arrayadapter of my model type, countrymodel

    Context mCTx; //context needed to inflate
    int resource;
    List<CountryModel> countryModelList;

    //Constructor.
    public ListViewAdapter(Context mCTx,int resource,List<CountryModel> countryModelList){
        super(mCTx,resource,countryModelList);
        this.mCTx = mCTx;
        this.resource = resource;
        this.countryModelList = countryModelList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mCTx); //create an inflater
        View view = layoutInflater.inflate(resource,null); //to inflate the view
        TextView countryTextView = view.findViewById(R.id.textViewCountry); //assigning
        ImageView countryImageView = view.findViewById(R.id.imageViewCountry);

        //Data to be displayed.
        CountryModel countryModel = countryModelList.get(position); //get the object at specified position
        countryTextView.setText(countryModel.getCountry()); //get the country name
        countryImageView.setImageDrawable(mCTx.getDrawable(countryModel.getImage())); //get the image id.



        return view; //return view.
    }
}
