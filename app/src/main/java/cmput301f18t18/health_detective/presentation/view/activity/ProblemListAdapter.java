package cmput301f18t18.health_detective.presentation.view.activity;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
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

public class ProblemListAdapter extends ArrayAdapter implements GeneralDialogFragment.onDialogFragmentClickListener{

    private Context mContext;
    private List<Problem> problemList = new ArrayList<>();


    public ProblemListAdapter(@NonNull Activity context, ArrayList<Problem> list) {
        super(context, R.layout.ind_problem_view, list);
        mContext = context;
        problemList = list;
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
//                Toast toast = Toast.makeText(mContext, "Delete", Toast.LENGTH_SHORT);
//                toast.show();
            }

        });

        editImg.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast toast = Toast.makeText(mContext, "Edit"+ testList.get(postition), Toast.LENGTH_SHORT);
//                toast.show();
            }
        }));

        return rowView;
    }

    @Override
    public void onPosBtnClicked(Boolean userClick) {
        Toast toast = Toast.makeText(mContext, "Edit", Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onNegBtnClicked(Boolean userClick) {
        Toast toast = Toast.makeText(mContext, "Delete", Toast.LENGTH_SHORT);
        toast.show();
    }
}
