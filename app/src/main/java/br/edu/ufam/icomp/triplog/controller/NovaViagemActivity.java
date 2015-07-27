package br.edu.ufam.icomp.triplog.controller;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import br.edu.ufam.icomp.triplog.R;
import br.edu.ufam.icomp.triplog.controller.fragments.DatePickerFragment;
import br.edu.ufam.icomp.triplog.dao.ViagemDAO;
import br.edu.ufam.icomp.triplog.model.Viagem;

public class NovaViagemActivity extends FragmentActivity implements DatePickerDialog.OnDateSetListener {

    private TextView tv_comeco = (TextView) findViewById(R.id.tv_comeco);
    private TextView tv_fim = (TextView) findViewById(R.id.tv_fim);

    private SimpleDateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_viagem);

        dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        EditText et_comeco = (EditText) findViewById(R.id.et_comeco_viagem);
        et_comeco.setInputType(InputType.TYPE_NULL);
        EditText et_fim = (EditText) findViewById(R.id.et_fim_viagem);
        et_fim.setInputType(InputType.TYPE_NULL);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_nova_viagem, menu);
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

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

    }

    public void showDatePickerDialog(View view) {
        DialogFragment fragment = new DatePickerFragment();
        fragment.show(getFragmentManager(), "datePicker");
    }

    public void concluirClicado(View view) {
        EditText et_nome = (EditText) findViewById(R.id.et_viagem_nome);
        EditText et_descricao = (EditText) findViewById(R.id.et_descricao);

        RadioGroup rg = (RadioGroup) findViewById(R.id.rg_tipo_viagem);

        Viagem viagem = new Viagem();

        switch (rg.getCheckedRadioButtonId()) {
            case R.id.rb_pessoal:
                viagem.setTipo(1);
                break;
            case R.id.rb_negocios:
                viagem.setTipo(2);
                break;
        }

        if (!et_nome.getText().toString().isEmpty())
            viagem.setNome(et_nome.getText().toString());
        else {
            Toast.makeText(getApplicationContext(), "Insira o nome da viagem", Toast.LENGTH_SHORT).show();
            return;
        }

        viagem.setDetalhes(et_descricao.getText().toString());

        if (!tv_comeco.getText().toString().isEmpty())
            viagem.setComeco(tv_comeco.getText().toString());
        else {
            Toast.makeText(getApplicationContext(), "Coloque a data do começo", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!tv_fim.getText().toString().isEmpty())
            viagem.setFim(tv_fim.getText().toString());
        else {
            Toast.makeText(getApplicationContext(), "Voce nao colocou o fim da viagem", Toast.LENGTH_SHORT).show();
            return;
        }

        ViagemDAO viagemDAO = new ViagemDAO(this);

        if (viagemDAO.addViagem(viagem)) {
            Toast.makeText(getApplicationContext(), "Nova viagem adicionada com sucesso", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(getApplicationContext(),"Erro ao adicionar viagem", Toast.LENGTH_SHORT).show();
        }
    }
}
