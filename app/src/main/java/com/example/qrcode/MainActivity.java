package com.example.qrcode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
    Button btnQR;
    TextView txtName;
    TextView txtDiachi;
    ImageView imageHinh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnQR=(Button) findViewById(R.id.btnQR);
        txtName=(TextView) findViewById(R.id.txtName);
        txtDiachi=(TextView) findViewById(R.id.txtDiachi);
        imageHinh=(ImageView) findViewById(R.id.imageHinh);
        final IntentIntegrator intentIntergrator=new IntentIntegrator(this);
        btnQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentIntergrator.initiateScan();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Picasso.get().load(result.getContents()).into(imageHinh);

                try {
                    JSONObject jsonObject=new JSONObject(result.getContents());
                    txtDiachi.setText(jsonObject.getString("DiaChi"));
                    txtName.setText(jsonObject.getString("name"));
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
