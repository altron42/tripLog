package br.edu.ufam.icomp.triplog.controller;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import br.edu.ufam.icomp.triplog.R;
import br.edu.ufam.icomp.triplog.dao.CarteiraDAO;
import br.edu.ufam.icomp.triplog.model.Carteira;

public class NovaCarteiraActivity extends Activity {
    private EditText et_nome;
    private EditText et_valor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_carteira);

        et_nome = (EditText) findViewById(R.id.et_nome_carteira);
        et_valor = (EditText) findViewById(R.id.et_valor_carteira);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_nova_carteira, menu);
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

    public void concluirNovaCarteira(View view) {
        CarteiraDAO carteiraDAO = new CarteiraDAO(this);

        Carteira carteira = new Carteira();

        carteira.setIdViagem(PrincipalViagemActivity.id_viagem_selecionada);
        carteira.setNome(et_nome.getText().toString());
        carteira.setValorDisponivel(Double.parseDouble(et_valor.getText().toString()));

        if (carteiraDAO.addCarteira(carteira)) {
            Toast.makeText(this,"Carteira adicionada com sucesso", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this,"Não foi possível adicionar carteira", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}
