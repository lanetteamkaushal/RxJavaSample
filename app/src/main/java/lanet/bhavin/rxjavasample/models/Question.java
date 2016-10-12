
package lanet.bhavin.rxjavasample.models;

import java.util.HashMap;
import java.util.Map;

public class Question {

    private Owner owner;
    private Integer acceptedAnswerId;
    private Integer answerCount;
    private Integer score;
    private Integer creationDate;
    private String link;
    private String title;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The owner
     */
    public Owner getOwner() {
        return owner;
    }

    /**
     * @param owner The owner
     */
    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    /**
     * @return The acceptedAnswerId
     */
    public Integer getAcceptedAnswerId() {
        return acceptedAnswerId;
    }

    /**
     * @param acceptedAnswerId The accepted_answer_id
     */
    public void setAcceptedAnswerId(Integer acceptedAnswerId) {
        this.acceptedAnswerId = acceptedAnswerId;
    }

    /**
     * @return The answerCount
     */
    public Integer getAnswerCount() {
        return answerCount;
    }

    /**
     * @param answerCount The answer_count
     */
    public void setAnswerCount(Integer answerCount) {
        this.answerCount = answerCount;
    }

    /**
     * @return The score
     */
    public Integer getScore() {
        return score;
    }

    /**
     * @param score The score
     */
    public void setScore(Integer score) {
        this.score = score;
    }

    /**
     * @return The creationDate
     */
    public Integer getCreationDate() {
        return creationDate;
    }

    /**
     * @param creationDate The creation_date
     */
    public void setCreationDate(Integer creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * @return The link
     */
    public String getLink() {
        return link;
    }

    /**
     * @param link The link
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
