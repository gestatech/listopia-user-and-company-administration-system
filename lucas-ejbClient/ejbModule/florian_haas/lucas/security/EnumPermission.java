package florian_haas.lucas.security;

import static florian_haas.lucas.security.EnumPermissionModule.*;

import java.util.ArrayList;

public enum EnumPermission {

	ALL(null, "*"),
	ACCESS_XHTMLS(null, "accessXHTMLs"),

	ACCOUNT_ALL(ACCOUNT, "*"),
	ACCOUNT_PAY_IN(ACCOUNT, "payIn"),
	ACCOUNT_PAY_OUT(ACCOUNT, "payOut"),
	ACCOUNT_TRANSACTION(ACCOUNT, "transaction"),
	ACCOUNT_BLOCK(ACCOUNT, "block"),
	ACCOUNT_UNBLOCK(ACCOUNT, "unblock"),
	ACCOUNT_PROTECT(ACCOUNT, "protect"),
	ACCOUNT_UNPROTECT(ACCOUNT, "unprotect"),
	ACCOUNT_FIND_ALL(ACCOUNT, "findAll"),
	ACCOUNT_FIND_BY_ID(ACCOUNT, "findById"),
	ACCOUNT_FIND_DYNAMIC(ACCOUNT, "findDynamic"),
	ACCOUNT_TRANSACTION_FROM_PROTECTED(ACCOUNT, "transactionFromProtected"),
	ACCOUNT_TRANSACTION_TO_PROTECTED(ACCOUNT, "transactionToProtected"),
	ACCOUNT_IGNORE_TRANSACTION_LIMIT(ACCOUNT, "ignoreTransactionLimit"),
	ACCOUNT_FIND_ACCOUNT_OWNER(ACCOUNT, "findAccountOwner"),
	ACCOUNT_ACCESS_VIEW(ACCOUNT, "accessView"),
	ACCOUNT_VIEW_TRANSACTION_LOGS(ACCOUNT, "viewTransactionLogs"),
	ACCOUNT_PRINT(ACCOUNT, "print"),
	ACCOUNT_EXPORT(ACCOUNT, "export"),

	ATTENDANCE_ALL(ATTENDANCE, "*"),
	ATTENDANCE_SCAN(ATTENDANCE, "scan"),
	ATTENDANCE_EVALUATE_ALL(ATTENDANCE, "evaluateAll"),
	ATTENDANCE_FIND_ALL(ATTENDANCE, "findAll"),
	ATTENDANCE_FIND_BY_ID(ATTENDANCE, "findById"),
	ATTENDANCE_FIND_BY_USER_CARD_ID(ATTENDANCE, "findByUserCardId"),
	ATTENDANCE_FIND_DYNAMIC(ATTENDANCE, "findDynamic"),

	COMPANY_ALL(COMPANY, "*"),
	COMPANY_CREATE(COMPANY, "create"),
	COMPANY_FIND_ALL(COMPANY, "findAll"),
	COMPANY_FIND_BY_ID(COMPANY, "findById"),
	COMPANY_FIND_DYNAMIC(COMPANY, "findDynamic"),
	COMPANY_SET_NAME(COMPANY, "setName"),
	COMPANY_SET_DESCRIPTION(COMPANY, "setDescription"),
	COMPANY_SET_SECTION(COMPANY, "setRoomSection"),
	COMPANY_SET_COMPANY_TYPE(COMPANY, "setCompanyType"),
	COMPANY_SET_PARENT_COMPANY(COMPANY, "setParentCompany"),
	COMPANY_REMOVE_PARENT_COMPANY(COMPANY, "removeParentCompany"),
	COMPANY_ADD_TAXDATA(COMPANY, "addTaxdata"),
	COMPANY_REMOVE_TAXDATA(COMPANY, "removeTaxdata"),
	COMPANY_SET_INCOMINGS(COMPANY, "setIncomings"),
	COMPANY_SET_OUTGOINGS(COMPANY, "setOutgoings"),
	COMPANY_ADD_COMPANY_CARD(COMPANY, "addCompanyCard"),
	COMPANY_REMOVE_COMPANY_CARD(COMPANY, "removeCompanyCard"),
	COMPANY_BLOCK_COMPANY_CARD(COMPANY, "blockCompanyCard"),
	COMPANY_UNBLOCK_COMPANY_CARD(COMPANY, "unblockCompanyCard"),
	COMPANY_GET_MANAGERS(COMPANY, "getManagers"),
	COMPANY_GET_ADVISORS(COMPANY, "getAdvisors"),
	COMPANY_GET_EMPLOYEES(COMPANY, "getEmployees"),
	COMPANY_FIND_COMPANY_CARD_BY_ID(COMPANY, "findCompanyCardById"),
	COMPANY_GET_COMPANY_CARDS(COMPANY, "getCompanyCards"),
	COMPANY_SET_VALID_DATE_COMPANY_CARD(COMPANY, "setValidDateCompanyCard"),
	COMPANY_ACCESS_VIEW(COMPANY, "accessView"),
	COMPANY_PRINT(COMPANY, "print"),
	COMPANY_EXPORT(COMPANY, "export"),

