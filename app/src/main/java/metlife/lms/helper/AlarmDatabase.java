package metlife.lms.helper;


import android.provider.BaseColumns;

public final class AlarmDatabase {

    public AlarmDatabase() {}

    public  abstract class Alarm implements BaseColumns {
        public static final String TABLE_NAME = "alarm";
        public static final String COLUMN_NAME_ALARM_NAME = "name";
        public static final String COLUMN_NAME_ALARM_TIME_HOUR = "btalarm_time";
        public static final String COLUMN_NAME_ALARM_REQUEST_CODE= "request_code";
        public static final String COLUMN_NAME_ALARM_TOPIC_ID= "topic_id";

    }

    public abstract class Historytrack implements BaseColumns{
        public static final String TABLE_NAME = "history";
        public static final String COLUMN_NAME_HISTORY_TOPIC_ID= "topic_id";
        public static final String COLUMN_NAME_HISTORY_TOPIC_NAME="topic_name";

    }

    public abstract class Favouritetrack implements BaseColumns{
        public static final String TABLE_NAME = "favourite";
        public static final String COLUMN_NAME_FAVOURITE_TOPIC_ID= "ftopic_id";
        public static final String COLUMN_NAME_FAVOURITE_TOPIC_NAME="ftopic_name";
        public static final String COLUMN_NAME_FAVOURITE_PARENT_CONTENT="parent_content";

    }



}
