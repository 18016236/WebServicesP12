package com.example.webservicesp12;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.loopj.android.http.*;
import cz.msebera.android.httpclient.*;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import cz.msebera.android.httpclient.entity.mime.Header;

public class MainActivity extends AppCompatActivity {

    private ListView lvContact;
    private ArrayList<Incident> alContact;
    private ArrayAdapter<Incident> aaContact;
    private AsyncHttpClient client;
    private FirebaseFirestore db;
    private CollectionReference colRef;
    private DocumentReference docRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvContact = (ListView) findViewById(R.id.listViewContact);
        client = new AsyncHttpClient();

        final CollectionReference colRef = db.collection("incidents");

        new AlertDialog.Builder(this)
                .setTitle("Upload to Firestore")
                .setMessage("Proceed to upload to Firestore?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for (Incident inc: alContact){
                            colRef.add(inc);
                        }
                        Toast.makeText(getApplicationContext(),"Incidents Uploaded successfully",Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(android.R.string.no,null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }

    private Context getActivity() {
        return null;
    }

    //refresh with latest contact data whenever this activity resumes
    @Override
    protected void onResume() {
        super.onResume();

		//TODO: call getListOfContacts.php to retrieve all contact details
        alContact = new ArrayList<Incident>();

        client.addHeader("AccountKey","5KNXscwzSEG5uUXEEg+l1g==");

        client.get("http://datamall2.mytransport.sg/ltaodataservice/TrafficIncidents",new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                try{
                    Log.i("JSON Results: ",response.toString());

                    for (int i=0; i<response.length(); i++){
                        JSONObject jsonObj = response.getJSONObject(String.valueOf(i));

                        String type = jsonObj.getString("Type");
                        double latitude = jsonObj.getDouble("Latitude");
                        double longtitude = jsonObj.getDouble("Longtitude");
                        String message = jsonObj.getString("Message");

                        Incident con = new Incident(type,latitude,longtitude,message);
                        alContact.add(con);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                aaContact = new IncidentAdapter(getApplicationContext(), R.layout.incident_row, alContact);
        lvContact.setAdapter(aaContact);


            }
        });
    }//end onResume


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.menu_add) {
            Intent intent = new Intent(getApplicationContext(), IncidentAdapter.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}