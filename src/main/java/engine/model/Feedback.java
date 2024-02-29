package engine.model;

public class Feedback {

    public static final String CORRECT_FEEDBACK = "Congratulations, you're right!";
    public static final String WRONG_FEEDBACK = "Wrong answer! Please, try again.";
    private boolean success;
    private String feedback;

    public Feedback() {

    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

}
