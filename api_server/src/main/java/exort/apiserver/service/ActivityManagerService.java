package exort.apiserver.service;

import java.util.Date;
import java.util.List;

public interface ActivityManagerService {

    public Response createNewActivity(Activity activity);
    public Response updateActivity(Activity activity,String activityid);
    public Response getActivities(Select select,int pagesize,int pagenum,int sortby);
    public Response publishActivity(String activityid, String type);
    public Response addParticipants(String activityid, List<Integer> participantIds);
    public Response addRealParticipants(String activityid, List<Integer> realParticipantIds);
    public Response deleteParticipants(String activityid, List<Integer> participantIds);
    public Response getActivityParticipants(int pagesize,int pagenum,String activityid, List<Integer> participantIds);
    public Response getActivityRealParticipants(int pagesize,int pagenum,String activityid, List<Integer> realParticipantIds);
    public Response acceptSignup(Operation operation);
    public Response getActivity(String acticityid);


    public class Operation {

        private int operatorId;
        private String action;
        private Application application;

        public class Application{
            private int id;
            private int applicantId;
            private String type;
            private Signup signup;
            private List<Integer> materialIds;
            private String createTime;
            private String handledTime;
            private String state;

            public class Signup{
                private String activityId;
            }
        }
    }

    public class Request {
        private int userId;
        private String type;
        private List<Integer> participantIds;
        private List<Integer> realParticipantIds;
    }

    public class Response<T> {

        private T data;
        private String error;
        private String message;

    }

    public class Activity {
        private String id;
        private List<Integer> associationIds;
        private Date createTime;
        private Date publishTime;
        private Date lastPublishTime;
        private Date lastModifyTime;
        private String title;
        private String content;
        private ActivityTime signupTime;
        private ActivityTime time;
        private int publishState;
        private int signupState;
        private int state;
        private boolean ifReview;
        private boolean ifOnlyMem;
        private int maxParticipants;
        private List<Integer> materialTemplateIds;
        private List<Integer> participantIds;
        private List<Integer> realParticipantIds;
        private List<String> tags;
        private String image;
    }

    public class TimeRange{

        private Date start;
        private Date end;

    }
    public class Select {

        private String keyword;
        private List<String> tags;
        private TimeRange createTime;
        private TimeRange startTime;
        private TimeRange signupTime;
        private int publishState;
        private int signupState;
        private int state;
        private int ifReview;
        private int ifOnlyMem;
    }

    public class ActivityTime {

        private int type;
        private List<TimeRange> time;

    }
}
