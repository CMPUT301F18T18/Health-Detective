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

public class ProblemListAdapter extends ArrayAdapter implements GeneralDialogFragment.onDialogFragmentClickListener{

    private Context mContext;
    private List<String> testList = new ArrayList<>();


    public ProblemListAdapter(@NonNull Activity context, ArrayList<String> list) {
        super(context, R.layout.ind_problem_view, list);
        mContext = context;
        testList = list;
    }

    @Override
    public View getView(final int postition, View view, ViewGroup parent){
        View rowView = view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        rowView = inflater.inflate(R.layout.ind_problem_view, null, true);
        ImageView deleteImg = (ImageView) rowView.findViewById(R.id.deleteImg);
        deleteImg.setImageResource(R.drawable.delete);

        ImageView editImg = (ImageView) rowView.findViewById(R.id.editImg);
        editImg.setImageResource(R.drawable.editpencil);

        TextView titleText = rowView.findViewById(R.id.probTitle);

        String data = testList.get(postition);
        titleText.setText(data);

        TextView descText = rowView.findViewById(R.id.descText);
        descText.setText(data);


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