package cmput301f18t18.health_detective;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CareRecordDialog extends AppCompatDialogFragment implements View.OnClickListener {
    private EditText addDesc;
    private TextView currentDate;
    private CareAddDialogListener listener;
    private Button DateBtn;
    private DateFormat dateFormat = new SimpleDateFormat("dd MMMM YYYY hh:mma");

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_care_record_add, null);


        builder.setView(view)
                .setTitle("Add Record")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String desc = addDesc.getText().toString();
                        listener.applyCareRecord(desc);
                    }
                });
            DateBtn = view.findViewById(R.id.CareaddDateRecordBtn);
            DateBtn.setOnClickListener(this);
            currentDate = view.findViewById(R.id.care_add_record_date);
            currentDate.setText(dateFormat.format(new Date()).replace("AM","am").replace("PM","pm"));
            addDesc = view.findViewById(R.id.care_add_comment_record);

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        listener = (CareAddDialogListener) context;

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.CareaddDateRecordBtn) {
            listener.applyDate();
        }
    }

    public interface CareAddDialogListener {
        void applyCareRecord(String comment);
        void applyDate();

    }
    public void changeTime(Date date){
        this.currentDate.setText(dateFormat.format(date).replace("AM","am").replace("PM","pm"));
    }

}

