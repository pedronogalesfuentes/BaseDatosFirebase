package com.example.pedro.pruebafirebase;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    DispositivoFirebase dispositivoFirebase = new DispositivoFirebase();
    final DatabaseReference myRef = dispositivoFirebase.getMyRef();

    //INI: lista de variables necesarias para crear la lista
    private List<String> lista; //objeto de tipo lista que tiene la lista de nombres
    private ListView listaListView; // objeto de tipo ListView que enlazaremos al ListView que hemos creado en el Layout
    private ArrayAdapter<String> adapter;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    //FIN: lista de variables necesarias para crear la lista


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//INI ahora hago una lista
        lista = new ArrayList<String>(); //hago la lista con los datos

        //hago el adapter con la lista anterior
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);

        listaListView = (ListView) findViewById(R.id.listView); // asociamos a nuestro objeto ListView el que hemos creado en el Layout
        //ahora le asocio el adapter a la lista
        listaListView.setAdapter(adapter);

        listaListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id){
                 //INI eliminamos el elemento seleccionado

                    final String elementoSeleccionado = (String) listaListView.getItemAtPosition(position);
                    Toast.makeText(getApplication(), "elementoSeleccionado:" + elementoSeleccionado, Toast.LENGTH_SHORT).show();
                    dispositivoFirebase.BorraDispositivo(elementoSeleccionado);
                    adapter.remove(elementoSeleccionado); //lo elimino de la lista

                //FIN eliminamos el elemento seleccionado
            }
        });




        //creamos la variable ligada al editText en el que introduciremos el texto cada vez que queramos
        //introducir un nuevo campo en la lista
        final EditText editText = (EditText) findViewById(R.id.editText);
        final EditText editText2 = (EditText) findViewById(R.id.editText2);

        //creamos un botón ligado al recurso button2
        //le asociamos un OnClickListener que leera el texto de la variable texto2 y lo introducirá en la BBDD Firebase
        final Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get the text from Edit text
                String texto = (String) editText.getText().toString();
                String texto2 = (String) editText2.getText().toString();
                Log.d("button","añado "+ texto);

                //INI  GUARDAR UN OBJETO
                    dispositivoFirebase.GuardaDispositivo(new Dispositivo(texto,texto2,texto));
                    adapter.add(texto);//lo incluyo en la lista
                // GUARDAR UN OBJETO
            }
        });


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

}

