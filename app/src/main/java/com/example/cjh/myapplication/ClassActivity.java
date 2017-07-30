package com.example.cjh.myapplication;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.lang.reflect.Method;
import java.sql.SQLOutput;
import java.util.List;

import dalvik.system.DexClassLoader;

public class ClassActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Button load_button = (Button)findViewById(R.id.button4);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        load_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TextView tv = (TextView) findViewById(R.id.show);
                //tv.setText("Hello Android-" + new java.util.Date());
                useDexClassLoader();
            }
        });
    }




    private void useDexClassLoader(){
        //创建一个意图，用来找到指定的apk
        Intent intent = new Intent("com.maplejaw.plugin");
        //获得包管理器
        PackageManager pm = getPackageManager();
        List<ResolveInfo> resolveinfoes =  pm.queryIntentActivities(intent, 0);
        if(resolveinfoes.size()==0){
            return;
        }
        //获得指定的activity的信息
        ActivityInfo actInfo = resolveinfoes.get(0).activityInfo;

        //获得包名
        String packageName = actInfo.packageName;
        //获得apk的目录或者jar的目录
        String apkPath = actInfo.applicationInfo.sourceDir;
        //dex解压后的目录,注意，这个用宿主程序的目录，android中只允许程序读取写自己
        //目录下的文件
        String dexOutputDir = getApplicationInfo().dataDir;

        //native代码的目录
        String libPath = actInfo.applicationInfo.nativeLibraryDir;

        System.out.println("CJHCJHCJH");

        //创建类加载器，把dex加载到虚拟机中
        DexClassLoader calssLoader = new DexClassLoader(apkPath, dexOutputDir, libPath,
                this.getClass().getClassLoader());

        //利用反射调用插件包内的类的方法

        try {
            Class<?> clazz = calssLoader.loadClass(packageName+".plugin");

            Object obj = clazz.newInstance();
            Class[] param = new Class[2];
            param[0] = Integer.TYPE;
            param[1] = Integer.TYPE;

            Method method = clazz.getMethod("function", param);

            Integer ret = (Integer)method.invoke(obj, 12,34);

            System.out.println("CJH2"+ret);

            Log.d("JG", "返回的调用结果为:" + ret);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


