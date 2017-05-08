package metlife.lms.reminder;

import android.app.Activity;
import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;

import metlife.lms.R;
import metlife.lms.helper.AlarmDBHelper;

public class WatchList extends Activity {
    //ListView watchlistview;
    private AlarmDBHelper adb = new AlarmDBHelper(this);
    private ArrayList<HashMap<String, String>> alarms = null;
    private ListAdapter adapter = null;
    ListView myList=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_list);
        // watchlistview=(ListView) findViewById(R.id.list);
        populateList();

    }

    public void populateList() {
        try {
            alarms = adb.getAllAlarms(getApplicationContext());

            adapter = new SimpleAdapter(WatchList.this, alarms, R.layout.watclist_item, new String[]{
                    "name", "btalarm_time"}, new int[]{R.id.name, R.id.btalarm_time}){
                
            	@Override
                public View getView (int position, View convertView,ViewGroup parent)
                {
                    View v = super.getView(position, convertView, parent);

                    return v;
                }


            };

            myList = (ListView) findViewById(android.R.id.list);
         //  itemClickListener(myList);
            myList.setAdapter(adapter);
            registerForContextMenu(myList);



        } catch (CursorIndexOutOfBoundsException e) {
            Toast toast = Toast.makeText(this, "You have not set any reminder", Toast.LENGTH_LONG);
            toast.show();
        }


    }
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.actions , menu);
    }

    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch(item.getItemId()){
            case R.id.cnt_mnu_edit:
                //Toast.makeText(this, "Edit : " + alarms.get(info.position), Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(WatchList.this, editNotification.class);
                //intent.putExtra()
                //  int h=Integer.parseInt(alarms.get(position).get("topic_id"));
                intent1.putExtra("topic_id",Integer.parseInt(alarms.get(info.position).get("topic_id")));

                intent1.putExtra("topic_name",alarms.get(info.position).get("name"));
                intent1.putExtra("request_code",Integer.parseInt(alarms.get(info.position).get("request_code")));


                startActivity(intent1);

                break;
            case R.id.cnt_mnu_delete:
               // Toast.makeText(this, "Delete : " + alarms.get(info.position)  , Toast.LENGTH_SHORT).show();
                editNotification e=new editNotification();
                e.CancelAlarm(this, Integer.parseInt(alarms.get(info.position).get("request_code")));
                Intent refresh1 = new Intent(WatchList.this, WatchList.class);
                startActivity(refresh1);
                finish();
                break;
        }
        return true;
    }



}
