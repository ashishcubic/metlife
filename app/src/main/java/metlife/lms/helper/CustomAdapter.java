package metlife.lms.helper;

/**
 * Created by ashishcubic on 2/21/2017.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import metlife.lms.R;


public class CustomAdapter extends BaseAdapter {
    Context context;
    String status_id[];
    String[] status;
    LayoutInflater inflter;

    public CustomAdapter(Context applicationContext, String[] status_id, String[] status) {
        this.context = applicationContext;
        this.status_id = status_id;
        this.status = status;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return status_id.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.custom_spinner_items, null);
        TextView names = (TextView) view.findViewById(R.id.textView);
        names.setText(status[i]);
        return view;
    }
}