package com.example.android.ukforce;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class contactsadapter extends ArrayAdapter<specificforce_contact> {
    public contactsadapter(Context context, List<specificforce_contact> specificforce_contacts) {
        super(context,0, specificforce_contacts);
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View listitemView=convertView;
        if(listitemView==null)
        {listitemView=LayoutInflater.from(getContext()).inflate(R.layout.contacts_list,parent,false);
        }
        specificforce_contact current=getItem(position);
        TextView con_title=(TextView)listitemView.findViewById(R.id.con_title);
        TextView con_des=(TextView)listitemView.findViewById(R.id.con_des);
        TextView con_url=(TextView)listitemView.findViewById(R.id.con_url);
        con_des.setText(current.getContactdes());
        con_title.setText(current.getContacttitle());
        con_url.setText(current.getContacturl());
        return listitemView;
    }
}
