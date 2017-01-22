package com.tutorials.hp.mysql_datatypes_save;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Oclemy on 10/11/2016 for ProgrammingWizards Channel and http://www.camposha.com.
 */
public class MySQLClient {

    //SAVE/RETRIEVE URLS
    private static final String DATA_INSERT_URL="http://10.0.2.2/android/Columbia/crud.php";

    //INSTANCE FIELDS
    private final Context c;

    public MySQLClient(Context c) {
        this.c = c;

    }
    /*
    SAVE/INSERT
     */
    public void add(Spacecraft s, final View...inputViews)
    {
        if(s==null)
        {
            Toast.makeText(c, "No Data To Save", Toast.LENGTH_SHORT).show();
        }
        else
        {
            AndroidNetworking.post(DATA_INSERT_URL)
                    .addBodyParameter("action","save")
                    .addBodyParameter("name",s.getName())
                    .addBodyParameter("propellant",s.getPropellant())
                    .addBodyParameter("technologyexists",String.valueOf(s.getTechnologyExists()))
                    .setTag("TAG_ADD")
                    .build()
                    .getAsJSONArray(new JSONArrayRequestListener() {
                        @Override
                        public void onResponse(JSONArray response) {

                            if(response != null)
                                try {
                                    //SHOW RESPONSE FROM SERVER
                                    String responseString = response.get(0).toString();
                                    Toast.makeText(c, "PHP SERVER RESPONSE : " + responseString, Toast.LENGTH_SHORT).show();

                                    if (responseString.equalsIgnoreCase("Success")) {
                                        //RESET VIEWS
                                        EditText nameTxt = (EditText) inputViews[0];
                                        Spinner spPropellant = (Spinner) inputViews[1];

                                        nameTxt.setText("");
                                        spPropellant.setSelection(0);
                                    }else
                                    {
                                        Toast.makeText(c, "PHP WASN'T SUCCESSFUL. ", Toast.LENGTH_SHORT).show();

                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(c, "GOOD RESPONSE BUT JAVA CAN'T PARSE JSON IT RECEIVED : "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                        }

                        //ERROR
                        @Override
                        public void onError(ANError anError) {
                            Toast.makeText(c, "UNSUCCESSFUL :  ERROR IS : "+anError.getMessage(), Toast.LENGTH_SHORT).show();
                        }


                    });

        }
    }



}
