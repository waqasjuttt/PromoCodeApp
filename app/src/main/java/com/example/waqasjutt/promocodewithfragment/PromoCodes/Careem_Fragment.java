package com.example.waqasjutt.promocodewithfragment.PromoCodes;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.waqasjutt.promocodewithfragment.Login_Fragment;
import com.example.waqasjutt.promocodewithfragment.MainActivity;
import com.example.waqasjutt.promocodewithfragment.OutOfMemoryfixer;
import com.example.waqasjutt.promocodewithfragment.Profile_Fragment;
import com.example.waqasjutt.promocodewithfragment.PromoCode_Fragment;
import com.example.waqasjutt.promocodewithfragment.PromoCodes.CareemPromoCodes.Business_Fragment;
import com.example.waqasjutt.promocodewithfragment.PromoCodes.CareemPromoCodes.GoPlus_Fragment;
import com.example.waqasjutt.promocodewithfragment.PromoCodes.CareemPromoCodes.Go_Fragment;
import com.example.waqasjutt.promocodewithfragment.PromoCodeAdapter;
import com.example.waqasjutt.promocodewithfragment.R;
import com.example.waqasjutt.promocodewithfragment.SharedPrefManager;
import com.example.waqasjutt.promocodewithfragment.Utils;


/**
 * A simple {@link Fragment} subclass.
 */
public class Careem_Fragment extends Fragment {

    private LinearLayout linearLayout;
    private View view;
    private ListView listView;
    private FragmentTransaction fragmentTransaction;
    private static FragmentManager fragmentManager;
    private String[] promo = {"Go", "Go+", "Rikshaw"};
    private Integer[] imagId = {R.drawable.careemgo, R.drawable.careemgoplus, R.drawable.rikshaw};
    private SharedPreferences sharedPreferences;

    public Careem_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_careem, container, false);
        fragmentManager = getActivity().getSupportFragmentManager();
//        ((MainActivity) getActivity()).setActionBarTitle("Careem Codes");

        //For Boom Menu Button
        ((MainActivity) getActivity()).initInfoBoom();
        ((MainActivity) getActivity()).mTitleTextView.setVisibility(View.VISIBLE);
        ((MainActivity) getActivity()).mTitleTextView.setText("Careem Codes");

        //For Set Back Ground picture to avoid outofmemory error
        linearLayout = (LinearLayout) view.findViewById(R.id.BGCareem_LinearLayout);
        getBG();

        PromoCodeAdapter adapter = new PromoCodeAdapter(getActivity(), promo, imagId);
        listView = (ListView) view.findViewById(R.id.CareemListView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int position, long id) {
                Toast.makeText(getActivity(), "You Clicked at "
                        + promo[+position], Toast.LENGTH_SHORT).show();
                switch (position) {
                    case 0:
                        fragmentTransaction =
                                fragmentManager
                                        .beginTransaction()
                                        .replace(R.id.frameContainer,
                                                new Go_Fragment());
                        fragmentTransaction
                                .addToBackStack(null)
                                .commit();
                        break;
                    case 1:
                        fragmentTransaction =
                                fragmentManager
                                        .beginTransaction()
                                        .replace(R.id.frameContainer,
                                                new GoPlus_Fragment());
                        fragmentTransaction
                                .addToBackStack(null)
                                .commit();
                        break;
                    case 2:
                        fragmentTransaction =
                                fragmentManager
                                        .beginTransaction()
                                        .replace(R.id.frameContainer,
                                                new Business_Fragment());
                        fragmentTransaction
                                .addToBackStack(null)
                                .commit();
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

    public void getBG() {
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        if (Build.VERSION.SDK_INT >= 16) {
            Drawable d = new BitmapDrawable(getResources(),
                    new OutOfMemoryfixer().decodeSampledBitmapFromResource(getResources(),
                            R.drawable.signup,
                            500, 500));
            linearLayout.setBackground(d);
        }
    }

    public boolean logout() {
        sharedPreferences = getActivity().getSharedPreferences("mySharedpref12", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
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

/*class CustomListViewofCareem extends BaseAdapter {
    private final Activity context;
    private final String[] promo;
    private final Integer[] careem_imageId;

    public CustomListViewofCareem(Activity context, String[] promo, Integer[] careem_imageId) {
        this.context = context;
        this.promo = promo;
        this.careem_imageId = careem_imageId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View rowView = layoutInflater.inflate(R.layout.careem_item, parent, false);
        TextView textView=rowView.findViewById(R.id.CareemTextView);
        ImageView imageView=rowView.findViewById(R.id.CareemImageView);

        textView.setText(promo[position]);
        imageView.setImageBitmap(OutOfMemoryfixer.decodeSampledBitmapFromResource(context.getResources(),careem_imageId[position],100,100));
//        imageView.setImageResource(careem_imageId[position]);
        return rowView;
    }

    @Override
    public int getCount() {
        return careem_imageId.length;
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