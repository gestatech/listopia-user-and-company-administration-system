package florian_haas.lucas.web;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.nio.charset.StandardCharsets;
import java.time.*;
import java.util.*;

import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.imageio.ImageIO;
import javax.inject.Named;
import javax.validation.constraints.*;

import org.hibernate.validator.constraints.NotBlank;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.*;

import florian_haas.lucas.business.*;
import florian_haas.lucas.model.*;
import florian_haas.lucas.persistence.*;
import florian_haas.lucas.security.EnumPermission;
import florian_haas.lucas.util.Utils;
import florian_haas.lucas.validation.*;
import florian_haas.lucas.web.converter.*;
import florian_haas.lucas.web.exporter.IdCardExporter;
import florian_haas.lucas.web.util.WebUtils;

@Named
@ViewScoped
public class UserBean extends BaseBean<ReadOnlyUser> {

	public UserBean() {
		super(BASE_NAME, 7);
	}

	public static final String BASE_NAME = "user";

	private static final long serialVersionUID = -2324504686340886417L;

	@EJB
	private UserBeanLocal userBean;

	@EJB
	private LoginBeanLocal loginBean;

	@EJB
	private EntityBeanLocal entityBean;

	@EJB
	private JobBeanLocal jobBean;

	@EJB
	private GlobalDataBeanLocal globalDataBean;

	/*
	 * -------------------- Create Pupil Dialog Start --------------------
	 */

	@NotBlank
	private String createPupilDialogForename = null;

	@NotBlank
	private String createPupilDialogSurname = null;

	@NotNull
	private EnumSchoolClass createPupilDialogSchoolClass = EnumSchoolClass.A5;

	@NotBlank
	private String createPupilDialogTmpRank = null;

	private String createPupilDialogSelectedRank = null;

	private List<@TypeNotNull @NotBlankString String> createPupilDialogRanks = new ArrayList<>();

	public String getCreatePupilDialogForename() {
		return createPupilDialogForename;
	}

	public void setCreatePupilDialogForename(String createPupilDialogForename) {
		this.createPupilDialogForename = createPupilDialogForename;
	}

	public String getCreatePupilDialogSurname() {
		return createPupilDialogSurname;
	}

	public void setCreatePupilDialogSurname(String createPupilDialogSurname) {
		this.createPupilDialogSurname = createPupilDialogSurname;
	}

	public String getCreatePupilDialogTmpRank() {
		return createPupilDialogTmpRank;
	}

	public void setCreatePupilDialogTmpRank(String createPupilDialogTmpRank) {
		this.createPupilDialogTmpRank = createPupilDialogTmpRank;
	}

	public List<@TypeNotNull @NotBlankString String> getCreatePupilDialogRanks() {
		return createPupilDialogRanks;
	}

	public void setCreatePupilDialogRanks(List<String> createPupilDialogRanks) {
		this.createPupilDialogRanks = createPupilDialogRanks;
	}

	public void resetCreatePupilDialogTmpRank() {
		createPupilDialogTmpRank = null;
	}

	public String getCreatePupilDialogSelectedRank() {
		return createPupilDialogSelectedRank;
	}

	public void setCreatePupilDialogSelectedRank(String createPupilDialogSelectedRank) {
		this.createPupilDialogSelectedRank = createPupilDialogSelectedRank;
	}

	public EnumSchoolClass getCreatePupilDialogSchoolClass() {
		return createPupilDialogSchoolClass;
	}

	public void setCreatePupilDialogSchoolClass(EnumSchoolClass createPupilDialogSchoolClass) {
		this.createPupilDialogSchoolClass = createPupilDialogSchoolClass;
	}

	public void createPupil() {
		WebUtils.executeTask((params) -> {
			params.add(WebUtils.getAsString(userBean.findById(
					userBean.createPupil(createPupilDialogForename, createPupilDialogSurname, createPupilDialogSchoolClass, createPupilDialogRanks)),
					UserConverter.CONVERTER_ID));
			return true;
		}, "lucas.application.userScreen.createPupil.message");
	}

	public void resetCreatePupilDialog() {
		createPupilDialogSchoolClass = EnumSchoolClass.A5;
		createPupilDialogRanks.clear();
	}

	public void editCreatePupilDialogSelectedRank() {
		if (createPupilDialogSelectedRank != null) {
			this.createPupilDialogRanks.remove(createPupilDialogSelectedRank);
			this.createPupilDialogTmpRank = createPupilDialogSelectedRank;
			createPupilDialogSelectedRank = null;
		}
	}

	public void removeCreatePupilDialogSelectedRank() {
		this.createPupilDialogRanks.remove(createPupilDialogSelectedRank);
		this.createPupilDialogTmpRank = null;
		this.createPupilDialogSelectedRank = null;
	}

	/*
	 * -------------------- Create Pupil Dialog End --------------------
	 */

	/*
	 * -------------------- Create Teacher Dialog Start --------------------
	 */

	@NotBlank
	private String createTeacherDialogForename = null;

	@NotBlank
	private String createTeacherDialogSurname = null;

	@NotBlank
	private String createTeacherDialogTmpRank = null;

	private String createTeacherDialogSelectedRank = null;

	private List<@TypeNotNull @NotBlankString String> createTeacherDialogRanks = new ArrayList<>();

	public String getCreateTeacherDialogForename() {
		return createTeacherDialogForename;
	}

	public void setCreateTeacherDialogForename(String createTeacherDialogForename) {
		this.createTeacherDialogForename = createTeacherDialogForename;
	}

	public String getCreateTeacherDialogSurname() {
		return createTeacherDialogSurname;
	}

	public void setCreateTeacherDialogSurname(String createTeacherDialogSurname) {
		this.createTeacherDialogSurname = createTeacherDialogSurname;
	}

	public String getCreateTeacherDialogTmpRank() {
		return createTeacherDialogTmpRank;
	}

	public void setCreateTeacherDialogTmpRank(String createTeacherDialogTmpRank) {
		this.createTeacherDialogTmpRank = createTeacherDialogTmpRank;
	}

