package Validators;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.project.Models.User;
import com.project.Repositories.UserRepository;

public class RegisterLoginValid implements ConstraintValidator<RegisterLoginConstraint, String> {
	
	@Autowired
	UserRepository userRepo;
	
    @Override
    public void initialize(RegisterLoginConstraint loginField) {
    }

	@Override
	public boolean isValid(String loginField, ConstraintValidatorContext cxt) {
		List<User> loginFromDb = userRepo.findAllByUserLogin(loginField);
		if(loginFromDb.isEmpty()) return true;
            return false;
	}

}