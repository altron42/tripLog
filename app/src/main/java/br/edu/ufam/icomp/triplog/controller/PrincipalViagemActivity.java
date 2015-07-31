package br.edu.ufam.icomp.triplog.controller;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import br.edu.ufam.icomp.triplog.R;
import br.edu.ufam.icomp.triplog.controller.fragments.MenuOpcoesFragment;
import br.edu.ufam.icomp.triplog.dao.CarteiraDAO;
import br.edu.ufam.icomp.triplog.model.Viagem;
import br.edu.ufam.icomp.triplog.util.BancoDeDados;
import br.edu.ufam.icomp.triplog.util.Opcoes;

public class PrincipalViagemActivity extends Activity {
    private CarteiraDAO carteiraDAO;

    private ImageView iv_banner_viagem;

    private TextView tv_periodo;
    private TextView tv_tipo;
    private TextView tv_orcamento_valor;

    private double orcamento_disponivel;

    private Viagem viagem_selecionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_viagem);

        new Opcoes(this);
        carteiraDAO = new CarteiraDAO(this);

        Intent intent = getIntent();
        viagem_selecionada = (Viagem) intent.getParcelableExtra("viagem_selecionada");

        Opcoes.setIdViagem(viagem_selecionada.getId());

        findViews();

        if (getFragmentManager().findFragmentById(R.id.fragment_lista_opcoes) == null) {
            MenuOpcoesFragment lista = new MenuOpcoesFragment();
            getFragmentManager().beginTransaction().add(R.id.fragment_lista_opcoes, lista).commit();
        }

        setTitle(viagem_selecionada.getNome());
    }

    @Override
    protected void onResume() {
        super.onResume();
        orcamento_disponivel = carteiraDAO.getOrcamentoDisponivel(viagem_selecionada.getId());
        setarValores();
    }

    private void findViews() {
        iv_banner_viagem = (ImageView) findViewById(R.id.iv_banner_viagem);
        iv_banner_viagem.setScaleType(ImageView.ScaleType.CENTER_CROP);
        tv_periodo = (TextView) findViewById(R.id.tv_periodo_viagem);
        tv_tipo = (TextView) findViewById(R.id.tv_tipo);
        tv_orcamento_valor = (TextView) findViewById(R.id.tv_orcamento_valor);
    }

    private void setarValores() {
        tv_periodo.setText("De " + viagem_selecionada.getComeco() +
                " até " + viagem_selecionada.getFim());

        tv_tipo.setText(getString(R.string.tv_categoria) + ": " + Opcoes.getTipoViagem(viagem_selecionada.getTipo()));

        tv_orcamento_valor.setText("R$ " + String.format("%.2f", orcamento_disponivel));

        String nome_imagem = viagem_selecionada.getIcone();

        if (nome_imagem == null || nome_imagem.matches("null")) {
            iv_banner_viagem.setImageResource(R.mipmap.ic_launcher);
        } else {
            File file = new File(this.getFilesDir(),nome_imagem);
            try {
                iv_banner_viagem.setImageBitmap(BitmapFactory.decodeStream(new FileInputStream(file)));
            } catch (FileNotFoundException e) {
                Log.e(null,"Imagem não encontrada");
                e.printStackTrace();
            }
        }
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
