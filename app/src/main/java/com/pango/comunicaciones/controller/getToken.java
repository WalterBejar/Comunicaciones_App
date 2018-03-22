package com.pango.comunicaciones.controller;

import android.util.Log;

import com.pango.comunicaciones.GlobalVariables;
import com.pango.comunicaciones.Utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class getToken {

    public void getToken(){


        try {
            HttpResponse response;
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet get = new HttpGet(GlobalVariables.Urlbase+ GlobalVariables.url_token);
            get.setHeader("Content-type", "application/json");

            //////
            response = httpClient.execute(get);
            String respstring2 = EntityUtils.toString(response.getEntity());
            GlobalVariables.token_auth=respstring2.substring(1,respstring2.length()-1);

            if(respstring2.equals(""))
            {
                GlobalVariables.token_auth=null;
                GlobalVariables.con_status =0;
            }else {
                GlobalVariables.token_auth = respstring2.substring(1, respstring2.length() - 1);
                Utils.token=respstring2.substring(1, respstring2.length() - 1);
                GlobalVariables.con_status = httpClient.execute(get).getStatusLine().getStatusCode();
            }




        } catch (Throwable e) {
            Log.d("InputStream", e.getLocalizedMessage());        }

    }
}


