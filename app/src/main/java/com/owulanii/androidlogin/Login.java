package com.owulanii.androidlogin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import prefs.UserInfo;
import prefs.UserSession;

public class Login extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = Login.class.getSimpleName();
    private EditText email, password;
    private Button login;
    private TextView signup;
    private ProgressDialog progressDialog;
    private UserSession session;
    private UserInfo userInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email           = (EditText)findViewById(R.id.email);
        password        = (EditText)findViewById(R.id.password);
        login           = (Button)findViewById(R.id.login);
        signup          = (TextView)findViewById(R.id.open_signup);
        progressDialog  = new ProgressDialog(this);
        session         = new UserSession(this);
        userInfo        = new UserInfo(this);

        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        if(session.isUserLoggedin()){
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail4 = email.getText().toString().trim();

                //géstion des erreurs de saisies
                if(isEmailValid(mail4) ==false){
                    email.setError("ex : julien@dupont.fr");
                }
                if (password.length() < 3) {
                    password.setError("le mot de passe doit contenir au moins 3 caractères");
                } else {
                    String eemail = email.getText().toString().trim();
                    String epassword = password.getText().toString().trim();
                    login(eemail, epassword);
                }
            }
        });
        signup.setOnClickListener(this);
    }

    private void login(final String email, final String password){
        // Tag used to cancel the request
        String tag_string_req = "req_login";
        progressDialog.setMessage("Connexion en cours...");
        progressDialog.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                Utils.LOGIN_URL, new Response.Listener<String>() {

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
                        String uId = user.getString("ID");
                        String uNom = user.getString("nom");
                        String uPrenom = user.getString("prenom");
                        String uDateNaissance = user.getString("dateNaissance");
                        String uSexe = user.getString("sexe");
                        String email = user.getString("email");
                        String uFonction = user.getString("fonction");
                        String uVille = user.getString("ville");
                        String uCP = user.getString("CP");
                        String uLicense = user.getString("license");
                        String uAdresse = user.getString("adresse");


                        // Inserting row in users table
                      //  userInfo.setId(uId);

                        userInfo.setId(uId);
                        userInfo.setEmail(email);
                        userInfo.setFonction(uFonction);
                        userInfo.setDateNaissance(uDateNaissance);
                        userInfo.setVille(uVille);
                        userInfo.setCp(uCP);
                        userInfo.setNom(uNom);
                        userInfo.setPrenom(uPrenom);
                        userInfo.setSexe(uSexe);
                        userInfo.setLicense(uLicense);
                        userInfo.setAdresse(uAdresse);


                        session.setLoggedin(true);

                        startActivity(new Intent(Login.this, MainActivity.class));
                        finish();
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        toast(errorMsg);
                        progressDialog.hide();

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
                params.put("password", password);

                return params;
            }

        };

        // Adding request to request queue
        AndroidLoginController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                String uName = email.getText().toString().trim();
                String pass  = password.getText().toString().trim();

                login(uName, pass);
                break;
            case R.id.open_signup:
                startActivity(new Intent(this, SignUp.class));
                break;
        }
    }
    private void toast(String x){
        Toast.makeText(this, x, Toast.LENGTH_SHORT).show();
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
}
