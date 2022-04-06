package com.viniciuysr.diaryinspection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ConsultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult);

        Bundle extras = getIntent().getExtras();

        if(extras != null) {
            String type = extras.getString("type");
                List<Register> registers = SqlHelper.getInstance(this).getRegisterBy(Integer.parseInt(type));
                    Log.d("Teste", registers.toString());

        }

    }

}