package com.shahzaib.threading;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements Handler.Callback {
    /***** Main purpose of this application
     Is example application main, background thread pr kuch data collect krna hy aur phr us data sy main thread main textview k
     text ko update krna hy..

     Inshort:  do some work in background and get result back
     */

    public static final String KEY_CURRENT_DATE = "currentData";


    TextView textView;
    public Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView  = findViewById(R.id.textView);


        handler = new Handler(this);


    }


    public void getDataFromBackgroundThread(View view) {

        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                // do background work here
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss",Locale.getDefault());
                String dateString = dateFormat.format(new Date());

                // packing and parceling the message to main thread
                Message  message = Message.obtain();
                Bundle bundle = new Bundle();
                bundle.putString(KEY_CURRENT_DATE,dateString);
                message.setData(bundle);
                handler.sendMessage(message);
            }

        };
        Thread thread = new Thread(runnable);
        thread.start();
    }



    @Override
    public boolean handleMessage(Message msg) {
        Bundle bundle = msg.getData();
        String currentDate = bundle.getString(KEY_CURRENT_DATE,"");
        textView.setText(currentDate);
        return true;
    }










    private void SHOW_LOG(String message)
    {
        Log.i("123456",message);
    }


}
