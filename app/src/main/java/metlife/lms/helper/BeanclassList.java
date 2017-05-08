package metlife.lms.helper;

/**
 * Created by apple on 15/03/16.
 *
 * //********LISTVIEW************
 */
public class BeanclassList {


    private String all_feed_status;
    private String followup_time;
    private String followup_date;
    private String callback_date;
    private String callback_time;
    private String conversion_date;
    private String conversion_time;
    private String meeting_date;
    private String meeting_time;
    private String apointset_date;
    private String apointset_time;
    private String id;
    private String name;
    private String mobile;
    private String dob;
    private String plan;
    private String city;
    private String income;
    private String appointDate;
    private String appointTime;
    private String addStType;
    private String address;
    private String lead_type;

    public String getAddStType() {
        return addStType;
    }

    public void setAddStType(String addStType) {
        this.addStType = addStType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public void setAppointTime(String appointTime) {
        this.appointTime = appointTime;
    }
    public String getAppointTime() {
        return appointTime;
    }


    public void setAppointDate(String appointDate) {
        this.appointDate = appointDate;
    }
    public String getAppointDate() {
        return appointDate;
    }

    public void setFollowup_date(String followup_date) {
        this.followup_date = followup_date;
    }
    public String getFollowup_date() {
        return followup_date;
    }
    public void setFollowup_time(String followup_time) {
        this.followup_time = followup_time;
    }
    public String getFollowup_time() {
        return followup_time;
    }

    public void setCallback_date(String callback_date) {
        this.callback_date = callback_date;
    }
    public String getCallback_date() {
        return callback_date;
    }
    public void setCallback_time(String callback_time) {
        this.callback_time = callback_time;
    }

    public String getCallback_time() {
        return callback_time;
    }

    public void setConversion_date(String conversion_date) {
        this.conversion_date = conversion_date;
    }
    public String getConversion_date() {
        return conversion_date;
    }

    public void setConversion_time(String conversion_time) {
        this.conversion_time = conversion_time;
    }
    public String getConversion_time() {
        return conversion_time;
    }
    public void setMeeting_date(String meeting_date) {
        this.meeting_date = meeting_date;
    }
    public String getMeeting_date() {
        return meeting_date;
    }
    public void setMeeting_time(String meeting_time) {
        this.meeting_time = meeting_time;
    }
    public String getMeeting_time() {
        return meeting_time;
    }
    public void setApointset_date(String apointset_date) {
        this.apointset_date = apointset_date;
    }
    public String getApointset_date() {
        return apointset_date;
    }
    public void setApointset_time(String apointset_time) {
        this.apointset_time = apointset_time;
    }
    public String getApointset_time() {
        return apointset_time;
    }





    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getCall_attempts() {
        return call_attempts;
    }

    public void setCall_attempts(String call_attempts) {
        this.call_attempts = call_attempts;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public void setFollowupdate(String followupdate) {
        this.followupdate = followupdate;
    }
    public String getFollowupdate() {
        return followupdate;
    }

    public String getPosted_on() {
        return posted_on;
    }

    public void setPosted_on(String posted_on) {
        this.posted_on = posted_on;
    }

    private String call_attempts;
    private String feedback;

    public String getFeedback_status() {
        return feedback_status;
    }

    public void setFeedback_status(String feedback_status) {
        this.feedback_status = feedback_status;
    }

    public void setAll_feed_status(String all_feed_status) {
        this.all_feed_status = all_feed_status;
    }
    public String getAll_feed_status() {
        return all_feed_status;
    }

    public String getLead_type() {
        return lead_type;
    }

    public void setLead_type(String lead_type) {
        this.lead_type = lead_type;
    }

    private String feedback_status;
    private String followupdate;

    public BeanclassList(String id, String name, String mobile, String dob, String plan, String city,String addStType,String address, String income, String call_attempts, String feedback,String feedback_status,String followupdate,String appoint_date,String appointTime, String posted_on,String callback_date,String callback_time,String followup_date,String followup_time,String conversion_date,String conversion_time,String apointset_date,String apointset_time,String meeting_date, String meeting_time,String lead_type) {
        this.id = id;
        this.name = name;
        this.mobile = mobile;
        this.dob = dob;
        this.plan = plan;
        this.city = city;
        this.addStType=addStType;
        this.address=address;
        this.income = income;
        this.call_attempts = call_attempts;
        this.feedback = feedback;
        this.feedback_status=feedback_status;
        this.followupdate=followupdate;
        this.posted_on = posted_on;

        this.appointDate=appoint_date;
        this.appointTime=appointTime;

        this.followup_date=followup_date;
        this.followup_time=followup_time;

        this.callback_date=callback_date;
        this.callback_time=callback_time;

        this.conversion_date=conversion_date;
        this.conversion_time=conversion_time;

        this.meeting_date=meeting_date;
        this.meeting_time=meeting_time;

        this.apointset_date=apointset_date;
        this.apointset_time=apointset_time;

        this.lead_type=lead_type;
    }

    private String posted_on;

    }


