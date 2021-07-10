package com.example.trabus.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.trabus.models.PlaceApis;

import java.util.ArrayList;

public class PlaceAutosuggestAdapter extends ArrayAdapter implements Filterable {
   ArrayList<String> results;
   int resource;
   Context context;
   PlaceApis placeApis=new PlaceApis();
   public PlaceAutosuggestAdapter(Context context,int resId)
   {
       super(context,resId);
       this.context=context;
       this.resource=resId;
   }

    @Override
    public int getCount() {
        return results.size();
    }

    @NonNull
    @Override
    public Filter getFilter() {
       Filter filter=new Filter() {
           @Override
           protected FilterResults performFiltering(CharSequence constraint) {
               FilterResults filterResults=new FilterResults();
               if(constraint!=null)
               {
                   results=placeApis.autocomplete(constraint.toString());
                   filterResults.values=results;
                   filterResults.count=results.size();
               }
               return filterResults;
           }

           @Override
           protected void publishResults(CharSequence constraint, FilterResults results) {
               if(results!=null&&results.count>0)
               {
                   notifyDataSetChanged();
               }
               else {
                   notifyDataSetInvalidated();
               }
           }
       };
        return filter;
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return results.get(position);
    }
}
