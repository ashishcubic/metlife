package metlife.lms.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import customfonts.MyEditText;
import customfonts.MyTextView;
import metlife.lms.helper.AlarmDBHelper;
import metlife.lms.helper.BeanclassList;
import metlife.lms.helper.CustomAdapter;
import metlife.lms.R;
import metlife.lms.app.AppConfig;
import metlife.lms.app.AppController;
import metlife.lms.helper.SQLiteHandler;
import metlife.lms.reminder.SetNotification;


public class DetailActivity extends AppCompatActivity {


    private static final String TAG = DetailActivity.class.getSimpleName();
    String[] arrayAdType, arrayAdTypeId;
    TextView txtName, txtEmail, txtDob, txtMobile, txtIncome, txtPlan, txtCity;
    LinearLayout linearLayoutSpinner2, linearLayoutDt, linearLayoutTm, linearLayoutAdress, linearLayoutSpineer3, dt_layout_call, tm_layout_call, sp_layout_agent;
    View viewSpinner2, viewDt, viewTm, spinner3View, view_dt_call, view_tm_call, view_reminder_appoint, sp_view_agent;
    private ProgressDialog pDialog;
    private SQLiteHandler db;
    String u_id;
    FrameLayout mainLayout;
    Spinner spinner, spinner2, spinner3, spineer_agent;

    String feed_agent_name[];
    String feed_agent_id[];

    String feed_a_type[];
    String feed_a_id[];

    String status_feed_a_type[];
    String status_feed_a_id[];

    String end_feed_a_type[];
    String end_feed_a_id[];

    int associated_topicId = -1;
    String associated_topic_name = null;
    String associated_topic_content = null;

    private Calendar cal;
    private int day;
    private int month;
    private int year;
    int hour;
    int minute;
    MyTextView date, time, save, call_txt_date, call_txt_time;
    String appointment_date, appointment_time;
    MyEditText editTextCompany, editTextBuildNo, editTextFloor, editTextOfficeNo, editTextAreaName, editTextCityName, editTextEastWest, editTextDept, editTextLandMark1, editTextLandMark2, editTextPincode, editTextComment;

