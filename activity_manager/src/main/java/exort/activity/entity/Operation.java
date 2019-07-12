package exort.activity.entity;

import java.util.List;

public class Operation {

    private Long operatorId;
    private String action;
    private Application application;

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
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
        private Long id;
        private Long applicantId;
        private String type;
        private Signup signup;
        private List<Long> materialIds;
        private String createTime;
        private String handledTime;
        private String state;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getApplicantId() {
            return applicantId;
        }

        public void setApplicantId(Long applicantId) {
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

        public List<Long> getMaterialIds() {
            return materialIds;
        }

        public void setMaterialIds(List<Long> materialIds) {
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
            private Long activityId;

            public Signup(Long activityid){
                this.activityId = activityid;
            }

            public Long getActivityId() {
                return activityId;
            }

            public void setActivityId(Long activityId) {
                this.activityId = activityId;
            }
        }
    }
}
