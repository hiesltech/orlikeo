package umk.zychu.inzynierka.controller.DTObeans;

import umk.zychu.inzynierka.model.UserNotification;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by emagdnim on 2015-09-15.
 */
public class UserNotificationDTO implements Serializable, Comparable<UserNotificationDTO> {

    Integer id;
    String username;
    String subject;
    String description;
    String link;

    UserNotificationDTO(){
        super();
    }

    public UserNotificationDTO(UserNotification userNotification){
        this.id = userNotification.getId();
        this.username = userNotification.getUser().getEmail();
        this.subject = userNotification.getSubject();
        this.description = userNotification.getDescription();
        this.link = userNotification.getLink();
        this.date = userNotification.getNotificationDate();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    Date date;

    @Override
    public int compareTo(UserNotificationDTO o) {
        if(this.date.before(o.getDate()))
            return 1;
        if(this.date.after(o.getDate()))
            return -1;
        else return 0;
    }
}