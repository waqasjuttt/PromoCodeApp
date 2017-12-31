package com.example.waqasjutt.promocodewithfragment.PromoCodes.CareemPromoCodes;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.waqasjutt.promocodewithfragment.R;

import java.util.List;

/**
 * Created by Waqas Jutt on 10/8/2017.
 */

public class GoCustomAdapter extends ArrayAdapter {

    private List<GoDetails> goDetailsList;
    private Context context;
    private int resource;

    public GoCustomAdapter(Context context, int resource, List<GoDetails> goDetails) {
        super(context, resource, goDetails);
        this.context = context;
        this.goDetailsList = goDetails;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        GoDetails details = goDetailsList.get(position);
        View row;
        ViewHolder viewHolder;
        row = convertView;
        if (row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.go_list, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.tvTilte = (TextView) row.findViewById(R.id.tvTitle);
            viewHolder.tvDiscount = (TextView) row.findViewById(R.id.tvDiscount);
            viewHolder.tvDiscription = (TextView) row.findViewById(R.id.tvDiscription);
            row.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) row.getTag();
        }

        viewHolder.tvTilte.setText(Html.fromHtml("<h4>Title</h4>:"+details.getOriginal_title()));

//        viewHolder.tvTilte.setText(details.getOriginal_title());
        viewHolder.tvDiscount.setText(details.getDiscount());
        viewHolder.tvDiscription.setText(details.getOverview());
        return row;
    }

    static class ViewHolder {
        TextView tvTilte, tvDiscount, tvDiscription;
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return goDetailsList.get(position);
    }

    @Override
    public int getCount() {
        return goDetailsList.size();
    }
}