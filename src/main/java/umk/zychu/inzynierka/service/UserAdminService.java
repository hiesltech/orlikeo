package umk.zychu.inzynierka.service;

import umk.zychu.inzynierka.model.User;

/**
 * Created by emagdnim on 2015-10-06.
 */
public interface UserAdminService {
    Boolean hasAdminRight(User user);
}
