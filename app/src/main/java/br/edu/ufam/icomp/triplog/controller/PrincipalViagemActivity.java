package br.edu.ufam.icomp.triplog.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import br.edu.ufam.icomp.triplog.R;
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
        tv_periodo.setText("De " + viagem_selecionada.getComeco() + " até " + viagem_selecionada.getFim());
        tv_tipo.setText("Categoria: " + viagem_selecionada.getTipoNome());
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