	public String getCreateTeacherDialogSelectedRank() {
		return createTeacherDialogSelectedRank;
	}

	public void setCreateTeacherDialogSelectedRank(String createTeacherDialogSelectedRank) {
		this.createTeacherDialogSelectedRank = createTeacherDialogSelectedRank;
	}

	public List<@TypeNotNull @NotBlankString String> getCreateTeacherDialogRanks() {
		return createTeacherDialogRanks;
	}

	public void setCreateTeacherDialogRanks(List<@TypeNotNull @NotBlankString String> createTeacherDialogRanks) {
		this.createTeacherDialogRanks = createTeacherDialogRanks;
	}

	public void resetCreateTeacherDialogTmpRank() {
		createTeacherDialogTmpRank = null;
	}

	public void createTeacher() {
		WebUtils.executeTask((params) -> {
			params.add(WebUtils.getAsString(
					userBean.findById(userBean.createTeacher(createTeacherDialogForename, createTeacherDialogSurname, createTeacherDialogRanks)),
					UserConverter.CONVERTER_ID));
			return true;
		}, "lucas.application.userScreen.createTeacher.message");
	}

	public void resetCreateTeacherDialog() {
		createTeacherDialogRanks.clear();
	}

	public void editCreateTeacherDialogSelectedRank() {
		if (createTeacherDialogSelectedRank != null) {
			this.createTeacherDialogRanks.remove(createTeacherDialogSelectedRank);
			this.createTeacherDialogTmpRank = createTeacherDialogSelectedRank;
			createTeacherDialogSelectedRank = null;
		}
	}

	public void removeCreateTeacherDialogSelectedRank() {
		this.createTeacherDialogRanks.remove(createTeacherDialogSelectedRank);
		this.createTeacherDialogTmpRank = null;
		this.createTeacherDialogSelectedRank = null;
	}

	/*
	 * -------------------- Create Pupil Dialog End --------------------
	 */

	/*
	 * -------------------- Create Guest Dialog Start --------------------
	 */

	@NotNull
	@Min(1)
	@Max(250)
	private Integer createGuestDialogGuestCount = 1;

	public Integer getCreateGuestDialogGuestCount() {
		return createGuestDialogGuestCount;
	}

	public void setCreateGuestDialogGuestCount(Integer createGuestDialogGuestCount) {
		this.createGuestDialogGuestCount = createGuestDialogGuestCount;
	}

	public void createGuests() {
		int createdGuests = 0;
		for (int i = 0; i < createGuestDialogGuestCount; i++) {
			if (WebUtils.executeTask((params) -> {
				userBean.createGuest();
				return true;
			}, false, false, "lucas.application.userScreen.createGuest.message")) {
				createdGuests++;
			}
		}
		WebUtils.addDefaultTranslatedInformationMessage("lucas.application.userScreen.createGuest.message.success", createdGuests);
	}

	/*
	 * -------------------- Create Guest Dialog End --------------------
	 */

	/*
	 * -------------------- Import Pupils Dialog Start --------------------
	 */

	@NotBlank
	private String importUsersDialogDelim = ";";

	private byte[] importUsersDialogCSV;

	public static final String IMPORT_PUPILS_DIALOG_MESSAGES_COMPONENT_ID = "importUsersDialogMessages";

	public String getImportUsersDialogDelim() {
		return this.importUsersDialogDelim;
	}

	public void setImportUsersDialogDelim(String importUsersDialogDelim) {
		this.importUsersDialogDelim = importUsersDialogDelim;
	}

	public byte[] getImportUsersDialogCSV() {
		return this.importUsersDialogCSV;
	}

	public void onImportUsersCSVUpload(FileUploadEvent event) {
		UploadedFile file = event.getFile();
		WebUtils.executeTask(params -> {
			importUsersDialogCSV = event.getFile().getContents();
			return true;
		}, "lucas.application.userScreen.uploadUsersCSV.message", IMPORT_PUPILS_DIALOG_MESSAGES_COMPONENT_ID, Utils.asList(file.getFileName()));
	}

	public void initImportUsersDialogCSV() {
		importUsersDialogCSV = null;
		importUsersDialogDelim = ";";
	}

	public void importUsersDialogCSV() {
		WebUtils.executeTask(params -> {
			int[] results = createUsersFromCSV(new String(importUsersDialogCSV, StandardCharsets.UTF_8), importUsersDialogDelim);
			int created = results[0];
			int failed = results[1];
			params.add(created);
			if (failed > 0) {
				WebUtils.addDefaultTranslatedErrorMessage("lucas.application.userScreen.importUsers.message.creationFailed", failed);
			}
			return created > 0;
		}, "lucas.application.userScreen.importUsers.message");
	}

	private int[] createUsersFromCSV(String content, String delim) throws Exception {
		int[] ret = new int[] {
				0, 0 };
		List<List<String>> parsedCSV = WebUtils.parseCSV(content, delim);
		for (List<String> line : parsedCSV) {
			if (line.size() >= 2) {
				String forename = line.get(0).trim();
				String surname = line.get(1).trim();
				if (line.size() >= 4) {
					String unparsedSchoolGrade = line.get(2).trim();
					String unparsedSchoolClass = line.get(3).trim().toUpperCase();
					EnumSchoolClass schoolClass = EnumSchoolClass.valueOf(unparsedSchoolClass.concat(unparsedSchoolGrade));
					if (forename != null && surname != null && schoolClass != null) {
						try {
							userBean.createPupil(forename, surname, schoolClass, null);
							ret[0] += 1;
						}
						catch (Exception e) {
							ret[1] += 1;
						}
					}
				} else {
					try {
						userBean.createTeacher(forename, surname, null);
						ret[0] += 1;
					}
					catch (Exception e) {
						ret[1] += 1;
					}
				}

			}
		}
		return ret;
	}

	/*
	 * -------------------- Import Pupils Dialog End --------------------
	 */

