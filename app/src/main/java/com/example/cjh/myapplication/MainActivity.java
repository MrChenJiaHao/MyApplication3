package com.example.cjh.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.tongdun.android.shell.paph.FMAgent;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Button button2 = (Button)findViewById(R.id.button);
        Button button1 = (Button)findViewById(R.id.button3);
        Button intent2_button = (Button)findViewById(R.id.intent2_button);
        Button button_file = (Button)findViewById(R.id.button5);
        Button button_class = (Button)findViewById(R.id.button_class);
        Context context = getApplicationContext();
        try{
            FMAgent.init(context, "production");
            String blackBox = FMAgent.onEvent(context);
            System.out.println("CJHCJHCJH");
            System.out.println(blackBox);
        }
        catch (Exception e)
        {

        }


        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("cn.abel.action.broadcast");
        //多次调用会接收多次
        registerReceiver(new MyReceiver2(), intentFilter);

        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());
        PackageManager pckMan = getPackageManager();
        List<PackageInfo> packs = pckMan.getInstalledPackages(0);
        String set_tv = "";
        for(int i=0;i<packs.size();i++)
        {
            PackageInfo PInfo = packs.get(i);
            set_tv = set_tv + "     " + PInfo.applicationInfo.packageName;
        }
        //PackageInfo PInfo = packs.get(0);
        //String packName = PInfo.applicationInfo.packageName;
        //List packs = getPackageManager().getInstalledPackages(0);
        tv.setText(set_tv);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TextView tv = (TextView) findViewById(R.id.show);
                //tv.setText("Hello Android-" + new java.util.Date());
                Intent intent=new Intent();
                //intent.setAction("cn.abel.action.broadcast");
                intent.setAction("com.android.xiong.bordcasetestonetest.MYRECEIVER");
                // 要发送的内容
                intent.putExtra("msg", "Abel");
                //发送广播
                sendBroadcast(intent);
                System.out.println("ok");
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TextView tv = (TextView) findViewById(R.id.show);
                //tv.setText("Hello Android-" + new java.util.Date());
                Intent intent1 = new Intent(MainActivity.this, Main2Activity.class);
                MainActivity.this.startActivity(intent1);
            }
        });

        intent2_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent("com.maplejaw.plugin");
                startActivity(intent);
            }
        });

        button_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TextView tv = (TextView) findViewById(R.id.show);
                //tv.setText("Hello Android-" + new java.util.Date());
                Intent intent1 = new Intent(MainActivity.this, FileOp.class);
                MainActivity.this.startActivity(intent1);
            }
        });

        button_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TextView tv = (TextView) findViewById(R.id.show);
                //tv.setText("Hello Android-" + new java.util.Date());
                Intent intent1 = new Intent(MainActivity.this, ClassActivity.class);
                MainActivity.this.startActivity(intent1);
            }
        });

    }

    public class MyReceiver2 extends BroadcastReceiver {
        public MyReceiver2() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO: This method is called when the BroadcastReceiver is receiving
            // an Intent broadcast.
            Toast.makeText(context, intent.getAction()+"\n消息的内容是："+intent.getStringExtra("msg"),Toast.LENGTH_LONG).show();
        }
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
