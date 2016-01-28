package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.warriors.group.icare.R;

import java.util.ArrayList;

import model.VaccineModel;

/**
 * Created by Jewana on 1/14/2016.
 */
public class VaccineListAdapter extends ArrayAdapter {

    ArrayList<VaccineModel> vaccineModels = new ArrayList<>();
    Context context;
    LayoutInflater inflater;

    public VaccineListAdapter(Context context, ArrayList<VaccineModel> vaccineModels) {
        super(context, R.layout.vaccine_list_view, vaccineModels);
        this.context = context;
        this.vaccineModels = vaccineModels;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder dietlHolder = new ViewHolder();

        if (convertView == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.vaccine_list_view, parent, false);

            dietlHolder.vaccineLVVaccineName = (TextView) convertView.findViewById(R.id.vaccineLVVaccineName);
            dietlHolder.vaccineLVVaccineDetails = (TextView) convertView.findViewById(R.id.vaccineLVVaccineDetails);
            dietlHolder.vaccineLVVaccineTime = (TextView) convertView.findViewById(R.id.vaccineLVVaccineTime);
            dietlHolder.vaccineLVVaccineDate = (TextView) convertView.findViewById(R.id.vaccineLVVaccineDate);

            convertView.setTag(dietlHolder);
        } else {
            dietlHolder = (ViewHolder) convertView.getTag();
        }

        String vaccineName = vaccineModels.get(position).getVaccineName();
        String vaccineDetails = vaccineModels.get(position).getvDetails();
        String vaccineTime = vaccineModels.get(position).getVaccineTime();
        String vaccineDate = vaccineModels.get(position).getVaccineDate();


        //String address = dietMenu+","+diteTime+","+dietDate;

        dietlHolder.vaccineLVVaccineName.setText(vaccineName);
        dietlHolder.vaccineLVVaccineDetails.setText(vaccineDetails);
        dietlHolder.vaccineLVVaccineTime.setText(vaccineTime);
        dietlHolder.vaccineLVVaccineDate.setText(vaccineDate);

        return convertView;
    }

    private static class ViewHolder {
        TextView vaccineLVVaccineName;
        TextView vaccineLVVaccineDetails;
        TextView vaccineLVVaccineTime;
        TextView vaccineLVVaccineDate;
    }
}