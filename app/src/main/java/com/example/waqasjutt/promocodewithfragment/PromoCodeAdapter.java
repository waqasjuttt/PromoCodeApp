package com.example.waqasjutt.promocodewithfragment;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Waqas Jutt on 9/24/2017.
 */

public class PromoCodeAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] promo;
    private final Integer[] imageId;

    public PromoCodeAdapter(Activity context, String[] promo, Integer[] imageId) {
        super(context, R.layout.main_list_item, promo);
        this.context = context;
        this.promo = promo;
        this.imageId = imageId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View rowView = layoutInflater.inflate(R.layout.main_list_item, parent, false);
        TextView textView=rowView.findViewById(R.id.MainTextView);
        ImageView imageView=rowView.findViewById(R.id.MainImageView);

        textView.setText(promo[position]);
        imageView.setImageBitmap(OutOfMemoryfixer.decodeSampledBitmapFromResource(context.getResources(),imageId[position],100,100));
        return rowView;
    }

    @Override
    public int getCount() {
        return imageId.length;
    }
}