	@NotNull
	private Boolean useSearchUserId = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC)
	private EnumQueryComparator searchUserIdComparator = EnumQueryComparator.EQUAL;

	@NotNull
	@Min(0)
	private Long searchUserId = 0l;

	@NotNull
	private Boolean useSearchUserForename = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.TEXT)
	private EnumQueryComparator searchUserForenameComparator = EnumQueryComparator.EQUAL;

	private String searchUserForename;

	@NotNull
	private Boolean useSearchUserSurname = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.TEXT)
	private EnumQueryComparator searchUserSurnameComparator = EnumQueryComparator.EQUAL;

	private String searchUserSurname;

	@NotNull
	private Boolean useSearchUserType = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.LOGIC)
	private EnumQueryComparator searchUserTypeComparator = EnumQueryComparator.EQUAL;

	@NotNull
	private EnumUserType searchUserType = EnumUserType.PUPIL;

	@NotNull
	private Boolean useSearchUserSchoolGrade = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC)
	private EnumQueryComparator searchUserSchoolGradeComparator = EnumQueryComparator.EQUAL;

	@NotNull
	@Min(0)
	private Integer searchUserSchoolGrade = EnumSchoolClass.A5.getGrade();

	@NotNull
	private Boolean useSearchUserSchoolClass = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC)
	private EnumQueryComparator searchUserSchoolClassComparator = EnumQueryComparator.EQUAL;

	@NotBlank
	private String searchUserSchoolClass = EnumSchoolClass.A5.getSchoolClass();

	@NotNull
	private Boolean useSearchUserRanks = Boolean.FALSE;

	private List<@TypeNotNull @NotBlankString String> searchUserRanks = new ArrayList<>();

	@QueryComparator(category = EnumQueryComparatorCategory.ARRAY)
	private EnumQueryComparator searchUserRanksComparator = EnumQueryComparator.MEMBER_OF;

	@NotNull
	@Min(0)
	private Integer searchUserEmploymentsCount = 0;

	@NotNull
	private Boolean useSearchUserEmploymentsCount = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC)
	private EnumQueryComparator searchUserEmploymentsCountComparator = EnumQueryComparator.EQUAL;

	@NotNull
	private ReadOnlyEmployment searchUserEmployment = null;

	@NotNull
	private Boolean useSearchUserEmployment = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.LOGIC)
	private EnumQueryComparator searchUserEmploymentComparator = EnumQueryComparator.EQUAL;

	public Long getSearchUserId() {
		return searchUserId;
	}

	public void setSearchUserId(Long searchUserId) {
		this.searchUserId = searchUserId;
	}

	public Boolean getUseSearchUserId() {
		return useSearchUserId;
	}

	public void setUseSearchUserId(Boolean useSearchUserId) {
		this.useSearchUserId = useSearchUserId;
	}

	public String getSearchUserForename() {
		return searchUserForename;
	}

	public void setSearchUserForename(String searchUserForename) {
		this.searchUserForename = searchUserForename;
	}

	public String getSearchUserSurname() {
		return searchUserSurname;
	}

	public void setSearchUserSurname(String searchUserSurname) {
		this.searchUserSurname = searchUserSurname;
	}

	public Boolean getUseSearchUserForename() {
		return useSearchUserForename;
	}

	public void setUseSearchUserForename(Boolean useSearchUserForename) {
		this.useSearchUserForename = useSearchUserForename;
	}

	public Boolean getUseSearchUserSurname() {
		return useSearchUserSurname;
	}

	public void setUseSearchUserSurname(Boolean useSearchUserSurname) {
		this.useSearchUserSurname = useSearchUserSurname;
	}

	public EnumQueryComparator getSearchUserIdComparator() {
		return searchUserIdComparator;
	}

	public void setSearchUserIdComparator(EnumQueryComparator searchUserIdComparator) {
		this.searchUserIdComparator = searchUserIdComparator;
	}

	public EnumQueryComparator getSearchUserForenameComparator() {
		return searchUserForenameComparator;
	}

	public void setSearchUserForenameComparator(EnumQueryComparator searchUserForenameComparator) {
		this.searchUserForenameComparator = searchUserForenameComparator;
	}

	public EnumQueryComparator getSearchUserSurnameComparator() {
		return searchUserSurnameComparator;
	}

	public void setSearchUserSurnameComparator(EnumQueryComparator searchUserSurnameComparator) {
		this.searchUserSurnameComparator = searchUserSurnameComparator;
	}

	public Boolean getUseSearchUserType() {
		return useSearchUserType;
	}

	public void setUseSearchUserType(Boolean useSearchUserType) {
		this.useSearchUserType = useSearchUserType;
	}

	public EnumQueryComparator getSearchUserTypeComparator() {
		return searchUserTypeComparator;
	}

	public void setSearchUserTypeComparator(EnumQueryComparator searchUserTypeComparator) {
		this.searchUserTypeComparator = searchUserTypeComparator;
	}

	public EnumUserType getSearchUserType() {
		return searchUserType;
	}

	public void setSearchUserType(EnumUserType searchUserType) {
		this.searchUserType = searchUserType;
	}

	public Boolean getUseSearchUserSchoolGrade() {
		return this.useSearchUserSchoolGrade;
	}

	public void setUseSearchUserSchoolGrade(Boolean useSearchUserSchoolGrade) {
		this.useSearchUserSchoolGrade = useSearchUserSchoolGrade;
	}

	public EnumQueryComparator getSearchUserSchoolGradeComparator() {
		return this.searchUserSchoolGradeComparator;
	}

	public void setSearchUserSchoolGradeComparator(EnumQueryComparator searchUserSchoolGradeComparator) {
		this.searchUserSchoolGradeComparator = searchUserSchoolGradeComparator;
	}

	public Integer getSearchUserSchoolGrade() {
		return this.searchUserSchoolGrade;
	}

	public void setSearchUserSchoolGrade(Integer searchUserSchoolGrade) {
		this.searchUserSchoolGrade = searchUserSchoolGrade;
	}

	public Boolean getUseSearchUserSchoolClass() {
		return this.useSearchUserSchoolClass;
	}

	public void setUseSearchUserSchoolClass(Boolean useSearchUserSchoolClass) {
		this.useSearchUserSchoolClass = useSearchUserSchoolClass;
	}

	public EnumQueryComparator getSearchUserSchoolClassComparator() {
		return this.searchUserSchoolClassComparator;
	}

	public void setSearchUserSchoolClassComparator(EnumQueryComparator searchUserSchoolClassComparator) {
		this.searchUserSchoolClassComparator = searchUserSchoolClassComparator;
	}

	public String getSearchUserSchoolClass() {
		return this.searchUserSchoolClass;
	}

	public void setSearchUserSchoolClass(String searchUserSchoolClass) {
		this.searchUserSchoolClass = searchUserSchoolClass;
	}

	public Boolean getUseSearchUserRanks() {
		return useSearchUserRanks;
	}

	public void setUseSearchUserRanks(Boolean useSearchUserRanks) {
		this.useSearchUserRanks = useSearchUserRanks;
	}

	public List<@TypeNotNull @NotBlankString String> getSearchUserRanks() {
		return searchUserRanks;
	}

	public void setSearchUserRanks(List<@TypeNotNull @NotBlankString String> searchUserRanks) {
		this.searchUserRanks = searchUserRanks;
	}

	public EnumQueryComparator getSearchUserRanksComparator() {
		return searchUserRanksComparator;
	}

	public void setSearchUserRanksComparator(EnumQueryComparator searchUserRanksComparator) {
		this.searchUserRanksComparator = searchUserRanksComparator;
	}

	public Integer getSearchUserEmploymentsCount() {
		return this.searchUserEmploymentsCount;
	}

	public void setSearchUserEmploymentsCount(Integer searchUserEmploymentsCount) {
		this.searchUserEmploymentsCount = searchUserEmploymentsCount;
	}

	public Boolean getUseSearchUserEmploymentsCount() {
		return this.useSearchUserEmploymentsCount;
	}

	public void setUseSearchUserEmploymentsCount(Boolean useSearchUserEmploymentsCount) {
		this.useSearchUserEmploymentsCount = useSearchUserEmploymentsCount;
	}

	public EnumQueryComparator getSearchUserEmploymentsCountComparator() {
		return this.searchUserEmploymentsCountComparator;
	}

	public void setSearchUserEmploymentsCountComparator(EnumQueryComparator searchUserEmploymentsCountComparator) {
		this.searchUserEmploymentsCountComparator = searchUserEmploymentsCountComparator;
	}

	public ReadOnlyEmployment getSearchUserEmployment() {
		return this.searchUserEmployment;
	}

	public void setSearchUserEmployment(ReadOnlyEmployment searchUserEmployment) {
		this.searchUserEmployment = searchUserEmployment;
	}

	public Boolean getUseSearchUserEmployment() {
		return this.useSearchUserEmployment;
	}

	public void setUseSearchUserEmployment(Boolean useSearchUserEmployment) {
		this.useSearchUserEmployment = useSearchUserEmployment;
	}

	public EnumQueryComparator getSearchUserEmploymentComparator() {
		return this.searchUserEmploymentComparator;
	}

	public void setSearchUserEmploymentComparator(EnumQueryComparator searchUserEmploymentComparator) {
		this.searchUserEmploymentComparator = searchUserEmploymentComparator;
	}

	@Override
	public EnumPermission getFindDynamicPermission() {
		return EnumPermission.USER_FIND_DYNAMIC;
	}

	@Override
	public EnumPermission getPrintPermission() {
		return EnumPermission.USER_PRINT;
	}

	@Override
	public EnumPermission getExportPermission() {
		return EnumPermission.USER_EXPORT;
	}

	@Override
	protected List<? extends ReadOnlyUser> searchEntities() {
		return userBean.findUsers(searchUserId, searchUserForename, searchUserSurname,
				EnumSchoolClass.getMatchingClasses(useSearchUserSchoolGrade ? searchUserSchoolGrade : null,
						useSearchUserSchoolClass ? searchUserSchoolClass : null, searchUserSchoolGradeComparator, searchUserSchoolClassComparator),
				searchUserType, searchUserRanks, searchUserEmploymentsCount, searchUserEmployment != null ? searchUserEmployment.getId() : null,
				useSearchUserId, useSearchUserForename, useSearchUserSurname, useSearchUserSchoolGrade || useSearchUserSchoolClass, useSearchUserType,
				useSearchUserRanks, useSearchUserEmploymentsCount, useSearchUserEmployment, searchUserIdComparator, searchUserForenameComparator,
				searchUserSurnameComparator, searchUserTypeComparator, searchUserRanksComparator, searchUserEmploymentsCountComparator,
				searchUserEmploymentComparator);
	}

	@Override
	protected ReadOnlyUser entityGetter(Long entityId) {
		return userBean.findById(entityId);
	}

	/*
	 * -------------------- Edit User Dialog Start --------------------
	 */

	@NotBlankString
	private String editUserDialogForename;

	@NotBlankString
	private String editUserDialogSurname;

	private EnumSchoolClass editUserDialogSchoolClass;

	@NotBlank
	private String editUserDialogTmpRank = null;

	private String editUserDialogSelectedRank = null;

	private List<@TypeNotNull @NotBlankString String> editUserDialogRanks = new ArrayList<>();

	private ReadOnlyUser editUserDialogSelectedUser;

	public String getEditUserDialogForename() {
		return editUserDialogForename;
	}

	public void setEditUserDialogForename(String editUserDialogForename) {
		this.editUserDialogForename = editUserDialogForename;
	}

	public String getEditUserDialogSurname() {
		return editUserDialogSurname;
	}

	public void setEditUserDialogSurname(String editUserDialogSurname) {
		this.editUserDialogSurname = editUserDialogSurname;
	}

	public EnumSchoolClass getEditUserDialogSchoolClass() {
		return editUserDialogSchoolClass;
	}

	public void setEditUserDialogSchoolClass(EnumSchoolClass editUserDialogSchoolClass) {
		this.editUserDialogSchoolClass = editUserDialogSchoolClass;
	}

	public String getEditUserDialogTmpRank() {
		return editUserDialogTmpRank;
	}

	public void setEditUserDialogTmpRank(String editUserDialogTmpRank) {
		this.editUserDialogTmpRank = editUserDialogTmpRank;
	}

	public void resetEditUserDialogTmpRank() {
		editUserDialogTmpRank = null;
	}

	public String getEditUserDialogSelectedRank() {
		return editUserDialogSelectedRank;
	}

	public void setEditUserDialogSelectedRank(String editUserDialogSelectedRank) {
		this.editUserDialogSelectedRank = editUserDialogSelectedRank;
	}

	public List<@TypeNotNull @NotBlankString String> getEditUserDialogRanks() {
		return editUserDialogRanks;
	}

	public void setEditUserDialogRanks(List<@TypeNotNull @NotBlankString String> editUserDialogRanks) {
		this.editUserDialogRanks = editUserDialogRanks;
	}

	public void editEditUserDialogSelectedRank() {
		if (editUserDialogSelectedRank != null) {
			this.editUserDialogRanks.remove(editUserDialogSelectedRank);
			this.editUserDialogTmpRank = editUserDialogSelectedRank;
			editUserDialogSelectedRank = null;
		}
	}

	public void removeEditUserDialogSelectedRank() {
		this.editUserDialogRanks.remove(editUserDialogSelectedRank);
		this.editUserDialogTmpRank = null;
		this.editUserDialogSelectedRank = null;
	}

	public EnumUserType computeUserType() {
		return (editUserDialogForename == null || editUserDialogSurname == null ? EnumUserType.GUEST
				: (editUserDialogSchoolClass == null ? EnumUserType.TEACHER : EnumUserType.PUPIL));
	}

	public void initEditUserDialog() {
		if (!this.selectedEntities.isEmpty()) {
			editUserDialogSelectedUser = this.selectedEntities.get(0);
			this.editUserDialogForename = editUserDialogSelectedUser.getForename();
			this.editUserDialogSurname = editUserDialogSelectedUser.getSurname();
			this.editUserDialogSchoolClass = editUserDialogSelectedUser.getSchoolClass();
			this.editUserDialogRanks.clear();
			this.editUserDialogRanks.addAll(editUserDialogSelectedUser.getRanks());
			this.editUserDialogTmpRank = null;
			this.editUserDialogSelectedRank = null;
		}
	}

	public void editUser() {
		if (editUserDialogSelectedUser != null) {
			WebUtils.executeTask(params -> {
				Long id = editUserDialogSelectedUser.getId();
				if (WebUtils.isPermitted(EnumPermission.USER_SET_FORENAME)) {
					userBean.setForename(id, editUserDialogForename);
				}
				if (WebUtils.isPermitted(EnumPermission.USER_SET_SURNAME)) {
					userBean.setSurname(id, editUserDialogSurname);
				}
				if (WebUtils.isPermitted(EnumPermission.USER_SET_SCHOOL_CLASS)) {
					userBean.setSchoolClass(id, editUserDialogSchoolClass);
				}
				ReadOnlyUser tmp = userBean.findById(id);
				if (WebUtils.isPermitted(EnumPermission.USER_ADD_RANK)) {
					editUserDialogRanks.forEach(rank -> {
						if (!tmp.getRanks().contains(rank)) {
							userBean.addRank(id, rank);
						}
					});
				}
				if (WebUtils.isPermitted(EnumPermission.USER_REMOVE_RANK)) {
					tmp.getRanks().forEach(rank -> {
						if (!editUserDialogRanks.contains(rank)) {
							userBean.removeRank(id, rank);
						}
					});
				}
				ReadOnlyUser tmp2 = userBean.findById(id);
				params.add(WebUtils.getAsString(tmp, UserConverter.CONVERTER_ID));
				WebUtils.refreshEntities(ReadOnlyUser.class, searchResults, selectedEntities, tmp2, userBean::findById, true);
				return true;
			}, "lucas.application.userScreen.editUser.message");
		}
	}

	/*
	 * -------------------- Edit User Dialog End --------------------
	 */

	/*
	 * -------------------- Image Manager Dialog Start --------------------
	 */

	private String imageManagerDialogDisplayImageAsBase64 = null;

	private byte[] imageManagerDialogUploadedImage = null;

	private ReadOnlyUser imageManagerDialogSelectedUser;

	public static final String IMAGE_DIALOG_MESSAGES_ID = "imageManagerDialogMessages";

	public Long getImageManagerDialogCurrentUserId() {
		return imageManagerDialogSelectedUser.getId();
	}

	public String getImageManagerDialogDisplayImageAsBase64() {
		return this.imageManagerDialogDisplayImageAsBase64;
	}

	public ReadOnlyUser getImageManagerDialogSelectedUser() {
		return this.imageManagerDialogSelectedUser;
	}

	public Integer getMaxUserImageWidth() {
		return globalDataBean.getMaxUserImageWidth();
	}

	public Integer getMaxUserImageHeight() {
		return globalDataBean.getMaxUserImageHeight();
	}

	public Long getMaxUserImageUploadSizeBytes() {
		return globalDataBean.getMaxUserImageUploadSizeBytes();
	}

	public void initImageManagerDialog() {
		if (!this.selectedEntities.isEmpty()) {
			imageManagerDialogSelectedUser = this.selectedEntities.get(0);
			imageManagerDialogUploadedImage = null;
			byte[] currentImage = userBean.getImage(imageManagerDialogSelectedUser.getId());
			imageManagerDialogDisplayImageAsBase64 = currentImage != null ? Base64.getEncoder().encodeToString(currentImage) : null;
		}
	}

	public void onImageUpload(FileUploadEvent event) {
		final UploadedFile file = event.getFile();
		WebUtils.executeTask(params -> {
			if (file.getContentType().equals(WebUtils.JPEG_MIME)) {
				imageManagerDialogUploadedImage = file.getContents();
				BufferedImage image = WebUtils.getBufferedImageFromBytes(imageManagerDialogUploadedImage);
				image = WebUtils.getBufferedImage(
						image.getScaledInstance(getMaxUserImageWidth(), getMaxUserImageHeight(), BufferedImage.SCALE_SMOOTH),
						BufferedImage.TYPE_INT_RGB);
				imageManagerDialogUploadedImage = WebUtils.convertBufferedImageToBytes(image, WebUtils.JPEG_TYPE);
				imageManagerDialogDisplayImageAsBase64 = Base64.getEncoder().encodeToString(imageManagerDialogUploadedImage);
				return true;
			}
			return false;
		}, "lucas.application.userScreen.uploadUserImage.message", IMAGE_DIALOG_MESSAGES_ID, Utils.asList(file.getFileName()));
	}

	public void removeImage() {
		WebUtils.executeTask((params) -> {
			imageManagerDialogUploadedImage = null;
			imageManagerDialogDisplayImageAsBase64 = null;
			return true;
		}, "lucas.application.userScreen.removeUserImage.message", IMAGE_DIALOG_MESSAGES_ID,
				Utils.asList(WebUtils.getAsString(imageManagerDialogSelectedUser, UserConverter.CONVERTER_ID)));
	}

	public void onSave() {
		WebUtils.executeTask(params -> {
			return userBean.setImage(imageManagerDialogSelectedUser.getId(), imageManagerDialogUploadedImage);
		}, "lucas.application.userScreen.changeUserImage.message",
				Utils.asList(WebUtils.getAsString(imageManagerDialogSelectedUser, UserConverter.CONVERTER_ID)));
	}

	/*
	 * -------------------- Image Manager Dialog End --------------------
	 */

	/*
	 * -------------------- User Card Manager Dialog Start --------------------
	 */

	@EJB
	private IdCardBeanLocal idCardBean;

	private List<ReadOnlyUser> userCardManagerDialogSelectedUsers = new ArrayList<>();

	private List<ReadOnlyIdCard> userCardManagerDialogSelectedUserCards = new ArrayList<>();

	private List<ReadOnlyIdCard> userCardManagerDialogUserCards = new ArrayList<>();

	private Date userCardManagerDialogValidDate = Date.from(Instant.now());

	public static final String USER_CARD_MANAGER_DIALOG_MESSAGES_ID = "userCardManagerDialogMessages";

	public List<ReadOnlyIdCard> getUserCardManagerDialogUserCards() {
		return this.userCardManagerDialogUserCards;
	}

	public List<ReadOnlyIdCard> getUserCardManagerDialogSelectedUserCards() {
		return this.userCardManagerDialogSelectedUserCards;
	}

	public void setUserCardManagerDialogSelectedUserCards(List<ReadOnlyIdCard> userCardManagerDialogSelectedUserCards) {
		this.userCardManagerDialogSelectedUserCards = userCardManagerDialogSelectedUserCards;
	}

	public ReadOnlyUser getUserCardManagerDialogSelectedUser() {
		return this.userCardManagerDialogSelectedUsers.get(0);
	}

	public Date getUserCardManagerDialogValidDate() {
		return userCardManagerDialogValidDate;
	}

	public void setUserCardManagerDialogValidDate(Date userCardManagerDialogValidDate) {
		this.userCardManagerDialogValidDate = userCardManagerDialogValidDate;
	}

	public Boolean isUserCardBatchMode() {
		return userCardManagerDialogSelectedUsers.size() > 1;
	}

	public List<ReadOnlyUser> getUserCardManagerDialogSelectedUsers() {
		return this.userCardManagerDialogSelectedUsers;
	}

	public void initUserCardManagerDialog() {
		if (!selectedEntities.isEmpty()) {
			userCardManagerDialogSelectedUsers.clear();
			userCardManagerDialogSelectedUsers.addAll(selectedEntities);
			if (userCardManagerDialogSelectedUsers.size() == 1) {
				userCardManagerDialogSelectedUserCards = new ArrayList<>();
				userCardManagerDialogUserCards.clear();
				userCardManagerDialogValidDate = Date.from(Instant.now());
				userCardManagerDialogUserCards.addAll(idCardBean.getIdCards(userCardManagerDialogSelectedUsers.get(0).getId()));
			}
		}
	}

	public void userCardManagerDialogNewUserCard() {
		for (ReadOnlyUser user : userCardManagerDialogSelectedUsers) {
			WebUtils.executeTask(params -> {
				Long id = idCardBean.addIdCard(user.getId());
				userCardManagerDialogUserCards.add(idCardBean.findIdCardById(id));
				params.add(id);
				return true;
			}, "lucas.application.userScreen.createUserCard", USER_CARD_MANAGER_DIALOG_MESSAGES_ID,
					Utils.asList(WebUtils.getAsString(user, UserConverter.CONVERTER_ID)));
		}
	}

	public void userCardManagerDialogBlockUserCards() {
		if (userCardManagerDialogSelectedUsers.size() == 1) {
			for (ReadOnlyIdCard card : userCardManagerDialogSelectedUserCards) {
				Long id = card.getId();
				if (WebUtils.executeTask(params -> {
					params.add(id);
					return idCardBean.blockIdCard(id);
				}, "lucas.application.userScreen.blockUserCard", USER_CARD_MANAGER_DIALOG_MESSAGES_ID,
						Utils.asList(WebUtils.getAsString(userCardManagerDialogSelectedUsers.get(0), UserConverter.CONVERTER_ID)))) {
					ReadOnlyIdCard newCard = idCardBean.findIdCardById(id);
					WebUtils.refreshEntities(ReadOnlyIdCard.class, userCardManagerDialogUserCards, userCardManagerDialogSelectedUserCards, newCard,
							idCardBean::findIdCardById, true);
				}
			}
		}
	}

	public void userCardManagerDialogUnblockUserCards() {
		if (userCardManagerDialogSelectedUsers.size() == 1) {
			for (ReadOnlyIdCard card : userCardManagerDialogSelectedUserCards) {
				Long id = card.getId();
				if (WebUtils.executeTask(params -> {
					params.add(id);
					return idCardBean.unblockIdCard(id);
				}, "lucas.application.userScreen.unblockUserCard", USER_CARD_MANAGER_DIALOG_MESSAGES_ID,
						Utils.asList(WebUtils.getAsString(userCardManagerDialogSelectedUsers.get(0), UserConverter.CONVERTER_ID)))) {
					ReadOnlyIdCard newCard = idCardBean.findIdCardById(id);
					WebUtils.refreshEntities(ReadOnlyIdCard.class, userCardManagerDialogUserCards, userCardManagerDialogSelectedUserCards, newCard,
							idCardBean::findIdCardById, true);
				}
			}
		}
	}

	public void initUserCardManagerDialogSetValidDayOverlay() {
		if (!userCardManagerDialogSelectedUserCards.isEmpty()) {
			LocalDate validDay = userCardManagerDialogSelectedUserCards.get(0).getValidDay();
			this.userCardManagerDialogValidDate = validDay != null ? Utils.asDate(validDay) : Date.from(Instant.now());
		}
	}

	public void userCardManagerDialogSetValidDay() {
		if (userCardManagerDialogSelectedUsers.size() == 1) {
			for (ReadOnlyIdCard card : userCardManagerDialogSelectedUserCards) {
				Long id = card.getId();
				if (WebUtils.executeTask(params -> {
					params.add(card.getId());
					params.add(WebUtils.getAsString(Utils.asLocalDate(userCardManagerDialogValidDate), LocalDateConverter.CONVERTER_ID));
					params.add(WebUtils.getAsString(card.getValidDay(), LocalDateConverter.CONVERTER_ID));
					return idCardBean.setValidDate(id, Utils.asLocalDate(userCardManagerDialogValidDate));
				}, "lucas.application.userScreen.setValidDay", USER_CARD_MANAGER_DIALOG_MESSAGES_ID)) {
					ReadOnlyIdCard newCard = idCardBean.findIdCardById(id);
					WebUtils.refreshEntities(ReadOnlyIdCard.class, userCardManagerDialogUserCards, userCardManagerDialogSelectedUserCards, newCard,
							idCardBean::findIdCardById, true);
				}
			}
		}
	}

	public void userCardManagerDialogRemoveValidDay() {
		if (userCardManagerDialogSelectedUsers.size() == 1) {
			for (ReadOnlyIdCard card : userCardManagerDialogSelectedUserCards) {
				Long id = card.getId();
				if (WebUtils.executeTask(params -> {
					params.add(card.getId());
					return idCardBean.setValidDate(id, null);
				}, "lucas.application.userScreen.removeValidDay", USER_CARD_MANAGER_DIALOG_MESSAGES_ID)) {
					ReadOnlyIdCard newCard = idCardBean.findIdCardById(id);
					WebUtils.refreshEntities(ReadOnlyIdCard.class, userCardManagerDialogUserCards, userCardManagerDialogSelectedUserCards, newCard,
							idCardBean::findIdCardById, true);
				}
			}
		}
	}

	public void userCardManagerDialogRemoveUserCards() {
		if (userCardManagerDialogSelectedUsers.size() == 1) {
			if (!userCardManagerDialogSelectedUserCards.isEmpty()) {
				ListIterator<ReadOnlyIdCard> it = userCardManagerDialogSelectedUserCards.listIterator();
				while (it.hasNext()) {
					WebUtils.executeTask(params -> {
						ReadOnlyIdCard userCard = it.next();
						Long id = userCard.getId();
						Boolean removed = idCardBean.removeIdCard(id);
						if (removed) {
							userCardManagerDialogUserCards.remove(userCard);
							it.remove();
						}
						params.add(id);
						return removed;
					}, "lucas.application.userScreen.deleteUserCard", USER_CARD_MANAGER_DIALOG_MESSAGES_ID,
							Utils.asList(WebUtils.getAsString(userCardManagerDialogSelectedUsers.get(0), UserConverter.CONVERTER_ID)));

				}
			}
		}
	}

	public void userCardManagerDialogRefreshUserCards() {
		if (userCardManagerDialogSelectedUsers.size() == 1) {
			WebUtils.executeTask((params) -> {
				WebUtils.refreshEntities(ReadOnlyIdCard.class, userCardManagerDialogUserCards, userCardManagerDialogSelectedUserCards,
						idCardBean::findIdCardById, true);
				params.add(userCardManagerDialogUserCards.size());
				return true;
			}, "lucas.application.userScreen.refreshUserCards", USER_CARD_MANAGER_DIALOG_MESSAGES_ID);
		}
	}

	@EJB
	private EmploymentBeanLocal employmentBean;

	private StreamedContent exportUserCardRet = null;

	public void userCardManagerDialogExportUserCards() {
		exportUserCardRet = null;
		WebUtils.executeTask((params) -> {
			Map<ReadOnlyUser, Set<ReadOnlyIdCard>> idCards = new HashMap<>();
			int size = 0;
			for (ReadOnlyUser user : userCardManagerDialogSelectedUsers) {
				if (!isUserCardBatchMode()) {
					for (ReadOnlyIdCard userCard : userCardManagerDialogSelectedUserCards) {
						if (!idCards.containsKey(user)) idCards.put(user, new HashSet<>());
						idCards.get(user).add(userCard);
						++size;
					}
					break;
				}
				Set<ReadOnlyIdCard> cards = new HashSet<>(idCardBean.getIdCards(user.getId()));
				cards.removeIf((idCard) -> idCard.getBlocked());
				for (ReadOnlyIdCard userCard : cards) {
					if (!idCards.containsKey(user)) idCards.put(user, new HashSet<>());
					idCards.get(user).add(userCard);
					++size;
				}
			}
			params.add(size);
			IdCardExporter exporter = new IdCardExporter(13.5f, 10f, new Dimension(90, 54), 5, 2, 0, 10,
					ImageIO.read(FacesContext.getCurrentInstance().getExternalContext()
							.getResourceAsStream("/WEB-INF/resources/images/Ausweis_System.png")),
					ImageIO.read(FacesContext.getCurrentInstance().getExternalContext()
							.getResourceAsStream("/WEB-INF/resources/images/Handwerkskammer_Rückseite.png")),
					0xFFFFFF, 0xFFFFFF, IdCardExporter.ofCoords(133, 325, 706, 354), null, IdCardExporter.ofCoords(251, 396, 706, 424), null,
					IdCardExporter.ofCoords(296, 458, 706, 579), IdCardExporter.ofCoords(243, 458, 282, 508), 0x000000,
					IdCardExporter.ofCoords(0, 514, 286, 637), null, IdCardExporter.ofCoords(716, 236, 1032, 552), null, '\u25AA', 3, 0xFFFFFF,
					"Times New Roman", 32, idCards, (user) -> {
						return userBean.getImage(user.getId());
					});
			exporter.setBean(employmentBean);
			exportUserCardRet = exporter.exportPdf();
			return true;
		}, "lucas.application.userScreen.exportUserCards", USER_CARD_MANAGER_DIALOG_MESSAGES_ID);
	}

	public StreamedContent getExportUserCardRet() {
		return this.exportUserCardRet;
	}

	/*
	 * -------------------- User Card Manager Dialog End --------------------
	 */

	/*
	 * -------------------- Job Requests Manager Dialog Start --------------------
	 */

	private ReadOnlyUser jobRequestsManagerDialogSelectedUser;

	private ReadOnlyJob jobRequestsManagerDialogFirstJobRequest;

	private ReadOnlyJob jobRequestsManagerDialogSecondJobRequest;

	private ReadOnlyJob jobRequestsManagerDialogThirdJobRequest;

	public ReadOnlyJob getJobRequestsManagerDialogFirstJobRequest() {
		return this.jobRequestsManagerDialogFirstJobRequest;
	}

	public void setJobRequestsManagerDialogFirstJobRequest(ReadOnlyJob jobRequestsManagerDialogFirstJobRequest) {
		this.jobRequestsManagerDialogFirstJobRequest = jobRequestsManagerDialogFirstJobRequest;
	}

	public ReadOnlyJob getJobRequestsManagerDialogSecondJobRequest() {
		return this.jobRequestsManagerDialogSecondJobRequest;
	}

	public void setJobRequestsManagerDialogSecondJobRequest(ReadOnlyJob jobRequestsManagerDialogSecondJobRequest) {
		this.jobRequestsManagerDialogSecondJobRequest = jobRequestsManagerDialogSecondJobRequest;
	}

	public ReadOnlyJob getJobRequestsManagerDialogThirdJobRequest() {
		return this.jobRequestsManagerDialogThirdJobRequest;
	}

	public void setJobRequestsManagerDialogThirdJobRequest(ReadOnlyJob jobRequestsManagerDialogThirdJobRequest) {
		this.jobRequestsManagerDialogThirdJobRequest = jobRequestsManagerDialogThirdJobRequest;
	}

	public void initJobRequestsManagerDialog() {
		if (!selectedEntities.isEmpty()) {
			jobRequestsManagerDialogSelectedUser = selectedEntities.get(0);
			jobRequestsManagerDialogFirstJobRequest = jobRequestsManagerDialogSelectedUser.getFirstJobRequest();
			jobRequestsManagerDialogSecondJobRequest = jobRequestsManagerDialogSelectedUser.getSecondJobRequest();
			jobRequestsManagerDialogThirdJobRequest = jobRequestsManagerDialogSelectedUser.getThirdJobRequest();
		}
	}

	public void editJobRequests() {
		if (jobRequestsManagerDialogSelectedUser != null) {
			WebUtils.executeTask(params -> {
				Long id = jobRequestsManagerDialogSelectedUser.getId();
				if (WebUtils.isPermitted(EnumPermission.USER_SET_JOB_REQUESTS)) {
					userBean.setFirstJobRequest(id,
							jobRequestsManagerDialogFirstJobRequest != null ? jobRequestsManagerDialogFirstJobRequest.getId() : null);
					userBean.setSecondJobRequest(id,
							jobRequestsManagerDialogSecondJobRequest != null ? jobRequestsManagerDialogSecondJobRequest.getId() : null);
					userBean.setThirdJobRequest(id,
							jobRequestsManagerDialogThirdJobRequest != null ? jobRequestsManagerDialogThirdJobRequest.getId() : null);
				}
				ReadOnlyUser tmp2 = userBean.findById(id);
				WebUtils.refreshEntities(ReadOnlyUser.class, searchResults, selectedEntities, tmp2, userBean::findById, true);
				return true;
			}, "lucas.application.userScreen.editJobRequests.message",
					Utils.asList(WebUtils.getAsString(jobRequestsManagerDialogSelectedUser, UserConverter.CONVERTER_ID)));
		}
	}

	/*
	 * -------------------- Job Requests Manager Dialog End --------------------
	 */

	public String showReferencedAccounts() {
		navigateToBeanSingle(AccountBean.BASE_NAME, (user) -> user.getAccount().getId());
		return "/accounts?faces-redirect=true";
	}

	public String showReferencedAttendancedata() {
		navigateToBeanSingle(AttendancedataBean.BASE_NAME, (user) -> user.getAttendancedata().getId());
		return "/attendancedata?faces-redirect=true";
	}

	public String showReferencedEmployments() {
		navigateToBean(EmploymentBean.BASE_NAME, (user) -> {
			List<Long> ids = new ArrayList<>();
			for (ReadOnlyEmployment employment : employmentBean.getEmploymentsByUser(user.getId())) {
				ids.add(employment.getId());
			}
			return ids;
		});
		return "/employments?faces-redirect=true";
	}

	public String showReferencedLoginUsers() {
		navigateToBeanSingle(LoginUserBean.BASE_NAME, (user) -> {
			ReadOnlyLoginUser loginUser = loginBean.getLoginUserByUser(user.getId());
			return loginUser == null ? null : loginUser.getId();
		});
		return "/loginUsers?faces-redirect=true";
	}

}
