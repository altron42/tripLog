package br.edu.ufam.icomp.triplog.controller;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import br.edu.ufam.icomp.triplog.R;
import br.edu.ufam.icomp.triplog.dao.ViagemDAO;
import br.edu.ufam.icomp.triplog.model.Viagem;

public class SelecionarImagemActivity extends Activity {

    Button bt_selecionar;
    Button bt_concluir;
    ImageView iv_imagem;

    int id_viagem;

    ViagemDAO viagemDAO;
    Viagem viagem;

    Bitmap foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecionar_imagem);

        viagemDAO = new ViagemDAO(this);

        Intent intent = getIntent();
        id_viagem = intent.getIntExtra("id_viagem", 0);

        viagem = viagemDAO.getViagem(id_viagem);

        bt_selecionar = (Button) findViewById(R.id.bt_selecionar_imagem);
        bt_concluir = (Button) findViewById(R.id.bt_cadastrar_viagem);
        iv_imagem = (ImageView) findViewById(R.id.iv_imagem_selecionada);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_selecionar_imagem, menu);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK && data != null) {
            // Very important
            try {
                InputStream is = getContentResolver().openInputStream(data.getData());
                foto = BitmapFactory.decodeStream(is);
                is.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            iv_imagem.setImageBitmap(foto);
        }
    }

    public void selecionarImageClicado(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), 0);
    }

    public void cadastrarImagemClicado(View view) {
        String nome_arquivo = "viagem_banner-"+id_viagem+".png";
        File file = new File(getFilesDir(),nome_arquivo);
        if (file.exists())  file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            foto.compress(Bitmap.CompressFormat.PNG,50,out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        viagem.setIcone(nome_arquivo);
        if (!viagemDAO.editViagem(viagem)) {
            Toast.makeText(this,"Erro ao editar banco de dados",Toast.LENGTH_SHORT).show();
            return;
        }
        finish();
    }
}
