package com.example.waqasjutt.promocodewithfragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Login_Fragment extends Fragment implements OnClickListener {

    private static View view;

    private static TextView creatNewAccount;
    @Bind(R.id.login_emailid)
    EditText emailid;
    @Bind(R.id.login_password)
    EditText password;
    private ProgressDialog progressDialog;
    private static Button loginButton;
    private static CheckBox show_hide_password;
    private static LinearLayout loginLayout;
    private static Animation shakeAnimation;
    private static FragmentManager fragmentManager;
    private SharedPreferences sharedPreferences;

    public Login_Fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(getActivity());

        //For Disable ActionBar Back Button
        if (((MainActivity) getActivity()).getSupportActionBar() != null) {
            ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(false);
        }

        //For Boom Menu Button
        ((MainActivity) getActivity()).boomMenuButton.setVisibility(View.GONE);
        ((MainActivity) getActivity()).mTitleTextView.setVisibility(View.GONE);
        ((MainActivity) getActivity()).setActionBarTitle(" Promo Codes");

        initViews();
        setListeners();
        return view;
    }

    public boolean isLoggedIn() {
        sharedPreferences = getActivity().getSharedPreferences("mySharedpref12", Context.MODE_PRIVATE);
        if (sharedPreferences.getString("useremail", null) != null) {
            return true;
        }
        return false;
    }

    // Initiate Views
    private void initViews() {
        fragmentManager = getActivity().getSupportFragmentManager();
        if (isLoggedIn()) {
            new MainActivity().finish();
            fragmentManager.beginTransaction()
                    .replace(R.id.frameContainer,
                            new PromoCode_Fragment(),
                            Utils.PromoCode_Fragment)
                    .commit();
        }
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait...");
        emailid = (EditText) view.findViewById(R.id.login_emailid);
        password = (EditText) view.findViewById(R.id.login_password);
        loginButton = (Button) view.findViewById(R.id.loginBtn);
        creatNewAccount = (TextView) view.findViewById(R.id.createAccount);
        show_hide_password = (CheckBox) view
                .findViewById(R.id.show_hide_password);
        loginLayout = (LinearLayout) view.findViewById(R.id.login_layout);

        // Load ShakeAnimation
        shakeAnimation = AnimationUtils.loadAnimation(getActivity(),
                R.anim.shake);

        // Setting text selector over textviews
        XmlResourceParser xrp = getResources().getXml(R.color.text_selector);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(),
                    xrp);

            show_hide_password.setTextColor(csl);
            creatNewAccount.setTextColor(csl);
        } catch (Exception e) {
        }
    }

    // Set Listeners
    private void setListeners() {
        loginButton.setOnClickListener(this);
        creatNewAccount.setOnClickListener(this);

        // Set check listener over checkbox for showing and hiding password
        show_hide_password
                .setOnCheckedChangeListener(new OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton button,
                                                 boolean isChecked) {

                        // If it is checkec then show password else hide
                        // password
                        if (isChecked) {

                            show_hide_password.setText("Show password");
                            // change checkbox text

                            password.setInputType(InputType.TYPE_CLASS_TEXT);
                            password.setTransformationMethod(HideReturnsTransformationMethod
                                    .getInstance());// show password
                        } else {
                            show_hide_password.setText("Hide password");
                            // change checkbox text

                            password.setInputType(InputType.TYPE_CLASS_TEXT
                                    | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            password.setTransformationMethod(PasswordTransformationMethod
                                    .getInstance());// hide password

                        }

                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginBtn:
                checkValidation();
                break;
            case R.id.createAccount:
                // Replace signup frgament with animation
                fragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                        .replace(R.id.frameContainer, new SignUp_Fragment(),
                                Utils.SignUp_Fragment).commit();
                break;
        }

    }

    // Check Validation before login
    private void checkValidation() {
        // Get email id and password
        String getEmailId = emailid.getText().toString();
        String getPassword = password.getText().toString();

        // Check patter for email id
        Pattern p = Pattern.compile(Utils.regEx);

        Matcher m = p.matcher(getEmailId);

        // Check for both field is empty or not
        if ((getEmailId.isEmpty() || getEmailId.equals("") || getEmailId.length() == 0)
                && (getPassword.isEmpty() || getPassword.equals("") || getPassword.length() == 0)) {
            loginLayout.startAnimation(shakeAnimation);
            new CustomToast().Show_Toast(getActivity(), view, "Enter both credentials.");
            TastyToast.makeText(getActivity(), "Try again.",
                    TastyToast.LENGTH_SHORT, TastyToast.ERROR).show();
        }
        // Check if email id is valid or not
        if (!m.find()) {
            loginLayout.startAnimation(shakeAnimation);
            emailid.setError("enter a valid email address.");
        } else if (getEmailId.contains(" ")) {
            emailid.setError("Space are not allowed.");
        } else {
            emailid.setError(null);
        }

        if (getPassword.length() <= 3) {
            loginLayout.startAnimation(shakeAnimation);
            password.setError("Password should be greater than 3.");
        } else {
            password.setError(null);
        }
        if (!emailid.getText().toString().isEmpty() &&
                !password.getText().toString().isEmpty()) {
//            TastyToast.makeText(getActivity(), "Login Successfully.",
//                    TastyToast.LENGTH_SHORT, TastyToast.SUCCESS).show();
//            fragmentManager
//                    .beginTransaction()
//                    .setCustomAnimations(R.anim.right_enter,R.anim.left_enter)
//                    .replace(R.id.frameContainer,new PromoCode_Fragment(),
//                            Utils.PromoCode_Fragment).commit();
            StringRequest stringRequest = new StringRequest(
                    Request.Method.POST,
                    Constants.URL_LOGIN,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            try {
                                JSONObject obj = new JSONObject(response);
                                if (!obj.getBoolean("error")) {
                                    SharedPrefManager.getInstance(getActivity())
                                            .userLogin(
                                                    obj.getInt("id"),
                                                    obj.getString("username"),
                                                    obj.getString("email"),
                                                    obj.getString("mobile")
                                            );
                                    fragmentManager
                                            .beginTransaction()
                                            .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                                            .replace(R.id.frameContainer, new PromoCode_Fragment(),
                                                    Utils.PromoCode_Fragment).commit();
                                    new MainActivity().finishAffinity();
                                } else {
                                    TastyToast.makeText(getActivity(),
                                            obj.getString("message"),
                                            Toast.LENGTH_SHORT,
                                            TastyToast.ERROR).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(),
                                    error.getMessage() + " Server is not responding...",
                                    Toast.LENGTH_SHORT).show();
//                            TastyToast.makeText(getActivity(),
//                                    error.getMessage() + " Not responding...",
//                                    Toast.LENGTH_SHORT,
//                                    TastyToast.ERROR).show();
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> param = new HashMap<>();
                    param.put("email", emailid.getText().toString());
                    param.put("password", password.getText().toString());
                    return param;
                }
            };
            RequestHandler.getInstance(getActivity()).addToRequestQueue(stringRequest);
        }
    }
}