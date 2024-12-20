package ro.ase.semdam_1087;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import androidx.appcompat.app.AppCompatActivity;

public class BNRActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bnr);
       /* ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/

        Log.e("lifecycle", "Am apelat onCreate()");

        TextView tvData = findViewById(R.id.tvDataCurs);
        EditText etEUR = findViewById(R.id.editTextEUR);
        EditText etUSD = findViewById(R.id.editTextUSD);
        EditText etGBP = findViewById(R.id.editTextGBP);
        EditText etXAU = findViewById(R.id.editTextXAU);

        //Button btn1 = new Button(this);
        Button btn1 = findViewById(R.id.btnAfisare);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*etEUR.setText("4.967");
                etUSD.setText("4.789");
                etGBP.setText("6.567");
                etXAU.setText("320.789");

                Toast.makeText(getApplicationContext(),
                        "Am afisat cursul valutar!",
                        Toast.LENGTH_LONG).show();*/
                Network network = new Network()
                {
                    @Override
                    protected void onPostExecute(InputStream inputStream) {
                        /*Toast.makeText(getApplicationContext(),
                                Network.rezultat,
                                Toast.LENGTH_LONG).show();*/
                        tvData.setText(cv.getDataCurs());
                        etEUR.setText(cv.getCursEUR());
                        etUSD.setText(cv.getCursUSD());
                        etGBP.setText(cv.getCursGBP());
                        etXAU.setText(cv.getCursXAU());
                    }
                };
                try {
                    network.execute(new URL("https://bnr.ro/nbrfxrates.xml"));
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }

            }
        });

        Button btn2 = findViewById(R.id.btnSalvare);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CursValutar cursValutar = new CursValutar("12/11/2024", etEUR.getText().toString(),
                        etUSD.getText().toString(),
                        etGBP.getText().toString(),
                        etXAU.getText().toString());


                Intent intent = new Intent(getApplicationContext(), AfisareBNRActivity.class);
                intent.putExtra("curs", cursValutar);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("lifecycle", "Am apelat onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("lifecycle", "Am apelat onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("lifecycle", "Am apelat onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("lifecycle", "Am apelat onStop()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("lifecycle", "Am apelat onRestart()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("lifecycle", "Am apelat onDestroy()");
    }
}