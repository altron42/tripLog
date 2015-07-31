package br.edu.ufam.icomp.triplog.controller;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import br.edu.ufam.icomp.triplog.R;
import br.edu.ufam.icomp.triplog.util.Opcoes;

public class NovoModoViagemActivity extends Activity {

    private int modo_selecionado;

    private Spinner spinner_modo;

    EditText et_nome;
    EditText et_partida;
    EditText et_partida_data;
    EditText et_partida_hora;
    EditText et_chegada;
    EditText et_chegeda_data;
    EditText et_chegada_hora;
    EditText et_comentario;

    TextView tv_partida;
    TextView tv_chegada;

    Button bt_concluir;

    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_modo_viagem);

        initViews();

        ArrayAdapter adapter_spinner_modo = ArrayAdapter.createFromResource(this, R.array.modo_viagem_array, android.R.layout.simple_spinner_item);
        adapter_spinner_modo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_modo.setAdapter(adapter_spinner_modo);
        spinner_modo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i(null, "Categoria selecionada " + Opcoes.modo_viagem[position]);
                modo_selecionado = position;
                if (position!= 0) {
                    setViewsVisibility(View.VISIBLE);
                } else {
                    setViewsVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_novo_modo_viagem, menu);
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

    public void initViews() {
        spinner_modo = (Spinner) findViewById(R.id.spinner_modo_viagem);

        et_nome = (EditText) findViewById(R.id.et_detalhes);

        et_partida = (EditText) findViewById(R.id.et_partida);
        et_partida_data = (EditText) findViewById(R.id.et_data_partida);
        et_partida_hora = (EditText) findViewById(R.id.et_hora_partida);

        et_chegada = (EditText) findViewById(R.id.et_chegada);
        et_chegeda_data = (EditText) findViewById(R.id.et_data_chegada);
        et_chegada_hora = (EditText) findViewById(R.id.et_hora_chegada);

        et_comentario = (EditText) findViewById(R.id.et_comentario_modo);

        bt_concluir = (Button) findViewById(R.id.bt_concluir_modo_viagem);

        layout = (LinearLayout) findViewById(R.id.layout_modo_viagem);

        setViewsVisibility(View.GONE);
    }

    public void setViewsVisibility(int viewsVisibility) {
        layout.setVisibility(viewsVisibility);
    }
}
