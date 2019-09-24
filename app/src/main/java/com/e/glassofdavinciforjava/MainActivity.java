package com.e.glassofdavinciforjava;

import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.e.glassofdavinciforjava.views.CustomView;

public class MainActivity extends AppCompatActivity {

    private CustomView mCustomView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCustomView = (CustomView) findViewById(R.id.customView);

        findViewById(R.id.btn_size_config).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCustomView.onSizeChanged(400,300,0,0);
            }
        });
    }
}
