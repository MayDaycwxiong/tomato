package com.learning.tomato;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public static void actionStart(Context context,String uri){
        Intent intent=new Intent(context,MainActivity.class);
        intent.putExtra("uri",uri);
        context.startActivity(intent);
    }
}
