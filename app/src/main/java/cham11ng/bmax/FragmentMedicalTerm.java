package cham11ng.bmax;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMedicalTerm extends Fragment {

    Spinner spDisease;
    TextView lblDiseaseName, lblDiseaseDefinition, ifNoInternetLayout;
    Button btnSearch;

    public FragmentMedicalTerm() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_medical_term, container, false);

        lblDiseaseName = (TextView) view.findViewById(R.id.lblDiseaseName);
        lblDiseaseDefinition = (TextView) view.findViewById(R.id.lblDiseaseDefinition);

        if (!isNetworkAvailable()) {
            Toast.makeText(getContext(), "No Internet Access", Toast.LENGTH_SHORT).show();
        }

        else {
            spDisease = (Spinner) view.findViewById(R.id.spDisease);

            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

            StringRequest stringRequest = new StringRequest(Request.Method.GET,
                    "https://schamling.com.np/bMAX/alldiseases/",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Type type = new TypeToken<List<Diseases>>() {
                            }.getType();
                            Gson gson = new Gson();
                            List<Diseases> list = gson.fromJson(response, type);
                            List<String> dList = new ArrayList<String>();
                            for (Diseases x : list) {
                                dList.add(x.getDisease_name());
                            }
                            System.out.println(dList.size());
                            spDisease.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, dList));
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
            requestQueue.add(stringRequest);

            btnSearch = (Button) view.findViewById(R.id.btnSearch);
            btnSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                RequestQueue queue = Volley.newRequestQueue(getContext());
                StringRequest request = new StringRequest(Request.Method.GET,
                        "https://www.schamling.com.np/bMAX/diseases/" + spDisease.getSelectedItem().toString() + "/",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Gson gson = new Gson();
                                DiseaseDescription disease = gson.fromJson(response, DiseaseDescription.class);
                                //System.out.println(disease.getDisease_name());
                                //System.out.println(response);
                                lblDiseaseName.setText(disease.getDisease_name());
                                lblDiseaseDefinition.setText(disease.getDisease_definition());

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                            }
                        });

                queue.add(request);
                }
            });
        }
        return view;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}