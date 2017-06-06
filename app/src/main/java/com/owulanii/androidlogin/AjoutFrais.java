package com.owulanii.androidlogin;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DialogTitle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.math.*;


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
import java.util.Calendar;
import android.app.DatePickerDialog;
import android.app.Dialog;

/**
 * Created by killi on 14/04/2017.
 */

public class AjoutFrais extends AppCompatActivity {
    private String TAG = AjoutFrais.class.getSimpleName();
    private EditText et_motif, et_trajet, et_essence,et_peage,et_km_parcourus,et_repas,et_hebergement,et_date_frais;
    private Button btn_ajouter;
    private ProgressDialog progressDialog;
    private UserSession session;
    private UserInfo userInfo;
    private FraisInfo fraisInfo;
    private TextView tvID;
    private int mYearIni, mMonthIni, mDayIni, sYearIni, sMonthIni, sDayIni;
    static final int DATE_ID = 0;

    Calendar C = Calendar.getInstance();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajoutfrais);

        et_motif        = (EditText)findViewById(R.id.et_idd);
        et_trajet       = (EditText)findViewById(R.id.et_trajet);
        et_essence      = (EditText)findViewById(R.id.et_essence);
        et_peage        = (EditText)findViewById(R.id.et_peage);
        et_repas        = (EditText)findViewById(R.id.et_repas);
        et_km_parcourus = (EditText)findViewById(R.id.et_km_parcourus);
        et_hebergement  = (EditText)findViewById(R.id.et_hebergement);
        et_date_frais   = (EditText)findViewById(R.id.et_date_frais);
        btn_ajouter     = (Button)findViewById(R.id.btn_ajouter);

        sMonthIni = C.get(Calendar.MONTH);
        sDayIni = C.get(Calendar.DAY_OF_MONTH);
        sYearIni = C.get(Calendar.YEAR);

        progressDialog  = new ProgressDialog(this);
        userInfo        = new UserInfo(this);
        fraisInfo       = new FraisInfo(this);

        et_date_frais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDialog(DATE_ID);
            }
        });

        btn_ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //géstion des erreurs de saisies
                if (et_motif.length() < 3) {
                    et_motif.setError("le motif doit contenir au moins 3 caractères");
                }
                if (et_trajet.length() < 3) {
                        et_trajet.setError("le trajet doit contenir au moins 3 caractères");
                }
                if (et_essence.length() == 0) {
                    et_essence.setError("ne doit pas etre vide");
                }
                if (et_peage.length() == 0) {
                    et_peage.setError("ne doit pas etre vide");
                }
                if (et_repas.length() == 0) {
                    et_repas.setError("ne doit pas etre vide");
                }
                if (et_hebergement.length() == 0) {
                    et_hebergement.setError("ne doit pas etre vide");
                }
                if (et_km_parcourus.length() == 0) {
                    et_km_parcourus.setError("ne doit pas etre vide");
                }
                if (et_date_frais.length() == 0) {
                    et_date_frais.setError("Une date doit etre entrée");

                } else {

                    String fMotif = et_motif.getText().toString().trim();
                    String fTrajet = et_trajet.getText().toString().trim();
                    String fEssence = et_essence.getText().toString().trim();
                    String fPeage = et_peage.getText().toString().trim();
                    String fKm_parcourus = et_km_parcourus.getText().toString().trim();
                    String fUtilisateur = userInfo.getKeyId();
                    String fRepas = et_repas.getText().toString().trim();
                    String fHebergement = et_hebergement.getText().toString().trim();
                    String fDate_frais = et_date_frais.getText().toString().trim();

                    //variable cout total trajet
                    double dEssence = Double.parseDouble(fEssence);
                    double dPeage = Double.parseDouble(fPeage);
                    Double cout_trajet = dEssence + dPeage;

                    //conversion a deux décimales
                    BigDecimal bd2 = new BigDecimal(cout_trajet);
                    bd2 = bd2.setScale(2, BigDecimal.ROUND_DOWN);
                    cout_trajet = bd2.doubleValue();

                    String cout_trajet_string = String.valueOf(cout_trajet);
                    String fCout_trajet = cout_trajet_string;


                    //variable total
                    double dRepas = Double.parseDouble(fRepas);
                    double dHebergement = Double.parseDouble(fHebergement);
                    double total = cout_trajet + dRepas + dHebergement;

                    //conversion a deux décimales
                    BigDecimal bd = new BigDecimal(total);
                    bd = bd.setScale(2, BigDecimal.ROUND_DOWN);
                    total = bd.doubleValue();

                    String total_string = String.valueOf(total);
                    String fTotal = total_string;


                    btn_ajouter(fMotif, fTrajet, fKm_parcourus, fCout_trajet, fEssence, fPeage, fRepas, fHebergement, fUtilisateur, fDate_frais, fTotal);

                }
            }
        });


    }



    private void btn_ajouter(final String fMotif,final String fTrajet,final String fKm_parcourus,final String fCout_trajet,final String fEssence,final String fPeage,final String fRepas,final String fHebergement,final String fUtilisateur, final String fDate_frais, final String fTotal) {
        String tag_string_req = "req_signup";
        progressDialog.setMessage("Ajout du frais en cours...");
        progressDialog.show();


        StringRequest strReq = new StringRequest(Request.Method.POST,
                Utils.REGISTER_URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        JSONObject frais = jObj.getJSONObject("frais");

                        String fMotif = frais.getString("motif");
                        String fTrajet = frais.getString("trajet");
                        String fKm_parcourus = frais.getString("km_parcourus");
                        String fCout_trajet = frais.getString("cout_trajet");
                        String fEssence = frais.getString("essence");
                        String fPeage = frais.getString("peage");
                        String fRepas = frais.getString("repas");
                        String fHebergement = frais.getString("hebergement");
                        String fUtilisateur = frais.getString("utilisateur");
                        String fDate_frais = frais.getString("date_frais");
                        String fTotal = frais.getString("total");



                        // Inserting row in frais table
                        fraisInfo.setMotif(fMotif);
                        fraisInfo.setTrajet(fTrajet);
                        fraisInfo.setKm_parcourus(fKm_parcourus);
                        fraisInfo.setCout_trajet(fCout_trajet);
                        fraisInfo.setEssnce(fEssence);
                        fraisInfo.setPeage(fPeage);
                        fraisInfo.setRepas(fRepas);
                        fraisInfo.setHebergement(fHebergement);
                        fraisInfo.setUtilisateur(fUtilisateur);
                        fraisInfo.setDate_frais(fDate_frais);
                        fraisInfo.setTotal(fTotal);



                        //   session.setLoggedin(true);

                        startActivity(new Intent(AjoutFrais.this, MainActivity.class));
                        toast("Succès de l'ajout du frais !");
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        toast("Erreur passe ici");
                        toast(errorMsg);
                        progressDialog.hide();
                        startActivity(new Intent(AjoutFrais.this, AfficherFrais.class));


                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    toast("Json error: " + e.getMessage());
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                toast("Une erreur a été rencontrée");
                progressDialog.hide();
            }
        }) {


            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();
                params.put("motif", fMotif);
                params.put("trajet", fTrajet);
                params.put("km_parcourus", fKm_parcourus);
                params.put("cout_trajet", fCout_trajet);
                params.put("essence", fEssence);
                params.put("peage", fPeage);
                params.put("repas", fRepas);
                params.put("hebergement", fHebergement);
                params.put("utilisateur", fUtilisateur);
                params.put("date_frais", fDate_frais);
                params.put("total", fTotal);

                return params;
            }

        };

        // Adding request to request queue
        AndroidLoginController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void toast(String x){
        Toast.makeText(this, x, Toast.LENGTH_SHORT).show();
    }

    private void colocar_fecha() {
        et_date_frais.setText((mMonthIni + 1) + "-" + mDayIni + "-" + mYearIni+" ");
    }



    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    mYearIni = year;
                    mMonthIni = monthOfYear;
                    mDayIni = dayOfMonth;
                    colocar_fecha();

                }

            };


    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_ID:
                return new DatePickerDialog(this, mDateSetListener, sYearIni, sMonthIni, sDayIni);


        }


        return null;
    }
}