	EMPLOYMENT_ALL(EMPLOYMENT, "*"),
	EMPLOYMENT_CREATE(EMPLOYMENT, "create"),
	EMPLOYMENT_REMOVE(EMPLOYMENT, "remove"),
	EMPLOYMENT_ADD_ATTENDANCEDATA(EMPLOYMENT, "addAttendancedata"),
	EMPLOYMENT_SET_ATTENDANCEDATA_DATE(EMPLOYMENT, "setAttendancedataDate"),
	EMPLOYMENT_SET_ATTENDANCEDATA_WAS_PRESENT(EMPLOYMENT, "setAttendancedataWasPresent"),
	EMPLOYMENT_REMOVE_ATTENDANCEDATA(EMPLOYMENT, "remvoeAttendancedata"),
	EMPLOYMENT_PAY_SALARY(EMPLOYMENT, "paySalary"),
	EMPLOYMENT_PRINT(EMPLOYMENT, "print"),
	EMPLOYMENT_EXPORT(EMPLOYMENT, "export"),
	EMPLOYMENT_FIND_DYNAMIC(EMPLOYMENT, "findDynamic"),
	EMPLOYMENT_FIND_ALL(EMPLOYMENT, "findAll"),
	EMPLOYMENT_FIND_BY_ID(EMPLOYMENT, "findById"),
	EMPLOYMENT_ACCESS_VIEW(EMPLOYMENT, "accessView"),
	EMPLOYMENT_ADD_WORK_SHIFT(EMPLOYMENT, "addWorkShift"),
	EMPLOYMENT_REMOVE_WORK_SHIFT(EMPLOYMENT, "removeWorkShift"),

	ENTITY_ALL(ENTITY, "*"),
	ENTITY_EXISTS(ENTITY, "exists"),

	GLOBAL_DATA_ALL(GLOBAL_DATA, "*"),
	GLOBAL_DATA_GET_SALARIES(GLOBAL_DATA, "getSalaries"),
	GLOBAL_DATA_GET_MIN_TIME_PRESENT(GLOBAL_DATA, "getMinTimePresent"),
	GLOBAL_DATA_GET_MINIMUM_WAGE(GLOBAL_DATA, "getMinimumWage"),
	GLOBAL_DATA_SET_SALARY(GLOBAL_DATA, "setSalary"),
	GLOBAL_DATA_SET_MIN_TIME_PRESENT(GLOBAL_DATA, "setMinTimePresent"),
	GLOBAL_DATA_SET_MINIMUM_WAGE(GLOBAL_DATA, "setMinimumWage"),
	GLOBAL_DATA_GET_WAREHOUSE(GLOBAL_DATA, "getWarehouse"),
	GLOBAL_DATA_SET_WAREHOUSE(GLOBAL_DATA, "setWarehouse"),
	GLOBAL_DATA_GET_INSTANCE(GLOBAL_DATA, "getInstance"),
	GLOBAL_DATA_GET_TRANSACTION_LIMIT(GLOBAL_DATA, "getTransactionLimit"),
	GLOBAL_DATA_SET_TRANSACTION_LIMIT(GLOBAL_DATA, "setTransactionLimit"),
	GLOBAL_DATA_GET_CURRENCY_SYMBOL(GLOBAL_DATA, "getCurrencySymbol"),
	GLOBAL_DATA_SET_CURRENCY_SYMBOL(GLOBAL_DATA, "setCurrencySymbol"),
	GLOBAL_DATA_GET_MAX_IDLE_TIME(GLOBAL_DATA, "getMaxIdleTime"),
	GLOBAL_DATA_SET_MAX_IDLE_TIME(GLOBAL_DATA, "setMaxIdleTime"),
	GLOBAL_DATA_GET_MAX_USER_IMAGE_WIDTH(GLOBAL_DATA, "getMaxUserImageWidth"),
	GLOBAL_DATA_SET_MAX_USER_IMAGE_WIDTH(GLOBAL_DATA, "setMaxUserImageWidth"),
	GLOBAL_DATA_GET_MAX_USER_IMAGE_HEIGHT(GLOBAL_DATA, "getMaxUserImageHeight"),
	GLOBAL_DATA_SET_MAX_USER_IMAGE_HEIGHT(GLOBAL_DATA, "setMaxUserImageHeight"),
	GLOBAL_DATA_GET_DEFAULT_UI_THEME(GLOBAL_DATA, "getDefaultUITheme"),
	GLOBAL_DATA_SET_DEFAULT_UI_THEME(GLOBAL_DATA, "setDefaultUITheme"),
	GLOBAL_DATA_GET_UI_THEMES(GLOBAL_DATA, "getUIThemes"),
	GLOBAL_DATA_ADD_UI_THEME(GLOBAL_DATA, "addUITheme"),
	GLOBAL_DATA_REMOVE_UI_THEME(GLOBAL_DATA, "removeUITheme"),
	GLOBAL_DATA_GET_MAX_USER_IMAGE_UPLOAD_SIZE_BYTES(GLOBAL_DATA, "getMaxUserImageUploadSizeBytes"),
	GLOBAL_DATA_SET_MAX_USER_IMAGE_UPLOAD_SIZE_BYTES(GLOBAL_DATA, "setMaxUserImageUploadSizeBytes"),
	GLOBAL_DATA_GET_OPTIMAL_CIVIL_MANAGER_COUNT(GLOBAL_DATA, "getOptimalCivilManagerCount"),
	GLOBAL_DATA_SET_OPTIMAL_CIVIL_MANAGER_COUNT(GLOBAL_DATA, "setOptimalCivilManagerCount"),
	GLOBAL_DATA_GET_MIN_CIVIL_MANAGER_SCHOOL_GRADE(GLOBAL_DATA, "getMinCivilManagerSchoolGrade"),
	GLOBAL_DATA_SET_MIN_CIVIL_MANAGER_SCHOOL_GRADE(GLOBAL_DATA, "setMinCivilManagerSchoolGrade"),
	GLOBAL_DATA_ACCESS_VIEW(GLOBAL_DATA, "accessView"),

