package cmput301f18t18.health_detective.presentation.view.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import cmput301f18t18.health_detective.R;
import cmput301f18t18.health_detective.domain.model.Record;
import cmput301f18t18.health_detective.presentation.view.activity.listeners.RecordOnClickListener;
import cmput301f18t18.health_detective.presentation.view.activity.presenters.CameraPresenter;

public class RecordListAdapter extends ArrayAdapter{

    private Context mContext;
    private List<Record> recordList = new ArrayList<>();
    private RecordOnClickListener listener;
    private Boolean userType;
    private String currentUser;
    private DateFormat dateFormat = new SimpleDateFormat("dd MMMM YYYY hh:mma");


    public RecordListAdapter(@NonNull Activity context, ArrayList<Record> list, RecordOnClickListener listener, Boolean user, String cUser) {
        super(context, R.layout.ind_record_view, list);
        mContext = context;
        recordList = list;
        this.listener = listener;
        this.userType = user;
        this.currentUser = cUser;
    }

    @Override
    public View getView(final int postition, View view, ViewGroup parent) {
        View rowView = view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        rowView = inflater.inflate(R.layout.ind_record_view, null, true);

        Record record = recordList.get(postition);

        TextView recordTitle = rowView.findViewById(R.id.problemTitle);
        recordTitle.setText(record.getTitle());

        TextView recordUser = rowView.findViewById(R.id.userIdView);
        recordUser.setText(record.getAuthor());

        TextView recordDescription = rowView.findViewById(R.id.recordDesc);
        recordDescription.setText(record.getComment());

        TextView recordDate = rowView.findViewById(R.id.recordDate);
        recordDate.setText(dateFormat.format(record.getDate()).replace("AM","am").replace("PM","pm"));

        ImageView deleteImg = rowView.findViewById(R.id.deleteImg);
        ImageView uglyBlueString = rowView.findViewById(R.id.imageView7);

        if (userType){ // if care provider
            if (currentUser.equals(record.getAuthor())){ // if the current user is the same as the record author
                deleteImg.setColorFilter(ContextCompat.getColor(mContext, R.color.colorCareProvider)); //set the delete img to purple
            }
            else { //otherwise hide the delete img
                deleteImg.setVisibility(View.INVISIBLE);
            }
            uglyBlueString.setColorFilter(ContextCompat.getColor(mContext, R.color.colorCareProvider)); // set record to purple
        }
        if (!currentUser.equals(record.getAuthor())){ // if the current user doesn't match the record author
            uglyBlueString.setColorFilter(ContextCompat.getColor(mContext, R.color.colorCareProvider)); // sets care provider comments in patient list as purple
            if (userType){}
            else {
                deleteImg.setVisibility(View.INVISIBLE);
            }

        }

        recordUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onUserClicked(recordList.get(postition));
            }

        });

        deleteImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDeleteClicked(recordList.get(postition));
            }

        });

        return rowView;
    }


}
