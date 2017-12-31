package com.example.waqasjutt.promocodewithfragment;

import android.app.ProgressDialog;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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

public class SignUp_Fragment extends Fragment implements OnClickListener {

    private static View view;
    private int count = 0;

    @Bind(R.id.UserName)
    EditText UserName;
    @Bind(R.id.userEmailId)
    EditText emailId;
    @Bind(R.id.mobileNumber)
    EditText mobileNumber;
    @Bind(R.id.password)
    EditText password;
    @Bind(R.id.confirmPassword)
    EditText confirmPassword;

    private ProgressDialog progressDialog;
    private static TextView already_user;
    private static Button btnSignup;
    private static CheckBox terms_conditions;
    private static FragmentManager fragmentManager;

    public SignUp_Fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_signup, container, false);
        ButterKnife.bind(getActivity());

        //For Go Back to previous fragment
        if (((MainActivity) getActivity()).getSupportActionBar() != null) {
            ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //For Boom Menu Button
        ((MainActivity) getActivity()).boomMenuButton.setVisibility(View.GONE);
        ((MainActivity) getActivity()).mTitleTextView.setVisibility(View.GONE);
        ((MainActivity) getActivity()).setActionBarTitle("Promo Codes");

        initViews();
        setListeners();
        return view;
    }

    // Initialize all views
    private void initViews() {
        fragmentManager = getActivity().getSupportFragmentManager();
        progressDialog = new ProgressDialog(getActivity());
        UserName = (EditText) view.findViewById(R.id.UserName);
        emailId = (EditText) view.findViewById(R.id.userEmailId);
        mobileNumber = (EditText) view.findViewById(R.id.mobileNumber);
        password = (EditText) view.findViewById(R.id.password);
        confirmPassword = (EditText) view.findViewById(R.id.confirmPassword);
        btnSignup = (Button) view.findViewById(R.id.signUpBtn);
        already_user = (TextView) view.findViewById(R.id.already_user);
        terms_conditions = (CheckBox) view.findViewById(R.id.terms_conditions);

        // Setting text selector over textviews
        XmlResourceParser xrp = getResources().getXml(R.color.text_selector);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(),
                    xrp);

            already_user.setTextColor(csl);
            terms_conditions.setTextColor(csl);
        } catch (Exception e) {
        }
    }

    // Set Listeners
    private void setListeners() {
        btnSignup.setOnClickListener(this);
        already_user.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signUpBtn:

                // Call checkValidation method
                checkValidation();
                break;

            case R.id.already_user:

                // Replace already_user fragment
                new MainActivity().replaceLoginFragment();
                break;
        }

    }

    // Check Validation Method
    private void checkValidation() {

        // Get all edittext texts
        final String getFullName = UserName.getText().toString();
        final String getEmailId = emailId.getText().toString();
        final String getMobileNumber = mobileNumber.getText().toString();
        String getPassword = password.getText().toString();
        final String getConfirmPassword = confirmPassword.getText().toString();

        // Pattern match for email id
        Pattern p = Pattern.compile(Utils.regEx);
        Matcher m = p.matcher(getEmailId);

        // Check if all strings are null or not
        if ((getFullName.isEmpty() || getFullName.equals("") || getFullName.length() == 0)
                && (getEmailId.isEmpty() || getEmailId.equals("") || getEmailId.length() == 0)
                && (getMobileNumber.isEmpty() || getMobileNumber.equals("") || getMobileNumber.length() == 0)
                && (getPassword.isEmpty() || getPassword.equals("") || getPassword.length() == 0)
                && (getConfirmPassword.isEmpty() || getConfirmPassword.equals("")
                || getConfirmPassword.length() == 0)) {
            TastyToast.makeText(getActivity(), "All fields are required.",
                    TastyToast.LENGTH_SHORT, TastyToast.ERROR).show();
        }

        // Check if Username id valid or not
        if (UserName.getText().toString().isEmpty()) {
            UserName.setError("Enter user name.");
        } else if (UserName.getText().toString().contains(" ")) {
            UserName.setError("Space are not allowed.");
        } else {
            UserName.setError(null);
        }

        // Check if email id valid or not
        if (!m.find()) {
            emailId.setError("Enter a valid email address.");
        } else {
            emailId.setError(null);
        }

        // Check if Mobile Number id valid or not
        if (getMobileNumber.isEmpty()) {
            mobileNumber.setError("Enter Mobile Number.");
        } else if (getMobileNumber.contains(" ")) {
            mobileNumber.setError("Space are not allowed.");
        } else if (getMobileNumber.length() <= 10) {
            count = count + 1;
            mobileNumber.setError("Enter valid phone number.");
        } else if (count > 1 &&
                getMobileNumber.length() <= 10) {
            mobileNumber.setError("Enter 11 digits phone number.");
        } else {
            mobileNumber.setError(null);
        }

        // Check if password should be greater than 3
        if (getPassword.isEmpty()) {
            password.setError("Enter your password.");
        } else if (getPassword.length() <= 4) {
            password.setError("Password should be greater than 3.");
        } else {
            password.setError(null);
        }

        // Check if both password should be equal
        if (!getConfirmPassword.equals(getPassword)) {
            confirmPassword.setError("Both paassword does not match");
        } else {
            confirmPassword.setError(null);
        }

        // Make sure user should check Terms and Conditions checkbox
        if (!UserName.getText().toString().isEmpty() &&
                !emailId.getText().toString().isEmpty() &&
                !password.getText().toString().isEmpty() &&
                getConfirmPassword.equals(getPassword) &&
                !mobileNumber.getText().toString().isEmpty() &&
                !terms_conditions.isChecked()) {
            TastyToast.makeText(getActivity(), "Terms and conditions not checked.",
                    TastyToast.LENGTH_SHORT, TastyToast.WARNING).show();
        }

        // Else do signup or do your stuff
        if (!UserName.getText().toString().isEmpty() &&
                !emailId.getText().toString().isEmpty() &&
                !password.getText().toString().isEmpty() &&
                !confirmPassword.getText().toString().isEmpty() &&
                !mobileNumber.getText().toString().isEmpty() &&
                terms_conditions.isChecked()) {
//            TastyToast.makeText(getActivity(), "Do SignUp.",
//                    TastyToast.LENGTH_SHORT, TastyToast.SUCCESS).show();
            progressDialog.setMessage("Registering user...");
            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    Constants.URL_REGISTER,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                TastyToast.makeText(getActivity(), jsonObject.getString("message")
                                        , Toast.LENGTH_SHORT, TastyToast.SUCCESS).show();
                                fragmentManager
                                        .beginTransaction()
                                        .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                                        .replace(R.id.frameContainer, new Login_Fragment(),
                                                Utils.Login_Fragment).commit();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.hide();
                            TastyToast.makeText(getActivity(), error.getMessage()
                                    , Toast.LENGTH_SHORT, TastyToast.ERROR).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("username", getFullName);
                    params.put("email", getEmailId);
                    params.put("mobile", getMobileNumber);
                    params.put("password", getConfirmPassword);
                    return params;
                }
            };
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);

            RequestHandler.getInstance(getActivity()).addToRequestQueue(stringRequest);
        }
    }
}