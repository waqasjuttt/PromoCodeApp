package com.example.waqasjutt.promocodewithfragment.PromoCodes.CareemPromoCodes;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.waqasjutt.promocodewithfragment.Constants;
import com.example.waqasjutt.promocodewithfragment.MainActivity;
import com.example.waqasjutt.promocodewithfragment.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Go_Fragment extends Fragment {

    private ProgressDialog progressDialog;
    private View view;
    private ListView lv;
    private ArrayAdapter<String> adapter;
    private InputStream is = null;
    private String line = null;
    private String result = null;
    private String[] data = null;
    private Constants constants = new Constants();

    public Go_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_go, container, false);

        progressDialog = new ProgressDialog(getActivity());

        //For Boom Menu Button
        ((MainActivity) getActivity()).boomMenuButton.setVisibility(View.GONE);
        ((MainActivity) getActivity()).mTitleTextView.setVisibility(View.GONE);
        ((MainActivity) getActivity()).setActionBarTitle("Go Codes");

        lv = (ListView) view.findViewById(R.id.listView);

        //Executing AsyncTask, passing api as parameter
        new PromoCodesAsyncTask().execute(constants.URL_CareemGo);

        //Allow Network in main thread
//        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
//        getData();

//        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, data);
//        lv.setAdapter(adapter);
        return view;
    }



    /*private void getData() {
        try {
            URL url = new URL(constants.URL_CareemGo);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");

            is = new BufferedInputStream(con.getInputStream());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();

            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            result = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            JSONArray ja = new JSONArray(result);
            JSONObject jo = null;

            data = new String[ja.length()];
            for (int i = 0; i < ja.length(); i++) {
                jo = ja.getJSONObject(i);
                data[i] = jo.getString("title")+"\ndiscount: "+jo.getString("discount");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    class PromoCodesAsyncTask extends AsyncTask<String, Void, String> {
        //This method will run on UIThread and it will execute before doInBackground
        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Please wait...");
        }

        //This method will run on background thread and after completion it will return result to onPostExecute
        @Override
        protected String doInBackground(String... params) {
            URL url = null;
            try {
//As we are passing just one parameter to AsyncTask, so used param[0] to get value at 0th position that is URL
                url = new URL(params[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

//Getting inputstream from connection, that is response which we got from server
                InputStream inputStream = urlConnection.getInputStream();
//Reading the response
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String s = bufferedReader.readLine();
                bufferedReader.close();
//Returning the response message to onPostExecute method
                return s;
            } catch (IOException e) {
                Log.e("Error: ", e.getMessage(), e);
            }
            return null;
        }

        //This method runs on UIThread and it will execute when doInBackground is completed
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            JSONObject jsonObject = null;
            try {
                progressDialog.dismiss();

//Parent JSON Object. Json object start at { and end at }
                jsonObject = new JSONObject(s);

                ArrayList<GoDetails> goArrayDetailses = new ArrayList<>();

//JSON Array of parent JSON object. Json array starts from [ and end at ]
                JSONArray jsonArray = jsonObject.getJSONArray("result");

//Reading JSON object inside Json array
                for (int i = 0; i < jsonArray.length(); i++) {

//Reading JSON object at 'i'th position of JSON Array
                    JSONObject object = jsonArray.getJSONObject(i);
                    GoDetails goDetails = new GoDetails();
                    goDetails.setOriginal_title(object.getString("title"));
                    goDetails.setOverview(object.getString("discription"));
                    goDetails.setDiscount(object.getString("discount"));
                    goArrayDetailses.add(goDetails);
                }
                //Creating custom array adapter instance and setting context of MainActivity, List item layout file and movie list.
                GoCustomAdapter goCustomAdapter = new GoCustomAdapter(getActivity(), R.layout.go_list, goArrayDetailses);

                //Setting adapter to listview
                lv.setAdapter(goCustomAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}