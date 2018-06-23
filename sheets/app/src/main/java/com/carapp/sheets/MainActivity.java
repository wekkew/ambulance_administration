//MainActivity.java

package com.carapp.sheets;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.Time;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.carapp.sheets.adapter.MyArrayAdapter;
import com.carapp.sheets.model.MyDataModel;
import com.carapp.sheets.parser.JSONparser;
import com.carapp.sheets.util.InternetConnection;
import com.carapp.sheets.util.Keys;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import static java.lang.Integer.parseInt;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<MyDataModel> list;
    private MyArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /**
         * Array List for Binding Data from JSON to this List
         */
        list = new ArrayList<>();
        /**
         * Binding that List to Adapter
         */
        adapter = new MyArrayAdapter(this, list);

        /**
         * Getting List and Setting List Adapter
         */
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Snackbar.make(findViewById(R.id.parentLayout), list.get(position).getName() + " => " + list.get(position), Snackbar.LENGTH_LONG).show();
            }
        });

        /**
         * Checking Internet Connection
         */
        boolean intConn = InternetConnection.checkConnection((getApplicationContext()));

        if (intConn) {
            new GetDataTask().execute();
        } else {
            Snackbar.make(findViewById(R.id.parentLayout), "Internet Connection Not Available", Snackbar.LENGTH_LONG).show();
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(@NonNull View view) {
                addActivity();
            }
        });
    }
    public void addActivity() {
        Intent intent = new Intent(this, AddActivity.class);

        startActivity(intent);
    }
    /**
     * Creating Get Data Task for Getting Data From Web
     */
    class GetDataTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;
        int jIndex;
        int x;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /**
             * Progress Dialog for User Interaction
             */

            x=list.size();

            if(x==0)
                jIndex=0;
            else
                jIndex=x;

            dialog = new ProgressDialog(MainActivity.this);
            dialog.setTitle("Hey Wait Please..."+x);
            dialog.setMessage("I am getting your JSON");
            dialog.show();
        }

        @Nullable
        @Override
        protected Void doInBackground(Void... params) {

            /**
             * Getting JSON Object from Web Using okHttp
             */
            JSONObject jsonObject = JSONparser.getDataFromWeb();

            try {
                /**
                 * Check Whether Its NULL???
                 */
                if (jsonObject != null) {
                    /**
                     * Check Length...
                     */
                    if(jsonObject.length() > 0) {
                        /**
                         * Getting Array named "contacts" From MAIN Json Object
                         */

                        Iterator<String> keys = jsonObject.keys();
                        while(keys.hasNext()) {
                            JSONArray array = jsonObject.getJSONArray(keys.next());
                            arrayOperations(array);
                        }
                    }
                } else {

                }
            } catch (JSONException je) {
                Log.i(JSONparser.TAG, "" + je.getLocalizedMessage());
            }
            return null;
        }

        private void arrayOperations(JSONArray array) {
            try {

                int lenArray = array.length();
                if(lenArray > 0) {
                    for( ; jIndex < lenArray; jIndex++) {

                        MyDataModel model = new MyDataModel();

                        JSONObject innerObject = array.getJSONObject(jIndex);
                        String name = innerObject.getString(Keys.KEY_NAME);
                        String time = innerObject.getString(Keys.KEY_TIMESTAMP);
                        int value = parseInt(innerObject.getString(Keys.KEY_VALUE));

                        model.setName(name);
                        model.setTime(time);
                        model.setValue(value);

                        list.add(model);
                    }
                }
            } catch (JSONException je) {
                Log.i(JSONparser.TAG, "" + je.getLocalizedMessage());
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                super.onPostExecute(aVoid);
                dialog.dismiss();
                /**
                 * Checking if List size if more than zero then
                 * Update ListView
                 */
                if(list.size() > 0) {
                    adapter.notifyDataSetChanged();
                } else {
                    Snackbar.make(findViewById(R.id.parentLayout), "No Data Found", Snackbar.LENGTH_LONG).show();
                }
            } catch (Exception e){
                Log.d(JSONparser.TAG, e.getLocalizedMessage());
            }

        }
    }
}