package br.edu.ufam.icomp.triplog.controller;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

import br.edu.ufam.icomp.triplog.R;
import br.edu.ufam.icomp.triplog.dao.DespesaDAO;
import br.edu.ufam.icomp.triplog.model.Despesa;
import br.edu.ufam.icomp.triplog.util.DateHandler;

public class NovaDespesaActivity extends Activity {

    private EditText et_nome;
    private EditText et_valor;
    private EditText et_data;
    private EditText et_categoria;
    private EditText et_pagoCom;
    private EditText et_comentario;

    private Calendar nova_data;

    private DatePickerDialog datePicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_despesa);

        initViews();

        nova_data = Calendar.getInstance();

        datePicker = new DatePickerDialog(this, date_listener,
                nova_data.get(Calendar.YEAR),
                nova_data.get(Calendar.MONTH),
                nova_data.get(Calendar.DAY_OF_MONTH));
    }

    private void initViews() {
        et_nome = (EditText) findViewById(R.id.et_nome_item_gasto);
        et_valor = (EditText) findViewById(R.id.et_valor_item_gasto);
        et_data = (EditText) findViewById(R.id.et_data_item_gasto);
        et_categoria = (EditText) findViewById(R.id.et_categoria_item_gasto);
        et_pagoCom = (EditText) findViewById(R.id.et_carteira_item_gasto);
        et_comentario = (EditText) findViewById(R.id.et_comentario_gasto);
    }

    private DatePickerDialog.OnDateSetListener date_listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            nova_data.set(year, monthOfYear, dayOfMonth);
            et_data.setText(DateHandler.sdf.format(nova_data.getTime()));
        }
    };

    public void showDatePickerDialog(View view) {
        datePicker.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_nova_despesa, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void btConcluirNovaDespesa(View view) {
        DespesaDAO despesaDAO = new DespesaDAO(this);
        Despesa despesa = new Despesa();
        despesa.setNome(et_nome.getText().toString());
        despesa.setValor(Double.parseDouble(et_valor.getText().toString()));
        despesa.setData(et_data.getText().toString());
        despesa.setCategoria(Integer.parseInt(et_categoria.getText().toString()));
        despesa.setPagoCom(Integer.parseInt(et_pagoCom.getText().toString()));
        despesa.setIdViagem(PrincipalViagemActivity.id_viagem_selecionada);

        if (despesaDAO.addDespesa(despesa)) {
            Toast.makeText(this,"Adicionado com sucesso", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this,"Erro ao adicionar despesa", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}
