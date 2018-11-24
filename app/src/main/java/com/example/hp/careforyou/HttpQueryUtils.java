package com.example.hp.careforyou;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

public final class HttpQueryUtils {

    private static final String LOG_TAG = HttpQueryUtils.class.getSimpleName();

    HttpQueryUtils()
    { }

    public static NutritionItem FetchNutritionData(String Food_REQUEST_URL)
    {
        URL url = createurl(Food_REQUEST_URL);

        String jsonresponse = "";

        try {
            jsonresponse = makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        NutritionItem nutritionItem = extractFeatureFromJson(jsonresponse);

        return nutritionItem;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if(url==null)
            return jsonResponse;
        HttpURLConnection urlConnection =null;
        InputStream inputStream  = null;
        try {

            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();

            if(urlConnection.getResponseCode()==200)
            {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }

            else
            {
                Log.e(LOG_TAG, "Error Response Code : " + urlConnection.getResponseCode());
            }
        }
        catch (IOException e)
        {
            Log.e(LOG_TAG, "Problem retriving the Food json result",e);
        }
        finally {
            if(urlConnection!=null) {
                urlConnection.disconnect();
            }
            if (inputStream!=null)
            {
                inputStream.close();
            }
        }

        return  jsonResponse;

    }

    private static String readFromStream(InputStream inputStream) throws IOException {

        StringBuilder output =new StringBuilder();
        if(inputStream!=null){

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while(line!=null)
            {
                output.append(line);
                line=  bufferedReader.readLine();
            }
        }
        return output.toString();
    }

    private static URL createurl(String food_request_url) {
        URL url =null;

        try {
            url = new  URL(food_request_url);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL", e);
            return null;
        }
        return  url;
    }

    private static  NutritionItem extractFeatureFromJson(String jsonresponse)
    {
        if(TextUtils.isEmpty(jsonresponse)) {
            return null;
        }

            try
            {
                JSONObject jsonObject = new JSONObject(jsonresponse);
                String brandname =jsonObject.getString("brand_name");
                String itemname = jsonObject.getString("item_name");
                String water =  jsonObject.getString("nf_water_grams");
                String totalfat =  jsonObject.getString("nf_total_fat");
                String energy =  jsonObject.getString("nf_calories");
                String salt =  jsonObject.getString("nf_sodium");
                String sugar =  jsonObject.getString("nf_sugars");
                String choles = jsonObject.getString("nf_cholesterol");
                String charbo = jsonObject.getString("nf_total_carbohydrate");
                String fiber = jsonObject.getString("nf_dietary_fiber");
                String protien = jsonObject.getString("nf_protein");
                String vitaminA = jsonObject.getString("nf_vitamin_a_dv");


                return new NutritionItem(brandname,itemname,water,totalfat,energy,salt,sugar
                                             ,choles,charbo,fiber,protien,vitaminA);
            }
            catch (JSONException e) {
                Log.e(LOG_TAG, "Problem parsing the earthquake JSON results", e);
                //return null;
            }

            return null;
    }
}
