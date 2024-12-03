package ro.ase.semdam_1087;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

        EditText etEUR = findViewById(R.id.editTextEUR);
        EditText etUSD = findViewById(R.id.editTextUSD);
        EditText etGBP = findViewById(R.id.editTextGBP);
        EditText etXAU = findViewById(R.id.editTextXAU);

        //Button btn1 = new Button(this);
        Button btn1 = findViewById(R.id.btnAfisare);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                etEUR.setText("4.967");
                etUSD.setText("4.789");
                etGBP.setText("6.567");
                etXAU.setText("320.789");

                Toast.makeText(getApplicationContext(),
                        "Am afisat cursul valutar!",
                        Toast.LENGTH_LONG).show();
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