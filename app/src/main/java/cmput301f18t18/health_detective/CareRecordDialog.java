package cmput301f18t18.health_detective;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.Date;

public class CareRecordDialog extends AppCompatDialogFragment implements View.OnClickListener {
    private EditText addDesc;
    private TextView currentDate;
    private CareAddDialogListener listener;
    private Date updateDate;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_add_record_dialog, null);


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

            currentDate = view.findViewById(R.id.care_add_comment_record);
            currentDate.setText(new Date().toString());
            addDesc = view.findViewById(R.id.care_add_record_date);

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        listener = (CareAddDialogListener) context;

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.addDateRecordBtn) {
            listener.applyDate();
        }
    }

    public interface CareAddDialogListener {
        void applyCareRecord(String comment);
        void applyDate();

    }
    public void changeTime(Date date){
        this.updateDate = date;
        this.currentDate.setText(updateDate.toString());
    }

}

