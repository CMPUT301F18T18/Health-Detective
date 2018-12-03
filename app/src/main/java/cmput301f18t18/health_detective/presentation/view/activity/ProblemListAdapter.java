package cmput301f18t18.health_detective.presentation.view.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import cmput301f18t18.health_detective.R;
import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.presentation.view.activity.listeners.ProblemOnClickListener;

public class ProblemListAdapter extends ArrayAdapter {

    private Context mContext;
    private List<Problem> problemList = new ArrayList<>();
    private ProblemOnClickListener listener;
    Boolean userType;
    private DateFormat dateFormat = new SimpleDateFormat("dd MMMM YYYY hh:mma");

    public ProblemListAdapter(@NonNull Activity context, ArrayList<Problem> list, ProblemOnClickListener listener, Boolean type) {
        super(context, R.layout.ind_problem_view, list);
        this.mContext = context;
        this.problemList = list;
        this.listener = listener;
        this.userType = type;
    }

    @Override
    public View getView(final int postition, View view, ViewGroup parent){
        View rowView = view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        rowView = inflater.inflate(R.layout.ind_problem_view, null, true);


        ImageView deleteImg = rowView.findViewById(R.id.deleteImg);
        ImageView editImg = rowView.findViewById(R.id.editImg);
        TextView titleText = rowView.findViewById(R.id.problemTitle);
        TextView descText = rowView.findViewById(R.id.descView);
        TextView recordBut = rowView.findViewById(R.id.recordsBut);
        TextView dateText = rowView.findViewById(R.id.problemDate);

        editImg.setImageResource(R.drawable.baseline_create_black_48);
        deleteImg.setImageResource(R.drawable.baseline_delete_black_48);


        if (userType){ // if care provider, hide the edit and delete buttons
            deleteImg.setVisibility(View.GONE);
            editImg.setVisibility(View.GONE);
            recordBut.setTextColor(ContextCompat.getColor(mContext, R.color.colorCareProvider));
        }

        Problem data = problemList.get(postition);
        titleText.setText(data.getTitle());
        descText.setText(data.getDescription());
        dateText.setText(dateFormat.format(data.getStartDate()).replace("AM","am").replace("PM","pm"));

        deleteImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDeleteClicked(problemList.get(postition));
            }

        });

        editImg.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onEditClicked(problemList.get(postition));
            }
        }));

        recordBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onRecordsClicked(problemList.get(postition));
            }
        });

        return rowView;
    }


}
