package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.warriors.group.icare.R;

import java.util.ArrayList;

import model.HospitalModel;

/**
 * Created by Rubayet on 13-Jan-16.
 */
public class HospitalListAdapter extends ArrayAdapter {

    ArrayList<HospitalModel> hospitalModels = new ArrayList<>();
    Context  context;
    LayoutInflater inflater;

    public HospitalListAdapter(Context context, ArrayList<HospitalModel> hospitalModels)
    {
        super(context, R.layout.hospital_list,hospitalModels);
        this.context = context;
        this.hospitalModels = hospitalModels;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder hospitalHolder=new ViewHolder();

        if(convertView==null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.hospital_list, parent, false);

            hospitalHolder.hospitalListNameTV = (TextView) convertView.findViewById(R.id.hospitalListNameTV);
            hospitalHolder.hospitalListAddressTV = (TextView) convertView.findViewById(R.id.hospitalListAddressTV);
            hospitalHolder.hospitalListEmailTV= (TextView) convertView.findViewById(R.id.hospitalListEmailTV);
            hospitalHolder.hospitalListPhoneTV = (TextView) convertView.findViewById(R.id.hospitalListPhoneTV);

            convertView.setTag(hospitalHolder);
        }
        else
        {
            hospitalHolder = (ViewHolder) convertView.getTag();
        }

        String hospitalName = hospitalModels.get(position).getHospitalName();
        String roadName = hospitalModels.get(position).getRoadName();
        String cityName = hospitalModels.get(position).getCityName();
        String countryName = hospitalModels.get(position).getCountryName();
        String email = hospitalModels.get(position).getEmailAddress();
        String phone = hospitalModels.get(position).getContactNumber();

        String address = roadName+",\n"+cityName+",\n"+countryName;

        hospitalHolder.hospitalListNameTV.setText(hospitalName);
        hospitalHolder.hospitalListAddressTV.setText(address);
        hospitalHolder.hospitalListEmailTV.setText(email);
        hospitalHolder.hospitalListPhoneTV.setText(phone);

        return convertView;
    }

    private static class ViewHolder
    {
        TextView hospitalListNameTV;
        TextView hospitalListAddressTV;
        TextView hospitalListEmailTV;
        TextView hospitalListPhoneTV;
    }
}
