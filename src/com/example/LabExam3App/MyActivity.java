package com.example.LabExam3App;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class MyActivity extends Activity
{
    /**
     * Called when the activity is first created.
     */

    String values,baseUrl;
    HashMap<String,String> valMap;
    private TextView textView1,textView2,mBaseUrl;
    private Button startBtn,stopBtn,connectBtn;
    final static String MY_ACTION = "MY_ACTION";
    MyBroadcastReceiver  myReciever;
    Intent intent;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mBaseUrl = (TextView)findViewById(R.id.base_url);
        textView1 = (TextView)findViewById(R.id.textView1);
        textView2 = (TextView)findViewById(R.id.textView2);
        startBtn = (Button)findViewById(R.id.startServiceBtn);
        stopBtn = (Button)findViewById(R.id.stopServiceBtn);
        connectBtn = (Button)findViewById(R.id.connectBtn);
        stopBtn.setEnabled(false);
        connectBtn.setEnabled(false);
    }
    public void onClickStart( View v){
        myReciever = new MyBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MyService.MY_ACTION);
        registerReceiver(myReciever,intentFilter);
        intent = new Intent(MyActivity.this,MyService.class);
        stopBtn.setEnabled(true);
        startService(intent);
    }
    public void onClickStop(View v){
        Log.e("MAIN", "onClick: stopping service");
        stopService(intent);
        connectBtn.setEnabled(true);
    }
    public void onClickConnect(View v){
        baseUrl = mBaseUrl.getText().toString();
        String[] tok = values.split(":");
        valMap = new HashMap<String,String>();
        for(int x = 0;x <tok.length;x++)
        {
            switch (x)
            {
                case 0:
                    valMap.put("Value 1",tok[x]);
                    break;
                case 1:
                    valMap.put("Value 2",tok[x]);
                    break;
                case 2:
                    valMap.put("Value 3",tok[x]);
                    break;
                case 3:
                    valMap.put("Value 4",tok[x]);
                    break;
                case 4:
                    valMap.put("Value 5",tok[x]);
                    break;
            }
        }
        JSONObject inputs = new JSONObject(valMap);
        try
        {
            String jsonString = HttpUtils.urlContentPost("http://10.3.4.76:8080/loan-calculator","Inputs",inputs.toString());
            System.out.println("DOIN");
            JSONObject jsonResult = new JSONObject(jsonString);
            int max = Integer.parseInt(jsonResult.getString("Max"));
            int min = Integer.parseInt(jsonResult.getString("Min"));
            int sum = Integer.parseInt(jsonResult.getString("Sum"));
            textView2.setText("Max: " +max+" Min: "+min+" Sum: "+sum);
        }
        catch (Exception e){}
    }
    @Override
    public void onDestroy(){
        this.unregisterReceiver(myReciever);
        super.onDestroy();
    }
    public class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("MAIN>>>", "ACTION: " + intent.getAction());
            if (intent.getAction().equals(MY_ACTION)) {
                textView1.setText(intent.getStringExtra("random nums"));
               values = textView1.getText().toString();
            }//onReceive
        }
    }
}
