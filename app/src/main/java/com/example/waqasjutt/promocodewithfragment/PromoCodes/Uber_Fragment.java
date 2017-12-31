package com.example.waqasjutt.promocodewithfragment.PromoCodes;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.waqasjutt.promocodewithfragment.Login_Fragment;
import com.example.waqasjutt.promocodewithfragment.MainActivity;
import com.example.waqasjutt.promocodewithfragment.Profile_Fragment;
import com.example.waqasjutt.promocodewithfragment.PromoCodeAdapter;
import com.example.waqasjutt.promocodewithfragment.R;
import com.example.waqasjutt.promocodewithfragment.SharedPrefManager;
import com.example.waqasjutt.promocodewithfragment.Utils;
import com.sdsmdg.tastytoast.TastyToast;


/**
 * A simple {@link Fragment} subclass.
 */
public class Uber_Fragment extends Fragment {

    private FragmentManager fragmentManager;
    private ListView listView;
    private String[] promo = {"Go","Go+","Business"};
    private Integer[] imagId={R.drawable.cargo,R.drawable.cargoplus,R.drawable.business};
    private View view;
    private FragmentTransaction fragmentTransaction;
    private SharedPreferences sharedPreferences;

    public Uber_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_uber_, container, false);
        ((MainActivity) getActivity()).setActionBarTitle("Uber Codes");
        fragmentManager = getActivity().getSupportFragmentManager();
        PromoCodeAdapter adapter = new PromoCodeAdapter(getActivity(), promo, imagId);
        listView=(ListView)view.findViewById(R.id.UberListView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int position, long id) {
                TastyToast.makeText(getActivity(),"You Clicked at "
                        + promo[+position],TastyToast.LENGTH_SHORT,TastyToast.SUCCESS).show();
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                }
            }
        });

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    /*@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Logout:
                Toast.makeText(getActivity(),"Logout Successfully",Toast.LENGTH_SHORT).show();
                logout();
                fragmentTransaction=getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction();
                fragmentTransaction
                        .setCustomAnimations(R.anim.left_enter,R.anim.right_enter)
                        .replace(R.id.frameContainer,new Login_Fragment())
                        .commit();
                new MainActivity().finishAffinity();
                return true;
            case R.id.profile:
                Toast.makeText(getActivity(),"You clicked profile",Toast.LENGTH_SHORT).show();
                fragmentTransaction =
                        fragmentManager
                                .beginTransaction()
                                .replace(R.id.frameContainer,
                                        new Profile_Fragment());
                fragmentTransaction
                        .addToBackStack(null)
                        .commit();


//                fragmentTransaction=getActivity()
//                        .getSupportFragmentManager()
//                        .beginTransaction();
//                fragmentTransaction
//                        .setCustomAnimations(R.anim.left_enter,R.anim.right_enter)
//                        .replace(R.id.frameContainer,new Profile_Fragment()
//                                ,Utils.Profile_Fragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
                return true;
            case R.id.menuSetting:
                Toast.makeText(getActivity(),"You clicked settings",Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/

    public boolean logout(){
        sharedPreferences= getActivity().getSharedPreferences("mySharedpref12", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }
}

/*class CustomListViewofUber extends BaseAdapter {
    private final Activity context;
    private final String[] promo;
    private final Integer[] uber_imageId;

    public CustomListViewofUber(Activity context, String[] promo, Integer[] uber_imageId) {
        this.context = context;
        this.promo = promo;
        this.uber_imageId = uber_imageId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View rowView = layoutInflater.inflate(R.layout.uber_item, parent, false);
        TextView textView=rowView.findViewById(R.id.UberTextView);
        ImageView imageView=rowView.findViewById(R.id.UberImageView);

        textView.setText(promo[position]);
        imageView.setImageBitmap(OutOfMemoryfixer.decodeSampledBitmapFromResource(context.getResources(),uber_imageId[position],100,100));
//        imageView.setImageResource(uber_imageId[position]);
        return rowView;
    }

    @Override
    public int getCount() {
        return uber_imageId.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
}*/