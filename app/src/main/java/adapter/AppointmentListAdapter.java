package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.warriors.group.icare.R;
import java.util.ArrayList;
import database.DataStorage;
import model.AppointmentModel;
import model.DoctorModel;

/**
 * Created by Rubayet on 18-Jan-16.
 */
public class AppointmentListAdapter extends ArrayAdapter
{
    ArrayList<AppointmentModel> appointmentModels = new ArrayList<>();
    Context  context;
    LayoutInflater inflater;
    DataStorage dataStorage;
    ArrayList<DoctorModel> doctorModels = new ArrayList<>();

    public AppointmentListAdapter(Context context,ArrayList<AppointmentModel> appointmentModels )
    {
        super(context, R.layout.appointment_list_view,appointmentModels);
        this.context =context;
        this.appointmentModels = appointmentModels;
        dataStorage = new DataStorage(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder AppointmentHolder=new ViewHolder();

        if(convertView==null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.appointment_list_view, parent, false);

            AppointmentHolder.apptLVDateTV = (TextView) convertView.findViewById(R.id.apptLVDateTV);
            AppointmentHolder.apptLVTimeTV = (TextView) convertView.findViewById(R.id.apptLVTimeTV);
            AppointmentHolder.apptLVDoctorTV = (TextView) convertView.findViewById(R.id.apptLVDoctorTV);
            AppointmentHolder.apptLVSpecialistTV = (TextView) convertView.findViewById(R.id.apptLVSpecialistTV);
            convertView.setTag(AppointmentHolder);
        }
        else
        {
            AppointmentHolder = (ViewHolder) convertView.getTag();
        }

        String aaptDate = appointmentModels.get(position).getApptDate();
        String aaptTime = appointmentModels.get(position).getApptTime();
        String doctorID = appointmentModels.get(position).getDoctorId();
        String remarks = appointmentModels.get(position).getRemarks();


        ////////
        int docID=0;
        String doctorName="";
        if (doctorID!=null) {
             docID = Integer.valueOf(doctorID);
        }
        doctorModels = dataStorage.getDoctorModelByDoctorID(docID);
        if(doctorModels.size()>0) {
            doctorName = doctorModels.get(0).getDocName();
        }

        AppointmentHolder.apptLVDateTV.setText(aaptDate);
        AppointmentHolder.apptLVTimeTV.setText(aaptTime);
        AppointmentHolder.apptLVDoctorTV.setText(doctorName);
        AppointmentHolder.apptLVSpecialistTV.setText(remarks);

        return convertView;
    }
    private static class ViewHolder
    {
        TextView apptLVDateTV;
        TextView  apptLVTimeTV;
        TextView apptLVDoctorTV;
        TextView apptLVSpecialistTV;
    }
}
