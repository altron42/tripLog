package br.edu.ufam.icomp.triplog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import br.edu.ufam.icomp.triplog.controller.NovaViagemActivity;
import br.edu.ufam.icomp.triplog.controller.fragments.ListaViagensFragment;


public class TripLogActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_log);

        if (getFragmentManager().findFragmentById(R.id.fragment_lista_viagens) == null) {
            ListaViagensFragment fragment = new ListaViagensFragment();
            getFragmentManager().beginTransaction().add(R.id.fragment_lista_viagens, fragment).commit();
        }
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
