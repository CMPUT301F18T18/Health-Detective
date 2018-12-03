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
import cmput301f18t18.health_detective.domain.model.Patient;
import cmput301f18t18.health_detective.domain.model.Problem;
import cmput301f18t18.health_detective.presentation.view.activity.listeners.PatientOnClickListener;

public class PatientListAdapter extends ArrayAdapter{

    private Context mContext;
    private List<Patient> patientList = new ArrayList<>();
    private PatientOnClickListener listener;

    public PatientListAdapter(@NonNull Activity context, ArrayList<Patient> list, PatientOnClickListener listener) {
        super(context, R.layout.ind_patient_view, list);
        this.mContext = context;
        this.patientList = list;
        this.listener = listener;
    }

    @Override
    public View getView(final int postition, View view, ViewGroup parent){
        View rowView = view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        rowView = inflater.inflate(R.layout.ind_patient_view, null, true);


        ImageView deleteImg = rowView.findViewById(R.id.deleteImg);
        TextView nameText = rowView.findViewById(R.id.patientName);
        TextView patientPhone = rowView.findViewById(R.id.patientPhone);
        TextView problemBut = rowView.findViewById(R.id.problemBut);

        deleteImg.setImageResource(R.drawable.baseline_delete_black_48);

        Patient data = patientList.get(postition);
        nameText.setText(data.getUserId());
        patientPhone.setText(data.getPhoneNumber());


        deleteImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDeleteClicked(patientList.get(postition));
            }

        });

        problemBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onPatientClicked(patientList.get(postition));
            }
        });

        return rowView;
    }
}
