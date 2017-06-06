package prefs;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 10/1/2016.
 */

public class UserInfo {
    private static final String TAG = UserSession.class.getSimpleName();
    private static final String PREF_NAME = "userinfo";
    private static final String KEY_ID = "ID";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_MDP = "mdp";
    private static final String KEY_NOM = "nom";
    private static final String KEY_PRENOM = "prenom";
    private static final String KEY_SEXE = "sexe";
    private static final String KEY_FONCTION = "fonction";
    private static final String KEY_LICENSE = "license";
    private static final String KEY_DATENAISSANCE = "dateNaissance";
    private static final String KEY_ADRESSE = "adresse";
    private static final String KEY_VILLE = "ville";
    private static final String KEY_CP = "cp";



    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Context ctx;

    public UserInfo(Context ctx) {
        this.ctx = ctx;
        prefs = ctx.getSharedPreferences(PREF_NAME, ctx.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void setId(String ID){
        editor.putString(KEY_ID, ID);
        editor.apply();
    }

    public void setEmail(String email){
        editor.putString(KEY_EMAIL, email);
        editor.apply();
    }


    public void setMdp(String mdp){
        editor.putString(KEY_MDP, mdp);
        editor.apply();
    }

    public void setNom(String nom){
        editor.putString(KEY_NOM, nom);
        editor.apply();
    }

    public void setPrenom(String prenom){
        editor.putString(KEY_PRENOM, prenom);
        editor.apply();
    }


    public void setSexe(String sexe){
        editor.putString(KEY_SEXE, sexe);
        editor.apply();
    }


    public void setFonction(String fonction){
        editor.putString(KEY_FONCTION, fonction);
        editor.apply();
    }

    public void setLicense(String license){
        editor.putString(KEY_LICENSE, license);
        editor.apply();
    }

    public void setDateNaissance(String dateNaissance){
        editor.putString(KEY_DATENAISSANCE, dateNaissance);
        editor.apply();
    }

    public void setAdresse(String adresse){
        editor.putString(KEY_ADRESSE, adresse);
        editor.apply();
    }

    public void setVille(String ville){
        editor.putString(KEY_VILLE, ville);
        editor.apply();
    }

    public void setCp(String cp){
        editor.putString(KEY_CP, cp);
        editor.apply();
    }

    public void clearUserInfo(){
        editor.clear();
        editor.commit();
    }

    public String getKeyId(){return prefs.getString(KEY_ID, "");}

    public String getKeyEmail(){return prefs.getString(KEY_EMAIL, "");}

    public String getKeyMdp(){return prefs.getString(KEY_MDP, "");}

    public String getKeyNom(){return prefs.getString(KEY_NOM, "");}

    public String getKeyPrenom(){return prefs.getString(KEY_PRENOM, "");}

    public String getKeySexe(){return prefs.getString(KEY_SEXE, "");}

    public String getKeyFonction(){return prefs.getString(KEY_FONCTION, "");}

    public String getKeyLicense(){return prefs.getString(KEY_LICENSE, "");}

    public String getKeyDatenaissance(){return prefs.getString(KEY_DATENAISSANCE, "");}

    public String getKeyAdresse(){return prefs.getString(KEY_ADRESSE, "");}

    public String getKeyVille(){return prefs.getString(KEY_VILLE, "");}

    public String getKeyCp(){return prefs.getString(KEY_CP, "");}


}
