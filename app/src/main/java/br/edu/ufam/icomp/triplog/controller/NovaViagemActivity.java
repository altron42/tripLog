package br.edu.ufam.icomp.triplog.controller;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import br.edu.ufam.icomp.triplog.R;
import br.edu.ufam.icomp.triplog.dao.ViagemDAO;
import br.edu.ufam.icomp.triplog.model.Viagem;

public class NovaViagemActivity extends Activity {

    private EditText et_comeco;
    private EditText et_fim;

    private SimpleDateFormat dateFormat;

    private Calendar nova_data;

    private DatePickerDialog datePicker_comeco;
    private DatePickerDialog datePicker_fim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_viagem);

        et_comeco = (EditText) findViewById(R.id.et_data_comeco);
        et_fim = (EditText) findViewById(R.id.et_data_fim);

        dateFormat = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt","BR"));

        nova_data = Calendar.getInstance();

        datePicker_comeco = new DatePickerDialog(this, listener_comeco,
                nova_data.get(Calendar.YEAR),
                nova_data.get(Calendar.MONTH),
                nova_data.get(Calendar.DAY_OF_MONTH));

        datePicker_fim = new DatePickerDialog(this,listener_fim,
                nova_data.get(Calendar.YEAR),
                nova_data.get(Calendar.MONTH),
                nova_data.get(Calendar.DAY_OF_MONTH));
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

    private DatePickerDialog.OnDateSetListener listener_comeco = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            nova_data.set(year, monthOfYear, dayOfMonth);
            et_comeco.setText(dateFormat.format(nova_data.getTime()));
        }
    };

    private DatePickerDialog.OnDateSetListener listener_fim = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            nova_data.set(year, monthOfYear, dayOfMonth);
            et_fim.setText(dateFormat.format(nova_data.getTime()));
        }
    };

    public void showDatePickerDialog(View view) {
        if (view == et_comeco) { datePicker_comeco.show(); } else { datePicker_fim.show(); }
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

        if (!et_comeco.getText().toString().isEmpty())
            viagem.setComeco(et_comeco.getText().toString());
        else {
            Toast.makeText(getApplicationContext(), "Coloque a data do começo", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!et_fim.getText().toString().isEmpty())
            viagem.setFim(et_fim.getText().toString());
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
