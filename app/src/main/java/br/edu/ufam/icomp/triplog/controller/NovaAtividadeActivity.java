package br.edu.ufam.icomp.triplog.controller;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import br.edu.ufam.icomp.triplog.R;
import br.edu.ufam.icomp.triplog.dao.AtividadeDAO;
import br.edu.ufam.icomp.triplog.model.Atividade;
import br.edu.ufam.icomp.triplog.util.DateHandler;
import br.edu.ufam.icomp.triplog.util.Opcoes;

public class NovaAtividadeActivity extends Activity {

    EditText et_nome;
    EditText et_data;
    EditText et_hora;
    EditText et_comentario;

    Calendar calendar;

    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;

    private boolean isedit = false;

    private Atividade atividade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_atividade);

        initViews();

        Intent intent = getIntent();
        isedit = intent.getBooleanExtra(Opcoes.isEditTag,false);

        initViews();

        if (isedit) {
            atividade = intent.getParcelableExtra(Opcoes.atividadeTag);
            fillViews();
        }

        calendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this,
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        timePickerDialog = new TimePickerDialog(this,
                timeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_nova_atividade, menu);
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
        et_nome = (EditText) findViewById(R.id.et_nome_atividade);
        et_data = (EditText) findViewById(R.id.et_data_atividade);
        et_hora = (EditText) findViewById(R.id.et_hora_atividade);
        et_comentario = (EditText) findViewById(R.id.et_detalhes_atividade);
    }

    public void fillViews() {
        et_nome.setText(atividade.getNome());
        et_data.setText(atividade.getData());
        et_hora.setText(atividade.getHora());
        et_comentario.setText(atividade.getDetalhes());
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            calendar.set(year,monthOfYear,dayOfMonth);
            et_data.setText(DateHandler.sdf.format(calendar.getTime()));
        }
    };

    private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
            calendar.set(Calendar.MINUTE,minute);
            et_hora.setText(DateHandler.sdf_time.format(calendar.getTime()));
        }
    };

    public void setarAtividadeTempo(View view) {
        if (view == et_data) datePickerDialog.show(); else timePickerDialog.show();
    }

    public void concluirAtividade(View view) {
        if (!isedit) {
            atividade = new Atividade();
        }

        atividade.setNome(et_nome.getText().toString());
        atividade.setData(et_data.getText().toString());
        atividade.setHora(et_hora.getText().toString());
        atividade.setDetalhes(et_comentario.getText().toString());
        atividade.setIdViagem(Opcoes.getIdViagem());

        AtividadeDAO atividadeDAO = new AtividadeDAO(this);
        if (isedit) {
            atividadeDAO.editAtividade(atividade);
        } else {
            if (atividadeDAO.addAtividade(atividade)) {
                Toast.makeText(this, "Concluido", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Erro", Toast.LENGTH_SHORT).show();
            }
        }
        finish();
    }
}
