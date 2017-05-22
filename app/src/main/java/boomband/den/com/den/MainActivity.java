package boomband.den.com.den;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {


    TextView result;
    EditText id2;
    TextView balance;
    TextView amount;
    ImageView status;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result = (TextView) findViewById(R.id.result);
        id2 = (EditText) findViewById(R.id.via_id);
        status=(ImageView)findViewById(R.id.status);
        amount=(TextView)findViewById(R.id.amount);
        progressDialog=new ProgressDialog(this);
        balance=(TextView)findViewById(R.id.balance);
        Button button = (Button) findViewById(R.id.getData);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Finding...");
                progressDialog.show();
                getdata(id2.getText().toString());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void getdata(String id) {
        if (id != null) {
            String url = "http://192.168.0.109/AnmolCables/fetch.php?id=" + id;
            StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                   // Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT)
                       //     .show();
                    progressDialog.hide();
                    JSONObject res = null;
                    try {
                        res = new JSONObject(response);
                        String master = res.optString("master_status");
                        String lcocode = res.optString("lcocode");
                        String vc_number = res.optString("vc_number");
                        String stb_status = res.optString("stb_status");
                        String ccode = res.optString("ccode");
                        String name = res.optString("name");
                        String monthly = res.optString("monthly");
                        String rec_number = res.optString("rec_number");
                        String rec_date = res.optString("rec_date");
                        String rec_amount = res.optString("rec_amount");
                        String rec_balance = res.optString("rec_balance");
                        String activation_date = res.optString("activation_date");
                        String gali_description = res.optString("gali_description");
                        String gali_number = res.optString("gali_number");
                        String filed_code = res.optString("filed_code");
                        String hindi = res.optString("hindi");
                        balance.setText("Balance  \n"+rec_balance);
                        amount.setText("Amount  \n "+rec_amount);
                        result.setText("NAME  :  " + name + '\n' + "STATUS  :  " + stb_status +
                                '\n' + "V.C NUMBER  :  " + vc_number + '\n' + "REC NUMBER  :  " + rec_number + '\n' +
                                "AMOUNT  :  " + rec_amount + '\n' + "BALANCE  :  " + rec_balance
                        );
                        if (stb_status.toLowerCase().equals("active")) {
                            status.setImageResource(R.drawable.active);
                        }
                        else
                        {
                            status.setImageResource(R.drawable.inactive);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.hide();
                            Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }
}
