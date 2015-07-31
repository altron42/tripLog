package br.edu.ufam.icomp.triplog.controller;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.util.Calendar;

import br.edu.ufam.icomp.triplog.R;
import br.edu.ufam.icomp.triplog.util.DateHandler;
import br.edu.ufam.icomp.triplog.util.Opcoes;

public class NovoModoViagemActivity extends Activity {

    private int modo_selecionado;

    private Spinner spinner_modo;

    EditText et_nome;
    EditText et_partida;
    EditText et_partida_data;
    EditText et_partida_hora;
    EditText et_chegada;
    EditText et_chegada_data;
    EditText et_chegada_hora;
    EditText et_comentario;

    Button bt_concluir;

    LinearLayout layout;

    private Calendar nova_data;

    private DatePickerDialog datePicker_partida;
    private TimePickerDialog timePicker_partida;
    private DatePickerDialog datePicker_chegada;
    private TimePickerDialog timePicker_chegada;

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
                if (position != 0) {
                    setViewsVisibility(View.VISIBLE);
                } else {
                    setViewsVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        nova_data = Calendar.getInstance();

        datePicker_partida = new DatePickerDialog(this,
                listener_partida,
                nova_data.get(Calendar.YEAR),
                nova_data.get(Calendar.MONTH),
                nova_data.get(Calendar.DAY_OF_MONTH));
        datePicker_chegada = new DatePickerDialog(this,
                listener_chegada,
                nova_data.get(Calendar.YEAR),
                nova_data.get(Calendar.MONTH),
                nova_data.get(Calendar.DAY_OF_MONTH));

        timePicker_partida = new TimePickerDialog(this,
                listener_hora_partida,
                nova_data.get(Calendar.HOUR_OF_DAY),
                nova_data.get(Calendar.MINUTE),
                true);
        timePicker_chegada = new TimePickerDialog(this,
                listener_hora_chegada,
                nova_data.get(Calendar.HOUR_OF_DAY),
                nova_data.get(Calendar.MINUTE),
                true);
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
        et_chegada_data = (EditText) findViewById(R.id.et_data_chegada);
        et_chegada_hora = (EditText) findViewById(R.id.et_hora_chegada);

        et_comentario = (EditText) findViewById(R.id.et_comentario_modo);

        bt_concluir = (Button) findViewById(R.id.bt_concluir_modo_viagem);

        layout = (LinearLayout) findViewById(R.id.layout_modo_viagem);

        setViewsVisibility(View.GONE);
    }

    public void setViewsVisibility(int viewsVisibility) {
        layout.setVisibility(viewsVisibility);
    }

    private DatePickerDialog.OnDateSetListener listener_partida = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            nova_data.set(year, monthOfYear, dayOfMonth);
            et_partida_data.setText(DateHandler.sdf.format(nova_data.getTime()));
            if (et_chegada_data.getText().toString().isEmpty()) {
                datePicker_chegada.updateDate(year,monthOfYear,dayOfMonth);
            }
        }
    };

    private DatePickerDialog.OnDateSetListener listener_chegada = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            nova_data.set(year, monthOfYear, dayOfMonth);
            et_chegada_data.setText(DateHandler.sdf.format(nova_data.getTime()));
        }
    };

    private TimePickerDialog.OnTimeSetListener listener_hora_partida = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            nova_data.set(Calendar.HOUR_OF_DAY, hourOfDay);
            nova_data.set(Calendar.MINUTE, minute);
            et_partida_hora.setText(DateHandler.sdf_time.format(nova_data.getTime()));
        }
    };
    private TimePickerDialog.OnTimeSetListener listener_hora_chegada = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            nova_data.set(Calendar.HOUR_OF_DAY, hourOfDay);
            nova_data.set(Calendar.MINUTE, minute);
            et_chegada_hora.setText(DateHandler.sdf_time.format(nova_data.getTime()));
        }
    };

    public void showDatePickerDialog(View view) {
        if (view == et_partida_data) { datePicker_partida.show(); } else { datePicker_chegada.show(); }
    }

    public void showTimePickerDialog(View view) {
        if (view == et_partida_hora) { timePicker_partida.show(); } else { timePicker_chegada.show(); }
    }
}
