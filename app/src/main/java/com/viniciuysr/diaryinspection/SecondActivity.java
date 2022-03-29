package com.viniciuysr.diaryinspection;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {

    private RecyclerView rvSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        rvSecond = findViewById(R.id.rv_second);

        List<MainItem> mainItems = new ArrayList<>();
        mainItems.add(new MainItem(1, R.drawable.custom_button, R.string.apontamento, Color.TRANSPARENT));
        mainItems.add(new MainItem(2, R.drawable.custom_button, R.string.consulta_apontamento, Color.TRANSPARENT));


        rvSecond.setLayoutManager(new LinearLayoutManager(this));
        SecondAdapter adapter = new SecondAdapter(mainItems);
        adapter.setListener(id -> {
            switch (id){
                case 1:
                    startActivity(new Intent(SecondActivity.this, FmActivity.class));
                    break;
                case 2:
                    startActivity(new Intent(SecondActivity.this, ConsultActivity.class));
                    break;
            }
                            });
        rvSecond.setAdapter(adapter);
    }

    private class SecondAdapter extends RecyclerView.Adapter<SecondAdapter.SecondViewHolder> {

        private List<MainItem> mainItems;
        private OnItemClickListener listener;

        public SecondAdapter(List<MainItem> mainItems) {
            this.mainItems = mainItems;
        }

        public void setListener(OnItemClickListener listener) {
            this.listener = listener;
        }

        @NonNull
        @Override
        public SecondViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new SecondViewHolder(getLayoutInflater().inflate(R.layout.second_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull SecondViewHolder holder, int position) {
          MainItem current =  mainItems.get(position);
          holder.bind(current);

        }

        @Override
        public int getItemCount() {
            return mainItems.size();
        }

        private class SecondViewHolder extends RecyclerView.ViewHolder {

            public SecondViewHolder(@NonNull View itemView) {
                super(itemView);
            }

            public void bind(MainItem current) {
                Button btnName = itemView.findViewById(R.id.btn_novo_apontamento);
                LinearLayout container = (LinearLayout) itemView;

                btnName.setOnClickListener(View -> {
                    listener.onClick(current.getId());
                        }
                );

                btnName.setText(current.getTextStringId());
                container.setBackgroundColor(current.getTextColor());
            }
        }
    }

}