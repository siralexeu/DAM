package ro.ase.semdam_1087;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_ADD = 100;
    public static final int REQUEST_CODE_EDIT = 200;
    public static final String EDIT_MASINA = "editareMasina";
    public int poz;
    private Intent intent;
    private ListView listView;
    private List<Masina> listaMasini = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), AddActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD);
            }
        });

        listView = findViewById(R.id.listViewMasini);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                poz = position;
                intent = new Intent(getApplicationContext(), AddActivity.class);
                intent.putExtra(EDIT_MASINA, listaMasini.get(position));
                startActivityForResult(intent, REQUEST_CODE_EDIT);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {

                Masina masina = listaMasini.get(position);

                ArrayAdapter adapter = (ArrayAdapter) listView.getAdapter();

                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Confirmare stergere")
                        .setMessage("Doriti stergerea?")
                        .setNegativeButton("NU", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(getApplicationContext(),
                                        "Nu am sters nimic!", Toast.LENGTH_LONG).show();
                                dialogInterface.cancel();
                            }
                        })
                        .setPositiveButton("DA", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                listaMasini.remove(masina);
                                adapter.notifyDataSetChanged();
                                Toast.makeText(getApplicationContext(),
                                        "Am sters "+masina.toString(), Toast.LENGTH_LONG).show();
                                dialogInterface.cancel();
                            }
                        }).create();

                dialog.show();

                return true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_ADD && resultCode==RESULT_OK && data!=null)
        {
            Masina masina = (Masina) data.getSerializableExtra(AddActivity.ADD_MASINA);
            if(masina!=null)
            {
                listaMasini.add(masina);
                /*ArrayAdapter<Masina> adapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_list_item_1, listaMasini);*/

                MasiniDB dataBase=MasiniDB.getInstanta(getApplicationContext());
                dataBase.getMasiniDAO().insert(masina);

                CustomAdapter adapter = new CustomAdapter(this,
                        R.layout.elem_listview,
                        listaMasini,
                        getLayoutInflater());
                listView.setAdapter(adapter);
            }
        }
        else
        if(requestCode == REQUEST_CODE_ADD && resultCode==RESULT_OK && data!=null)
        {
            Masina masina = (Masina) data.getSerializableExtra(AddActivity.ADD_MASINA);
            if(masina!=null)
            {
                listaMasini.get(poz).setMarca(masina.getMarca());
                listaMasini.get(poz).setCuloare(masina.getCuloare());
                listaMasini.get(poz).setDataFabricatiei(masina.getDataFabricatiei());
                listaMasini.get(poz).setPret(masina.getPret());
                listaMasini.get(poz).setMotorizare(masina.getMotorizare());

                MasiniDB database= MasiniDB.getInstanta(getApplicationContext());
                database.getMasiniDAO().update(listaMasini.get(poz));

                CustomAdapter adapter = new CustomAdapter(this,
                        R.layout.elem_listview,
                        listaMasini,
                        getLayoutInflater());

                listView.setAdapter(adapter);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        MasiniDB database= MasiniDB.getInstanta(getApplicationContext());
        database.getMasiniDAO().getAll();

        CustomAdapter adapter = new CustomAdapter(this,
                R.layout.elem_listview,
                listaMasini,
                getLayoutInflater());

        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.meniu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.optiune1)
        {
            Intent intent1 = new Intent(getApplicationContext(), BNRActivity.class);
            startActivity(intent1);
            return true;
        }
        else
            if(item.getItemId() == R.id.optiune2)
            {
                @SuppressLint("StaticFieldLeak")
                ExtractXML extractXML = new ExtractXML()
                {
                    @SuppressLint("StaticFieldLeak")/*ca sa nu imi mai apara subliniat cu galben*/
                    @Override
                    protected void onPostExecute(InputStream inputStream) {

                        listaMasini.addAll(this.masinaList);

                        MasiniDB database= MasiniDB.getInstanta(getApplicationContext());
                        database.getMasiniDAO().insert(masinaList);

                        CustomAdapter adapter = new CustomAdapter(getApplicationContext(),
                                R.layout.elem_listview,
                                listaMasini,
                                getLayoutInflater());

                        listView.setAdapter(adapter);
                    }
                };
                try {
                    extractXML.execute(new URL("https://pastebin.com/raw/Duf43Nyq"));
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }

                return true;
            }
            else
                if(item.getItemId() == R.id.optiune3)
                {
                    @SuppressLint("StaticFieldLeak")
                    ExtractJSON extractJSON=new ExtractJSON()
                    {
                        ProgressDialog dialog;
                        @Override
                        protected void onPreExecute() {
                            dialog=new ProgressDialog(MainActivity.this);
                            dialog.setMessage("Please wait...");
                            dialog.show();
                        }

                        @Override
                        protected void onPostExecute(String s) {
                            dialog.cancel();
                            listaMasini.addAll(masinaListJSON);

                            MasiniDB database= MasiniDB.getInstanta(getApplicationContext());
                            database.getMasiniDAO().insert(masinaListJSON);

                            CustomAdapter adapter = new CustomAdapter(getApplicationContext(),
                                    R.layout.elem_listview,
                                    listaMasini,
                                    getLayoutInflater());

                            listView.setAdapter(adapter);
                        }
                    };
                    try {
                        extractJSON.execute(new URL("https://pastebin.com/raw/hrE8Hie2"));
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                    return true;
                }
            else
                if(item.getItemId() == R.id.optiune4)
                {
                    int nrMasini = MasiniDB.getInstanta(getApplicationContext())
                            .getMasiniDAO().getNumarMasiniPret(10000);
                    Toast.makeText(this, "Masini cu pret mai mare decat 10000 sunt:" + nrMasini, Toast.LENGTH_SHORT).show();
                    return true;

                    /*MasiniDB database = MasiniDB.getInstanta(getApplicationContext());
                    List<Masina> masiniPret = database.getMasiniDao().getNumarMasiniPret(5000);
                    String mesaj = "Numărul de mașini: " + masiniPret.size();
                    Toast.makeText(getApplicationContext(),
                            mesaj, Toast.LENGTH_LONG).show();*/
                }
                else
                if(item.getItemId() == R.id.optiune5)
                {
                    MasiniDB.getInstanta(getApplicationContext())
                            .getMasiniDAO().deleteAll();
                    Toast.makeText(this, "Toate masinile au fost sterse", Toast.LENGTH_SHORT).show();
                    return true;

                }

        return false;
    }
}