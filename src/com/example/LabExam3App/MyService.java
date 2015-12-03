package com.example.LabExam3App;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import com.example.LabExam3App.MyActivity;

import java.util.Random;

public class MyService extends Service
{
    Random random;
    final static String MY_ACTION = "MY_ACTION";
    boolean isRunning = true;
    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub
        //new getRandomNumbers().execute();
        random = new Random();
        int[] n = {0,0,0,0,0};
        for(int i = 0;i<5;i++)
            n[i] = random.nextInt(100);
        StringBuilder sb = new StringBuilder();
        sb.append(n[0] + ": ");
        sb.append(n[1] + ": ");
        sb.append(n[2] + ": ");
        sb.append(n[3] + ": ");
        sb.append(n[4] + ": ");
        String data = sb.toString();
        Intent intentFilter5 = new Intent(MY_ACTION);
        intentFilter5.putExtra("random nums", data);
        sendBroadcast(intentFilter5);
        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        isRunning = false;
    }//onDestroy
    /*
    public class getRandomNumbers extends AsyncTask<Integer, Integer, Integer> {
        @Override
        protected Integer doInBackground(Integer... params) {
            try {
                wait(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i=0; i<5; i++)
            {
                random = new Random();
                publishProgress(i, random.nextInt(100) + 1);
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Intent intentFilter5 = new Intent(MY_ACTION);
            StringBuilder sb = new StringBuilder();
            sb.append(values[0]);
            sb.append(values[1]);
            sb.append(values[2]);
            sb.append(values[3]);
            sb.append(values[4]);
            String data = sb.toString();
            intentFilter5.putExtra("random nums", data);
            sendBroadcast(intentFilter5);
        }
    }// ComputeFibonacciRecursivelyTas
    */
}
