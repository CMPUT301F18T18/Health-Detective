package cmput301f18t18.health_detective.presentation.view.activity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;
import java.util.Set;

import cmput301f18t18.health_detective.R;


/*
    Author: Tyler V
    Title: android spinner dropdown checkbox
    Source: https://stackoverflow.com/questions/38417984/android-spinner-dropdown-checkbox

    This was taken for a simple Adapter for the drop down menu on search. Will eventually be used as
    a guideline when changing it to fit with Health-Detective design
 */

public class BodyPartCheckBoxAdapter extends BaseAdapter {

    static class SpinnerItem<T> {
        private String txt;
        private T item;

        SpinnerItem(T t, String s) {
            item = t;
            txt = s;
        }
    }

    private Context context;
    private Set<String> selectedBodyParts;
    private List<SpinnerItem<String>> all_items;
    private String headerText;

    BodyPartCheckBoxAdapter(Context context,
                    String headerText,
                    List<SpinnerItem<String>> all_items,
                    Set<String> selectedBodyParts) {
        this.context = context;
        this.headerText = headerText;
        this.all_items = all_items;
        this.selectedBodyParts = selectedBodyParts;
    }

    @Override
    public int getCount() {
        return all_items.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        if( position < 1 ) {
            return null;
        }
        else {
            return all_items.get(position-1);
        }
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    @SuppressWarnings({"unchecked"})
    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        final ViewHolder holder;
        if (convertView == null ) {
            LayoutInflater layoutInflator = LayoutInflater.from(context);
            convertView = layoutInflator.inflate(R.layout.drop_down_checkbox, parent, false);

            holder = new ViewHolder();
            holder.mTextView = convertView.findViewById(R.id.text);
            holder.mCheckBox = convertView.findViewById(R.id.checkbox);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        if( position < 1 ) {
            holder.mCheckBox.setVisibility(View.GONE);
            holder.mTextView.setText(headerText);
        }
        else {
            final int listPos = position - 1;
            holder.mCheckBox.setVisibility(View.VISIBLE);
            holder.mTextView.setText(all_items.get(listPos).txt);

            final String item = all_items.get(listPos).item;
            boolean isSel = selectedBodyParts.contains(item);

            holder.mCheckBox.setOnCheckedChangeListener(null);
            holder.mCheckBox.setChecked(isSel);

            holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if( isChecked ) {
                        selectedBodyParts.add(item);
                    }
                    else {
                        selectedBodyParts.remove(item);
                    }
                }
            });

            holder.mTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.mCheckBox.toggle();
                }
            });
        }

        return convertView;
    }

    private class ViewHolder {
        private TextView mTextView;
        private CheckBox mCheckBox;
    }

}
