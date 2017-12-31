package com.example.waqasjutt.promocodewithfragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Profile_Fragment extends Fragment {

    private View view;
    private TextView name;
    private TextView email;
    private TextView mobile;
    private FragmentManager fragmentManager;

    public Profile_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_profile, container, false);
        fragmentManager = getActivity().getSupportFragmentManager();
        name = (TextView) view.findViewById(R.id.tvName);
        email = (TextView) view.findViewById(R.id.tvEmail);
        mobile = (TextView) view.findViewById(R.id.tvMobile);

        name.setText(SharedPrefManager.getInstance(getActivity()).getUsername());
        email.setText(SharedPrefManager.getInstance(getActivity()).getUserEmail());
        mobile.setText("+92"+SharedPrefManager.getInstance(getActivity()).getUserMobile());

        //For Boom Menu Button
        ((MainActivity) getActivity()).boomMenuButton.setVisibility(View.GONE);
        ((MainActivity) getActivity()).mTitleTextView.setVisibility(View.GONE);
        ((MainActivity) getActivity()).setActionBarTitle("Profile");

        return view;
    }
}