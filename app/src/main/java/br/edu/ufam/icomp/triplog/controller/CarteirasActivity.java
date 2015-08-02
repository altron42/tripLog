package br.edu.ufam.icomp.triplog.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import br.edu.ufam.icomp.triplog.R;
import br.edu.ufam.icomp.triplog.controller.fragments.CarteirasFragment;
import br.edu.ufam.icomp.triplog.controller.fragments.TabelaFragment;
import br.edu.ufam.icomp.triplog.util.Opcoes;

public class CarteirasActivity extends Activity {

    int opcao_selecionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carteiras);

        if (getFragmentManager().findFragmentById(R.id.fragment_carteiras) == null) {
            CarteirasFragment carteirasFragment = new CarteirasFragment();
            getFragmentManager().beginTransaction().add(R.id.fragment_carteiras, carteirasFragment).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_carteiras, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_nova_carteira) {
            Intent intent = new Intent(this, NovaCarteiraActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_nova_despesa) {
            Intent intent = new Intent(this, NovaDespesaActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
