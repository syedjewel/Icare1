package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.warriors.group.icare.R;

import java.util.ArrayList;

import model.DoctorModel;

/**
 * Created by Jewana on 1/14/2016.
 */
public class DoctorsListAdapter extends ArrayAdapter {


    ArrayList<DoctorModel> doctorModels = new ArrayList<>();
    Context  context;
    LayoutInflater inflater;


    public DoctorsListAdapter(Context context, ArrayList<DoctorModel> doctorModels)
    {
        super(context, R.layout.doctor_list_view, doctorModels);
        this.context = context;
        this.doctorModels = doctorModels;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder doctorlHolder=new ViewHolder();

        if(convertView==null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.doctor_list_view, parent, false);

            doctorlHolder.doctorListNameTV = (TextView) convertView.findViewById(R.id.doctorListNameTV);
            doctorlHolder.doctorListSpecialistTV = (TextView) convertView.findViewById(R.id.doctorListSpecialistTV);
            doctorlHolder.doctorListAddressTV = (TextView) convertView.findViewById(R.id.doctorListAddressTV);
            doctorlHolder.doctorListMobileTV = (TextView) convertView.findViewById(R.id.doctorListMobileTV);
            doctorlHolder.doctorListEmailTV = (TextView) convertView.findViewById(R.id.doctorListEmailTV);

            convertView.setTag(doctorlHolder);
        }
        else
        {
            doctorlHolder = (ViewHolder) convertView.getTag();
        }

        String doctorName = doctorModels.get(position).getDocName();
        String doctorSpecialist = doctorModels.get(position).getDocSpecialist();
        String doctorAddress = doctorModels.get(position).getDocAddress();
        String doctorMobile = doctorModels.get(position).getDocContactNo();
        String doctorEmail = doctorModels.get(position).getDocEmail();


        //String address = dietMenu+","+diteTime+","+dietDate;

        doctorlHolder.doctorListNameTV.setText(doctorName);
        doctorlHolder.doctorListSpecialistTV.setText(doctorSpecialist);
        doctorlHolder.doctorListAddressTV.setText(doctorAddress);
        doctorlHolder.doctorListMobileTV.setText(doctorMobile);
        doctorlHolder.doctorListEmailTV.setText(doctorEmail);

        return convertView;
    }

    private static class ViewHolder{

        TextView doctorListNameTV;
        TextView  doctorListSpecialistTV;
        TextView doctorListAddressTV;
        TextView  doctorListMobileTV;
        TextView doctorListEmailTV;

    }

}
