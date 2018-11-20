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

import cmput301f18t18.health_detective.R;

public class EditDialog extends AppCompatDialogFragment {
    private EditText editText;
    private ExampleDialogListener listener;
    private String dialogPrompt, recordInfo;
    private int type;

    public EditDialog(){
        this.dialogPrompt = "Edit";
        this.type = 0;
        this.recordInfo = "";
    }

    @SuppressLint("ValidFragment")
    public EditDialog(String prompt, int type, String recordInfo) {
        this.dialogPrompt = prompt;
        this.type = type;
        this.recordInfo = recordInfo;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog,null);

        builder.setView(view)
                .setTitle(dialogPrompt)
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String editTextString  = editText.getText().toString();
                        if (type == 0) {
                            listener.applyEditTitle(editTextString);
                        }
                        if (type == 2){
                            listener.applyEditDesc(editTextString);
                        }

                    }
                });
        editText = view.findViewById(R.id.edit_dialog);
        editText.setText(this.recordInfo);

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (ExampleDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement ExampleDialogListener");
        }
    }

    public interface ExampleDialogListener{
        void applyEditTitle(String editedTitle);
        void applyEditDesc(String editedTitle);

    }
}
