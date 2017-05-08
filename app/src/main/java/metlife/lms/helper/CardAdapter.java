package metlife.lms.helper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import metlife.lms.R;
import metlife.lms.activity.DetailActivity;

/**
 * Created by Ashish on 27/3/2017.
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {


    private Context context;

    //List of superHeroes
    ArrayList<BeanclassList> bean;

    public CardAdapter(ArrayList<BeanclassList> bean, Context context){
        super();
        //Getting all the superheroes
        this.bean = bean;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final BeanclassList superHero = bean.get(position);


        holder.textViewName.setText(capitalizeString(superHero.getName()));
        holder.textViewMobile.setText(String.valueOf(superHero.getMobile()));
        holder.textViewCity.setText(superHero.getIncome());
        if(superHero.getFeedback_status().equals("null")||superHero.getFeedback_status().equals("")) {


        }
        else
        {
            if(superHero.getLead_type().equals("1")) {
                if (superHero.getFeedback_status().equals("Conversion")) {
                    holder.mStatusView.setVisibility(View.VISIBLE);
                    holder.textViewFeedbackStatus.setText(superHero.getFeedback_status());
                    holder.textViewFeedbackStatus.setTextColor(Color.GREEN);
                    holder.mStatusView.setBackgroundColor(Color.GREEN);
                } else if (superHero.getFeedback_status().equals("Not Eligible") || superHero.getFeedback_status().equals("Fake Number") || superHero.getFeedback_status().equals("Not Interested") || superHero.getFeedback_status().equals("Already bought")) {
                    holder.mStatusView.setVisibility(View.VISIBLE);
                    holder.textViewFeedbackStatus.setText(superHero.getFeedback_status());
                    holder.textViewFeedbackStatus.setTextColor(Color.RED);
                    holder.mStatusView.setBackgroundColor(Color.RED);
                } else {
                    holder.mStatusView.setVisibility(View.VISIBLE);
                    holder.textViewFeedbackStatus.setText(superHero.getFeedback_status());
                }

            }
        }
       /* if(superHero.getFollowupdate().equals("null") || superHero.getFollowupdate().equals("00-00-0000") ) {

            if(superHero.getAppointDate().equals("null") || superHero.getAppointDate().equals("00-00-0000")) {

                holder.rowFollow.setVisibility(View.GONE);
            }
            else
            {
                holder.textViewFollowupLabel.setText("App. Date");
                holder.textViewFollowupdate.setText(superHero.getAppointDate()+" "+superHero.getAppointTime());

            }
        }*/
            if(superHero.getLead_type().equals("1")) {
                if (superHero.getFeedback_status().equals("Callback")){
                    if (!superHero.getCallback_date().equals("null") || !superHero.getCallback_date().equals("") || !superHero.getCallback_date().equals("0000-00-00 00:00:00")) {
                        holder.textViewFollowupLabel.setText("Call. Date");
                        holder.textViewFollowupdate.setText(superHero.getCallback_date() + " " + superHero.getCallback_time());
                    } else {
                        holder.textViewFollowupLabel.setText("P App. Date");
                        holder.textViewFollowupdate.setText(superHero.getAppointDate() + " " + superHero.getAppointTime());
                    }
            }
                if (superHero.getFeedback_status().equals("Follow up")){
                    if (!superHero.getFollowup_date().equals("null") || !superHero.getFollowup_date().equals("") || !superHero.getFollowup_date().equals("0000-00-00 00:00:00")) {
                    holder.textViewFollowupLabel.setText("Foll. Date");
                    holder.textViewFollowupdate.setText(superHero.getFollowup_date()+" "+superHero.getFollowup_time());
                }
               else
               {
                   holder.textViewFollowupLabel.setText("P App. Date");
                   holder.textViewFollowupdate.setText(superHero.getAppointDate()+" "+superHero.getAppointTime());
               }}
                if (superHero.getFeedback_status().equals("Conversion")) {
                    if (!superHero.getConversion_date().equals("null") || !superHero.getConversion_date().equals("") || !superHero.getConversion_date().equals("0000-00-00 00:00:00")) {
                        holder.textViewFollowupLabel.setText("C. Date");
                        holder.textViewFollowupdate.setText(superHero.getConversion_date() + " " + superHero.getConversion_time());
                    } else {
                        holder.textViewFollowupLabel.setText("P App. Date");
                        holder.textViewFollowupdate.setText(superHero.getAppointDate() + " " + superHero.getAppointTime());
                    }
                }
                if (superHero.getFeedback_status().equals("Meeting Postponed")) {
                    if (!superHero.getMeeting_date().equals("null") || !superHero.getMeeting_date().equals("") || !superHero.getMeeting_date().equals("0000-00-00 00:00:00")) {
                        holder.textViewFollowupLabel.setText("M App. Date");
                        holder.textViewFollowupdate.setText(superHero.getMeeting_date() + " " + superHero.getMeeting_time());
                    } else {
                        holder.textViewFollowupLabel.setText("P App. Date");
                        holder.textViewFollowupdate.setText(superHero.getAppointDate() + " " + superHero.getAppointTime());
                    }
                }
                if (superHero.getFeedback_status().equals("Appointment Set")) {
              if (!superHero.getApointset_date().equals("null") || !superHero.getApointset_date().equals("") || !superHero.getApointset_date().equals("0000-00-00 00:00:00")) {

                    holder.textViewFollowupLabel.setText("F App. Date");
                    holder.textViewFollowupdate.setText(superHero.getApointset_date()+" "+superHero.getApointset_time());
                }
                else
                {
                    holder.textViewFollowupLabel.setText("P App. Date");
                    holder.textViewFollowupdate.setText(superHero.getAppointDate()+" "+superHero.getAppointTime());
                }
                }
            }
            else{
                holder.textViewFollowupLabel.setText("Posted On");
                holder.textViewFollowupdate.setText(superHero.getPosted_on());
            }

        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context, DetailActivity.class);
                intent.putExtra("lead_id",superHero.getId());
                intent.putExtra("lead_type",superHero.getLead_type());
                context.startActivity(intent);
            }
        });
        if(superHero.getAddStType().equals("null"))
        {
            holder.textLocate.setVisibility(View.INVISIBLE);
        }
        else {
            holder.textLocate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String map = "http://maps.google.com/maps?saddr=My Location&daddr=" + superHero.getAddress();
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(map));
                    context.startActivity(intent);
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return bean.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public CardView card_view;
        public View     mStatusView;
        public TextView textViewName;
        public TextView textViewMobile;
        public TextView textViewCity;
        public TextView textViewFeedbackStatus;
        public TextView  textViewFollowupdate;
        public TextView  textViewFollowupLabel;
        public TextView  textView;
        public TextView  textLocate;
        public LinearLayout rowFollow;
        public ViewHolder(View itemView) {
            super(itemView);

            textViewName = (TextView) itemView.findViewById(R.id.name);
            textViewMobile= (TextView) itemView.findViewById(R.id.mobile);
            textViewCity = (TextView) itemView.findViewById(R.id.city);
            textViewFeedbackStatus= (TextView) itemView.findViewById(R.id.feedback_status);
            textViewFollowupdate= (TextView) itemView.findViewById(R.id.followup_date);
            textViewFollowupLabel= (TextView) itemView.findViewById(R.id.follow_label);
            textView = (TextView) itemView.findViewById(R.id.view);
            mStatusView=(View)itemView.findViewById(R.id.status);
            textLocate = (TextView) itemView.findViewById(R.id.locate);
            rowFollow= (LinearLayout) itemView.findViewById(R.id.below);
            card_view=(CardView)itemView.findViewById(R.id.card_view);

        }
    }
    public static String capitalizeString(String string) {
        char[] chars = string.toLowerCase().toCharArray();
        boolean found = false;
        for (int i = 0; i < chars.length; i++) {
            if (!found && Character.isLetter(chars[i])) {
                chars[i] = Character.toUpperCase(chars[i]);
                found = true;
            } else if (Character.isWhitespace(chars[i]) || chars[i]=='.' || chars[i]=='\'') { // You can add other chars here
                found = false;
            }
        }
        return String.valueOf(chars);
    }
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
}