	ITEM_ALL(ITEM, "*"),
	ITEM_SELL(ITEM, "sell"),
	ITEM_FIND_ALL(ITEM, "findAll"),
	ITEM_FIND_BY_ID(ITEM, "findById"),
	ITEM_FIND_DYNAMIC(ITEM, "findDynamic"),
	ITEM_SET_NAME(ITEM, "setName"),
	ITEM_SET_DESCRIPTION(ITEM, "setDescription"),
	ITEM_SET_PRICE_PER_ITEM(ITEM, "setPricePerItem"),
	ITEM_ADD_ITEMS_AVAIBLE(ITEM, "addItemsAvaible"),
	ITEM_SUB_ITEMS_AVAIBLE(ITEM, "subItemsAvaible"),
	ITEM_CREATE(ITEM, "create"),
	ITEM_PRINT(ITEM, "print"),
	ITEM_EXPORT(ITEM, "export"),

	USER_ALL(USER, "*"),
	USER_CREATE_PUPIL(USER, "createPupil"),
	USER_CREATE_TEACHER(USER, "createTeacher"),
	USER_CREATE_GUEST(USER, "createGuest"),
	USER_IMPORT_FROM_CSV(USER, "importPupils"),
	USER_FIND_ALL(USER, "findAll"),
	USER_FIND_BY_ID(USER, "findById"),
	USER_FIND_DYNAMIC(USER, "findDynamic"),
	USER_SET_FORENAME(USER, "setForename"),
	USER_SET_SURNAME(USER, "setSurname"),
	USER_SET_SCHOOL_CLASS(USER, "setSchoolClass"),
	USER_ADD_RANK(USER, "addRank"),
	USER_REMOVE_RANK(USER, "removeRank"),
	USER_ADD_USER_CARD(USER, "addUserCard"),
	USER_REMOVE_USER_CARD(USER, "removeUserCard"),
	USER_BLOCK_USER_CARD(USER, "blockUserCard"),
	USER_UNBLOCK_USER_CARD(USER, "unblockUserCard"),
	USER_SET_VALID_DATE_USER_CARD(USER, "setValidDate"),
	USER_FIND_USER_CARD_BY_ID(USER, "findUserCardById"),
	USER_SET_IMAGE(USER, "setImage"),
	USER_GET_IMAGE_FROM_ID(USER, "getImageFromId"),
	USER_GET_USER_CARDS(USER, "getUserCards"),
	USER_ACCESS_VIEW(USER, "accessView"),
	USER_PRINT(USER, "print"),
	USER_EXPORT(USER, "export"),
	USER_SET_JOB_REQUESTS(USER, "setJobRequests"),

	LOGIN_ALL(LOGIN, "*"),
	LOGIN_CHANGE_PASSWORD(LOGIN, "changePassword"),
	LOGIN_SET_UI_THEME(LOGIN, "setUITheme"),
	LOGIN_ACCESS_PREFERENCES_VIEW(LOGIN, "accessPreferencesView"),

