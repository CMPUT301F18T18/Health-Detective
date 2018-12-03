package cmput301f18t18.health_detective;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

// SingleAddDialog is a dialog that adds one string to a database
public class SingleAddDialog extends AppCompatDialogFragment {
    private AddPatientDialogListener addPatientDialogListener;
    private EditText addPatient;
    private String title;

    public SingleAddDialog(){
        this.title = "Add a Body Location Part";
    }

    @SuppressLint("ValidFragment")
    public SingleAddDialog(String title){
        this.title = title;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_add_patient_dialog, null);

        builder.setView(view)
                .setTitle(title)
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String patient = addPatient.getText().toString();
                        addPatientDialogListener.applyEdit(patient);
                    }
                });
        addPatient = view.findViewById(R.id.add_patient_id);

        return builder.create();
    }

    // assign listener to dialog
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            addPatientDialogListener = (AddPatientDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement ExampleDialogListener");
        }
    }

    // AddPatientDialogListener is implemented by listener/activity
    // applyEdit - applys the edit to database
    public interface AddPatientDialogListener{
        void applyEdit(String patient);

    }
}
