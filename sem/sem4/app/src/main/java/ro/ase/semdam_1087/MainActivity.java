package ro.ase.semdam_1087;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
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
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                poz = position;
                intent = new Intent(getApplicationContext(), AddActivity.class);
                intent.putExtra(EDIT_MASINA,listaMasini.get(position));
                startActivityForResult(intent, REQUEST_CODE_EDIT);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Masina masina = listaMasini.get(position);
                ArrayAdapter adapter = (ArrayAdapter) listView.getAdapter();
                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Confirmare stergere")
                        .setMessage("Sigur doriti stergerea?")
                        .setNegativeButton("Nu", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(getApplicationContext(), "Nu am sters nimic!", Toast.LENGTH_SHORT).show();
                                dialogInterface.cancel();
                            }
                        })
                        .setPositiveButton("Da", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                listaMasini.remove(masina);
                                adapter.notifyDataSetChanged();
                                Toast.makeText(getApplicationContext(), "Am sters " + masina.toString(), Toast.LENGTH_SHORT).show();
                                dialogInterface.cancel();
                            }
                        })
                        .create();
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
                if(masina!=null){
//                    ArrayAdapter<Masina> adapter = new ArrayAdapter<>(this,
//                            android.R.layout.simple_list_item_1, listaMasini);
//                    listView.setAdapter(adapter);
                    CustomAdapter adapter =  new CustomAdapter(this,
                    R.layout.elem_listview,
                    listaMasini,
                    getLayoutInflater());
                    listView.setAdapter(adapter);
                }

            }
        }
    }
}