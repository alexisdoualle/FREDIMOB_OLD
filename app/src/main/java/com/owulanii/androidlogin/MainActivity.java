package com.owulanii.androidlogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

import prefs.UserInfo;
import prefs.UserSession;

public class MainActivity extends AppCompatActivity {

    private Button logout;
    private Button ajoutfrais;
    private Button afficherfrais;
    private Button modifierinfosperso;
    private Button gererfrais;
    private TextView tvUsername;
    private TextView tvId;
    private UserInfo userInfo;
    private UserSession userSession;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userInfo        = new UserInfo(this);
        userSession     = new UserSession(this);
        logout          = (Button)findViewById(R.id.logout);
        ajoutfrais      = (Button)findViewById(R.id.ajoutfrais);
        modifierinfosperso  = (Button)findViewById(R.id.btn_modifier);
        afficherfrais   = (Button)findViewById(R.id.afficherfrais);
        gererfrais      = (Button)findViewById(R.id.gererfrais);
        tvUsername      = (TextView)findViewById(R.id.key_username);
        tvId            = (TextView)findViewById(R.id.key_id);



        if(!userSession.isUserLoggedin()){
            startActivity(new Intent(this, Login.class));
            finish();
        }

        String bienvenue ="Bienvenue ";
        String espace =" ";

        String id = userInfo.getKeyId();
        String prenom = userInfo.getKeyPrenom();
        String nom = userInfo.getKeyNom();
        String sexe = userInfo.getKeySexe();

        ajoutfrais = (Button) findViewById(R.id.ajoutfrais);

        String verifierFonction = userInfo.getKeyFonction();
        System.out.println(verifierFonction);

        if(verifierFonction.equals("Adherant")){
            ajoutfrais.setVisibility(View.VISIBLE);
            afficherfrais.setVisibility(View.VISIBLE);
            gererfrais.setVisibility(View.INVISIBLE);
        }else{
            ajoutfrais.setVisibility(View.INVISIBLE);
            afficherfrais.setVisibility(View.INVISIBLE);
            afficherfrais.setVisibility(View.VISIBLE);
        }


        tvUsername.setText(bienvenue + sexe + espace + prenom + espace + nom);
        tvId.setText("Fonctionalités disponibles :");

        afficherfrais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AfficherFrais.class));
            }
        });

        ajoutfrais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AjoutFrais.class));
            }
        });

        gererfrais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AfficherFrais.class));
            }
        });

        modifierinfosperso.setOnClickListener(new View.OnClickListener() {
           public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ModifierInfosPerso.class));
            }
       });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userSession.setLoggedin(false);
                userInfo.clearUserInfo();
                startActivity(new Intent(MainActivity.this, Login.class));
                finish();
            }
         });
    }

}
