

package adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import model.ProfileModel;
import util.CircleTransform;
import util.Utility;

/**
 * Created by Mobile App Develop on 7-1-16.
 */


public class ProfileListAdapter extends ArrayAdapter {
    ArrayList<ProfileModel> profileModels = new ArrayList<>();
    Context context;
    LayoutInflater inflater;


    public ProfileListAdapter(Context context, ArrayList<ProfileModel> profileModels) {
        super(context, R.layout.profile_list_view, profileModels);
        this.context = context;
        this.profileModels = profileModels;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder profileHolder = new ViewHolder();

        if (convertView == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.profile_list_view, parent, false);
            profileHolder.profileLVnameTV = (TextView) convertView.findViewById(R.id.profileLVnameTV);
            profileHolder.profileLVRelationTV = (TextView) convertView.findViewById(R.id.profileLVRelationTV);
            profileHolder.profileLVpicIV = (ImageView) convertView.findViewById(R.id.profileLVpicIV);
            convertView.setTag(profileHolder);

        } else {
            profileHolder = (ViewHolder) convertView.getTag();
        }

        String profileNamePosition = (profileModels.get(position)).getProfileName();
        String profileRelationPosition = (profileModels.get(position)).getRelation();
        String profileImagePathPosition = (profileModels.get(position)).getImagePath();

        if (profileImagePathPosition != null) {
            Uri uri = Uri.fromFile(new File(profileImagePathPosition));
            Picasso.with(context).load(uri)
                    .resize(136, 136).centerCrop().transform(new CircleTransform()).into(profileHolder.profileLVpicIV);

            //Picasso.with(activity).load(mayorShipImageLink).transform(new CircleTransform()).into(profileHolder.profileLVpicIV);

        }

        profileHolder.profileLVnameTV.setText(profileNamePosition);
        profileHolder.profileLVRelationTV.setText(profileRelationPosition);

        return convertView;
    }

    private static class ViewHolder {
        TextView profileLVnameTV;
        TextView profileLVRelationTV;
        ImageView profileLVpicIV;
    }
}