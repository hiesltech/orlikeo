package umk.zychu.inzynierka.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import umk.zychu.inzynierka.controller.DTObeans.OrlikForm;
import umk.zychu.inzynierka.model.Orlik;
import umk.zychu.inzynierka.model.User;
import umk.zychu.inzynierka.service.OrlikService;
import umk.zychu.inzynierka.service.UserService;

import java.util.Optional;

/**
 * Created by emagdnim on 2015-10-12.
 */
@Component
public class OrlikFormValidator implements Validator {

    @Autowired
    OrlikService orlikService;
    @Autowired
    UserService userService;

    private static final String INVALID_EMAIL = "web.register.validation.email.invalid";
    private static final String USER_DOESNT_EXISTS = "web.admin.orlik.animator.notexists";
    private static final String USER_IS_ALREADY_AN_ANIMATOR = "web.admin.orlik.animator.alreadytaken";

    @Override
    public boolean supports(Class<?> clazz) {
        return OrlikForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        OrlikForm form = (OrlikForm) target;
        if (!form.getAnimatorEmail().matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}")) {
            errors.rejectValue("animatorEmail", INVALID_EMAIL);
            return;
        }
        User user = userService.getUser(form.getAnimatorEmail());
        if(user == null){
            errors.rejectValue("animatorEmail", USER_DOESNT_EXISTS);
            return;
        }
        Orlik  requestedOrlik = null;
        if(form.getId() != null) {
            requestedOrlik = orlikService.getOrlikById(form.getId());
        }
        Optional<Orlik> orlikOpt = orlikService.getAnimatorOrlik(user);
        if (orlikOpt.isPresent() && !orlikOpt.get().equals(requestedOrlik)) {
                errors.rejectValue("animatorEmail", USER_IS_ALREADY_AN_ANIMATOR);
        }
    }
}
