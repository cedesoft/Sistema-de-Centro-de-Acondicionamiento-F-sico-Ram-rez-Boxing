package com.example.galeria;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Vector;

public class MainActivity extends AppCompatActivity {
    int n=0;
    private EditText entrada;
    Vector<Integer> id = new Vector<Integer>();
    Vector<String> habilitado = new Vector<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().
                permitNetwork().build());

        setContentView(R.layout.activity_main);
        Button boton;

        entrada = (EditText) findViewById(R.id.editText3);

        boton=(Button)findViewById(R.id.button);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (entrada.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),
                            "Ingrese el id por favor", Toast.LENGTH_SHORT).show();
                    // Focus en jugar y abrir el Teclado
                    entrada.requestFocus();
                }else {
                    n = Integer.parseInt(entrada.getText().toString());
                    MiTarea tarea = new MiTarea();
                    tarea.execute(n);

                }
            }
        });
    }
    class MiTarea extends AsyncTask<Integer, Integer, Integer> {

        private ProgressDialog progreso;
        @Override protected void onPreExecute() {
            progreso = new ProgressDialog(MainActivity.this);
            progreso.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progreso.setMessage("Calculando...");
            progreso.setCancelable(true);
            progreso.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override public void onCancel(DialogInterface dialog) {
                    MiTarea.this.cancel(true);
                }
            });
            progreso.setMax(100);
            progreso.setProgress(0);
            progreso.show();
        }
        @Override protected Integer doInBackground(Integer... n) {
            int res = 1;
            int i=0;
            boolean band= false;
            JSONParser2 obj = new JSONParser2(n);
            JSONParser3 obj1 = new JSONParser3(n);
        id =obj.doInBackground();
        habilitado=obj1.doInBackground();
            for(int i1=0;i1<id.size();i1++) {
                Log.d("HOLA",""+id.get(i1));
                publishProgress(id.size() * 100 / id.get(i1));
if(id.get(i1)==n[0]&&habilitado.get(i1).equals("v")){
    i1=id.size()+1;

    band=true;
    publishProgress(100);
    FragmentManager manager = getSupportFragmentManager();
    DialogFragmentGaleria galeria = new DialogFragmentGaleria(n[0]);
    galeria.setStyle(DialogFragment.STYLE_NO_FRAME,R.style.transparente);
    galeria.show(manager,"");
}



    SystemClock.sleep(1000);

            }


            return res;
        }
        @Override protected void onProgressUpdate(Integer... porc) {
            progreso.setProgress(porc[0]);
        }
        @Override protected void onPostExecute(Integer res) {
            progreso.dismiss();

        }
        @Override protected void onCancelled() {

        }

}

}
