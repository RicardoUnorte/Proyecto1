package com.example.ricardo.proyecto1;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Ricardo on 28/03/2015.
 */

public class ExpandableAdapter extends BaseExpandableListAdapter {

    private Activity act;
    private ArrayList<Object> childitems;
    private LayoutInflater infalter;
    private ArrayList<String> parentItem, child;


    public ExpandableAdapter(ArrayList<String> parentItem, ArrayList<Object> childitems) {
        this.parentItem = parentItem;
        this.childitems = childitems;
    }

    public void setInfalter(Activity act, LayoutInflater infalter) {
        this.act = act;
        this.infalter = infalter;
    }

    @Override
    public int getGroupCount() {
        return parentItem.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        return ((ArrayList<String>)childitems.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = infalter.inflate(R.layout.item_ciclos,null);
        }

        ((CheckedTextView) convertView).setText(parentItem.get(groupPosition));
        ((CheckedTextView) convertView).setChecked(isExpanded);


        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        child = (ArrayList<String>) childitems.get(groupPosition);
        TextView textviewC = null;


        if(convertView == null)
        {
            convertView = infalter.inflate(R.layout.item_ciclos2,null);
        }

        textviewC = (TextView) convertView.findViewById(R.id.CiChild);


        textviewC.setText(child.get(childPosition));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SI HAGO CLICK EN UN HIJO

            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}