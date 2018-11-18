package cmput301f18t18.health_detective.presentation.view.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;

public class GeneralDialogFragment extends DialogFragment{

    private onDialogFragmentClickListener listener;

    public interface onDialogFragmentClickListener{
        public void onPosBtnClicked(Boolean posBtn);
        public void onNegBtnClicked(Boolean negBtn);
    }


    // creating instance of the dialog
    public static GeneralDialogFragment newInstance(String title, String posBtn, String negBtn) {
        GeneralDialogFragment newFrag = new GeneralDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("posBtn", posBtn);
        args.putString("negBtn", negBtn);
        newFrag.setArguments(args);
        return newFrag;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        listener = (onDialogFragmentClickListener) context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        return new AlertDialog.Builder(getActivity())
                .setTitle(getArguments().getString("title"))
                .setMessage(getArguments().getString("message"))
                .setCancelable(false)
                .setPositiveButton(getArguments().getString("posBtn"),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // Positive button clicked
                                listener.onPosBtnClicked(true);
                            }
                        }
                )
                .setNegativeButton(getArguments().getString("negBtn"),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // negative button clicked
                                listener.onNegBtnClicked(false);
                            }
                        }
                )
                .create();
    }
}
