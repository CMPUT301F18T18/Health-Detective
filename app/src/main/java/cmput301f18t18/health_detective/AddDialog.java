package cmput301f18t18.health_detective;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import cmput301f18t18.health_detective.domain.model.Geolocation;
import cmput301f18t18.health_detective.presentation.view.activity.MapActivity;
import cmput301f18t18.health_detective.presentation.view.activity.PatientRecordsActivity;

/**
/  This class creates a Add Record Dialog box that gets user inputted Title and Description
/
*/
public class AddDialog extends AppCompatDialogFragment implements View.OnClickListener{
    private AddDialogListener listener;
    private EditText addTitle, addDesc;
    private TextView currentDate, currentLocation;
    private Button addDate, addGeo;
    private Geolocation geolocation;
    private Date updateDate = new Date();
    private Address address;


    public AddDialog() {
        this.geolocation = null;
    }

    @SuppressLint("ValidFragment")
    public AddDialog(Geolocation geolocation) {
        this.geolocation = geolocation;
    }

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
        currentDate = view.findViewById(R.id.add_record_date);
        currentLocation = view.findViewById(R.id.add_record_geo);
        Calendar c = Calendar.getInstance();
        Date nowDate = c.getTime();
        try {
            address = listener.getAddress();
        } catch (IOException e) {
            e.printStackTrace();
        }
        updateAddress(address);
        currentDate.setText(nowDate.toString());

        addDate = view.findViewById(R.id.addDateRecordBtn);
        addGeo = view.findViewById(R.id.addGeoRecordBtn);
        addGeo.setOnClickListener(this);
        addDate.setOnClickListener(this);

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

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.addDateRecordBtn){
            listener.applyDate();
        }
        else if (v.getId() == R.id.addGeoRecordBtn){
            listener.openMapDialog();

        }
    }

    /**
     * AddDialogListener is an interface that our activity implements, and then they define applyEdit
     */
    public interface AddDialogListener{
        void applyEdit(String title, String comment);
        void applyDate();
        Address getAddress() throws IOException;
        void openMapDialog();

    }


    public void updateAddress(Address address){
        currentLocation.setText(address.getAddressLine(0));
    }

    public void changeTime(Date date){
        this.updateDate = date;
        this.currentDate.setText(updateDate.toString());
    }



}
