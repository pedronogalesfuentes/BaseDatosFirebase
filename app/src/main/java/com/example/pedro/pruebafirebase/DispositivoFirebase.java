package com.example.pedro.pruebafirebase;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by pedro on 28/04/2017.
 */

public class DispositivoFirebase {

    FirebaseDatabase database;
    DatabaseReference myRef;

    public DispositivoFirebase() {

        //INI Create Firebase database object by using the following code:
        // Connect to the Firebase database
        database = FirebaseDatabase.getInstance();
        // Get a reference to the todoItems child items it the database
        myRef = database.getReference("hijo");
        //FIN Create Firebase database object by using the following code
        // Assign a listener to detect changes to the child items
        // of the database reference.
        myRef.addChildEventListener(new ChildEventListener() {

            // This function is called once for each child that exists
            // when the listener is added. Then it is called
            // each time a new child is added.
            /*

public abstract void onChildAdded (DataSnapshot snapshot, String previousChildName)
This method is triggered when a new child is added to the location to which this listener was added.
Parameters
snapshot	An immutable snapshot of the data at the new child location
previousChildName	The key name of sibling location ordered before the new child. This will be null for the first child node of a location.
             */
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                /*
                public T getValue (Class<T> valueType)
This method is used to marshall the data contained in this snapshot into a class of your choosing. The class must fit 2 simple constraints:
The class must have a default constructor that takes no arguments
The class must define public getters for the properties to be assigned. Properties without a public getter will be set to their default value when an instance is deserialized
An example class might look like:
Parameters
valueType	The class into which this snapshot should be marshalled
Returns
An instance of the class passed in, populated with the data from this snapshot
                 */
                Dispositivo dispositivo = dataSnapshot.getValue(Dispositivo.class); //creo una instancia de la clase Dispositivo y la rellena con todos los datos del dataSnapshot de los datos del nuevo hijo añadido
                String id = dispositivo.getId(); //cojo el campo id
                Log.d("DispositivoFirebase","onChildAdded:" + id );

            }

            // This function is called each time a child item is removed.
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Dispositivo dispositivo = dataSnapshot.getValue(Dispositivo.class); //creo una instancia de la clase Dispositivo y la rellena con todos los datos del dataSnapshot de los datos del nuevo hijo añadido
                String id = dispositivo.getId(); //cojo el campo id
                Log.d("DispositivoFirebase","onChildRemoved:" + id );

            }

            // The following functions are also required in ChildEventListener implementations.
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
            }

            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG:", "Failed to read value.", error.toException());
            }
        });
        //FIN
    }

    public DatabaseReference getMyRef() {
        return myRef;
    }

    public Dispositivo LeeDispositivo(String id) {

        return null;
    }

    public boolean GuardaDispositivo(Dispositivo dispositivo) {
        // Create a new child with a auto-generated ID.
        DatabaseReference childRef = myRef.push();
        // Set the child's data to the value passed in from the text box.
        childRef.setValue(dispositivo);
        Log.d("GuardaDispositivo", "guardado dispositivo");
        //FIN PROBAMOS A GUARDAR UN OBJETO

        return true;
    }

    public boolean BorraDispositivo(final String idABorrar) {

                        /*
                The Query class (and its subclass, DatabaseReference) are used for reading data. Listeners are attached, and they will be triggered when the corresponding data changes.
 public Query orderByValue ()
Create a query in which nodes are ordered by their value
Returns
A Query with the new constraint
                 */
        Query myQuery = myRef.orderByValue();
           /*
           public void addListenerForSingleValueEvent (ValueEventListener listener)
Add a listener for a single change in the data at this location. This listener will be triggered once with the value of the data at the location.
Parameters
listener	The listener to be called with the data
            */
        myQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
                        /*
                        public abstract void onDataChange (DataSnapshot snapshot)
                        This method will be called with a snapshot of the data at this location. It will also be called each time that data changes.
                        Parameters
                        snapshot	The current data at the location
                         */
            //String elementoABorrar = idABorrar;
            public void onDataChange(DataSnapshot dataSnapshot) {
                //nos pasan un dataSnapshot que es una copia de la BBDD desde el DatabaseReference myRef (en este caso todo lo que cuelga de hijo)
                if (dataSnapshot.exists()) { //si el snapShot que nos han pasado existe...
                    for (DataSnapshot child : dataSnapshot.getChildren()) { //para cada hijo que cuelga (en nuestro caso cada ramita que cuelga de "hijo")
                        if (child.child("id").exists()) {
                            String id = child.child("id").getValue().toString(); //valor del campo "id" en nuestra ramita
                            //Log.d("kk", id);
                            if (id.equals(idABorrar)) { //si el id del elemento coincide con el que tengo que borrar
                                child.getRef().removeValue(); //elimino el elemento
                                Log.d("BorrarDispositivo", "borrado dispositivo"+id);
                            }
                        }
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return true;

    }
}
