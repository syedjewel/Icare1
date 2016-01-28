package adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.warriors.group.icare.R;

import java.io.File;
import java.util.ArrayList;

import database.DataStorage;
import model.DoctorModel;
import model.MedicalHistoryModel;

/**
 * Created by Rubayet on 17-Jan-16.
 */
public class MedicalHistroyListAdapter extends ArrayAdapter {
    ArrayList<MedicalHistoryModel> medicalHistoryModels = new ArrayList<>();
    Context  context;
    LayoutInflater inflater;
    ArrayList<DoctorModel> doctorModels = new ArrayList<>();
    DataStorage dataStorage;
    public MedicalHistroyListAdapter(Context context, ArrayList<MedicalHistoryModel> medicalHistoryModels)
    {
        super(context,R.layout.medical_history_list_view,medicalHistoryModels);
        this.context =context;
        this.medicalHistoryModels= medicalHistoryModels;
        dataStorage = new DataStorage(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder medicalHistoryHolder=new ViewHolder();

        if(convertView==null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.medical_history_list_view, parent, false);

            medicalHistoryHolder.mhLVpescriptionIV = (ImageView) convertView.findViewById(R.id.mhLVpescriptionIV);
            medicalHistoryHolder.mhLVdateTV = (TextView) convertView.findViewById(R.id.mhLVdateTV);
            medicalHistoryHolder.mhLVdetailsTV = (TextView) convertView.findViewById(R.id.mhLVdetailsTV);
            medicalHistoryHolder.mhLVdoctorTV = (TextView) convertView.findViewById(R.id.mhLVdoctorTV);

            convertView.setTag(medicalHistoryHolder);
        }
        else
        {
            medicalHistoryHolder = (ViewHolder) convertView.getTag();
        }

        String doctorID = medicalHistoryModels.get(position).getDoctorId();
        String date = medicalHistoryModels.get(position).getMhDate();
        String details =medicalHistoryModels.get(position).getDetails();
        String prescriptionPath = medicalHistoryModels.get(position).getPrescriptionPath();

        ///////
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
        String doctor = doctorName+"\nDepartment :"+doctorSpecialist+"\n"+doctorAddress+"\nContact no: "+doctorMobile+"\nEmail: "+doctorEmail;

        medicalHistoryHolder.mhLVdateTV.setText(date);
        medicalHistoryHolder.mhLVdetailsTV.setText(details);
        medicalHistoryHolder.mhLVdoctorTV.setText(doctor);

        if(prescriptionPath!= null)
        {
            Uri uri = Uri.fromFile(new File(prescriptionPath));
            Picasso.with(context).load(uri)
                    .resize(600,500).into(medicalHistoryHolder.mhLVpescriptionIV);
        }
        return convertView;
    }
    private static class ViewHolder{

        TextView mhLVdateTV;
        TextView  mhLVdetailsTV;
        TextView mhLVdoctorTV;
        ImageView mhLVpescriptionIV;

    }
}
