package com.owulanii.androidlogin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.util.ArrayList;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import prefs.UserInfo;
import prefs.FraisInfo;
import prefs.UserSession;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.widget.Toast;

import prefs.UserInfo;
import prefs.FraisInfo;
import prefs.UserSession;

public class AfficherFrais extends AppCompatActivity {
    private String TAG = AjoutFrais.class.getSimpleName();
    ProgressDialog pDialog;
    private FraisInfo fraisInfo;
    private FraisInfo fraisInfo2;
    private UserInfo userInfo;
    private UserInfo userInfo2;


    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();
    ListView lv;
    ArrayList<HashMap<String, String>> productsList;
    // url to get all products list
    private static String url_all_products = "http://77.136.61.170/afficherinfos/afficher.php";
    // products JSONArray
    JSONArray products = null;
    int error=0;
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCTS = "plante";
    private static final  String TAG_FONCTION = "fonction";
    private static final  String TAG_UTILISATEUR = "utilisateur";
    private static final String TAG_PID = "ID_frais";
    private static final String TAG_NAME = "motif";
    private static final String TAG_NATURE = "date_frais";
    private static final String TAG_TRAJET = "trajet";
    private static final String TAG_PEAGE = "peage";
    private static final String TAG_ESSENCE = "essence";
    private static final String TAG_HEBERGEMENT = "hebergement";
    private static final String TAG_COUT_TRAJET = "cout_trajet";
    private static final String TAG_REPAS = "repas";
    private static final String TAG_TOTAL = "total";
    private static final String TAG_STATUS = "status";
    private static final String TAG_KM_PARCOURUS = "km_parcourus";

    @Override

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.centent_main);

        // Hashmap for ListView.
        productsList = new ArrayList<HashMap<String, String>>();
        userInfo        = new UserInfo(this);

        HashMap<String, String> params = new HashMap<String, String>();

        // Get listview
        lv = (ListView)findViewById(R.id.listView);
        // Loading products in Background Thread
        new LoadAllProducts().execute();


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                HashMap<String,String> map=new HashMap<String, String>();
                map=(HashMap<String,String>)lv.getAdapter().getItem(position);
                String fonction = map.get(TAG_FONCTION);
                String utilisateur = map.get(TAG_UTILISATEUR);
                String ID_frais = map.get(TAG_PID);
                String motif = map.get(TAG_NAME);
                String date_frais = map.get(TAG_NATURE);
                String trajet = map.get(TAG_TRAJET);
                String essence = map.get(TAG_ESSENCE);
                String peage = map.get(TAG_PEAGE);
                String cout_trajet = map.get(TAG_COUT_TRAJET);
                String hebergement = map.get(TAG_HEBERGEMENT);
                String repas = map.get(TAG_REPAS);
                String status = map.get(TAG_STATUS);
                String total = map.get(TAG_TOTAL);
                String km_parcourus = map.get(TAG_KM_PARCOURUS);


                // Toast.makeText(AfficherFrais.this,ide,Toast.LENGTH_LONG).show();

                Intent i =new Intent(getApplicationContext(),DetailFrais.class);

                i.putExtra("ID_frais",ID_frais);
                i.putExtra("motif",motif);
                i.putExtra("utilisateur",utilisateur);
                i.putExtra("fonction",fonction);
                i.putExtra("date_frais",date_frais);
                i.putExtra("peage",peage);
                i.putExtra("hebergement",hebergement);
                i.putExtra("total",total);
                i.putExtra("status",status);
                i.putExtra("essence",essence);
                i.putExtra("cout_trajet",cout_trajet);
                i.putExtra("repas",repas);
                i.putExtra("trajet",trajet);
                i.putExtra("km_parcourus",km_parcourus);

                startActivity(i);
                finish();
            }
        });

    }


    /*private void donnerinfos(final String utilisateur){
        StringRequest strReq = new StringRequest(Request.Method.DEPRECATED_GET_OR_POST,
                Utils.AFFICHER_URL, new Response.Listener<String>() {

            public void onResponse(String response) {
                Log.d(TAG, "Affichage de donner infos: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    jObj.put("utilisateur", utilisateur);


                        // Inserting row in frais table

                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
            }
        }) {
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();
                params.put("utilisateur", userInfo.getKeyId());
                return params;
            }

        };
        AndroidLoginController.getInstance().addToRequestQueue(strReq);
    }*/





    class LoadAllProducts extends
            AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AfficherFrais.this);
            pDialog.setMessage("Chargement ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }


        /**         * getting All products from url         * */
        protected String doInBackground(String... args) {


            // Building Parameters
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("utilisateur", userInfo.getKeyId());
            params.put("fonction", userInfo.getKeyFonction());


            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_all_products, "POST", params);
              //JSONObject json = jParser.makeHttpRequest(url_all_products, "GET", params);

            // Check your log cat for JSON reponse
           Log.d("All Products: ", json.toString());


            // Checking for SUCCESS TAG
            int success = 0;

            try {
                success = json.getInt(TAG_SUCCESS);



                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    products = json.getJSONArray(TAG_PRODUCTS);

                    // looping through All Products
                    for (int i = 0; i < products.length(); i++) {
                        JSONObject c = products.getJSONObject(i);

                        // Storing each json item in variable
                        String ID_frais = c.getString(TAG_PID);
                        String utilisateur = userInfo.getKeyId();
                        String fonction = userInfo.getKeyFonction();
                        String motif = c.getString(TAG_NAME);
                        String date_frais=c.getString(TAG_NATURE);
                        String trajet =c.getString(TAG_TRAJET);
                        String hebergement =c.getString(TAG_HEBERGEMENT);
                        String repas =c.getString(TAG_REPAS);
                        String cout_trajet =c.getString(TAG_COUT_TRAJET);
                        String status =c.getString(TAG_STATUS);
                        String total =c.getString(TAG_TOTAL);
                        String essence =c.getString(TAG_ESSENCE);
                        String peage =c.getString(TAG_PEAGE);
                        String km_parcourus =c.getString(TAG_KM_PARCOURUS);


                        // creating new HashMap
                        params = new HashMap<>();
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put(TAG_PID, ID_frais);
                        map.put(TAG_NAME, motif);
                        map.put(TAG_FONCTION,fonction);
                        map.put(TAG_NATURE,date_frais);
                        map.put(TAG_TRAJET,trajet);
                        map.put(TAG_COUT_TRAJET,cout_trajet);
                        map.put(TAG_PEAGE,peage);
                        map.put(TAG_ESSENCE,essence);
                        map.put(TAG_HEBERGEMENT,hebergement);
                        map.put(TAG_TOTAL,total);
                        map.put(TAG_STATUS,status);
                        map.put(TAG_REPAS,repas);
                        map.put(TAG_KM_PARCOURUS,km_parcourus);
                        map.put(TAG_UTILISATEUR,utilisateur);

                        // adding HashList to ArrayList
                        productsList.add(map);
                    }

                } else {

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override

        /**    * After completing

         background task Dismiss the progress dialog         * **/

        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            ListAdapter adapter = new SimpleAdapter(
                    AfficherFrais.this, productsList,
                    R.layout.item, new String[] { TAG_PID,
                    TAG_NAME,TAG_NATURE},
                    new int[] { R.id.pid, R.id.nom,R.id.nature });
            lv.setAdapter(adapter);

        }

    }
}