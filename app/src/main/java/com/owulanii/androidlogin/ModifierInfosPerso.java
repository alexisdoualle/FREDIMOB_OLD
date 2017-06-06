package com.owulanii.androidlogin;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.owulanii.androidlogin.AfficherFrais;
import com.owulanii.androidlogin.AjoutFrais;
import com.owulanii.androidlogin.Login;
import com.owulanii.androidlogin.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import prefs.UserInfo;
import prefs.UserSession;

public class ModifierInfosPerso extends AppCompatActivity {

    private String TAG = ModifierInfosPerso.class.getSimpleName();
    private ProgressDialog progressDialog;
    private Button modifierinfosperso;
    private EditText  et_nomm,et_prenomm,et_civilitem,et_fonctionm,et_datenaissancem,et_adressem,et_villem,et_cpm,et_mdpm;
    private int mYearIni, mMonthIni, mDayIni, sYearIni, sMonthIni, sDayIni;
    private UserInfo userInfo;
    private UserSession userSession;
    static final int DATE_ID = 0;
    Spinner spinner;
    Spinner spinner2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier_infos_perso);

        progressDialog  = new ProgressDialog(this);
        userInfo        = new UserInfo(this);
        userSession     = new UserSession(this);


//METHODE POUR LA GESTION DU SPINNER civilite
// Get reference of widgets from XML layout
        final Spinner spinner = (Spinner) findViewById(R.id.spinner_civilitem);

        // Initializing a String Array
        String[] plants = new String[]{
                "M.",
                "Mme",
        };

        final List<String> plantsList = new ArrayList<>(Arrays.asList(plants));
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.support_simple_spinner_dropdown_item,plantsList){
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position%2 == 1) {
                    // Set the item background color
                    tv.setBackgroundColor(Color.parseColor("#64b5f6"));
                }
                else {
                    // Set the alternate item background color
                    tv.setBackgroundColor(Color.parseColor("#90caf9"));
                }
                return view;
            }
        };

        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                String valueCvilite = userInfo.getKeySexe();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


//METHODE POUR LA GESTION DU SPINNER civilite
// Get reference of widgets from XML layout
        final Spinner spinner2 = (Spinner) findViewById(R.id.spinner_fonctionm);

        // Initializing a String Array
        String[] fonctions = new String[]{
                "Adherant",
                "Tresorier",
        };

        final List<String> fonctionList = new ArrayList<>(Arrays.asList(fonctions));

        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter<String>(
                this,R.layout.support_simple_spinner_dropdown_item,fonctionList){
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position%2 == 1) {
                    // Set the item background color
                    tv.setBackgroundColor(Color.parseColor("#64b5f6"));
                }
                else {
                    // Set the alternate item background color
                    tv.setBackgroundColor(Color.parseColor("#90caf9"));
                }
                return view;
            }
        };

        spinnerArrayAdapter2.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner2.setAdapter(spinnerArrayAdapter2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        et_nomm  = (EditText)findViewById(R.id.et_nomm);
        et_prenomm  = (EditText)findViewById(R.id.et_prenomm);
        et_datenaissancem  = (EditText)findViewById(R.id.et_datenaissancem);
        et_villem  = (EditText)findViewById(R.id.et_villem);
        et_cpm  = (EditText)findViewById(R.id.et_cpm);
        et_adressem  = (EditText)findViewById(R.id.et_adressem);
        et_mdpm  = (EditText)findViewById(R.id.et_mdpm);
        modifierinfosperso  = (Button)findViewById(R.id.modifierinfosperso);




        String mail = userInfo.getKeyEmail();
        String nom = userInfo.getKeyNom();
        String prenom = userInfo.getKeyPrenom();
        String datenaissance = userInfo.getKeyDatenaissance();
        String ville = userInfo.getKeyVille();
        String cp = userInfo.getKeyCp();
        String adresse = userInfo.getKeyAdresse();



        et_datenaissancem.setText(datenaissance);
        System.out.println("la date de naissance est : " +datenaissance);
        et_prenomm.setText(prenom);
        et_nomm.setText(nom);
        et_villem.setText(ville);
        et_cpm.setText(cp);
        et_adressem.setText(adresse);


        et_datenaissancem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDialog(DATE_ID);
            }
        });


        modifierinfosperso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //géstion des erreurs de saisies
                String fonction = spinner2.getSelectedItem().toString();
                String civilite = spinner.getSelectedItem().toString();

                    String mdp = et_mdpm.getText().toString().trim();
                    String nom = et_nomm.getText().toString().trim();
                    String prenom = et_prenomm.getText().toString().trim();
                    String dateNaissance = et_datenaissancem.getText().toString().trim();
                    String ville = et_villem.getText().toString().trim();
                    String adresse = et_adressem.getText().toString().trim();
                    String cp = et_cpm.getText().toString().trim();
                    String email = userInfo.getKeyEmail();
                    String ID = userInfo.getKeyId();
                    btn_modifier(ID,email,mdp,nom,prenom,dateNaissance,ville,adresse,cp,fonction,civilite);
        }
        });
    }

    private void btn_modifier (final String ID, final String email, final String mdp, final String nom, final String prenom, final String dateNaissance, final String ville, final String adresse, final String cp,final String fonction,final String civilite){
        // Tag used to cancel the request
        String tag_string_req = "req_login";
        progressDialog.setMessage("modification en cours ...");
        progressDialog.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                Utils.MODIFIER_URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        // Now store the user in SQLite
                        JSONObject user = jObj.getJSONObject("user");

                        String uNom = user.getString("nom");
                        String uPrenom = user.getString("prenom");
                        String uDateNaissance = user.getString("dateNaissance");
                        String adresse = user.getString("adresse");
                        String ville = user.getString("ville");
                        String cp = user.getString("CP");
                        String fonction = user.getString("fonction");
                        String civilite = user.getString("sexe");


                        // Inserting row in users table
                        userInfo.setNom(uNom);
                        userInfo.setPrenom(uPrenom);
                        userInfo.setDateNaissance(uDateNaissance);
                        userInfo.setVille(ville);
                        userInfo.setCp(cp);
                        userInfo.setAdresse(adresse);
                        userInfo.setSexe(civilite);
                        userInfo.setFonction(fonction);


                        progressDialog.hide();
                        toast("Succès de la modification.");
                        startActivity(new Intent(ModifierInfosPerso.this, MainActivity.class));
                        finish();
                    } else {
                        // Error in login. Get the error message
                        progressDialog.hide();
                        String errorMsg = jObj.getString("error_msg");
                        toast(errorMsg);

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
                toast("Problème de connexion au serveur.");
                progressDialog.hide();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("ID", ID);
                params.put("mdp", mdp);
                params.put("nom", nom);
                params.put("prenom", prenom);
                params.put("dateNaissance", dateNaissance);
                params.put("ville", ville);
                params.put("adresse", adresse);
                params.put("CP", cp);
                params.put("fonction", fonction);
                params.put("sexe", civilite);



                return params;
            }

        };

        // Adding request to request queue
        AndroidLoginController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }



    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    private void toast(String x){
        Toast.makeText(this, x, Toast.LENGTH_SHORT).show();
    }




    private void colocar_fecha() {
        et_datenaissancem.setText((mMonthIni + 1) + "-" + mDayIni + "-" + mYearIni+" ");
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