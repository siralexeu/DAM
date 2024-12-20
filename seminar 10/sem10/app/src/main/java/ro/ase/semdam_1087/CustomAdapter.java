package ro.ase.semdam_1087;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CustomAdapter extends ArrayAdapter<Masina> {

    private Context context;
    private int resource;
    private List<Masina> masinaList;
    private LayoutInflater layoutInflater;

    public CustomAdapter(@NonNull Context context, int resource, List<Masina> list, LayoutInflater layoutInflater) {
        super(context, resource, list);
        this.context = context;
        this.resource = resource;
        this.masinaList = list;
        this.layoutInflater = layoutInflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = layoutInflater.inflate(resource, parent, false);

        Masina masina = masinaList.get(position);

        if(masina!=null)
        {
            TextView tvMarca = view.findViewById(R.id.tvMarca);
            tvMarca.setText(masina.getMarca());

            TextView tvDataFabricatie = view.findViewById(R.id.tvDataFabricatie);
            tvDataFabricatie.setText(masina.getDataFabricatiei().toString());

            TextView tvPret = view.findViewById(R.id.tvPret);
            tvPret.setText(String.valueOf(masina.getPret()));

            TextView tvCuloare = view.findViewById(R.id.tvCuloare);
            tvCuloare.setText(masina.getCuloare());

            TextView tvMotorizare = view.findViewById(R.id.tvMotorizare);
            tvMotorizare.setText(masina.getMotorizare());
        }
        return view;
    }
}