    String call_date = "", follow_date = "", call_time = "", follow_time = "", company_string = "", off_bldg_name_string = "", off_floor_string = "", officeNo_string = "",
            off_area_string = "", off_city_string = "", off_east_west_string = "", dept_string = "", landmark1_string = "", landmark2_string = "", off_pincode_string = "",
            rs_bldg_name_string = "", rs_floor_name_string = "", flat_string = "", rs_area_string = "", road_string = "", rs_city_string = "", rs_east_west_string = "", rs_ms_string = "",
            rs_landmark1_string = "", rs_landmark2_string = "", rs_pincode_string = "", aapt_status = "", feed_status = "", feed_status_id = "";
    String navigation;
    String name;
    String role;
    String field_agent_update;
    private LinearLayout linearDateandTimelayout;
    private LinearLayout mlinear_slide_up;
    private Button mSlide_upbtn;
    private LinearLayout mSlidelayout;
    private LinearLayout mSlide_l;
    private int pos3;
    private String lead_id;
    private LinearLayout linearLayoutSpineer4;
    private Spinner spinner4;
    private String end_feed_status;
    private String end_feed_status_id;
    private int pos4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);
        Context context;
        getSupportActionBar().setTitle("Customer Personal Details");
        getSupportActionBar().setElevation(0);
        arrayAdType = getResources().getStringArray(R.array.array_ad_type);
        arrayAdTypeId = getResources().getStringArray(R.array.array_ad_type_id);
        mainLayout = (FrameLayout) findViewById(R.id.mainLayoout);
        linearLayoutSpinner2 = (LinearLayout) findViewById(R.id.spinner2_layout2);
        linearLayoutSpineer3 = (LinearLayout) findViewById(R.id.spinner3_layout);
        linearLayoutSpineer4 = (LinearLayout) findViewById(R.id.spinner4_layout);

        linearLayoutDt = (LinearLayout) findViewById(R.id.dt_layout);
        linearLayoutTm = (LinearLayout) findViewById(R.id.tm_layout);
        linearLayoutAdress = (LinearLayout) findViewById(R.id.adress_layout);
        sp_layout_agent = (LinearLayout) findViewById(R.id.spineer_agent_layout);
        dt_layout_call = (LinearLayout) findViewById(R.id.dt_layout_call);
        tm_layout_call = (LinearLayout) findViewById(R.id.tm_layout_call);
        txtName = (TextView) findViewById(R.id.name);
        txtEmail = (TextView) findViewById(R.id.email);
        txtDob = (TextView) findViewById(R.id.dob);
        txtMobile = (TextView) findViewById(R.id.mobile);
        txtIncome = (TextView) findViewById(R.id.income);
        txtPlan = (TextView) findViewById(R.id.plan);
        txtCity = (TextView) findViewById(R.id.city);
        date = (MyTextView) findViewById(R.id.date);
        time = (MyTextView) findViewById(R.id.time);
        call_txt_date = (MyTextView) findViewById(R.id.date_call);
        call_txt_time = (MyTextView) findViewById(R.id.time_call);



        save = (MyTextView) findViewById(R.id.save);


        editTextCompany = (MyEditText) findViewById(R.id.company);
        editTextBuildNo = (MyEditText) findViewById(R.id.build_no);
        editTextFloor = (MyEditText) findViewById(R.id.floor);
        editTextOfficeNo = (MyEditText) findViewById(R.id.office_no);
        editTextAreaName = (MyEditText) findViewById(R.id.area_name);
        editTextCityName = (MyEditText) findViewById(R.id.city_name);
        editTextEastWest = (MyEditText) findViewById(R.id.east_west);
        editTextDept = (MyEditText) findViewById(R.id.dept);
        editTextLandMark1 = (MyEditText) findViewById(R.id.landmark_1);
        editTextLandMark2 = (MyEditText) findViewById(R.id.landmark_2);
        editTextPincode = (MyEditText) findViewById(R.id.pin_code);
        editTextComment = (MyEditText) findViewById(R.id.comment);

        spinner = (Spinner) findViewById(R.id.spinner);
        spinner2 = (Spinner) findViewById(R.id.spinner2);

        spinner3 = (Spinner) findViewById(R.id.spinner3);
        spinner4 = (Spinner) findViewById(R.id.spinner4);
        spineer_agent = (Spinner) findViewById(R.id.spinner_agent);

        final Intent intent = getIntent();
         lead_id = intent.getStringExtra("lead_id");

        db = new SQLiteHandler(getApplicationContext());
        HashMap<String, String> details = db.getUserDetails();
        u_id = details.get("uid");
        role = details.get("email");

        cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);
        hour = cal.get(Calendar.HOUR_OF_DAY);
        minute = cal.get(Calendar.MINUTE);

        /*date.setText(""+day+"-"+(month+1)+"-"+year);
        time.setText(""+hour+":"+minute);*/

        date.setText("DD-MM-YYYY");
        time.setText("HH:MM:SS");

        call_txt_date.setText("DD-MM-YYYY");
        call_txt_time.setText("HH:MM:SS");

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        fetchData(lead_id);

        Intent intent1=getIntent();
        if (intent1.getStringExtra("lead_type").equals("1")) {
            linearLayoutSpineer4.setVisibility(View.VISIBLE);
        }
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                int pos = spinner.getSelectedItemPosition();
                String feedback = feed_a_id[pos];


                int pos2 = 0;
                try {
                    pos2 = spinner2.getSelectedItemPosition();
                    aapt_status = arrayAdTypeId[pos2];
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    pos3 = spinner3.getSelectedItemPosition();
                    feed_status = status_feed_a_type[pos3];
                    feed_status_id = status_feed_a_id[pos3];
                } catch (Exception e) {
                    e.printStackTrace();
                }try {
                    Intent intent1=getIntent();
                    if (intent1.getStringExtra("lead_type").equals("1")) {
                        pos4 = spinner4.getSelectedItemPosition();
                        end_feed_status = end_feed_a_type[pos4];
                    }else {
                        end_feed_status ="";

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                String comment = editTextComment.getText().toString();

                appointment_date = date.getText().toString();
                appointment_time = time.getText().toString();

                try {
                    follow_date = getFormatedDate(call_date, "dd-mm-yyyy", "yyyy-mm-dd");
                } catch (Exception e) {
                    e.printStackTrace();
                }


                if (feed_a_type[pos].equals("Appointment")) {
                    appointment_date = getFormatedDate(appointment_date, "dd-mm-yyyy", "yyyy-mm-dd");
                    if (arrayAdTypeId[pos2].equals("0")) {
                        company_string = editTextCompany.getText().toString();
                        off_bldg_name_string = editTextBuildNo.getText().toString();
                        off_floor_string = editTextFloor.getText().toString();
                        officeNo_string = editTextOfficeNo.getText().toString();
                        off_area_string = editTextAreaName.getText().toString();
                        off_city_string = editTextCityName.getText().toString();
                        off_east_west_string = editTextEastWest.getText().toString();
                        dept_string = editTextDept.getText().toString();
                        landmark1_string = editTextLandMark1.getText().toString();
                        landmark2_string = editTextLandMark2.getText().toString();
                        off_pincode_string = editTextPincode.getText().toString();

                    }
                    if (arrayAdTypeId[pos2].equals("1")) {
                        rs_bldg_name_string = editTextCompany.getText().toString();
                        rs_floor_name_string = editTextBuildNo.getText().toString();
                        flat_string = editTextFloor.getText().toString();
                        rs_area_string = editTextOfficeNo.getText().toString();
                        road_string = editTextAreaName.getText().toString();
                        rs_city_string = editTextCityName.getText().toString();
                        rs_east_west_string = editTextEastWest.getText().toString();
                        rs_ms_string = editTextDept.getText().toString();
                        rs_landmark1_string = editTextLandMark1.getText().toString();
                        rs_landmark2_string = editTextLandMark2.getText().toString();
                        rs_pincode_string = editTextPincode.getText().toString();

                    }


                    if (status_feed_a_id[pos3].equals("3")||status_feed_a_id[pos3].equals("16") ||status_feed_a_id[pos3].equals("17") ||status_feed_a_id[pos3].equals("20") ||status_feed_a_id[pos3].equals("21")) {
                        follow_date = call_txt_date.getText().toString();

                        follow_date = getFormatedDate(follow_date, "dd-mm-yyyy", "yyyy-mm-dd");

                        follow_time = call_txt_time.getText().toString();


                    }


                } else {
                    appointment_date = "";
                }
                if (feed_a_type[pos].equals("CallBack")) {
                    follow_date = date.getText().toString();
                    follow_date = getFormatedDate(follow_date, "dd-mm-yyyy", "yyyy-mm-dd");
                    follow_time = time.getText().toString();
                }

                postData(u_id, lead_id, feedback, comment, appointment_date, appointment_time,
                        company_string, off_bldg_name_string, off_floor_string, officeNo_string,
                        off_area_string, off_city_string, off_east_west_string, dept_string, landmark1_string, landmark2_string, off_pincode_string,
                        rs_bldg_name_string, rs_floor_name_string, flat_string, rs_area_string, road_string, rs_city_string, rs_east_west_string, rs_ms_string,
                        rs_landmark1_string, rs_landmark2_string, rs_pincode_string, call_date, call_time, follow_date, follow_time, aapt_status, feed_status, feed_status_id, field_agent_update,end_feed_status
                );
            }
        });

        /*reminder_appoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int pos = spinner.getSelectedItemPosition();
                String feedback = feed_a_id[pos];

                AlarmDBHelper adb2 = new AlarmDBHelper(DetailActivity.this);
                associated_topicId = Integer.parseInt(lead_id);
                String alarm_date = date.getText().toString();
                String alarm_time = time.getText().toString();
                associated_topic_name = name;
                associated_topic_content = alarm_date + " " + alarm_time;
                Log.i("Edit Button Clicked", "**********");
                if (!adb2.selectAlarmCheck(associated_topicId)) {
                    Intent intent = new Intent(DetailActivity.this, SetNotification.class);

                    try {

                    } catch (NullPointerException e) {
                        Toast t = Toast.makeText(DetailActivity.this, "null exception", Toast.LENGTH_LONG);
                        t.show();
                    }

                    Toast t = Toast.makeText(DetailActivity.this, "tid=" + feedback, Toast.LENGTH_LONG);
                    t.show();
                    intent.putExtra("topic_id", associated_topicId);
                    intent.putExtra("topic_name", capitalizeString(associated_topic_name));
                    intent.putExtra("date", alarm_date);
                    intent.putExtra("time", alarm_time);
                    //intent.putExtra("count",count);
                    startActivity(intent);

                } else {
                    Toast.makeText(DetailActivity.this, "Alarm is already set for" + " " + associated_topic_name + " " + "for edit or delete go to watchlist", Toast.LENGTH_SHORT).show();
                }

            }
        });
*/
    }

    private void fetchData(final String lead_id) {
        // Tag used to cancel the request
        String tag_string_req = "req_data";

        pDialog.setMessage("Loading ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_FETCH_DETAIL + lead_id + "/" + u_id, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Data Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    String error = jObj.getString("status");
                    //String data_as=jObj.getString("data");

                    // Toast.makeText(DetailActivity.this,data_as,Toast.LENGTH_LONG).show();

                    // Check for error node in json
                    if (error.equals("OK")) {

                        mainLayout.setVisibility(View.VISIBLE);
                        JSONArray f_agent = jObj.getJSONArray("all_fieldagent");
                        feed_agent_name = new String[f_agent.length()];
                        feed_agent_id = new String[f_agent.length()];
                        for (int n = 0; n < f_agent.length(); n++) {
                            JSONObject feedObj_agent = (JSONObject) f_agent.get(n);
                            String f_agent_id = feedObj_agent.getString("id");
                            String f_agent_name = feedObj_agent.getString("username");
                            feed_agent_name[n] = f_agent_name;
                            feed_agent_id[n] = f_agent_id;


                        }

                        JSONArray feedback = jObj.getJSONArray("feedback");
                        feed_a_type = new String[feedback.length()];
                        feed_a_id = new String[feedback.length()];
                        for (int i = 0; i < feedback.length(); i++) {
                            JSONObject feedObj = (JSONObject) feedback.get(i);
                            String feed_id = feedObj.getString("id");
                            String feed_type = feedObj.getString("type");
                            feed_a_type[i] = feed_type;
                            feed_a_id[i] = feed_id;


                        }

                        JSONArray feedback_status_arr = jObj.getJSONArray("all_feedback_status");
                        status_feed_a_type = new String[feedback_status_arr.length()];
                        status_feed_a_id = new String[feedback_status_arr.length()];
                        for (int j = 0; j < feedback_status_arr.length(); j++) {
                            JSONObject feedObj_ar = (JSONObject) feedback_status_arr.get(j);
                            String status_feed_id = feedObj_ar.getString("id");
                            String status_feed_type = feedObj_ar.getString("type");
                            status_feed_a_type[j] = status_feed_type;
                            status_feed_a_id[j] = status_feed_id;

                        }

                        JSONArray end_feedback_arr = jObj.getJSONArray("end_feedback");
                        end_feed_a_type = new String[end_feedback_arr.length()];
                        end_feed_a_id = new String[end_feedback_arr.length()];
                        for (int j = 0; j < end_feedback_arr.length(); j++) {
                            JSONObject feedObj_ar = (JSONObject) end_feedback_arr.get(j);
                            String end_feed_type = feedObj_ar.getString("type");
                            String end_feed_id = feedObj_ar.getString("id");
                            end_feed_a_type[j] = end_feed_type;
                            end_feed_a_id[j]= end_feed_id;
                        }

                        JSONObject data = jObj.getJSONObject("data");

                        String id = data.getString("ID");
                        name = data.getString("Name");
                        String email = data.getString("Email_ID");
                        String dob = data.getString("DOB");
                        String mobile = data.getString("ContactNo");
                        String income = data.getString("Annual_Income");
                        String plan = data.getString("insurance_product");
                        String city = data.getString("City");
                        String field_agent = data.getString("field_agent_id");
                        String feedback_status = data.getString("feedback_status");
                        String feedback_status_id = data.getString("feedback_status_id");
                        String status_id = data.getString("feedback_id");
                        String aapt_type = data.getString("appt_type");
                        String company = data.getString("company");
                        String office_build_no = data.getString("off_bldg_name");
                        String office_floor = data.getString("off_floor");
                        String office_no = data.getString("officeNo");
                        String office_area = data.getString("off_area");
                        String office_city = data.getString("off_city");
                        String off_east_west = data.getString("off_east_west");
                        String off_dept = data.getString("dept");
                        String landmark1 = data.getString("landmark1");
                        String landmark2 = data.getString("landmark2");
                        String off_pincode = data.getString("off_pincode");

                        String rs_bldg_name = data.getString("rs_bldg_name");
                        String rs_floor = data.getString("rs_floor");
                        String flat = data.getString("flat");
                        String rs_area = data.getString("rs_area");
                        String road = data.getString("road");
                        String rs_city = data.getString("rs_city");
                        String rs_east_west = data.getString("rs_east_west");
                        String rs_ms = data.getString("rs_ms");
                        String rs_landmark1 = data.getString("rs_landmark1");
                        String rs_landmark2 = data.getString("rs_landmark2");
                        String rs_pincode = data.getString("rs_pincode");

                        String lead_status = data.getString("lead_status");


                        String dtm = data.getString("appointments_date");
                        String tm = data.getString("appointment_time");
                        String comment = data.getString("comment");

                        String callback_date_val = data.getString("callback_date");
                        String callback_time_val = data.getString("callback_time");

                        String followup_date_val = data.getString("followup_date");
                        String followup_time_val = data.getString("followup_time");

                        String pmeeting_date_val = data.getString("pmeeting_date");
                        String pmeeting_time_val = data.getString("pmeeting_time");

                        String conversion_date_val = data.getString("conversion_date");
                        String conversion_time_val= data.getString("conversion_time");

                        String aptset_date_val = data.getString("aptset_date");
                        String aptset_time_val = data.getString("aptset_time");

                        String off_adress = data.getString("off_address");
                        String res_adress = data.getString("res_address");

                        txtName.setText("" + capitalizeString(name));
                        txtEmail.setText("" + email);
                        txtEmail.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                String emailSend = txtEmail.getText().toString();
                                Intent intent = new Intent(Intent.ACTION_SEND);
                                intent.setType("text/plain");
                                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{emailSend});
                                intent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                                intent.putExtra(Intent.EXTRA_TEXT, "I'm email body.");
                                startActivity(Intent.createChooser(intent, "Send Email"));
                            }
                        });
                        txtDob.setText("" + dob);
                        txtMobile.setText("" + mobile);
                        txtMobile.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String phone = txtMobile.getText().toString();
                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                intent.setData(Uri.parse("tel:" + phone));
                                startActivity(intent);
                            }
                        });
                        txtIncome.setText("" + income);
                        txtPlan.setText("" + plan);
                        txtCity.setText("" + city);
                        txtCity.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                String yourAddress = txtCity.getText().toString();
                                // Toast.makeText(DetailActivity.this,yourAddress,Toast.LENGTH_SHORT).show();
                                String map = "http://maps.google.com/maps?saddr=My Location&&daddr=" + navigation;
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(map));
                                startActivity(intent);
                            }
                        });

                        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), feed_a_id, feed_a_type);
                        spinner.setAdapter(customAdapter);


                        CustomAdapter customAdapter1 = new CustomAdapter(getApplicationContext(), arrayAdTypeId, arrayAdType);
                        spinner2.setAdapter(customAdapter1);

                        if (role.equals("2")) {
                            sp_layout_agent.setVisibility(View.VISIBLE);
                            spineer_agent.setVisibility(View.VISIBLE);

                            CustomAdapter customAdapter_agent = new CustomAdapter(getApplicationContext(), feed_agent_id, feed_agent_name);
                            spineer_agent.setAdapter(customAdapter_agent);
                            spineer_agent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    field_agent_update = feed_agent_id[i];
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });
                        }

                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                                // your code here

                                if (feed_a_type[position].equals("Appointment")) {

                                    linearLayoutSpinner2.setVisibility(View.VISIBLE);
                                    linearLayoutDt.setVisibility(View.VISIBLE);
                                    linearLayoutTm.setVisibility(View.VISIBLE);
                                    linearLayoutAdress.setVisibility(View.VISIBLE);

                                    /*Intent intent=new Intent();
                                    intent.putExtra("appt",feed_a_type[position]);*/
                                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                            if (arrayAdTypeId[i].equals("1")) {
                                                editTextCompany.setHint("Bldg / Complex / Tower Name");
                                                editTextBuildNo.setHint("Wing/Floor");
                                                editTextFloor.setHint("Flat No.");
                                                editTextOfficeNo.setHint("Area Name");
                                                editTextAreaName.setHint("Road Name");
                                                editTextCityName.setHint("City Name");
                                                editTextEastWest.setHint("East/West");
                                                editTextDept.setHint("RS / MS");
                                                editTextLandMark1.setHint("Landmark 1");
                                                editTextLandMark2.setHint("Landmark 1");
                                                editTextPincode.setHint("Pin Code");
                                            }

                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> adapterView) {

                                        }
                                    });

                                    date.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            showDialog(0);
                                        }
                                    });

                                    time.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            showDialog(1);
                                        }
                                    });

                                } else {
                                    linearLayoutSpinner2.setVisibility(View.GONE);
                                    linearLayoutDt.setVisibility(View.GONE);
                                    linearLayoutTm.setVisibility(View.GONE);
                                    linearLayoutSpineer3.setVisibility(View.GONE);
                                    linearLayoutAdress.setVisibility(View.GONE);

                                }

                                if (feed_a_type[position].equals("CallBack")) {
                                    Toast.makeText(DetailActivity.this, feed_a_type[position], Toast.LENGTH_SHORT).show();
                                    linearLayoutDt.setVisibility(View.VISIBLE);
                                    linearLayoutTm.setVisibility(View.VISIBLE);
                                    date.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            showDialog(0);
                                        }
                                    });

                                    time.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            showDialog(1);
                                        }
                                    });
                                }

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parentView) {
                                // your code here
                            }

                        });

                        if (!field_agent.equals("null")) {

                            for (int p = 0; p < feed_agent_id.length; p++) {
                                if (feed_agent_id[p].equals(field_agent)) {
                                    spineer_agent.setSelection(p);
                                }
                            }
                        }
                        if (!status_id.equals("") && !status_id.isEmpty() && !status_id.equals("null")) {

                            if (!status_id.equals("null")) {
                                spinner.setSelection(Integer.parseInt(status_id) - 1);
                            }
                            if (status_id.equals("1")) {
                                spinner.setEnabled(false);
                                CustomAdapter customAdapter3 = new CustomAdapter(getApplicationContext(), status_feed_a_id, status_feed_a_type);
                                spinner3.setAdapter(customAdapter3);
                                CustomAdapter customAdapter4 = new CustomAdapter(getApplicationContext(), end_feed_a_id, end_feed_a_type);
                                spinner4.setAdapter(customAdapter4);
                                //Toast.makeText(DetailActivity.this,feedback_status_id,Toast.LENGTH_SHORT).show();
                               spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        spinner4.setSelection(position);
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                                if (!feedback_status_id.equals("null")) {

                                    spinner3.setSelection(Integer.parseInt(feedback_status_id) - 2);
                                    if (feedback_status_id.equals("3"))
                                    {
                                        call_txt_date.setText(callback_date_val);
                                        call_txt_time.setText(callback_time_val);
                                    }
                                    if (feedback_status_id.equals("16"))
                                    {
                                        call_txt_date.setText(conversion_date_val);
                                        call_txt_time.setText(conversion_time_val);

                                    }
                                    if (feedback_status_id.equals("17"))
                                    {
                                        call_txt_date.setText(pmeeting_date_val);
                                        call_txt_time.setText(pmeeting_time_val);

                                    }
                                    if (feedback_status_id.equals("20"))
                                    {
                                        call_txt_date.setText(followup_date_val);
                                        call_txt_time.setText(followup_time_val);

                                    }
                                    if (feedback_status_id.equals("21"))
                                    {
                                        call_txt_date.setText(aptset_date_val);
                                        call_txt_time.setText(aptset_time_val);
                                    }
                                }

                                spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                        if (status_feed_a_id[i].equals("3") ||status_feed_a_id[i].equals("16") ||status_feed_a_id[i].equals("17") ||status_feed_a_id[i].equals("20") ||status_feed_a_id[i].equals("21")) {

                                            dt_layout_call.setVisibility(View.VISIBLE);
                                            tm_layout_call.setVisibility(View.VISIBLE);
                                            call_txt_date.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    showDialog(2);
                                                }
                                            });

                                            call_txt_time.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    showDialog(3);
                                                }
                                            });
                                        } else {
                                            dt_layout_call.setVisibility(View.GONE);
                                            tm_layout_call.setVisibility(View.GONE);
                                        }
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });


                                linearLayoutSpinner2.setVisibility(View.VISIBLE);
                                linearLayoutSpineer3.setVisibility(View.VISIBLE);
                                linearLayoutDt.setVisibility(View.VISIBLE);
                                linearLayoutTm.setVisibility(View.VISIBLE);
                                if (!aapt_type.equals("null") || !aapt_type.equals("")) {
                                    spinner2.setSelection(Integer.parseInt(aapt_type));
                                }
                                if (aapt_type.equals("0")) {
                                    editTextCompany.setText(company);
                                    editTextBuildNo.setText(office_build_no);
                                    editTextFloor.setText(office_floor);
                                    editTextOfficeNo.setText(office_no);
                                    editTextAreaName.setText(office_area);
                                    editTextCityName.setText(office_city);
                                    editTextEastWest.setText(off_east_west);
                                    editTextDept.setText(off_dept);
                                    editTextLandMark1.setText(landmark1);
                                    editTextLandMark2.setText(landmark2);
                                    editTextPincode.setText(off_pincode);
                                    navigation = off_adress;


                                }
                                if (aapt_type.equals("1")) {
                                    editTextCompany.setText(rs_bldg_name);
                                    editTextBuildNo.setText(rs_floor);
                                    editTextFloor.setText(flat);
                                    editTextOfficeNo.setText(rs_area);
                                    editTextAreaName.setText(road);
                                    editTextCityName.setText(rs_city);
                                    editTextEastWest.setText(rs_east_west);
                                    editTextDept.setText(rs_ms);
                                    editTextLandMark1.setText(rs_landmark1);
                                    editTextLandMark2.setText(rs_landmark2);
                                    editTextPincode.setText(rs_pincode);
                                    navigation = res_adress;

                                }
                                date.setText("" + dtm);
                                date.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        showDialog(0);
                                    }
                                });
                                time.setText("" + tm);
                                time.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        showDialog(1);
                                    }
                                });
                            } else {
                                linearLayoutSpinner2.setVisibility(View.GONE);
                                linearLayoutSpineer3.setVisibility(View.GONE);
                                linearLayoutDt.setVisibility(View.GONE);
                                linearLayoutTm.setVisibility(View.GONE);
                            }

                            if (status_id.equals("3")) {
                                linearLayoutDt.setVisibility(View.VISIBLE);
                                linearLayoutTm.setVisibility(View.VISIBLE);

                                date.setText("" + callback_date_val);
                                date.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        showDialog(0);
                                    }
                                });
                                time.setText("" + callback_time_val);
                                time.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        showDialog(1);
                                    }
                                });
                            }




                        }

                        if (!comment.equals("") && !comment.isEmpty() && !comment.equals("null")) {
                            editTextComment.setText("" + comment);
                        }

                    } else {
                        // Error in login. Get the error message

                        Toast.makeText(getApplicationContext(),
                                error, Toast.LENGTH_LONG).show();
                    }


                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Data Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("lead_id", lead_id);
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void postData(final String u_id, final String lead_id, final String feedback_p, final String comment_p, final String appointment_date_p, final String appointment_time_p, final String company_string_p, final String off_bldg_name_string_p, final String off_floor_string_p, final String officeNo_string_p,
                          final String off_area_string_p, final String off_city_string_p, final String off_east_west_string_p, final String dept_string_p, final String landmark1_string_p, final String landmark2_string_p, final String off_pincode_string_p,
                          final String rs_bldg_name_string_p, final String rs_floor_name_string_p, final String flat_string_p, final String rs_area_string_p, final String road_string_p, final String rs_city_string_p, final String rs_east_west_string_p, final String rs_ms_string_p,
                          final String rs_landmark1_string_p, final String rs_landmark2_string_p, final String rs_pincode_string_p, final String call_date_p, final String call_time_p, final String followup_date_p, final String followup_time_p, final String aapt_type_p, final String feed_status_p, final String feed_status_id_p, final String feed_agent_id_p,final String end_feed_status) {
        // Tag used to cancel the request
        String tag_string_req = "req_data";


        pDialog.setMessage("Updating ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_UPDATE_STATUS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Data Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    String error = jObj.getString("status");
                    String data = jObj.getString("data");

                    // Check for error node in json
                    if (error.equals("OK")) {

                        Toast.makeText(DetailActivity.this, "Sucessfully Updated", Toast.LENGTH_LONG).show();
                        // Launch main activity
                    } else {
                        // Error in login. Get the error message

                        Toast.makeText(getApplicationContext(),
                                error, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Data Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("field_agent_id", u_id);
                params.put("lead_id", lead_id);
                params.put("appointments_date", appointment_date_p);
                params.put("appointment_time", appointment_time_p);
                params.put("feedback", feedback_p);
                params.put("comment", comment_p);
                params.put("callback_date", call_date_p);
                params.put("callback_time", call_time_p);
                params.put("followup_date", followup_date_p);
                params.put("followup_time", followup_time_p);
                params.put("company", company_string_p);
                params.put("off_bldg_name", off_bldg_name_string_p);
                params.put("off_floor", off_floor_string_p);
                params.put("officeNo", officeNo_string_p);
                params.put("off_area", off_area_string_p);
                params.put("off_city", off_city_string_p);
                params.put("off_east_west", off_east_west_string_p);
                params.put("dept", dept_string_p);
                params.put("landmark1", landmark1_string_p);
                params.put("landmark2", landmark2_string_p);
                params.put("off_pincode", off_pincode_string_p);
                params.put("rs_bldg_name", rs_bldg_name_string_p);
                params.put("rs_floor", rs_floor_name_string_p);
                params.put("flat", flat_string_p);
                params.put("rs_area", rs_area_string_p);
                params.put("road", road_string_p);
                params.put("rs_city", rs_city_string_p);
                params.put("rs_east_west", rs_east_west_string_p);
                params.put("rs_ms", rs_ms_string_p);
                params.put("rs_landmark1", rs_landmark1_string_p);
                params.put("rs_landmark2", rs_landmark2_string_p);
                params.put("rs_pincode", rs_pincode_string_p);
                params.put("appt_type", aapt_type_p);
                params.put("feedback_status", feed_status_p);
                params.put("feedback_status_id", feed_status_id_p);
                params.put("fieldagent", feed_agent_id_p);
                params.put("lead_status",end_feed_status);
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id) {

        switch (id) {

            case 0:
                return new DatePickerDialog(DetailActivity.this, datePickerListener, year, month, day);
            case 1:
                return new TimePickerDialog(DetailActivity.this, timePickerListener, hour, minute, DateFormat.is24HourFormat(this));
            case 2:
                return new DatePickerDialog(DetailActivity.this, datePickerListener1, year, month, day);
            case 3:
                return new TimePickerDialog(DetailActivity.this, timePickerListener1, hour, minute, DateFormat.is24HourFormat(this));

        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            date.setText(selectedDay + "-" + (selectedMonth + 1) + "-"
                    + selectedYear);

        }
    };
    private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {


            time.setText(selectedHour + ":" + selectedMinute);

            int pos = spinner.getSelectedItemPosition();
            String feedback = feed_a_id[pos];
            AlarmDBHelper adb2 = new AlarmDBHelper(DetailActivity.this);
            associated_topicId = Integer.parseInt(lead_id);
            String alarm_date = date.getText().toString();
            String alarm_time = time.getText().toString();
            associated_topic_name = name;
            associated_topic_content = alarm_date + " " + alarm_time;
            Log.i("Edit Button Clicked", "**********");
            if (!adb2.selectAlarmCheck(associated_topicId)) {
                Intent intent = new Intent(DetailActivity.this, SetNotification.class);

                try {

                } catch (NullPointerException e) {
                    Toast t = Toast.makeText(DetailActivity.this, "null exception", Toast.LENGTH_LONG);
                    t.show();
                }

                /*Toast t = Toast.makeText(DetailActivity.this, "tid=" + feedback, Toast.LENGTH_LONG);
                t.show();*/
                intent.putExtra("topic_id", associated_topicId);
                intent.putExtra("topic_name", capitalizeString(associated_topic_name));
                intent.putExtra("date", alarm_date);
                intent.putExtra("time", alarm_time);
                intent.putExtra("feedback",pos);
                //intent.putExtra("count",count);
                startActivity(intent);

            } else {
                Toast.makeText(DetailActivity.this, "Alarm is already set for" + " " + associated_topic_name + " " + "for edit or delete go to watchlist", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private DatePickerDialog.OnDateSetListener datePickerListener1 = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            call_txt_date.setText(selectedDay + "-" + (selectedMonth + 1) + "-"
                    + selectedYear);

        }
    };
    private TimePickerDialog.OnTimeSetListener timePickerListener1 = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
            call_txt_time.setText(selectedHour + ":" + selectedMinute);
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

    public static String capitalizeString(String string) {
        char[] chars = string.toLowerCase().toCharArray();
        boolean found = false;
        for (int i = 0; i < chars.length; i++) {
            if (!found && Character.isLetter(chars[i])) {
                chars[i] = Character.toUpperCase(chars[i]);
                found = true;
            } else if (Character.isWhitespace(chars[i]) || chars[i] == '.' || chars[i] == '\'') { // You can add other chars here
                found = false;
            }
        }
        return String.valueOf(chars);
    }

}
