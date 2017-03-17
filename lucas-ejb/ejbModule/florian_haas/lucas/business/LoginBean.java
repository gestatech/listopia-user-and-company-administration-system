package florian_haas.lucas.business;

import static florian_haas.lucas.security.EnumPermission.*;

import java.util.*;

import javax.ejb.*;
import javax.inject.Inject;
import javax.validation.executable.*;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.*;
import org.apache.shiro.subject.Subject;

import florian_haas.lucas.database.*;
import florian_haas.lucas.model.*;
import florian_haas.lucas.security.*;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@ValidateOnExecution(type = ExecutableType.IMPLICIT)
@Secured
public class LoginBean implements LoginBeanLocal {

	@Inject
	@JPADAO
	private LoginUserDAO loginUserDao;

	@Inject
	@JPADAO
	private LoginUserRoleDAO loginUserRoleDao;

	@EJB
	private UserBeanLocal userBean;

	@Override
	public void login(String username, char[] password) {
		SecurityUtils.getSubject().login(new UsernamePasswordToken(username, password, false));
		Arrays.fill(password, 'c');
	}

	@Override
	public void logout() {
		SecurityUtils.getSubject().logout();
	}

	@Override
	public Subject getSubject() {
		return SecurityUtils.getSubject();
	}

	@Override
	@RequiresPermissions(LOGIN_CREATE_RAW_LOGIN_USER)
	public Long newLoginUser(String username, char[] password, List<Long> userRoleIds) {
		return newLoginUserHelper(username, password, null, userRoleIds);
	}

	@Override
	@RequiresPermissions(LOGIN_CREATE_REGISTERED_LOGIN_USER)
	public Long newLoginUser(Long user, char[] password, List<Long> userRoleIds) {
		return newLoginUserHelper(Long.toString(user), password, userBean.findById(user), userRoleIds);
	}

	private Long newLoginUserHelper(String username, char[] password, User usr, List<Long> userRoleIds) {
		PasswordService passwordService = new DefaultPasswordService();
		String encryptedPassword = passwordService.encryptPassword(password);
		LoginUser user = new LoginUser(username, encryptedPassword, usr);
		userRoleIds.forEach(id -> {
			user.addRole(loginUserRoleDao.findById(id));
		});
		Arrays.fill(password, 'c');
		loginUserDao.persist(user);
		return user.getId();
	}

	@Override
	@RequiresPermissions(LOGIN_CHANGE_PASSWORD)
	public Boolean changePassword(char[] oldPassword, char[] newPassword) {
		Boolean ret = Boolean.FALSE;
		try {
			ret = changePasswordHelper(Long.valueOf(SecurityUtils.getSubject().getPrincipal().toString()), oldPassword, newPassword);
		}
		catch (NumberFormatException e) {}
		return ret;
	}

	@Override
	@RequiresPermissions(LOGIN_NEW_PASSWORD)
	public Boolean newPassword(Long loginUserId, char[] newPassword) {
		return changePasswordHelper(loginUserId, null, newPassword);
	}

	private Boolean changePasswordHelper(Long loginUserId, char[] oldPassword, char[] newPassword) {
		PasswordService passwordService = new DefaultPasswordService();
		LoginUser user = loginUserDao.findById(loginUserId);
		String newEncryptedPassword = passwordService.encryptPassword(newPassword);
		Arrays.fill(newPassword, 'c');
		if (oldPassword == null || (oldPassword != null && passwordService.passwordsMatch(oldPassword, user.getHashedPassword()))) {
			Arrays.fill(oldPassword, 'c');
			user.setHashedPassword(newEncryptedPassword);
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	@Override
	public LoginUser findLoginUserById(Long id) {
		return loginUserDao.findById(id);
	}

	@Override
	public Boolean addLoginUserRoleToUser(Long userId, Long roleId) {
		LoginUser user = loginUserDao.findById(userId);
		LoginUserRole role = loginUserRoleDao.findById(roleId);
		return user.addRole(role);
	}

	@Override
	public Boolean removeLoginUserRoleFromUser(Long userId, Long roleId) {
		LoginUser user = loginUserDao.findById(userId);
		LoginUserRole role = loginUserRoleDao.findById(roleId);
		return user.removeRole(role);
	}

	@Override
	public List<LoginUser> findAllLoginUsers() {
		return loginUserDao.findAll();
	}

	@Override
	public String getDBUsername(Long userId) {
		LoginUser user = loginUserDao.findById(userId);
		User user2 = user.getUser();
		return user2 != null && user2.getForename() != null && user2.getSurname() != null ? user2.getForename() + " " + user2.getSurname() : null;
	}

	@Override
	public List<LoginUser> findLoginUsers(Long id, String username, Long userId, List<Long> roleIds, Boolean useId, Boolean useUsername,
			Boolean useUserId, Boolean useRoleIds, EnumQueryComparator idComparator, EnumQueryComparator usernameComparator,
			EnumQueryComparator userIdComparator, EnumQueryComparator roleIdsComparator) {
		return loginUserDao.findLoginUsers(id, username, userId, roleIds, useId, useUsername, useUserId, useRoleIds, idComparator, usernameComparator,
				userIdComparator, roleIdsComparator);
	}

}
