package com.example.cavaleralexandru;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Job> listaJoburi=new ArrayList<>();
    private ListView listViewAfisare;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.proba_practica_2_cavaler_alexandru);

        listViewAfisare=findViewById(R.id.listViewJoburi);
        Button btn1=findViewById(R.id.btnIncarcaFisier);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                @SuppressLint("StaticFieldLeak")
                ExtractCSV extractCSV=new ExtractCSV(){
                    @Override
                    protected void onPostExecute(String s) {
                        listaJoburi.clear();
                        listaJoburi.addAll(jobListCSV);
                        ArrayAdapter<Job> adapter = new ArrayAdapter<>(
                                getApplicationContext(),
                                android.R.layout.simple_list_item_1,
                                listaJoburi
                        );
                        listViewAfisare.setAdapter(adapter);
                        int nrjoburi = listaJoburi.size();
                        Toast.makeText(getApplicationContext(),"Am incarcat "+nrjoburi+" joburi din CSV",Toast.LENGTH_SHORT).show();
                    }
                };
                try {
                    extractCSV.execute(new URL("https://cristianciurea.ase.ro/upload/jobs.txt"));
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
                /*1,IT Designer,SC Firma 1 SRL,9900
                2,Data Analyst,SC Firma 2 SRL,8700
                3,Programator,SC Firma 3 SRL,11900
                4,Secretar,SC Firma 4 SRL,4500
                5,Manager,SC Firma 5 SRL,12700*/
            }
        });
        Button btn2=findViewById(R.id.btnSalveazaDate);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                @SuppressLint("StaticFieldLeak")
                ExtractCSV extractCSV=new ExtractCSV(){
                    @Override
                    protected void onPostExecute(String s) {
                        listaJoburi.addAll(jobListCSV);
                        JoburiDB database=JoburiDB.getInstanta(getApplicationContext());
                        database.getJoburiDAO().deleteAll();
                        database.getJoburiDAO().insert(jobListCSV);
                        int nrjoburi = jobListCSV.size();
                        Toast.makeText(getApplicationContext(),"Am salvat in baza de date "+nrjoburi+" joburi",Toast.LENGTH_SHORT).show();
                    }
                };
                try {
                    extractCSV.execute(new URL("https://cristianciurea.ase.ro/upload/jobs.txt"));
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        Button btn3=findViewById(R.id.btnIncarcaDate);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JoburiDB database = JoburiDB.getInstanta(getApplicationContext());
                List<Job> jobMin = database.getJoburiDAO().getSalary(10000);
                ArrayAdapter<Job> adapter = new ArrayAdapter<>(
                        getApplicationContext(),
                        android.R.layout.simple_list_item_1,
                        jobMin
                );
                listViewAfisare.setAdapter(adapter);

                int nrjoburi = jobMin.size();
                Toast.makeText(getApplicationContext(),"Sunt "+ nrjoburi+" joburi cu salariu peste 10.000 ",Toast.LENGTH_SHORT).show();
            }
        });
        Button btn4=findViewById(R.id.btnStergeDate);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JoburiDB database=JoburiDB.getInstanta(getApplicationContext());
                int nrjob = database.getJoburiDAO().deleteJobs(10000);

                Toast.makeText(getApplicationContext(), "Au fost sterse "+nrjob+" joburi cu salariu sub 10.000", Toast.LENGTH_SHORT).show();
            }
        });

    }
}