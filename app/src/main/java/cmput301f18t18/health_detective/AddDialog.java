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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cmput301f18t18.health_detective.domain.model.Geolocation;
import cmput301f18t18.health_detective.presentation.view.activity.MapActivity;
import cmput301f18t18.health_detective.presentation.view.activity.PatientRecordsActivity;

/**
/  This class creates a Add Record Dialog box that gets user inputted Title and Description
/  AddDialog is the dialog that is used to add a new record as a patient
*/
public class AddDialog extends AppCompatDialogFragment implements View.OnClickListener{
    private AddDialogListener listener;
    private EditText addTitle, addDesc;
    private TextView currentDate, currentLocation;
    private Button addDate, addGeo;
    private Geolocation geolocation;
    private Date updateDate = new Date();
    private Address address;
    private DateFormat dateFormat = new SimpleDateFormat("dd MMMM YYYY hh:mma");

    // override default constructor
    public AddDialog() {
        this.geolocation = null;
    }

    // constructor with argument representing geolocation
    @SuppressLint("ValidFragment")
    public AddDialog(Geolocation geolocation) {
        this.geolocation = geolocation;
    }

    // called upon creation
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
                    // on save button click, call listener appyedit function
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String title  = addTitle.getText().toString();
                        String desc = addDesc.getText().toString();
                        listener.applyEdit(title,desc);
                    }
                });
        // set view id's
        addTitle = view.findViewById(R.id.add_title_record);
        addDesc = view.findViewById(R.id.add_comment_record);
        currentDate = view.findViewById(R.id.add_record_date);
        currentLocation = view.findViewById(R.id.add_record_geo);
        Calendar c = Calendar.getInstance();
        Date nowDate = c.getTime();
        // try and catch getting address from a marked location
        try {
            address = listener.getAddress();
        } catch (IOException e) {
            e.printStackTrace();
        }
        updateAddress(address);

        // update currentDate string with date format
        currentDate.setText(dateFormat.format(nowDate).replace("AM","am").replace("PM","pm"));

        // set buttons and their on clicks
        addDate = view.findViewById(R.id.addDateRecordBtn);
        addGeo = view.findViewById(R.id.addGeoRecordBtn);
        addGeo.setOnClickListener(this);
        addDate.setOnClickListener(this);

        return builder.create();
    }

    // method called upon creation, assign listener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (AddDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "exception failed");
        }
    }

    // on click method for button clicks
    @Override
    public void onClick(View v) {
        // on add date click, call listener to apply date update
        if(v.getId() == R.id.addDateRecordBtn){
            listener.applyDate();
        }
        // on add geolocation click, call listener to apply geo update
        else if (v.getId() == R.id.addGeoRecordBtn){
            listener.openMapDialog();

        }
    }



    // AddDialogListener is an interface which is implemented by the dialogs listener
    // applyEdit - applys all edits on the record
    // applyDate - applys the date update after add Date Btn click
    // getaddress - returns an address from a geolocation
    // openmapdialog - applys the geo update after add geo Btn click
    public interface AddDialogListener{
        void applyEdit(String title, String comment);
        void applyDate();
        Address getAddress() throws IOException;
        void openMapDialog();
    }

    // updateAddress sets the text to our current address
    public void updateAddress(Address address){
        currentLocation.setText(address.getAddressLine(0));
    }

    // changeTime is given a date and updates dialogs date and textview of date
    public void changeTime(Date date){
        this.updateDate = date;
        this.currentDate.setText(dateFormat.format(updateDate).replace("AM","am").replace("PM","pm"));
    }
}
