package com.example.android.ukforce;

import android.text.TextUtils;
import android.util.Log;

import com.example.android.ukforce.force_details.Force;
import com.example.android.ukforce.specificforce_details.Specificforce;
import com.example.android.ukforce.specificforce_details.specificforce_contact;

import org.json.JSONArray;
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
import java.util.ArrayList;
import java.util.List;
//HELPER CLASS
public final class Connect {
    private Connect(){}
    private static URL createUrl(String stringurl)
        { URL url=null;
        try{
            url=new URL(stringurl);

        }catch (MalformedURLException e)
        {Log.e("Connect","Problem in building URL",e);
        }
        return url;
        }
        private static String makeHttpRequest(URL url)throws IOException
        {
            String jsonResponse="";
            if(url==null)
            {return jsonResponse;}
            HttpURLConnection urlConnection=null;
            InputStream inputStream=null;
            try{
                urlConnection=(HttpURLConnection)url.openConnection();
                urlConnection.setReadTimeout(100000);
                urlConnection.setConnectTimeout(100000);
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                if(urlConnection.getResponseCode()==200)
                {
                    inputStream=urlConnection.getInputStream();
                    jsonResponse=readInputStream(inputStream);
                }else{
                    Log.e("Connect","Error response code:"+urlConnection.getResponseCode());
                }
                }catch (IOException e)
            {Log.e("Connect","Problem retrieving Info",e);}
            finally {
                if(urlConnection!=null)
                {urlConnection.disconnect();}
                if(inputStream!=null)
                {inputStream.close();}
            }
            return jsonResponse;
        }
    private static String readInputStream (InputStream inputStream)throws IOException
    {StringBuilder finalstring=new StringBuilder();
    if(inputStream!=null)
    {
        InputStreamReader inputStreamReader=new InputStreamReader(inputStream,Charset.forName("UTF-8"));
        BufferedReader reader=new BufferedReader(inputStreamReader);
        String currentline=reader.readLine();
        while(currentline!=null)
        {
            finalstring.append(currentline);
            currentline=reader.readLine();
        }
    }
        return finalstring.toString();
    }
private static List<Force> extractJSON(String jsonResponse)
{
    if (TextUtils.isEmpty(jsonResponse))
    {
          return null;
    }
    List<Force> forces=new ArrayList<>();
        try{
            JSONArray root=new JSONArray(jsonResponse);
            for(int i=0;i<root.length();i++)
            {
                JSONObject forcedetails=root.getJSONObject(i);
                String id=forcedetails.getString("id");
                String name=forcedetails.getString("name");
                Force current=new Force(id,name);
                forces.add(current);
            }

             }catch(JSONException e)
        {Log.e("Connect","Problem in parsing",e);}
        return forces;

        }
public static List<Force> fetchdata(String requestedurl)
{
    URL url =createUrl(requestedurl);
    String jsonResponse=null;
    try{ jsonResponse=makeHttpRequest(url);

    }catch(IOException e){
        Log.e("Connect","Problem making HTTP request",e);
}
List<Force> forces=extractJSON(jsonResponse);
    return forces;
}
private static Specificforce ExtractSpecificForce(String jsonResponse)
{ if (TextUtils.isEmpty(jsonResponse))
{
    return null;
}
String url=null;
String phone=null;
String name=null;
String description=null;
    Specificforce current = null;
    ArrayList<specificforce_contact> contacts=new ArrayList<>();
    try{
    JSONObject root=new JSONObject(jsonResponse);
     description=root.getString("description");
 url=root.getString("url");
 phone=root.getString("telephone");
 name=root.getString("name");
 JSONArray methods=root.getJSONArray("engagement_methods");
for(int i=0;i<methods.length();i++)
{JSONObject contact=methods.getJSONObject(i);
 String typeurl=contact.getString("url");
 String title=contact.getString("title");
 String details=contact.getString("description");
 specificforce_contact specificforceContact=new specificforce_contact(typeurl,details,title);
contacts.add(specificforceContact);
}
     current=new Specificforce(name,description,phone,url,contacts);

} catch (JSONException e) {
    Log.e("Connect","Problem parsing",e);
}
return current;
}
public static Specificforce fetchspecificdata(String requestedUrl)
{
    URL url =createUrl(requestedUrl);
    String jsonResponse=null;
    try{ jsonResponse=makeHttpRequest(url);

    }catch(IOException e){
        Log.e("Connect","Problem making HTTP request",e);
    }
Specificforce force=ExtractSpecificForce(jsonResponse);
    return  force;
}
    private static List<crime> extractcrimeJSON(String jsonResponse)
    {
        if (TextUtils.isEmpty(jsonResponse))
        {
            return null;
        }
        List<crime> crimes=new ArrayList<>();
        try{
            JSONArray root=new JSONArray(jsonResponse);
            for(int i=0;i<root.length();i++)
            { JSONObject one=root.getJSONObject(i);
              String category=one.getString("category");
              String location_type=one.getString("location_type");
              String crime_context=one.getString("context");
              String outcome=one.getString("outcome_status");
              String id=one.getString("persistent_id");
              String locationsubtype=one.getString("location_subtype");
              String month=one.getString("month");
              JSONObject location=one.getJSONObject("location");
              String latitude=location.getString("latitude");
              String longitude=location.getString("longitude");
              JSONObject street=location.getJSONObject("street");
              String name=street.getString("name");
              crime Crimes=new crime(name,month,category,id,latitude,longitude,location_type,crime_context,locationsubtype,outcome);
              crimes.add(Crimes);
              }
        }catch(JSONException e)
        {Log.e("Connect","Problem in parsing",e);}
        return crimes;
    }
    public static List<crime> fetchcrimedata(String requestedurl)
    {
        URL url =createUrl(requestedurl);
        String jsonResponse=null;
        try{ jsonResponse=makeHttpRequest(url);
        }catch(IOException e){
            Log.e("Connect","Problem making HTTP request",e);
        }
        List<crime> Crimes=extractcrimeJSON(jsonResponse);
        return Crimes;
    }
    private static arrayoutcomes extractcrimeoutcomeJSON(String jsonResponse)
    {
        if (TextUtils.isEmpty(jsonResponse))
        {
            return null;
        }
        arrayoutcomes current=null;
        ArrayList<crimeoutcome> crimes=new ArrayList<>();
        try{ JSONObject root=new JSONObject(jsonResponse);
              JSONArray out=root.getJSONArray("outcomes");
              for(int i=0;i<out.length();i++)
              { JSONObject outc=out.getJSONObject(i);
                 JSONObject outco=outc.getJSONObject("category");
                  String name=outco.getString("name");
                  String date=outc.getString("date");
                  String person_id=outc.getString("person_id");
                  crimeoutcome c=new crimeoutcome(name,date,person_id);
                  crimes.add(c);
              }
current=new arrayoutcomes(crimes);
             }
        catch(JSONException e)
        {Log.e("Connect","Problem in parsing",e);}
        return current;
    }
    public static arrayoutcomes fetchcrimeoutcomedata(String requestedurl)
    {
        URL url =createUrl(requestedurl);
        String jsonResponse=null;
        try{ jsonResponse=makeHttpRequest(url);
        }catch(IOException e){
            Log.e("Connect","Problem making HTTP request",e);
        }
        arrayoutcomes Crimes=extractcrimeoutcomeJSON(jsonResponse);
        return Crimes;
    }

}

