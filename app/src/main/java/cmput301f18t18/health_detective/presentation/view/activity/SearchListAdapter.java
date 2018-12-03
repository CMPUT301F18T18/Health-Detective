package cmput301f18t18.health_detective.presentation.view.activity;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cmput301f18t18.health_detective.R;

public class SearchListAdapter extends ArrayAdapter{

    private Context mContext;
    private List<String> testList = new ArrayList<>();


    public SearchListAdapter(@NonNull Activity context, ArrayList<String> list) {
        super(context, R.layout.ind_search_view, list);
        mContext = context;
        testList = list;
    }

    @Override
    public View getView(final int postition, View view, ViewGroup parent){
        View rowView = view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        rowView = inflater.inflate(R.layout.ind_search_view, null, true);

//        // Setting images
        ImageView deleteImg = (ImageView) rowView.findViewById(R.id.deleteImg);
        ImageView editImg = (ImageView) rowView.findViewById(R.id.editImg);
        String data = testList.get(postition);
        TextView titleText = rowView.findViewById(R.id.problemTitle);
        titleText.setText(data);

        TextView userIdText = rowView.findViewById(R.id.userIdView);
        userIdText.setText(data);

        TextView descText = rowView.findViewById(R.id.descView);
        descText.setText(data);

        TextView activityBtn = rowView.findViewById(R.id.activityBtn);

        activityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(mContext, "Change Activity", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        deleteImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(mContext, "Delete", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        editImg.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(mContext, "Edit"+ testList.get(postition), Toast.LENGTH_SHORT);
                toast.show();
            }
        }));

        return rowView;

    }
}
