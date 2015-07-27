package br.edu.ufam.icomp.triplog;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import br.edu.ufam.icomp.triplog.controller.NovaViagemActivity;
import br.edu.ufam.icomp.triplog.dao.ViagemDAO;
import br.edu.ufam.icomp.triplog.util.BancoDeDados;


public class TripLogActivity extends ListActivity {
    private ViagemDAO viagemDAO;
    private SimpleCursorAdapter viagens;

    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL, new Locale("pt", "BR"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.viagemDAO = new ViagemDAO(this);

        String[] colunasDe = {BancoDeDados.VIAGEM_COL_NOME, BancoDeDados.VIAGEM_COL_COMECO,
                BancoDeDados.VIAGEM_COL_FIM, BancoDeDados.VIAGEM_COL_ICONE};
        int[] colunasPara = {R.id.tv_titulo_viagem, R.id.tv_comeco_viagem, R.id.tv_fim_viagem, R.id.iv_icone_viagem };

        viagens = new SimpleCursorAdapter(this,
                R.layout.lista_viagens,
                viagemDAO.getViagens(),
                colunasDe,
                colunasPara,
                0);

        setListAdapter(viagens);

        viagens.setViewBinder(new SimpleCursorAdapter.ViewBinder(){
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                if (columnIndex == cursor.getColumnIndex(BancoDeDados.VIAGEM_COL_ICONE)) {
                    String nome_imagem = cursor.getString(columnIndex);
                    ImageView iv_icone = (ImageView) view;

                    Log.i(null,"Nome da imagem no DB: " + nome_imagem);

                    if (nome_imagem == null || nome_imagem.matches("null")) {
                        Log.d("ImageView", "Nome da imagem não especificado");
                        iv_icone.setImageResource(R.mipmap.ic_launcher);
                    } else {
                        int resource = getResources().getIdentifier(nome_imagem, "drawable", null);
                        iv_icone.setImageResource(resource);
                    }
                    return true;
                }
                if (columnIndex == cursor.getColumnIndex(BancoDeDados.VIAGEM_COL_COMECO)) {
                    String cursor_data = cursor.getString(columnIndex);
                    Date date = null;
                    try {
                        date = sdf.parse(cursor_data);
                        ((TextView) view).setText("Começo: " + dateFormat.format(date));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return true;
                }
                if (columnIndex == cursor.getColumnIndex(BancoDeDados.VIAGEM_COL_FIM)) {
                    String cursor_data = cursor.getString(columnIndex);
                    Date date = null;
                    try {
                        date = sdf.parse(cursor_data);
                        ((TextView) view).setText("Término: " + dateFormat.format(date));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    public void onListItemClick(ListView l, View v, int pos, long id) {

        Toast.makeText(this,"Teste maroto",Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_trip_log, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case R.id.action_nova_viagem:
                Intent intent = new Intent(this, NovaViagemActivity.class);
                startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
