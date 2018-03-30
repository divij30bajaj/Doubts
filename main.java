package com.bajaj.divij.chatbox;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by DIVIJ on 21-03-2018.
 */

public class main extends Activity implements View.OnClickListener
{
    Button send,receive;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        send = (Button) findViewById(R.id.send);
        receive = (Button) findViewById(R.id.receive);
        send.setOnClickListener(this);
        receive.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent1 = new Intent("android.intent.action.ACTIVITY1");
        Intent intent2 = new Intent("android.intent.action.ACTIVITY2");
        switch (v.getId()){
            case R.id.receive:
                startActivity(intent1);
                break;
            case R.id.send:
                startActivity(intent2);
                break;
        }
    }
    protected void onPause(){
        super.onPause();
    }

}
