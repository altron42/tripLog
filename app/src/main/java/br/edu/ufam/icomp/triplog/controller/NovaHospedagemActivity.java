package br.edu.ufam.icomp.triplog.controller;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import br.edu.ufam.icomp.triplog.R;
import br.edu.ufam.icomp.triplog.dao.HospedagemDAO;
import br.edu.ufam.icomp.triplog.model.Hospedagem;
import br.edu.ufam.icomp.triplog.model.Viagem;
import br.edu.ufam.icomp.triplog.util.DateHandler;
import br.edu.ufam.icomp.triplog.util.Opcoes;

public class NovaHospedagemActivity extends Activity {

    EditText et_nome;
    EditText et_checkin;
    EditText et_checkin_hora;
    EditText et_checkout;
    EditText et_checkout_hora;
    EditText et_comentario;

    DatePickerDialog datePickerDialog_checkin;
    DatePickerDialog datePickerDialog_checkout;

    TimePickerDialog timePickerDialog_checkin;
    TimePickerDialog timePickerDialog_checkout;

    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_hospedagem);

        initViews();

        calendar = Calendar.getInstance();

        datePickerDialog_checkin = new DatePickerDialog(this,
                dateSetListener_checkin,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog_checkout = new DatePickerDialog(this,
                dateSetListener_checkout,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        timePickerDialog_checkin = new TimePickerDialog(this,
                timeSetListener_checkin,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true);
        timePickerDialog_checkout = new TimePickerDialog(this,
                timeSetListener_checkout,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_nova_hospedagem, menu);
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

    private void initViews() {
        et_nome = (EditText) findViewById(R.id.et_nome);
        et_checkin = (EditText) findViewById(R.id.et_data_checkin);
        et_checkin_hora = (EditText) findViewById(R.id.et_hora_checkin);
        et_checkout = (EditText) findViewById(R.id.et_data_checkout);
        et_checkout_hora = (EditText) findViewById(R.id.et_hora_checkou);
        et_comentario = (EditText) findViewById(R.id.et_comentario_hospedagem);
    }

    private DatePickerDialog.OnDateSetListener dateSetListener_checkin = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            calendar.set(year, monthOfYear, dayOfMonth);
            et_checkin.setText(DateHandler.sdf.format(calendar.getTime()));
            if (et_checkout.getText().toString().isEmpty()) {
                datePickerDialog_checkout.updateDate(year,monthOfYear,dayOfMonth);
            }
        }
    };

    private DatePickerDialog.OnDateSetListener dateSetListener_checkout = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            calendar.set(year,monthOfYear,dayOfMonth);
            et_checkout.setText(DateHandler.sdf.format(calendar.getTime()));
        }
    };

    private TimePickerDialog.OnTimeSetListener timeSetListener_checkin = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
            calendar.set(Calendar.MINUTE,minute);
            et_checkin_hora.setText(DateHandler.sdf_time.format(calendar.getTime()));
        }
    };

    private TimePickerDialog.OnTimeSetListener timeSetListener_checkout = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
            calendar.set(Calendar.MINUTE,minute);
            et_checkout_hora.setText(DateHandler.sdf_time.format(calendar.getTime()));
        }
    };

    public void escolherData(View view) {
        if (view == et_checkin) datePickerDialog_checkin.show(); else datePickerDialog_checkout.show();
    }

    public void escolherHora(View view) {
        if (view == et_checkin_hora) timePickerDialog_checkin.show(); else timePickerDialog_checkout.show();
    }

    public void btConcluirHospedagem(View view) {
        Hospedagem hospedagem = new Hospedagem();
        hospedagem.setNome(et_nome.getText().toString());
        hospedagem.setData_checkin(et_checkin.getText().toString());
        hospedagem.setHora_checkin((et_checkin_hora.getText().toString()));
        hospedagem.setData_checkout(et_checkout.getText().toString());
        hospedagem.setHora_checkout(et_checkout_hora.getText().toString());
        hospedagem.setComentario(et_comentario.getText().toString());
        hospedagem.setIdViagem(Opcoes.getIdViagem());

        HospedagemDAO hospedagemDAO = new HospedagemDAO(this);

        if (hospedagemDAO.addHospedagem(hospedagem)) {
            Toast.makeText(this,"Concluído",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this,"Erro",Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}
