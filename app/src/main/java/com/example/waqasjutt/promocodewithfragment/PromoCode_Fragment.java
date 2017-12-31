package com.example.waqasjutt.promocodewithfragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.waqasjutt.promocodewithfragment.PromoCodes.Careem_Fragment;
import com.example.waqasjutt.promocodewithfragment.PromoCodes.McDonalds_Fragment;
import com.example.waqasjutt.promocodewithfragment.PromoCodes.Uber_Fragment;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.Types.BoomType;
import com.nightonke.boommenu.Types.ButtonType;
import com.nightonke.boommenu.Types.PlaceType;
import com.nightonke.boommenu.Util;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class PromoCode_Fragment extends Fragment{

    private FragmentTransaction fragmentTransaction;
    private SharedPreferences sharedPreferences;
    private MainActivity mainActivity;
    private View view;
    private ListView listView;
    private static FragmentManager fragmentManager;
    String[] MainPromoCode = {"Careem", "Uber", "Mcdonalds"};
    Integer[] MainImagIds = {R.drawable.creem, R.drawable.uber, R.drawable.mcdonalds};

    public PromoCode_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_promo_code, container, false);
        fragmentManager = getActivity().getSupportFragmentManager();

        //For Go Back to previous fragment
        if (((MainActivity) getActivity()).getSupportActionBar() != null) {
            ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //For Boom Menu Button
        ((MainActivity) getActivity()).boomMenuButton.setVisibility(View.VISIBLE);
        ((MainActivity) getActivity()).mTitleTextView.setVisibility(View.VISIBLE);
        ((MainActivity) getActivity()).initInfoBoom();
        ((MainActivity) getActivity()).mTitleTextView.setText("Promo Codes");


//        ((MainActivity) getActivity()).setActionBarTitle("Promo Codes");
        if (!isLoggedIn()) {
            new MainActivity().finishAffinity();
            fragmentManager.beginTransaction()
                    .replace(R.id.frameContainer,
                            new PromoCode_Fragment(),
                            Utils.PromoCode_Fragment)
                    .commit();
        }

        PromoCodeAdapter adapter = new PromoCodeAdapter(getActivity(), MainPromoCode, MainImagIds);
        listView = (ListView) view.findViewById(R.id.MainListView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int position, long id) {
                TastyToast.makeText(getActivity(), "You Clicked at "
                        + MainPromoCode[+position], TastyToast.LENGTH_SHORT, TastyToast.SUCCESS).show();
                switch (position) {
                    case 0:
                        fragmentTransaction =
                                fragmentManager
                                        .beginTransaction()
                                        .replace(R.id.frameContainer,
                                                new Careem_Fragment());
                        fragmentTransaction
                                .addToBackStack(null)
                                .commit();
                        break;
                    case 1:
                        fragmentTransaction =
                                fragmentManager
                                        .beginTransaction()
                                        .replace(R.id.frameContainer,
                                                new Uber_Fragment());
                        fragmentTransaction
                                .addToBackStack(null)
                                .commit();
                        break;
                    case 2:
                        fragmentTransaction =
                                fragmentManager
                                        .beginTransaction()
                                        .replace(R.id.frameContainer,
                                                new McDonalds_Fragment());
                        fragmentTransaction
                                .addToBackStack(null)
                                .commit();
                        break;
                }
            }
        });

        return view;
    }

    public boolean isLoggedIn() {
        sharedPreferences = getActivity().getSharedPreferences("mySharedpref12", Context.MODE_PRIVATE);
        if (sharedPreferences.getString("useremail", null) != null) {
            return true;
        }
        return false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    /*
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        // TODO Add your menu entries here
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Logout:
                Toast.makeText(getActivity(), "Logout Successfully", Toast.LENGTH_SHORT).show();
                logout();
                fragmentTransaction = getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction();
                fragmentTransaction
                        .setCustomAnimations(R.anim.left_enter, R.anim.right_enter)
                        .replace(R.id.frameContainer, new Login_Fragment())
                        .commit();
                new MainActivity().finishAffinity();
                return true;
            case R.id.profile:
                Toast.makeText(getActivity(), "You clicked profile", Toast.LENGTH_SHORT).show();
                fragmentTransaction =
                        fragmentManager
                                .beginTransaction()
                                .replace(R.id.frameContainer,
                                        new Profile_Fragment());
                fragmentTransaction
                        .addToBackStack(null)
                        .commit();
                return true;
            case R.id.menuSetting:
                Toast.makeText(getActivity(), "You clicked settings", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/
}