package exort.activity.entity;

import java.util.List;

public class Operation {

    private int operatorId;
    private String action;
    private Application application;

    public int getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(int operatorId) {
        this.operatorId = operatorId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }


    public class Application{
        private int id;
        private int applicantId;
        private String type;
        private Signup signup;
        private List<Integer> materialIds;
        private String createTime;
        private String handledTime;
        private String state;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getApplicantId() {
            return applicantId;
        }

        public void setApplicantId(int applicantId) {
            this.applicantId = applicantId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Signup getSignup() {
            return signup;
        }

        public void setSignup(Signup signup) {
            this.signup = signup;
        }

        public List<Integer> getMaterialIds() {
            return materialIds;
        }

        public void setMaterialIds(List<Integer> materialIds) {
            this.materialIds = materialIds;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getHandledTime() {
            return handledTime;
        }

        public void setHandledTime(String handledTime) {
            this.handledTime = handledTime;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }


        public class Signup{
            private String activityId;

            public String getActivityId() {
                return activityId;
            }

            public void setActivityId(String activityId) {
                this.activityId = activityId;
            }
        }
    }
}
