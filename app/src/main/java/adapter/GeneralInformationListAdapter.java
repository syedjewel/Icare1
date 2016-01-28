package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import com.warriors.group.icare.R;

import model.GeneralInformationModel;

/**
 * Created by Rubayet on 20-Jan-16.
 */
public class GeneralInformationListAdapter extends ArrayAdapter {

    Context context;
    LayoutInflater inflater;
    ArrayList<GeneralInformationModel> names;
    public GeneralInformationListAdapter(Context context,ArrayList<GeneralInformationModel> names){
        super(context,R.layout.general_info_list_view,names);
        this.context = context;
        this.names = names;
    }
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder viewHolder = new ViewHolder();
        if(convertView == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.general_info_list_view,parent,false);
            viewHolder.nameTV = (TextView) convertView.findViewById(R.id.nameTV);
            viewHolder.descriptionTV = (TextView) convertView.findViewById(R.id.descriptionTV);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.nameTV.setText(names.get(position).getName());
        viewHolder.descriptionTV.setText(names.get(position).getDescription());
        return convertView;
    }
    private class ViewHolder{
        TextView nameTV;
        TextView descriptionTV;
    }
}