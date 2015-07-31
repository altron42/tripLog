package br.edu.ufam.icomp.triplog.controller;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.Calendar;

import br.edu.ufam.icomp.triplog.R;
import br.edu.ufam.icomp.triplog.dao.CarteiraDAO;
import br.edu.ufam.icomp.triplog.dao.DespesaDAO;
import br.edu.ufam.icomp.triplog.model.Carteira;
import br.edu.ufam.icomp.triplog.model.Despesa;
import br.edu.ufam.icomp.triplog.util.BancoDeDados;
import br.edu.ufam.icomp.triplog.util.DateHandler;
import br.edu.ufam.icomp.triplog.util.Opcoes;

public class NovaDespesaActivity extends Activity {

    private CarteiraDAO carteiraDAO;

    private EditText et_nome;
    private EditText et_valor;
    private EditText et_data;
    private Spinner spinner_categoria;
    private Spinner spinner_carteira;
    private EditText et_comentario;

    private Calendar nova_data;

    private DatePickerDialog datePicker;

    private int categoria_selecionada;
    private int carteira_selecionada;


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

        ArrayAdapter adapter_spinner_categoria = ArrayAdapter.createFromResource(this, R.array.categorias_array, android.R.layout.simple_spinner_item);
        adapter_spinner_categoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_categoria.setAdapter(adapter_spinner_categoria);
        spinner_categoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i(null, "Categoria selecionada " + Opcoes.categorias_gastos_lista[position]);
                categoria_selecionada = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        carteiraDAO = new CarteiraDAO(this);

        String[] adapterCol = {BancoDeDados.CARTEIRA_COL_NOME};
        int[] adapterViews = {android.R.id.text1};

        Cursor cursor = carteiraDAO.getCarteiras(Opcoes.getIdViagem());

        SimpleCursorAdapter scp = new SimpleCursorAdapter(this,
                android.R.layout.simple_spinner_item,
                cursor,
                adapterCol,
                adapterViews,
                0);
        scp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_carteira.setAdapter(scp);
        spinner_carteira.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i(null, "Carteira selecionada " + position + " ID: " + id);
                carteira_selecionada = (int)id;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initViews() {
        et_nome = (EditText) findViewById(R.id.et_nome_item_gasto);
        et_valor = (EditText) findViewById(R.id.et_valor_item_gasto);
        et_data = (EditText) findViewById(R.id.et_data_item_gasto);
        spinner_categoria = (Spinner) findViewById(R.id.spinner_categoria_gasto);
        spinner_carteira = (Spinner) findViewById(R.id.spinner_carteira_gasto);
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
        CarteiraDAO carteiraDAO = new CarteiraDAO(this);

        Despesa despesa = new Despesa();
        Carteira carteira = carteiraDAO.getCarteira(carteira_selecionada);

        despesa.setNome(et_nome.getText().toString());
        despesa.setValor(Double.parseDouble(et_valor.getText().toString()));
        despesa.setData(et_data.getText().toString());
        despesa.setCategoria(categoria_selecionada);
        despesa.setPagoCom(carteira_selecionada);
        despesa.setIdViagem(Opcoes.getIdViagem());
        despesa.setComentario(et_comentario.getText().toString());

        if (!carteira.subtrairValor(despesa.getValor())) {
            Toast.makeText(this,"A carteira ficou com saldo negativo\nVerifique seu orçamento", Toast.LENGTH_SHORT).show();
        }
        carteiraDAO.editCarteira(carteira);

        if (despesaDAO.addDespesa(despesa)) {
            Toast.makeText(this,"Adicionado com sucesso", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this,"Erro ao adicionar despesa", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}
