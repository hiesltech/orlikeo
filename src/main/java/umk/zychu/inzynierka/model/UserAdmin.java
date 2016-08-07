package umk.zychu.inzynierka.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Created by emagdnim on 2015-10-06.
 */
@Entity
@Table(name = "admin")
public class UserAdmin extends BaseEntity {

    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @OneToOne(orphanRemoval = true)
    User admin;

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

}
