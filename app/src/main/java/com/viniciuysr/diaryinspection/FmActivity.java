package com.viniciuysr.diaryinspection;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FmActivity extends AppCompatActivity {

    private EditText editPedido;
    private EditText editItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fm);

        editPedido = findViewById(R.id.edit_txt_pedido);
        editItem = findViewById(R.id.edit_txt_item);

        Button btnApp = findViewById(R.id.btn_appointment_fm);
        btnApp.setOnClickListener(view -> {
            int a = editPedido.length();
            if (a != 6) {
                Toast.makeText(this, R.string.error_msg_length, Toast.LENGTH_SHORT).show();
                return;
            } else if (!validate()) {
                Toast.makeText(this, R.string.error_msg, Toast.LENGTH_SHORT).show();
                return;
            }

            String sPedido = editPedido.getText().toString();
            String sItem = editItem.getText().toString();

            String pedido = String.valueOf(sPedido);
            String item = String.valueOf(editItem);

            AlertDialog dialog = new AlertDialog.Builder(FmActivity.this)
                    .setTitle(R.string.set_tittle_dialog).setMessage("O item " + sItem + " foi inserido com sucesso dentro do pedido " + sPedido)
                    .setPositiveButton(android.R.string.ok, (DialogInterface, i) -> {

                    })
                    .setNegativeButton(R.string.save, ((dialog1, which) -> {

                        new Thread(() -> {
                            long fmId = SqlHelper.getInstance(FmActivity.this).addItem("num_pedido", pedido);
                            runOnUiThread(() -> {
                                if (fmId > 0)
                                    Toast.makeText(FmActivity.this, R.string.salvado, Toast.LENGTH_SHORT).show();
                            });
                        }).start();

                    }))
                    .create();

            editItem.setText("");
            editPedido.setText("");

            dialog.show();


            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editPedido.getWindowToken(), 0);
            imm.hideSoftInputFromWindow(editItem.getWindowToken(), 0);

        });

    }

    private boolean validate() {
        return (!editPedido.getText().toString().startsWith("0")
                && !editItem.getText().toString().startsWith("0")
                && !editPedido.getText().toString().isEmpty()
                && !editItem.getText().toString().isEmpty());

    }


}


