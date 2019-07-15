package com.sunnychaser.client.xmltest;

import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import org.xmlrpc.android.XMLRPCClient;
import org.xmlrpc.android.XMLRPCException;

import java.net.URI;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showDialog(String info) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle("登录结果");
        builder.setMessage(info);
        builder.setPositiveButton("确定", null);
        builder.show();
    }

    protected class Login extends AsyncTask<Integer, Integer, String> {
        protected String username;
        protected String password;

        protected Login(String uname, String upwd) {
            username = uname;
            password = upwd;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Integer... params) {
            XMLRPCClient client = new XMLRPCClient(URI.create("http://192.168.1.253:9090/rpc"));
            String i = null;
            try {
                i = (String) client.call("login", username, password);
            } catch (XMLRPCException e) {
                e.printStackTrace();
            }
            return i;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            showDialog(s);
        }
    }

    public void onlogin(View v) {
        EditText etusername = (EditText)findViewById(R.id.editText);
        EditText etpassword = (EditText)findViewById(R.id.editText2);
        final String username = etusername.getText().toString();
        final String password = etpassword.getText().toString();
        new Thread() {
            @Override
            public void run() {
                Login l = new Login(username, password);
                l.execute();
            }
        }.start();
    }
}


