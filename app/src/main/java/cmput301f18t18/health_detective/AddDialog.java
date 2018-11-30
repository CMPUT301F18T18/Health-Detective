package cmput301f18t18.health_detective;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**
/  This class creates a Add Record Dialog box that gets user inputted Title and Description
/
*/
public class AddDialog extends AppCompatDialogFragment{
    private AddDialogListener listener;
    private EditText addTitle, addDesc;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_add_record_dialog,null);

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
                        String title  = addTitle.getText().toString();
                        String desc = addDesc.getText().toString();
                        listener.applyEdit(title,desc);
                    }
                });
        addTitle = view.findViewById(R.id.add_title_record);
        addDesc = view.findViewById(R.id.add_comment_record);

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (AddDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement ExampleDialogListener");
        }
    }

    /**
     * AddDialogListener is an interface that our activity implements, and then they define applyEdit
     */
    public interface AddDialogListener{
        void applyEdit(String title, String comment);

    }

}
