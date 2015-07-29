package br.edu.ufam.icomp.triplog.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import br.edu.ufam.icomp.triplog.R;
import br.edu.ufam.icomp.triplog.controller.fragments.MenuOpcoesFragment;
import br.edu.ufam.icomp.triplog.model.Viagem;

public class PrincipalViagemActivity extends Activity {

    private ImageView iv_banner_viagem;

    private TextView tv_periodo;
    private TextView tv_tipo;
    private TextView tv_orcamento_valor;

    private Viagem viagem_selecionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_viagem);

        Intent intent = getIntent();
        viagem_selecionada = (Viagem) intent.getParcelableExtra("viagem_selecionada");

        findViews();

        if (getFragmentManager().findFragmentById(R.id.fragment_lista_opcoes) == null) {
            MenuOpcoesFragment lista = new MenuOpcoesFragment();
            getFragmentManager().beginTransaction().add(R.id.fragment_lista_opcoes, lista).commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        setarValores();
    }

    private void findViews() {
        iv_banner_viagem = (ImageView) findViewById(R.id.iv_banner_viagem);

        tv_periodo = (TextView) findViewById(R.id.tv_periodo_viagem);
        tv_tipo = (TextView) findViewById(R.id.tv_tipo);
        tv_orcamento_valor = (TextView) findViewById(R.id.tv_orcamento_valor);
    }

    private void setarValores() {
        DateFormat df = new SimpleDateFormat();
        tv_periodo.setText("De " + viagem_selecionada.getComeco() + " até " + viagem_selecionada.getFim());
        String categoria = viagem_selecionada.getTipo() == 1 ? getString(R.string.rb_tipo_viagem_pessoal) : getString(R.string.rb_tipo_viagem_trabalho);
        tv_tipo.setText(getString(R.string.tv_categoria) + ": " + categoria);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal_viagem, menu);
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
}
