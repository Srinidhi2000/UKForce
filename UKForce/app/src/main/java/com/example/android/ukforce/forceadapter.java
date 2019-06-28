package com.example.android.ukforce;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class forceadapter extends ArrayAdapter<Force>{
    public forceadapter(Context context, List<Force> forces) {
        super(context, 0,forces);
    }


    @NonNull
    @Override
    public View getView(int position,  View convertView, @NonNull ViewGroup parent) {
       View listitemView=convertView;
       if(listitemView==null)
       {listitemView=LayoutInflater.from(getContext()).inflate(R.layout.force_list_item,parent,false);
       }
         Force current=getItem(position);
       TextView force_id=(TextView)listitemView.findViewById(R.id.force_id);
       TextView force_name=(TextView)listitemView.findViewById(R.id.force_name);
       force_id.setText(current.getforcename());
       force_name.setText(current.getforceid());

        return listitemView;
        }
}
