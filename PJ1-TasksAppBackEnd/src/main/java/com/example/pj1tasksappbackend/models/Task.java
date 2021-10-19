package com.example.pj1tasksappbackend.models;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

/**
 * The type Task.
 */
@Entity
@Table(name="tasks_test")
public class Task {
    //-------------------- Variables --------------------
    @Id
    @Column(name="uuid", length=40)
    private String uuid;

    @Column(name="created_by_uuid", length=40)
    private String createdByUuid;

    @Column(name="title", length=64)
    private String title;

    @Column(name="description", length=512)
    private String description;

    @Column(name="is_completed")
    private Boolean isCompleted;

    @Column(name="priority")
    private int priority;

    @Column(name="created_at_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date createdAtTimestamp;

    @Column(name="due_by_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date dueByTimestamp;

    //-------------------- Constructors --------------------

    /**
     * Instantiates a new Task.
     */
    public Task() {};

    /**
     * Instantiates a new Task.
     *
     * @param createdByUuid the created by uuid
     * @param title         the title
     * @param description   the description
     * @param priority      the priority
     */
    public Task(String createdByUuid, String title, String description, int priority) {
        this.uuid = UUID.randomUUID().toString();
        this.createdByUuid = createdByUuid;
        this.title = title;
        this.description = description;
        this.isCompleted = false;
        this.priority = priority;
        this.createdAtTimestamp = new Date(System.currentTimeMillis());
        this.dueByTimestamp = new Date(System.currentTimeMillis() - 1000*60*60*24);
    };

    /**
     * Instantiates a new Task.
     *
     * @param uuid               the uuid
     * @param createdByUuid      the created by uuid
     * @param title              the title
     * @param description        the description
     * @param isCompleted        the is completed
     * @param priority           the priority
     * @param createdAtTimestamp the created at timestamp
     * @param dueByTimestamp     the due by timestamp
     */
    public Task(String uuid, String createdByUuid, String title, String description, Boolean isCompleted,
                int priority, Date createdAtTimestamp, Date dueByTimestamp
    ) {
        this.uuid = uuid;
        this.createdByUuid = createdByUuid;
        this.title = title;
        this.description = description;
        this.isCompleted = isCompleted;
        this.priority = priority;
        this.createdAtTimestamp = createdAtTimestamp;
        this.dueByTimestamp = dueByTimestamp;
    }

    /**
     * Instantiates a new Task.
     *
     * @param uuid               the uuid
     * @param title              the title
     * @param description        the description
     * @param isCompleted        the is completed
     * @param priority           the priority
     * @param createdAtTimestamp the created at timestamp
     * @param dueByTimestamp     the due by timestamp
     */
    public Task(String uuid, String title, String description, Boolean isCompleted, int priority, Date createdAtTimestamp, Date dueByTimestamp) {
        this.uuid = uuid;
        this.title = title;
        this.description = description;
        this.isCompleted = isCompleted;
        this.priority = priority;
        this.createdAtTimestamp = createdAtTimestamp;
        this.dueByTimestamp = dueByTimestamp;
    }

//-------------------- Getters and Setters --------------------


    /**
     * Gets uuid.
     *
     * @return the uuid
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * Gets created by uuid.
     *
     * @return the created by uuid
     */
    public String getCreatedByUuid() {
        return createdByUuid;
    }

    /**
     * Sets created by uuid.
     *
     * @param createdByUuid the created by uuid
     */
    public void setCreatedByUuid(String createdByUuid) {
        this.createdByUuid = createdByUuid;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets completed.
     *
     * @return the completed
     */
    public Boolean getCompleted() {
        return isCompleted;
    }

    /**
     * Sets completed.
     *
     * @param completed the completed
     */
    public void setCompleted(Boolean completed) {
        isCompleted = completed;
    }

    /**
     * Gets priority.
     *
     * @return the priority
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Sets priority.
     *
     * @param priority the priority
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }

    /**
     * Gets created at timestamp.
     *
     * @return the created at timestamp
     */
    public Date getCreatedAtTimestamp() {
        return createdAtTimestamp;
    }

    /**
     * Gets due by timestamp.
     *
     * @return the due by timestamp
     */
    public Date getDueByTimestamp() {
        return dueByTimestamp;
    }

    /**
     * Sets due by timestamp.
     *
     * @param dueByTimestamp the due by timestamp
     */
    public void setDueByTimestamp(Date dueByTimestamp) {
        this.dueByTimestamp = dueByTimestamp;
    }

    @Override
    public String toString() {
        return "Task{" +
                "uuid='" + uuid + '\'' +
                ", createdByUuid='" + createdByUuid + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", isCompleted=" + isCompleted +
                ", priority=" + priority +
                ", createdAtTimestamp=" + createdAtTimestamp +
                ", dueByTimestamp=" + dueByTimestamp +
                '}';
    }
}
