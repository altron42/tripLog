package br.edu.ufam.icomp.triplog.controller;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import br.edu.ufam.icomp.triplog.R;
import br.edu.ufam.icomp.triplog.controller.fragments.HospedagemFragment;

public class HospedagemActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospedagem);

        if (getFragmentManager().findFragmentById(R.id.fragment) == null) {
            HospedagemFragment hospedagemFragment = new HospedagemFragment();
            getFragmentManager().beginTransaction().add(R.id.fragment, hospedagemFragment).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_hospedagem, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_nova_hospedagem) {
            Intent intent = new Intent(this,NovaHospedagemActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
