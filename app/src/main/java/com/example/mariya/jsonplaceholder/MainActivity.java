package com.example.mariya.jsonplaceholder;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public ListView list;
    public ArrayList<Contact> contactList = new ArrayList<>();
    public String jsonStr="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new GetContacts().execute("https://jsonplaceholder.typicode.com/users/");
    }

    private boolean isConnected () {
        ConnectivityManager cm = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        boolean isWifiConn = networkInfo.isConnected();
        networkInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        boolean isMobileConn = networkInfo.isConnected();

        Log.d("MainActivity", "Wifi connected: " + isWifiConn);
        Log.d("MainActivity","Mobile connected: " + isMobileConn);

        if (isWifiConn || isMobileConn) {
            return true;
        }else{
            return false;
        }
    }

    private class GetContacts extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if(!isConnected()){
                startActivity(new Intent(WifiManager.ACTION_PICK_WIFI_NETWORK));
            }
        }

        @Override
        protected String doInBackground(String... arg) {

            if(isConnected()){
                // Making a request to url and getting response
                HttpHandler sh = new HttpHandler();
                jsonStr = sh.makeServiceCall(arg[0]);
            }else{
                String filename="contacts.json";
                StringBuffer stringBuffer = new StringBuffer();

                try {
                    BufferedReader inputReader = new BufferedReader(new InputStreamReader(openFileInput(filename)));
                    String inputString;

                    while ((inputString = inputReader.readLine()) != null) {
                        stringBuffer.append(inputString);
                    }

                    jsonStr = stringBuffer.toString();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (ParseJson())
                return "AsyncTask finished";
            else
                return "Error";
        }

        @Override
        protected void onPostExecute(String retString) {
            super.onPostExecute(retString);

            Toast.makeText(MainActivity.this,retString,Toast.LENGTH_LONG).show();

            //findViewByIds
            list = (ListView) findViewById(R.id.list);

            //List Adapter
            ListAdapter adapter = new ItemAdapter(getApplicationContext(),android.R.layout.simple_list_item_1, contactList);
            list.setAdapter(adapter);

            WriteJsonInLocalStorage();

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapter, View view, final int position, long arg) {

                    //Toast.makeText(getApplicationContext(),contactList.get(position).getEmail(),Toast.LENGTH_LONG).show();

                    Intent i = new Intent(getApplicationContext(), second_activity.class);
                    i.putExtra("contact", (Parcelable) contactList.get(position));
                    startActivity(i);
                }
            });
        }


        public void WriteJsonInLocalStorage() {

            String filename="contacts.json";
            String data= jsonStr;

            FileOutputStream fos;

            try {
                fos = openFileOutput(filename, Context.MODE_PRIVATE);
                fos.write(data.getBytes());
                fos.close();

                Toast.makeText(getApplicationContext(), filename + " saved", Toast.LENGTH_LONG).show();

            } catch (Exception e) {
                Log.d("Main_activity",e.getMessage());
            }
        }

        public Boolean ParseJson () {
            try {
                JSONArray json = new JSONArray(jsonStr);

                   for (int i = 0; i < json.length(); i++) {

                   JSONObject c = json.getJSONObject(i);

                    String id = c.getString("id");
                    String name = c.getString("name");
                    String email = c.getString("email");
                    String username = c.getString("username");

                    contactList.add(new Contact(id, name, email, username));
                }

            } catch (final JSONException e) {
                Log.e("MainActivity", "Impossible to download the json file.");
                Toast.makeText(getApplicationContext(),"Impossible to download the json.",Toast.LENGTH_LONG).show();
                return false;
            }
            return true;
        }
    }


}
