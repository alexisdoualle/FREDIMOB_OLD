package prefs;

import android.content.Context;
import android.content.SharedPreferences;
/**
 * Created by killi on 13/04/2017.
 */


public class FraisInfo {
    private static final String TAG = UserSession.class.getSimpleName();
    private static final String PREF_NAME = "fraisinfo";
    private static final String KEY_ID_FRAIS = "ID_frais";
    private static final String KEY_UTILISATEUR = "utilisateur";

    private static final String KEY_MOTIF = "motif";
    private static final String KEY_TRAJET = "trajet";
    private static final String KEY_KM_PARCOURUS = "km_parcourus";
    private static final String KEY_COUT_TRAJET = "cout_trajet";
    private static final String KEY_ESSENCE = "essence";
    private static final String KEY_PEAGE = "peage";
    private static final String KEY_REPAS = "repas";
    private static final String KEY_HEBERGEMENT = "hebergement";
    private static final String KEY_TOTAL = "total";
    private static final String KEY_STATUS = "status";
    private static final String KEY_DATE_FRAIS = "date_frais";


    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Context ctx;

    public FraisInfo(Context ctx) {
        this.ctx = ctx;
        prefs = ctx.getSharedPreferences(PREF_NAME, ctx.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void setId_frais(String ID_frais){
        editor.putString(KEY_ID_FRAIS, ID_frais);
        editor.apply();
    }

    public void setUtilisateur(String utilisateur){
        editor.putString(KEY_UTILISATEUR, utilisateur);
        editor.apply();
    }

    public void setMotif(String motif){
        editor.putString(KEY_MOTIF, motif);
        editor.apply();
    }

    public void setTrajet(String trajet){
        editor.putString(KEY_TRAJET, trajet);
        editor.apply();
    }

    public void setKm_parcourus(String km_parcourus){
        editor.putString(KEY_KM_PARCOURUS, km_parcourus);
        editor.apply();
    }

    public void setCout_trajet(String cout_trajet){
        editor.putString(KEY_COUT_TRAJET, cout_trajet);
        editor.apply();
    }

    public void setEssnce(String essence){
        editor.putString(KEY_ESSENCE, essence);
        editor.apply();
    }

    public void setPeage(String peage){
        editor.putString(KEY_PEAGE, peage);
        editor.apply();
    }

    public void setRepas(String repas){
        editor.putString(KEY_REPAS, repas);
        editor.apply();
    }

    public void setHebergement(String hebergement){
        editor.putString(KEY_HEBERGEMENT, hebergement);
        editor.apply();
    }

    public void setTotal(String total){
        editor.putString(KEY_TOTAL, total);
        editor.apply();
    }

    public void setStatus(String status){
        editor.putString(KEY_STATUS, status);
        editor.apply();
    }

    public void setDate_frais(String date_frais){
        editor.putString(KEY_DATE_FRAIS, date_frais);
        editor.apply();
    }

    public String getKeyId_frais(){return prefs.getString(KEY_ID_FRAIS, "");}
    public String getKeyUtilisateur(){return prefs.getString(KEY_UTILISATEUR, "");}
    public String getKeyMotif(){return prefs.getString(KEY_MOTIF, "");}
    public String getKeyTrajet(){return prefs.getString(KEY_TRAJET, "");}
    public String getKeyKmParcourus(){return prefs.getString(KEY_KM_PARCOURUS, "");}
    public String getKeyCoutTrajet(){return prefs.getString(KEY_COUT_TRAJET, "");}
    public String getKeyEssence(){return prefs.getString(KEY_ESSENCE, "");}
    public String getKeyPeage(){return prefs.getString(KEY_PEAGE, "");}
    public String getKeyRepas(){return prefs.getString(KEY_REPAS, "");}
    public String getKeyHebergement(){return prefs.getString(KEY_HEBERGEMENT, "");}
    public String getKeyTotal(){return prefs.getString(KEY_TOTAL, "");}
    public String getKeyStatus(){return prefs.getString(KEY_STATUS, "");}
    public String getKeyDateFrais(){return prefs.getString(KEY_DATE_FRAIS, "");}
}