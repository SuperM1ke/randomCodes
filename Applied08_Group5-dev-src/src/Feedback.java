/**
 * This Class is for feedback.
 *
 * @author Haojun Huang
 * @version 1.0.0
 */
public class Feedback {
    public int feedbackId;
    public int classId;
    public int rating;
    public String description;

    public Feedback() {}

    public Feedback(int feedbackId, int classId, int rating, String description) {
        this.feedbackId = feedbackId;
        this.classId = classId;
        this.rating = rating;
        this.description = description;
    }

    public int getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(int feedbackId) {
        this.feedbackId = feedbackId;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
