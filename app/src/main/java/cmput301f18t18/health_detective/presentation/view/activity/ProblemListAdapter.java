package cmput301f18t18.health_detective.presentation.view.activity;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
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
import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.presentation.view.activity.listeners.ProblemOnClickListener;

public class ProblemListAdapter extends ArrayAdapter {

    private Context mContext;
    private List<Problem> problemList = new ArrayList<>();
    private ProblemOnClickListener listener;


    public ProblemListAdapter(@NonNull Activity context, ArrayList<Problem> list, ProblemOnClickListener listener) {
        super(context, R.layout.ind_problem_view, list);
        this.mContext = context;
        this.problemList = list;
        this.listener = listener;
    }

    @Override
    public View getView(final int postition, View view, ViewGroup parent){
        View rowView = view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        rowView = inflater.inflate(R.layout.ind_problem_view, null, true);
        ImageView deleteImg = (ImageView) rowView.findViewById(R.id.deleteImg);
        deleteImg.setImageResource(R.drawable.delete);

        ImageView editImg = (ImageView) rowView.findViewById(R.id.editImg);
        TextView titleText = rowView.findViewById(R.id.probTitle);
        TextView descText = rowView.findViewById(R.id.descText);
        editImg.setImageResource(R.drawable.editpencil);


        Problem data = problemList.get(postition);
        titleText.setText(data.getTitle());
        descText.setText(data.getDescription());


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

        return rowView;
    }


}
