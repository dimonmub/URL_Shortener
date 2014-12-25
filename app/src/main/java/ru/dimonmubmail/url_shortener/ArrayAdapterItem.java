package ru.dimonmubmail.url_shortener;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Dmitriy Mubarakshin on 26.11.2014.
 */
public class ArrayAdapterItem extends ArrayAdapter<Link> {

    int layoutResourceId;
    public ArrayAdapterItem(Context mContext, int layoutResourceId, ArrayList<Link> data) {

        super(mContext, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if(convertView == null) {
            LayoutInflater inflater = ((Activity)getContext()).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.mTextView = (TextView)convertView.findViewById(R.id.linkElement);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.mTextView.setText(getItem(position).getText());

        return convertView;
    }

    public static class ViewHolder {
        public TextView mTextView;
    }

}
