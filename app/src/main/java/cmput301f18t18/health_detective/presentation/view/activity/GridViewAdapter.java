package cmput301f18t18.health_detective.presentation.view.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import cmput301f18t18.health_detective.R;
import cmput301f18t18.health_detective.presentation.view.activity.listeners.ProblemOnClickListener;

public class GridViewAdapter extends BaseAdapter {

    private Context mContext;
    private int testIMAGES;
    private String images;
    private ProblemOnClickListener listener;

    public GridViewAdapter(Context context, int testImages){
        mContext = context;
        testIMAGES = testImages;
        this.images = images;
    }

    @Override
    public int getCount() {
        return testIMAGES;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View gridView;
            gridView = inflater.inflate(R.layout.body_image_view, null);
            ImageView imageView = (ImageView) gridView.findViewById(R.id.imageView);
            imageView.setImageResource(R.drawable.ic_launcher_background);
        return gridView;
    }
}
