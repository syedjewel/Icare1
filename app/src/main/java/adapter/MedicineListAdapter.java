package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import database.DataStorage;
import model.AppointmentModel;
import model.DoctorModel;
import model.MedicineModel;
import com.warriors.group.icare.R;

/**
 * Created by Rubayet on 19-Jan-16.
 */
public class MedicineListAdapter extends ArrayAdapter
{
    ArrayList<MedicineModel> medicineModels = new ArrayList<>();
    Context  context;
    LayoutInflater inflater;
    DataStorage dataStorage;
    ArrayList<DoctorModel> doctorModels = new ArrayList<>();
    int cross = R.drawable.cross;
    int check = R.drawable.check;

    public MedicineListAdapter(Context context, ArrayList<MedicineModel> medicineModels)
    {
        super(context, R.layout.medicine_list_view,medicineModels);
        this.context =context;
        this.medicineModels = medicineModels;
        dataStorage = new DataStorage(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder medicineHolder=new ViewHolder();

        if(convertView==null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.medicine_list_view, parent, false);
            medicineHolder.medicineLVmedicineNameTV = (TextView) convertView.findViewById(R.id.medicineLVmedicineNameTV);
            medicineHolder.medicineLVstartDateTV = (TextView) convertView.findViewById(R.id.medicineLVstartDateTV);
            medicineHolder.medicineLVendDateTV = (TextView) convertView.findViewById(R.id.medicineLVendDateTV);
            medicineHolder.medicineLVdoseTV = (TextView) convertView.findViewById(R.id.medicineLVdoseTV);
            medicineHolder.medicineLdoctorTV = (TextView) convertView.findViewById(R.id.medicineLdoctorTV);
            medicineHolder.medicineLremarksTV = (TextView) convertView.findViewById(R.id.medicineLremarksTV);
            medicineHolder.medicineLVmorningIV = (ImageView) convertView.findViewById(R.id.medicineLVmorningIV);
            medicineHolder.medicineLVafternoonIV = (ImageView) convertView.findViewById(R.id.medicineLVafternoonIV);
            medicineHolder.medicineLVeveningIV = (ImageView) convertView.findViewById(R.id.medicineLVeveningIV);
            medicineHolder.medicineLVnightIV = (ImageView) convertView.findViewById(R.id.medicineLVnightIV);

            convertView.setTag(medicineHolder);
        }
        else
        {
            medicineHolder = (ViewHolder) convertView.getTag();
        }

        String doctorID = medicineModels.get(position).getDoctorId();
        int docID=0;
        String doctorName="";
        String doctorSpecialist="";
        String doctorAddress="";
        String doctorMobile="";
        String doctorEmail="";
        if (doctorID!=null) {
            docID = Integer.valueOf(doctorID);
        }
        doctorModels = dataStorage.getDoctorModelByDoctorID(docID);
        if(doctorModels.size()>0) {
            doctorName = doctorModels.get(0).getDocName();
            doctorSpecialist = doctorModels.get(0).getDocSpecialist();
            doctorAddress = doctorModels.get(0).getDocAddress();
            doctorMobile = doctorModels.get(0).getDocContactNo();
            doctorEmail = doctorModels.get(0).getDocEmail();
        }
        ///////
        String medicineName = medicineModels.get(position).getMedicineName();
        String startDate = medicineModels.get(position).getmFromDate();
        String endDate = medicineModels.get(position).getmToDate();
        String dose = medicineModels.get(position).getMedicineDose();
        String remarks = medicineModels.get(position).getmRemarks();

        String morning = medicineModels.get(position).getmMorning();
        String afternoon = medicineModels.get(position).getmAfterNoon();
        String evening = medicineModels.get(position).getmEvening();
        String night = medicineModels.get(position).getmNight();

        /////
        medicineHolder.medicineLVmedicineNameTV.setText(medicineName);
        medicineHolder.medicineLVstartDateTV.setText(startDate);
        medicineHolder.medicineLVendDateTV.setText(endDate);
        medicineHolder.medicineLVdoseTV.setText(dose);
        medicineHolder.medicineLdoctorTV.setText(doctorName);
        medicineHolder.medicineLremarksTV.setText(remarks);


        if(morning == null || morning.equalsIgnoreCase("0"))
        {
            medicineHolder.medicineLVmorningIV.setImageResource(cross);
        }
        else
        {
            medicineHolder.medicineLVmorningIV.setImageResource(check);
        }
/////////////////////////////////////
        if(afternoon == null || afternoon.equalsIgnoreCase("0"))
        {
            medicineHolder.medicineLVafternoonIV.setImageResource(cross);
        }
        else
        {
            medicineHolder.medicineLVafternoonIV.setImageResource(check);
        }
        /////////////////////////////////////
        if(evening == null || evening.equalsIgnoreCase("0"))
        {
            medicineHolder.medicineLVeveningIV.setImageResource(cross);
        }
        else
        {
            medicineHolder.medicineLVeveningIV.setImageResource(check);
        }
        /////////////////////////////////////
        if(night == null || night.equalsIgnoreCase("0"))
        {
            medicineHolder.medicineLVnightIV.setImageResource(cross);
        }
        else
        {
            medicineHolder.medicineLVnightIV.setImageResource(check);
        }


        return convertView;
    }

    private static class ViewHolder{

        TextView medicineLVmedicineNameTV;
        TextView medicineLVstartDateTV;
        TextView medicineLVendDateTV;
        TextView medicineLVdoseTV;
        TextView medicineLdoctorTV;
        TextView medicineLremarksTV;
        ImageView medicineLVmorningIV;
        ImageView medicineLVafternoonIV;
        ImageView medicineLVeveningIV;
        ImageView medicineLVnightIV;

    }
}
