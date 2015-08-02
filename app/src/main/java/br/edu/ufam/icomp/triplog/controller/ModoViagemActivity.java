package br.edu.ufam.icomp.triplog.controller;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import br.edu.ufam.icomp.triplog.R;
import br.edu.ufam.icomp.triplog.controller.fragments.ModoViagemFragment;

public class ModoViagemActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modo_viagem);

        if (getFragmentManager().findFragmentById(R.id.fragment_modo_viagem) == null) {
            ModoViagemFragment modoViagemFragment = new ModoViagemFragment();
            getFragmentManager().beginTransaction().add(R.id.fragment_modo_viagem,modoViagemFragment).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_modo_viagem, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_novo_modo) {
            Intent intent = new Intent(this, NovoModoViagemActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
