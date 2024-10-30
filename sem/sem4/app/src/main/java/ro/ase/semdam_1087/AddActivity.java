package ro.ase.semdam_1087;

import static ro.ase.semdam_1087.MainActivity.EDIT_MASINA;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddActivity extends AppCompatActivity {

    public static final String ADD_MASINA = "addMasina";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();

        String[] culori = {"ALB", "NEGRU", "ROSU", "GRI", "ALBASTRU"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                culori);

        Spinner spinnerCulori = findViewById(R.id.spinnerCulori);
        spinnerCulori.setAdapter(adapter);

        EditText etMarca = findViewById(R.id.editTextMarca);
        EditText etDataFabricatiei = findViewById(R.id.editTextDate);
        EditText etPret = findViewById(R.id.editTextPret);
        RadioGroup radioGroup = findViewById(R.id.radioGroup);

        if(intent.hasExtra(EDIT_MASINA))
        {
            Masina masina = (Masina)intent.getSerializableExtra(EDIT_MASINA);
            etMarca.setText(masina.getMarca());
            etDataFabricatiei.setText(new SimpleDateFormat("MM/dd/yyyy",Locale.US)
                    .format(masina.getDataFabricatiei()));
            etPret.setText(String.valueOf(masina.getPret()));
            ArrayAdapter<String> adapter1 = (ArrayAdapter<String>) spinnerCulori.getAdapter();
            for(int i=0;i<adapter1.getCount();i++)
                if(adapter1.getItem(1).equals(masina.getCuloare()))
                {
                    spinnerCulori.setSelection(1);
                    break;
                }
                if(masina.getMotorizare().equals("BENZINA"))
                    radioGroup.check(R.id.radioBenzina);

        }

        Button btnAdauga = findViewById(R.id.btnAdauga);
        btnAdauga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etMarca.getText().toString().isEmpty())
                    etMarca.setError("Introduceti marca!");
                else
                    if(etDataFabricatiei.getText().toString().isEmpty())
                        etDataFabricatiei.setError("Introduceti data!");
                    else
                        if(etPret.getText().toString().isEmpty())
                            etPret.setError("Introduceti pretul!");
                        else {
                            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
                            try {
                                sdf.parse(etDataFabricatiei.getText().toString());
                                Date dataFabricatiei = new Date(etDataFabricatiei.getText().toString());

                                String marca = etMarca.getText().toString();

                                float pret = Float.parseFloat(etPret.getText().toString());

                                String culoare = spinnerCulori.getSelectedItem().toString();

                                RadioButton radioButton = findViewById(radioGroup.getCheckedRadioButtonId());

                                String motorizare = radioButton.getText().toString();

                                Masina masina = new Masina(marca, dataFabricatiei, pret, culoare, motorizare);
                                //Toast.makeText(getApplicationContext(), masina.toString(), Toast.LENGTH_LONG).show();

                                intent.putExtra(ADD_MASINA, masina);
                                setResult(RESULT_OK, intent);
                                finish();

                            } catch (ParseException e) {
                                throw new RuntimeException(e);
                            }
                            catch (Exception ex)
                            {
                                Log.e("AddActivity","Eroare introducere date!");
                                Toast.makeText(getApplicationContext(),
                                        "Eroare introducere date!", Toast.LENGTH_LONG).show();
                            }
                        }
            }
        });
    }
}