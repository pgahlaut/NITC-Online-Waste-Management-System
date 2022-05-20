package com.team15.nitcwastemanagementsystem;

public class Feedback {

    private int dustbin_id;
    private String msg;
    private String feedback_id;

    public Feedback(int dustbin_id, String msg, String feedback_id) {
        this.dustbin_id = dustbin_id;
        this.msg = msg;
        this.feedback_id = feedback_id;
    }

    public String getDustbin_id() {
        return String.valueOf(dustbin_id);
    }

    public void setDustbin_id(int dustbin_id) {
        this.dustbin_id = dustbin_id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getFeedback_id() {
        return feedback_id;
    }

    public void setFeedback_id(String feedback_id) {
        this.feedback_id = feedback_id;
    }
}
