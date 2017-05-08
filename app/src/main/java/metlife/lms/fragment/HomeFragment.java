package metlife.lms.fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.view.ViewGroup.LayoutParams;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import metlife.lms.helper.CardAdapter;
import metlife.lms.R;
import metlife.lms.app.AppConfig;
import metlife.lms.app.AppController;
import metlife.lms.helper.SQLiteHandler;
import metlife.lms.helper.BeanclassList;
import metlife.lms.helper.CustomAdapter;
import metlife.lms.activity.DetailActivity;



public class HomeFragment extends Fragment {

    public String[] id;
    public String[] name;
    public String[] phone;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private ArrayList<BeanclassList> Bean;
    private ProgressDialog pDialog;
    private static final String TAG = DetailActivity.class.getSimpleName();
    private SQLiteHandler db;
    int noPage=0;
    String u_id;
    LinearLayout mainLayout,bottomLayout;
    private LinearLayout previousLayout;
    private LinearLayout nextLayout;
    private LinearLayout dateLayout,statusLayout,mobileLayout,nameLayout,filterLayout,paranetLayout,agentLayout;;
    String from=" ";
    String to=" ";
    String total_pages;
    String feed_a_type[];
    String feed_a_id[];

    String feed_agent_type[];
    String feed_agent_id[];

    View vw,agent_view;

    ImageButton imageButton1,imageButton2;
    EditText editText1,editText2;

    private Calendar cal;
    private int day;
    private int month;
    private int year;
    String status_agent="";
    String status_agent_id="";
    String callback_date="",callback_time="",conversion_date="",conversion_time="",followup_date="",followup_time="",apptset_date="",apptset_time="",meeting_date="",meeting_time="";

    String mobile_no="";
    String nameF="";
    TextView emptyTextView;
    String role;
    String lead_type="1";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vw= inflater.inflate(R.layout.fragment_home, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        FragmentTransaction mFragmentTransaction = getFragmentManager().beginTransaction();
        mFragmentTransaction.addToBackStack(null);

        mainLayout=(LinearLayout)vw.findViewById(R.id.mainLayoout);
        bottomLayout=(LinearLayout)vw.findViewById(R.id.bottomLayoout);
        agentLayout=(LinearLayout)vw.findViewById(R.id.filter_agent);

        agent_view=(View)vw.findViewById(R.id.view_agent) ;

        recyclerView = (RecyclerView)vw.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        previousLayout = (LinearLayout)vw.findViewById(R.id.previous_layout);
        nextLayout = (LinearLayout)vw.findViewById(R.id.next_layout);

        dateLayout = (LinearLayout)vw.findViewById(R.id.date_filter);
        statusLayout = (LinearLayout)vw.findViewById(R.id.lead_filter);
        mobileLayout = (LinearLayout)vw.findViewById(R.id.filter_mobile);
        nameLayout = (LinearLayout)vw.findViewById(R.id.filter_name);
        filterLayout=(LinearLayout)vw.findViewById(R.id.filter);
        emptyTextView=(TextView) vw.findViewById(R.id.empty);

        return  vw;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);


        db = new SQLiteHandler(getContext());

        HashMap<String,String> details=db.getUserDetails();
        u_id=details.get("uid");
        String name = details.get("name");
        role = details.get("email");



        cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);

        fetchData(u_id,String.valueOf(noPage),"","","","","","");

        previousLayout.setVisibility(View.INVISIBLE);

        nextLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noPage++;
                previousLayout.setVisibility(View.VISIBLE);
                if((from != null && !from.isEmpty()) && (to != null && !to.isEmpty())) {  //do your stuffs here
                    fetchData(u_id,String.valueOf(noPage),from,to,"","","","");
                }
                else if(status_agent != null && !status_agent.isEmpty())
                {
                    fetchData(u_id,String.valueOf(noPage),"","",status_agent,"","","");
                }
                else
                {
                    fetchData(u_id,String.valueOf(noPage),"","","","","","");
                }

                if(noPage>=Integer.parseInt(total_pages)-1)
                {
                    nextLayout.setEnabled(false);
                    previousLayout.setEnabled(true);
                }
                else
                {
                    nextLayout.setEnabled(true);
                    previousLayout.setEnabled(true);
                }
            }
        });

        previousLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                noPage--;
                if((from != null && !from.isEmpty()) && (to != null && !to.isEmpty())) {  //do your stuffs here
                    fetchData(u_id,String.valueOf(noPage),from,to,"","","","");
                }
                else if(status_agent != null && !status_agent.isEmpty())
                {
                    fetchData(u_id,String.valueOf(noPage),"","",status_agent,"","","");
                }
                else if(mobile_no != null && !mobile_no.isEmpty())
                {
                    fetchData(u_id,String.valueOf(noPage),"","","",mobile_no,"","");
                }
                else if(nameF != null && !nameF.isEmpty())
                {
                    fetchData(u_id,String.valueOf(noPage),"","","","",nameF,"");
                }
                else
                {
                    fetchData(u_id,String.valueOf(noPage),"","","","","","");
                }

                if(noPage<1)
                {
                    previousLayout.setEnabled(false);
                    nextLayout.setEnabled(true);
                }
                else
                {
                    previousLayout.setEnabled(true);
                    nextLayout.setEnabled(true);
                }
            }
        });

        dateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getActivity());
                View mView = layoutInflaterAndroid.inflate(R.layout.date_input_dialog_box, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(getActivity());
                alertDialogBuilderUserInput.setView(mView);
                imageButton1=(ImageButton)mView.findViewById(R.id.imageButton1);
                imageButton2=(ImageButton)mView.findViewById(R.id.imageButton2);

                editText1=(EditText)mView.findViewById(R.id.editText1);
                editText2=(EditText)mView.findViewById(R.id.editText2);

                imageButton1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        DatePickerDialog datePicker = new DatePickerDialog(getActivity(),datePickerListener1, year, month, day);
                        datePicker.setCancelable(false);
                        datePicker.setTitle("Select the date");
                        datePicker.show();
                    }
                });
                imageButton2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        DatePickerDialog datePicker = new DatePickerDialog(getActivity(),datePickerListener2, year, month, day);
                        datePicker.setCancelable(false);
                        datePicker.setTitle("Select the date");
                        datePicker.show();
                    }
                });

                alertDialogBuilderUserInput
                        .setCancelable(false)
                        .setPositiveButton("Filter", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                // ToDo get user input here
                                noPage=0;
                                fetchData(u_id,String.valueOf(noPage),from,to,"","","","");
                            }
                        })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        dialog.cancel();
                                    }
                                });

                AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                alertDialogAndroid.show();

            }

        });
        statusLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getActivity());
                View mView = layoutInflaterAndroid.inflate(R.layout.status_input_dialog_box, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(getActivity());
                alertDialogBuilderUserInput.setView(mView);


                final Spinner spinner = (Spinner) mView.findViewById(R.id.spinner);
                CustomAdapter customAdapter=new CustomAdapter(getContext(),feed_a_id,feed_a_type);
                spinner.setAdapter(customAdapter);
                alertDialogBuilderUserInput
                        .setCancelable(false)
                        .setPositiveButton("Filter", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                // ToDo get user input here
                                status_agent=feed_a_id[spinner.getSelectedItemPosition()];
                                noPage=0;
                                fetchData(u_id,String.valueOf(noPage),"","",status_agent,"","","");
                            }
                        })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        dialog.cancel();
                                    }
                                });

                AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                alertDialogAndroid.show();

            }
        });

        mobileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getActivity());
                View mView = layoutInflaterAndroid.inflate(R.layout.mobile_input_dialog_box, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(getActivity());
                alertDialogBuilderUserInput.setView(mView);

                final EditText userInputDialogEditText = (EditText) mView.findViewById(R.id.userInputDialog);
                alertDialogBuilderUserInput
                        .setCancelable(false)
                        .setPositiveButton("Filter", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                // ToDo get user input here
                                noPage=0;
                                mobile_no=userInputDialogEditText.getText().toString();
                                fetchData(u_id,String.valueOf(noPage),"","","",mobile_no,"","");

                            }
                        })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        dialog.cancel();
                                    }
                                });

                AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                alertDialogAndroid.show();
            }

        });
        nameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getActivity());
                View mView = layoutInflaterAndroid.inflate(R.layout.name_input_dialog_box, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(getActivity());
                alertDialogBuilderUserInput.setView(mView);

                final EditText userInputDialogEditText = (EditText) mView.findViewById(R.id.userInputDialog);
                alertDialogBuilderUserInput
                        .setCancelable(false)
                        .setPositiveButton("Filter", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                // ToDo get user input here

                                noPage=0;
                                nameF=userInputDialogEditText.getText().toString();
                                fetchData(u_id,String.valueOf(noPage),"","","","",nameF,"");
                            }
                        })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        dialog.cancel();
                                    }
                                });

                AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                alertDialogAndroid.show();
            }
        });

        agentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getActivity());
                View mView = layoutInflaterAndroid.inflate(R.layout.agent_filter_input_box, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(getActivity());
                alertDialogBuilderUserInput.setView(mView);


                final Spinner spinner = (Spinner) mView.findViewById(R.id.spinner);
                CustomAdapter customAdapter=new CustomAdapter(getContext(),feed_agent_id,feed_agent_type);
                spinner.setAdapter(customAdapter);
                alertDialogBuilderUserInput
                        .setCancelable(false)
                        .setPositiveButton("Filter", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                // ToDo get user input here
                                status_agent_id=feed_agent_id[spinner.getSelectedItemPosition()];
                                noPage=0;
                                fetchData(u_id,String.valueOf(noPage),"","","","","",status_agent_id);
                            }
                        })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        dialog.cancel();
                                    }
                                });

                AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                alertDialogAndroid.show();

            }
        });

    }
    private void fetchData(final String uid,final String page,final String from,final String to,final String status,final String mobile,final String nameFilter,final String agentFilter) {
        // Tag used to cancel the request
        String tag_string_req = "req_data";

        pDialog.setMessage("Loading ...");
        showDialog();
        String url;
        if((from != null && !from.isEmpty()) && (to != null && !to.isEmpty())) {  //do your stuffs here

            url= AppConfig.URL_FETCH_FILTER + uid + "/" + page + "?from=" +from + "&to=" +to;

        }
        else if (status != null && !status.isEmpty())
        {
            url= AppConfig.URL_FETCH_FILTER + uid + "/" + page + "?feedback="+status;
        }
        else if (mobile != null && !mobile.isEmpty())
        {
            url= AppConfig.URL_FETCH_FILTER + uid + "/" + page + "?mobile="+mobile;
        }
        else if (nameFilter != null && !nameFilter.isEmpty())
        {
            url= AppConfig.URL_FETCH_FILTER + uid + "/" + page + "?name="+nameFilter.replaceAll(" ","%20");
        }
        else if (agentFilter != null && !agentFilter.isEmpty())
        {
            url= AppConfig.URL_FETCH_FILTER + uid + "/" + page + "?field_agent_id="+agentFilter;
        }
        else {
            url = AppConfig.URL_FETCH_DATA + uid + "/" + page;
        }


        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Data Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    String error = jObj.getString("status");
                    // Check for error node in json
                    if (error.equals("OK")) {
                        mainLayout.setVisibility(View.VISIBLE);
                        bottomLayout.setVisibility(View.VISIBLE);
                        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
                        total_pages=jObj.getString("no_of_pages");

                        if((Integer.parseInt(total_pages)-1)==0)
                        {
                            bottomLayout.setVisibility(View.GONE);
                        }

                        if(role.equals("2")) {

                            JSONArray fb_agent =jObj.getJSONArray("all_fieldagent");
                            feed_agent_type=new String[fb_agent.length()];
                            feed_agent_id=new String[fb_agent.length()];
                            for (int i = 0; i < fb_agent.length(); i++) {
                                JSONObject feedObj_agent = (JSONObject) fb_agent.get(i);
                                String feed_a_id = feedObj_agent.getString("id");
                                String feed_a_type = feedObj_agent.getString("username");
                                feed_agent_type[i]=feed_a_type;
                                feed_agent_id[i]=feed_a_id;
                            }

                            agentLayout.setVisibility(View.VISIBLE);
                            agent_view.setVisibility(View.VISIBLE);
                        }

                        JSONArray feedback = jObj.getJSONArray("feedback");
                        feed_a_type=new String[feedback.length()];
                        feed_a_id=new String[feedback.length()];
                        for (int i = 0; i < feedback.length(); i++) {
                            JSONObject feedObj = (JSONObject) feedback.get(i);
                            String feed_id = feedObj.getString("id");
                            String feed_type = feedObj.getString("type");
                            feed_a_type[i]=feed_type;
                            feed_a_id[i]=feed_id;


                        }

                        JSONArray data = jObj.getJSONArray("data");
                        if(data != null && data.length() > 0 ) {
                            id = new String[data.length()];
                            name = new String[data.length()];
                            phone = new String[data.length()];
                            Bean = new ArrayList<BeanclassList>();
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject feedObj = (JSONObject) data.get(i);
                                String id = feedObj.getString("ID");
                                String name = feedObj.getString("Name");
                                String mobile = feedObj.getString("ContactNo");
                                String dob = feedObj.getString("DOB");
                                String plan = feedObj.getString("insurance_product");
                                String city = feedObj.getString("City");
                                String income = feedObj.getString("Annual_Income");
                                String call_attempts = feedObj.getString("call_attempts");
                                String feedback_status = feedObj.getString("Feedback");
                                String feedback_ap_status = feedObj.getString("feedback_status");
                                String follow_up_date = feedObj.getString("FollowUp_Date");
                                String appoint_date = feedObj.getString("appointments_date");
                                String appoint_time = feedObj.getString("appointment_time");

                               /* String followup_date = feedObj.getString("followup_date");
                                String followup_time = feedObj.getString("followup_time");

                                String callback_date = feedObj.getString("callback_date");
                                String callback_time = feedObj.getString("callback_time");

                                String pmeeting_date = feedObj.getString("pmeeting_date");
                                String pmeeting_time = feedObj.getString("pmeeting_time");

                                String conversion_date = feedObj.getString("conversion_date");
                                String conversion_time = feedObj.getString("conversion_time");

                                String aptset_date = feedObj.getString("aptset_date");
                                String aptset_time = feedObj.getString("aptset_time");*/

                                String adType = feedObj.getString("appt_type");
                                String off_adress = feedObj.getString("off_address");
                                String res_adress = feedObj.getString("res_address");
                                String adress;
                                if(adType.equals("0"))
                                {
                                    adress=off_adress;
                                }
                                else
                                {
                                    adress=res_adress;
                                }
                                String post_date = feedObj.getString("PostDate");
                                BeanclassList BeanclassList = new BeanclassList(id, name, mobile, dob, plan, city,adType,adress,income, call_attempts, feedback_status, feedback_ap_status, follow_up_date,appoint_date,appoint_time,post_date,callback_date,callback_time,followup_date,followup_time,conversion_date,conversion_time,apptset_date,apptset_time,meeting_date,meeting_time,lead_type);
                                Bean.add(BeanclassList);

                            }
                            adapter = new CardAdapter(Bean, getActivity()) {
                            };
                            recyclerView.setVisibility(View.VISIBLE);
                            recyclerView.setAdapter(adapter);
                            emptyTextView.setVisibility(View.GONE);
                        }
                        else
                        {

                            recyclerView.setVisibility(View.GONE);
                            mainLayout.setVisibility(View.VISIBLE);
                            bottomLayout.setVisibility(View.GONE);
                            filterLayout.setVisibility(View.VISIBLE);
                            emptyTextView.setVisibility(View.VISIBLE);
                        }

                        // Launch main activity
                    } else {
                        // Error in login. Get the error message
                        String error_message = jObj.getString("error");

                        recyclerView.setVisibility(View.GONE);

                        bottomLayout.setVisibility(View.GONE);
                        filterLayout.setVisibility(View.VISIBLE);
                        mainLayout.setVisibility(View.VISIBLE);
                        emptyTextView.setVisibility(View.VISIBLE);
                        emptyTextView.setText(""+error_message);

                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                   // Toast.makeText(getActivity(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();

                    recyclerView.setVisibility(View.GONE);

                    bottomLayout.setVisibility(View.GONE);
                    filterLayout.setVisibility(View.GONE);

                    mainLayout.setVisibility(View.VISIBLE);
                    emptyTextView.setVisibility(View.VISIBLE);
                    emptyTextView.setText(""+ e.getMessage());

                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Data Error: " + error.getMessage());
                Toast.makeText(getActivity(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
    private DatePickerDialog.OnDateSetListener datePickerListener1 = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            editText1.setText(selectedDay + "-" + (selectedMonth + 1) + "-"
                    + selectedYear);
            from=getFormatedDate(editText1.getText().toString(), "dd-mm-yyyy", "yyyy-mm-dd");

        }
    };

    private DatePickerDialog.OnDateSetListener datePickerListener2 = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            editText2.setText(selectedDay + "-" + (selectedMonth + 1) + "-"
                    + selectedYear);
            to=getFormatedDate(editText2.getText().toString(), "dd-mm-yyyy", "yyyy-mm-dd");

        }
    };

    public static String getFormatedDate(String strDate, String sourceFormate,
                                         String destinyFormate) {
        SimpleDateFormat df;
        df = new SimpleDateFormat(sourceFormate);
        Date date = null;
        try {
            date = df.parse(strDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        df = new SimpleDateFormat(destinyFormate);
        return df.format(date);

    }



    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


    public void onBackPressed() {
        if (emptyTextView.getVisibility()==View.VISIBLE) {
            emptyTextView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            mainLayout.setVisibility(View.VISIBLE);
            bottomLayout.setVisibility(View.VISIBLE);
            filterLayout.setVisibility(View.VISIBLE);
        }
    }
}
