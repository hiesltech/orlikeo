package umk.zychu.inzynierka.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by emagdnim on 2015-09-12.
 */
@Entity
@Table(name = "user_notifications")
public class UserNotification extends BaseEntity implements Comparable<UserNotification>{

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    User user;

    @Column
    String subject;

    @Column
    String description;

    @Column
    String link;

    @Column
    Boolean checked = false;

    @Column(name = "creation_date")
    Date notificationDate;

    UserNotification() {
        super();
    }

    public UserNotification(String title, String description, UserEvent ue) {
        super();
        this.user = ue.getUser();
        this.subject = title;
        this.description = description;
        this.link = "/events/details/" + ue.getEvent().getId();
    }

    public UserNotification(String title, String description, User user) {
        super();
        this.user = user;
        this.subject = title;
        this.description = description;
        this.link = "#";
    }

    public UserNotification(String title, String description, String href, User targetUser){
        super();
        this.user = targetUser;
        this.subject = title;
        this.description = description;
        if(href == null){
            link = "#";
        }else{
            link = href;
        }
    }




    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }


    public Boolean isChecked(){
        return checked;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(Date notificationDate) {
        this.notificationDate = notificationDate;
    }

    @Override
    public int compareTo(UserNotification o) {
        if(notificationDate.before(o.getNotificationDate())){
            return 1;
        }else if(notificationDate.after(o.getNotificationDate())){
            return -1;
        }else{
            return 0;
        }
    }
}