	LOGIN_USER_ALL(LOGIN_USER, "*"),
	LOGIN_USER_CREATE_RAW(LOGIN_USER, "createRaw"),
	LOGIN_USER_CREATE_REGISTERED(LOGIN_USER, "createRegistered"),
	LOGIN_USER_NEW_PASSWORD(LOGIN_USER, "newPassword"),
	LOGIN_USER_FIND_BY_ID(LOGIN_USER, "findById"),
	LOGIN_USER_FIND_BY_USERNAME(LOGIN_USER, "findByUsername"),
	LOGIN_USER_FIND_ALL(LOGIN_USER, "findAll"),
	LOGIN_USER_GET_ROLES(LOGIN_USER, "getRolesOfUser"),
	LOGIN_USER_FIND_DYNAMIC(LOGIN_USER, "findDynamic"),
	LOGIN_USER_ADD_ROLE(LOGIN_USER, "addRole"),
	LOGIN_USER_REMOVE_ROLE(LOGIN_USER, "removeRole"),
	LOGIN_USER_CHANGE_USERNAME(LOGIN_USER, "changeUsername"),
	LOGIN_USER_ACCESS_VIEW(LOGIN_USER, "accessView"),
	LOGIN_USER_PRINT(LOGIN_USER, "print"),
	LOGIN_USER_EXPORT(LOGIN_USER, "export"),

	LOGIN_ROLE_ALL(LOGIN_ROLE, "*"),
	LOGIN_ROLE_CREATE(LOGIN_ROLE, "create"),
	LOGIN_ROLE_SET_NAME(LOGIN_ROLE, "setName"),
	LOGIN_ROLE_ADD_PERMISSION(LOGIN_ROLE, "addPermission"),
	LOGIN_ROLE_REMOVE_PERMISSION(LOGIN_ROLE, "removePermission"),
	LOGIN_ROLE_FIND_BY_ID(LOGIN_ROLE, "findById"),
	LOGIN_ROLE_FIND_ALL(LOGIN_ROLE, "findAll"),
	LOGIN_ROLE_FIND_DYNAMIC(LOGIN_ROLE, "findDynamic"),
	LOGIN_ROLE_GET_PERMISSIONS(LOGIN_ROLE, "getPermissions"),
	LOGIN_ROLE_REMOVE(LOGIN_ROLE, "remove"),
	LOGIN_ROLE_ACCESS_VIEW(LOGIN_ROLE, "accessView"),
	LOGIN_ROLE_PRINT(LOGIN_ROLE, "print"),
	LOGIN_ROLE_EXPORT(LOGIN_ROLE, "export"),

	ROOM_ALL(ROOM, "*"),
	ROOM_CREATE(ROOM, "create"),
	ROOM_FIND_ALL(ROOM, "findAll"),
	ROOM_FIND_BY_ID(ROOM, "findById"),
	ROOM_FIND_DYNAMIC(ROOM, "findDynamic"),
	ROOM_SET_NAME(ROOM, "setName"),
	ROOM_ADD_SECTION(ROOM, "addSection"),
	ROOM_REMOVE_SECTION(ROOM, "removeSection"),
	ROOM_FIND_ROOM_SECTION_BY_ID(ROOM, "findRoomSectionById"),
	ROOM_ACCESS_VIEW(ROOM, "accessView"),
	ROOM_PRINT(ROOM, "print"),
	ROOM_EXPORT(ROOM, "export"),

	JOB_ALL(JOB, "*"),
	JOB_CREATE(JOB, "create"),
	JOB_FIND_BY_ID(JOB, "findById"),
	JOB_FIND_ALL(JOB, "findAll"),
	JOB_FIND_DYNAMIC(JOB, "findDynamic"),
	JOB_SET_NAME(JOB, "setName"),
	JOB_SET_DESCRIPTION(JOB, "setDescription"),
	JOB_SET_SALARY_CLASS(JOB, "setSalaryClass"),
	JOB_SET_OPTIMAL_SCHOOL_GRADE(JOB, "setOptimalSchoolGrade"),
	JOB_SET_REQUIRED_EMPLOYMENT_COUNT(JOB, "setRequiredEmployeesCount"),
	JOB_SET_EMPLOYEE_POSITION(JOB, "setEmployeePosition"),
	JOB_DELETE(JOB, "deleteJob"),
	JOB_ACCESS_VIEW(JOB, "accessView"),
	JOB_PRINT(JOB, "print"),
	JOB_EXPORT(JOB, "export");

	private String permissionString;

	private EnumPermission(EnumPermissionModule module, String permissionString) {
		this.permissionString = (module != null ? module.getModuleName() + ":" : "") + permissionString;
	}

	public String getPermissionString() {
		return permissionString;
	}

	public static String[] getPermissionStringsFromArray(EnumPermission[] permissions) {
		ArrayList<String> list = new ArrayList<>();
		for (EnumPermission permission : permissions) {
			list.add(permission.permissionString);
		}
		return list.toArray(new String[list.size()]);
	}

	@Override
	public String toString() {
		return permissionString;
	}

}