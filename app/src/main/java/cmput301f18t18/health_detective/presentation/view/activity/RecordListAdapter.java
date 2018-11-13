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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import cmput301f18t18.health_detective.R;

public class RecordListAdapter extends ArrayAdapter implements View.OnClickListener{

    private Context mContext;
    private List<String> testList = new ArrayList<>();


    public RecordListAdapter(@NonNull Activity context, ArrayList<String> list) {
        super(context, R.layout.ind_record_view, list);
        mContext = context;
        testList = list;
    }

    @Override
    public View getView(final int postition, View view, ViewGroup parent) {
        View rowView = view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        rowView = inflater.inflate(R.layout.ind_record_view, null, true);

        TextView recordTitle = rowView.findViewById(R.id.recordTitle);
        recordTitle.setText("Test");

        TextView recordUser = rowView.findViewById(R.id.recordUser);
        recordUser.setText("test");

        TextView recordDescription = rowView.findViewById(R.id.recordDesc);
        recordDescription.setText("test");

        TextView recordDate = rowView.findViewById(R.id.recordDate);
        recordDate.setText("test");

        TextView recordBL = rowView.findViewById(R.id.recordBL);
        recordBL.setText("test");

        ImageView deleteImg = rowView.findViewById(R.id.deleteImg);
        deleteImg.setImageResource(R.drawable.delete);
        deleteImg.setOnClickListener(this);

        ImageView recordImg = rowView.findViewById(R.id.recordImg);
        recordImg.setImageResource(R.drawable.ic_launcher_background);



        return rowView;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.deleteImg){
            Toast toast = Toast.makeText(mContext, "Delete", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
