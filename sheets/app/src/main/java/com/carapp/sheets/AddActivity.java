package com.carapp.sheets;

import android.accounts.AccountManager;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.carapp.sheets.model.MyDataModel;
import com.carapp.sheets.parser.JSONparser;
import com.carapp.sheets.util.Keys;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Iterator;

import static java.lang.Integer.parseInt;


public class AddActivity extends AppCompatActivity {

    public static String InputName;
    public static String InputValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        //EditText inputNameField = (EditText)findViewById(R.id.inputName);

        Toast toast = Toast.makeText(getApplicationContext(), "Voila", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public void onSubmit(View view) {
        EditText inputNameField = (EditText) findViewById(R.id.inputName);
        InputName = inputNameField.getText().toString();
        EditText inputValueField = (EditText) findViewById(R.id.inputValue);
        InputValue = inputValueField.getText().toString();

        new GetDataPostTask().execute();


        Snackbar.make(view, "message", Snackbar.LENGTH_LONG).show();

    }

    public void onCancel(View view) {
        Snackbar.make(view, "Voila im canceling", Snackbar.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    class PostMethod {

        private static final String SheetName = "Sheet1";
        private static final String BASE_URL = "https://script.google.com/macros/s/AKfycbz_wpfJN787dOKsxGL9B4PZjCLGvQAGY37x2Tvcp6AxxAEWqWI/exec";

        private static final String SheetKey = "LicencePlate";
        private static final String Name = "Name";
        private static final String Value = "Value";


        public static final String TAG = "TAG";

        private Response response;

        public JSONObject Post(String name, String value) {
            try {
                OkHttpClient client = new OkHttpClient();

                String requestUrl = BASE_URL
                        + "?" + SheetKey + "=" + SheetName
                        + "&" + Name + "=" + name
                        + "&" + Value + "=" + value;
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                RequestBody body = RequestBody.create(JSON, "{}");

                Request request = new Request.Builder()
                        .url(requestUrl)
                        .post(body)
                        .build();
                response = client.newCall(request).execute();
                String answereText = response.body().string();
                return new JSONObject(answereText);
            } catch (@NonNull IOException | JSONException e) {
                Log.e(TAG, "" + e.getLocalizedMessage());
            }
            return null;
        }
    }

    class GetDataPostTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;
        int jIndex;
        int x;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Nullable
        @Override
        protected Void doInBackground(Void... params) {

            /**
             * Getting JSON Object from Web Using okHttp
             */
            JSONObject jsonObject = new PostMethod().Post(AddActivity.InputName, AddActivity.InputValue);

            /**
             * Check Whether Its NULL???
             */
            if (jsonObject != null) {
                /**
                 * Check Length...
                 */
                if (jsonObject.length() > 0) {
                    /**
                     * Getting answere
                     */


                }
                return null;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
