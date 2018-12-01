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

import java.util.ArrayList;
import java.util.List;

import cmput301f18t18.health_detective.R;
import cmput301f18t18.health_detective.domain.model.Record;
import cmput301f18t18.health_detective.presentation.view.activity.listeners.RecordOnClickListener;

public class RecordListAdapter extends ArrayAdapter{

    private Context mContext;
    private List<Record> recordList = new ArrayList<>();
    private RecordOnClickListener listener;


    public RecordListAdapter(@NonNull Activity context, ArrayList<Record> list, RecordOnClickListener listener) {
        super(context, R.layout.ind_record_view, list);
        mContext = context;
        recordList = list;
        this.listener = listener;
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
        recordDate.setText(record.getDate().toString());

        ImageView deleteImg = rowView.findViewById(R.id.deleteImg);

        ImageView recordImg = rowView.findViewById(R.id.recordImg);
        recordImg.setImageResource(R.drawable.ic_launcher_background);

        deleteImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDeleteClicked(recordList.get(postition));
            }

        });

        return rowView;
    }


}
