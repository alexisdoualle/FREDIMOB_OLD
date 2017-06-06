package com.owulanii.androidlogin;

import com.owulanii.androidlogin.AfficherFrais;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.view.KeyEventCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.*;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Button;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import prefs.FraisInfo;
import prefs.UserInfo;

/**
 * Created by killi on 19/04/2017.
 */

public class DetailFrais extends AppCompatActivity {
    private String TAG = DetailFrais.class.getSimpleName();
    private FraisInfo fraisInfo;
    private TextView et_motifd,et_idd,et_trajetd,et_km_total,cout_trajetd,et_peaged,et_hebergementd,et_repasd,et_totald,et_statusd,et_essenced,et_dated,et_cout_trajetd;
    private Button btn_supprimer;
    private Button btn_accueil;
    private Button btn_valider;
    private UserInfo userInfo;
    Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailfrais);

        //déclaration des éléments
        userInfo        = new UserInfo(this);
        btn_supprimer = (Button)findViewById(R.id.btn_supprimer);
        btn_accueil = (Button)findViewById(R.id.btn_accueil);
        btn_valider = (Button)findViewById(R.id.btn_valider);
        et_idd = (TextView)findViewById(R.id.et_idd);
        et_motifd = (TextView)findViewById(R.id.et_motifd);
        et_km_total = (TextView)findViewById(R.id.et_km_total);
        btn_supprimer = (Button)findViewById(R.id.btn_supprimer);
        et_dated = (TextView)findViewById(R.id.et_dated);
        et_trajetd = (TextView)findViewById(R.id.et_trajetd);
        et_cout_trajetd = (TextView)findViewById(R.id.et_cout_trajetd);
        et_essenced = (TextView)findViewById(R.id.et_essenced);
        et_peaged = (TextView)findViewById(R.id.et_peaged);
        et_hebergementd = (TextView)findViewById(R.id.et_hebergementd);
        et_totald = (TextView)findViewById(R.id.et_totald);
        et_statusd = (TextView)findViewById(R.id.et_statusd);
        et_repasd = (TextView)findViewById(R.id.et_repasd);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


//récupération des données de AfficherFrais.java
        Intent i = getIntent();
        final String ID_frais = i.getStringExtra(("ID_frais"));
        String motif = i.getStringExtra(("motif"));
        String date_frais = i.getStringExtra(("date_frais"));
        String trajet = i.getStringExtra(("trajet"));
        String repas = i.getStringExtra(("repas"));
        String cout_trajet = i.getStringExtra(("cout_trajet"));
        String hebergement = i.getStringExtra(("hebergement"));
        String essence = i.getStringExtra(("essence"));
        String peage = i.getStringExtra(("peage"));
        String total = i.getStringExtra(("total"));
        String status = i.getStringExtra(("status"));
        String km_total = i.getStringExtra(("km_parcourus"));


        //Affichage des données sur les édit texte
        et_motifd.setText      ("motif                 : " +motif);
        et_idd.setText         ("Identifiant        : " +ID_frais);
        et_dated.setText       ("Date                  : " +date_frais);
        et_trajetd.setText     ("Trajet                 : " +trajet);
        et_statusd.setText     ("Status                : " +status);
        et_peaged.setText      ("Peage                : " +peage +"€");
        et_totald.setText      ("Montant total   : " +total +"€");
        et_essenced.setText    ("Essence            : " +essence +"€");
        et_cout_trajetd.setText("Cout du trajet   : " +cout_trajet +"€");
        et_hebergementd.setText("Hebergement   : " +hebergement +"€");
        et_repasd.setText      ("Repas                : " +repas +"€");
        et_km_total.setText    ("KM total            : " +km_total +"km");




        //vérification de la fonction de la personne adherant/trésorier
        String verifierFonction = userInfo.getKeyFonction();



        if(verifierFonction.equals("Adherant")){
            btn_valider.setVisibility(View.INVISIBLE);
        }else{
            btn_valider.setVisibility(View.VISIBLE);
        }


        //action sur les boutons
        btn_valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_valider(ID_frais);
            }
        });

        btn_supprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn_supprimer(ID_frais);
            }
        });

        btn_accueil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent accueil =new Intent(getApplicationContext(),MainActivity.class);
                startActivity(accueil);
                finish();
            }
        });

        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void btn_supprimer(final String ID_frais){
        StringRequest strReq = new StringRequest(Request.Method.POST,
                Utils.SUPPRIMER_URL, new Response.Listener<String>() {

            public void onResponse(String response) {
                Log.d(TAG, "Affichage de donner infos: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    jObj.put("utilisateur", ID_frais);

                    startActivity(new Intent(DetailFrais.this, AfficherFrais.class));
                    toast("Succès de la suppression du frais");


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
                params.put("ID_frais", ID_frais);
                return params;
            }

        };
        AndroidLoginController.getInstance().addToRequestQueue(strReq);
    }




    private void btn_valider(final String ID_frais){
        StringRequest strReq = new StringRequest(Request.Method.POST,
                Utils.VALIDER_URL, new Response.Listener<String>() {

            public void onResponse(String response) {
                Log.d(TAG, "Affichage de donner infos: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    jObj.put("utilisateur", ID_frais);

                    startActivity(new Intent(DetailFrais.this, AfficherFrais.class));
                    toast("Succès de la validation du frais");


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
                params.put("ID_frais", ID_frais);
                return params;
            }

        };
        AndroidLoginController.getInstance().addToRequestQueue(strReq);
    }
    private void toast(String x){
        Toast.makeText(this, x, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, AfficherFrais.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}


