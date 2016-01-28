package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.warriors.group.icare.R;

import java.util.ArrayList;

import model.DietModel;

/**
 * Created by Jewana on 1/14/2016.
 */
public class DietListAdapter extends ArrayAdapter {

    ArrayList<DietModel> dietModels = new ArrayList<>();
    Context  context;
    LayoutInflater inflater;

    public DietListAdapter(Context context, ArrayList<DietModel> dietModels)
    {
        super(context, R.layout.diet_list_view, dietModels);
        this.context = context;
        this.dietModels = dietModels;
    }
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder dietlHolder=new ViewHolder();

        if(convertView==null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.diet_list_view, parent, false);

            dietlHolder.dietListViewDietType = (TextView) convertView.findViewById(R.id.dietListViewDietType);
            dietlHolder.dietListViewMenuType = (TextView) convertView.findViewById(R.id.dietListViewMenuType);
            dietlHolder.dietListViewTime = (TextView) convertView.findViewById(R.id.dietListViewTime);
            dietlHolder.dietListViewDate = (TextView) convertView.findViewById(R.id.dietListViewDate);

            convertView.setTag(dietlHolder);
        }
        else
        {
            dietlHolder = (ViewHolder) convertView.getTag();
        }

        String dietType = dietModels.get(position).getDiteType();
        String dietMenu = dietModels.get(position).getDietMenu();
        String diteTime = dietModels.get(position).getDietTime();
        String dietDate = dietModels.get(position).getDietDate();


        //String address = dietMenu+","+diteTime+","+dietDate;

        dietlHolder.dietListViewDietType.setText(dietType);
        dietlHolder.dietListViewMenuType.setText(dietMenu);
        dietlHolder.dietListViewTime.setText(diteTime);
        dietlHolder.dietListViewDate.setText(dietDate);

        return convertView;
    }

    private static class ViewHolder
    {
        TextView dietListViewDietType;
        TextView dietListViewMenuType;
        TextView dietListViewTime;
        TextView dietListViewDate;
    }
}
