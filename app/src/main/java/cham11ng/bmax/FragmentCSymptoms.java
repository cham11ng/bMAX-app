package cham11ng.bmax;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCSymptoms extends Fragment {

    private String URL = "https://schamling.com.np/bMAX/wservice";

    EditText txtName;
    RadioButton rbtnMale, rbtnFemale;
    Spinner spSymptoms, spAgeGroup;
    Button btnCheck;
    TextView txtResult;
    String name, gender, agroup, symptoms, responseArray;

    public FragmentCSymptoms() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_csymptoms, container, false);
        txtName = (EditText) view.findViewById(R.id.txtName);

        rbtnMale = (RadioButton) view.findViewById(R.id.rbMale);
        rbtnFemale = (RadioButton) view.findViewById(R.id.rbFemale);

        spSymptoms = (Spinner) view.findViewById(R.id.spSymptoms);
        spAgeGroup = (Spinner) view.findViewById(R.id.spAgeGroup);

        txtResult = (TextView) view.findViewById(R.id.txtResult);

        if (!isNetworkAvailable()) {
            Toast.makeText(getContext(), "No Internet Access", Toast.LENGTH_SHORT).show();
        }

        else {
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://schamling.com.np/bMAX/allsymptoms/",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Type type = new TypeToken<List<Symptoms>>() {}.getType();
                            Gson gson = new Gson();
                            List<Symptoms> list = gson.fromJson(response, type);
                            List<String> dList = new ArrayList<String>();
                            for (Symptoms x : list) {
                                dList.add(x.getSymptom_name());
                            }
                            //System.out.println(dList.size());
                            spSymptoms.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, dList));
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });

            requestQueue.add(stringRequest);

            RequestQueue requestQueue1 = Volley.newRequestQueue(getActivity());
            StringRequest stringRequest1 = new StringRequest(Request.Method.GET, "https://schamling.com.np/bMAX/agegroups/",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Type type1 = new TypeToken<List<AgeGroups>>() {}.getType();
                            Gson gson1 = new Gson();
                            List<AgeGroups> list1 = gson1.fromJson(response, type1);
                            List<String> dList1 = new ArrayList<String>();
                            for (AgeGroups x : list1) {
                                dList1.add(x.getAge_group());
                            }
                            //System.out.println(dList.size());
                            spAgeGroup.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, dList1));
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });

            requestQueue1.add(stringRequest1);

            btnCheck = (Button) view.findViewById(R.id.btnCheck);
            txtResult = (TextView) view.findViewById(R.id.txtResult);
            btnCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    submitData();
                    /*Intent i = new Intent(getContext(), ReportActivity.class);
                    i.putExtra("name", name);
                    i.putExtra("agroup", agroup);
                    i.putExtra("gender", gender);
                    i.putExtra("symptoms", symptoms);
                    i.putExtra("diseases", responseArray);
                    startActivity(i);*/

                    txtResult.setText(responseArray);
                }
            });
        }
        return view;
    }

    private void submitData() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest sr = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                responseArray = response;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                name = txtName.getText().toString();
                params.put("name", name);

                String gender = "";
                if (rbtnMale.isChecked()) {
                    gender = "Male";
                }
                else if (rbtnFemale.isChecked()) {
                    gender = "Female";
                }

                params.put("gender", gender);
                agroup = spAgeGroup.getSelectedItem().toString();
                symptoms = spSymptoms.getSelectedItem().toString();
                params.put("agroup", agroup);
                params.put("symptoms", symptoms);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                //change second paramater to application/json if sending json data to php
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(sr);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
