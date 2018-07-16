package tasfiqul.ghani.tashfik;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static tasfiqul.ghani.tashfik.Constant.LOGIN_URL;
import static tasfiqul.ghani.tashfik.Constant.Signup_URL;

public class LogInActivity extends AppCompatActivity {

    String url;
    EditText edEmail,edPassword,edName;
    TextView tvSignup;
    Button btLogin;
    Boolean isSignUp=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        btLogin=(Button)findViewById(R.id.login);
        edEmail=(EditText)findViewById(R.id.edemail);
        edPassword=(EditText)findViewById(R.id.edpass);
        tvSignup=(TextView)findViewById(R.id.signup);
        edName=(EditText)findViewById(R.id.edname);

        url=LOGIN_URL;
        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url=Signup_URL;
                isSignUp=true;
                ((LinearLayout)findViewById(R.id.namelayout)).setVisibility(View.VISIBLE);
            }
        });

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if(isSignUp) {
                        JSONObject js = new JSONObject();
                        js.accumulate("email", edEmail.getText().toString());
                        js.accumulate("password", edPassword.getText().toString());
                        js.accumulate("name",edName.getText().toString());
                        VolleyPost(js.toString());
                    }else {
                        JSONObject js = new JSONObject();
                        js.accumulate("email", edEmail.getText().toString());
                        js.accumulate("password", edPassword.getText().toString());

                        VolleyPost(js.toString());
                    }

                }catch (Exception e){

                }
            }
        });










    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    public void VolleyPost(final String requestBody){

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest JOPR = new JsonObjectRequest(Request.Method.POST,
                url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
              try{
                  String msg = response.getString("message");

                      Dialog(msg);

                  Toast.makeText(LogInActivity.this, msg, Toast.LENGTH_SHORT).show();
              }catch (Exception e){

              }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Dialog(error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }


            @Override
            public byte[] getBody() {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                            requestBody, "utf-8");
                    return null;
                }
            }


        };

        queue.add(JOPR);
    }




    public void Dialog(String title){
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(LogInActivity.this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(LogInActivity.this);
        }
        builder.setTitle(title)

                .setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })

                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

}
