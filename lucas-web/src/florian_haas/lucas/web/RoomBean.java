package florian_haas.lucas.web;

import java.util.*;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.*;

import org.hibernate.validator.constraints.NotBlank;

import florian_haas.lucas.business.*;
import florian_haas.lucas.model.*;
import florian_haas.lucas.persistence.*;
import florian_haas.lucas.security.EnumPermission;
import florian_haas.lucas.validation.QueryComparator;
import florian_haas.lucas.web.converter.*;
import florian_haas.lucas.web.util.WebUtils;

@Named
@ViewScoped
public class RoomBean extends BaseBean<ReadOnlyRoom> {

	public RoomBean() {
		super(BASE_NAME, 5);
	}

	public static final String BASE_NAME = "room";

	private static final long serialVersionUID = 9056222365379147492L;

	@EJB
	private RoomBeanLocal roomBean;

	@EJB
	private EntityBeanLocal entityBean;

	@NotNull
	@Min(0)
	private Long searchRoomId = 0l;

	@NotNull
	private Boolean useSearchRoomId = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC)
	private EnumQueryComparator searchRoomIdComparator = EnumQueryComparator.EQUAL;

	@NotBlank
	private String searchRoomName = "";

	@NotNull
	private Boolean useSearchRoomName = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.TEXT)
	private EnumQueryComparator searchRoomNameComparator = EnumQueryComparator.EQUAL;

	@NotNull
	private ReadOnlyRoomSection searchRoomSection = null;

	@NotNull
	private Boolean useSearchRoomSectionId = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.LOGIC)
	private EnumQueryComparator searchRoomSectionIdComparator = EnumQueryComparator.EQUAL;

	@NotNull
	private Integer searchRoomSectionsCount = 0;

	@NotNull
	private Boolean useSearchRoomSectionsCount = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC)
	private EnumQueryComparator searchRoomSectionsCountComparator = EnumQueryComparator.EQUAL;

	@NotNull
	private Integer searchRoomOccupiedSectionsCount = 0;

	@NotNull
	private Boolean useSearchRoomOccupiedSectionsCount = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC)
	private EnumQueryComparator searchRoomOccupiedSectionsCountComparator = EnumQueryComparator.EQUAL;

	@NotNull
	private Integer searchRoomFreeSectionsCount = 0;

	@NotNull
	private Boolean useSearchRoomFreeSectionsCount = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC)
	private EnumQueryComparator searchRoomFreeSectionsCountComparator = EnumQueryComparator.EQUAL;

	@NotNull
	private ReadOnlyCompany searchRoomCompany = null;

	@NotNull
	private Boolean useSearchRoomCompany = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.LOGIC)
	private EnumQueryComparator searchRoomCompanyComparator = EnumQueryComparator.EQUAL;

	public Long getSearchRoomId() {
		return searchRoomId;
	}

	public void setSearchRoomId(Long searchRoomId) {
		this.searchRoomId = searchRoomId;
	}

	public Boolean getUseSearchRoomId() {
		return useSearchRoomId;
	}

	public void setUseSearchRoomId(Boolean useSearchRoomId) {
		this.useSearchRoomId = useSearchRoomId;
	}

	public EnumQueryComparator getSearchRoomIdComparator() {
		return searchRoomIdComparator;
	}

	public void setSearchRoomIdComparator(EnumQueryComparator searchRoomIdComparator) {
		this.searchRoomIdComparator = searchRoomIdComparator;
	}

	public String getSearchRoomName() {
		return searchRoomName;
	}

	public void setSearchRoomName(String searchRoomName) {
		this.searchRoomName = searchRoomName;
	}

	public Boolean getUseSearchRoomName() {
		return useSearchRoomName;
	}

	public void setUseSearchRoomName(Boolean useSearchRoomName) {
		this.useSearchRoomName = useSearchRoomName;
	}

	public EnumQueryComparator getSearchRoomNameComparator() {
		return searchRoomNameComparator;
	}

	public void setSearchRoomNameComparator(EnumQueryComparator searchRoomNameComparator) {
		this.searchRoomNameComparator = searchRoomNameComparator;
	}

	public ReadOnlyRoomSection getSearchRoomSection() {
		return searchRoomSection;
	}

	public void setSearchRoomSection(ReadOnlyRoomSection searchRoomSection) {
		this.searchRoomSection = searchRoomSection;
	}

	public Boolean getUseSearchRoomSectionId() {
		return useSearchRoomSectionId;
	}

	public void setUseSearchRoomSectionId(Boolean useSearchRoomSectionId) {
		this.useSearchRoomSectionId = useSearchRoomSectionId;
	}

	public EnumQueryComparator getSearchRoomSectionIdComparator() {
		return searchRoomSectionIdComparator;
	}

	public void setSearchRoomSectionIdComparator(EnumQueryComparator searchRoomSectionIdComparator) {
		this.searchRoomSectionIdComparator = searchRoomSectionIdComparator;
	}

	public Integer getSearchRoomSectionsCount() {
		return this.searchRoomSectionsCount;
	}

	public void setSearchRoomSectionsCount(Integer searchRoomSectionsCount) {
		this.searchRoomSectionsCount = searchRoomSectionsCount;
	}

	public Boolean getUseSearchRoomSectionsCount() {
		return this.useSearchRoomSectionsCount;
	}

	public void setUseSearchRoomSectionsCount(Boolean useSearchRoomSectionsCount) {
		this.useSearchRoomSectionsCount = useSearchRoomSectionsCount;
	}

	public EnumQueryComparator getSearchRoomSectionsCountComparator() {
		return this.searchRoomSectionsCountComparator;
	}

	public void setSearchRoomSectionsCountComparator(EnumQueryComparator searchRoomSectionsCountComparator) {
		this.searchRoomSectionsCountComparator = searchRoomSectionsCountComparator;
	}

	public Integer getSearchRoomOccupiedSectionsCount() {
		return this.searchRoomOccupiedSectionsCount;
	}

	public void setSearchRoomOccupiedSectionsCount(Integer searchRoomOccupiedSectionsCount) {
		this.searchRoomOccupiedSectionsCount = searchRoomOccupiedSectionsCount;
	}

	public Boolean getUseSearchRoomOccupiedSectionsCount() {
		return this.useSearchRoomOccupiedSectionsCount;
	}

	public void setUseSearchRoomOccupiedSectionsCount(Boolean useSearchRoomOccupiedSectionsCount) {
		this.useSearchRoomOccupiedSectionsCount = useSearchRoomOccupiedSectionsCount;
	}

	public EnumQueryComparator getSearchRoomOccupiedSectionsCountComparator() {
		return this.searchRoomOccupiedSectionsCountComparator;
	}

	public void setSearchRoomOccupiedSectionsCountComparator(EnumQueryComparator searchRoomOccupiedSectionsCountComparator) {
		this.searchRoomOccupiedSectionsCountComparator = searchRoomOccupiedSectionsCountComparator;
	}

	public Integer getSearchRoomFreeSectionsCount() {
		return this.searchRoomFreeSectionsCount;
	}

	public void setSearchRoomFreeSectionsCount(Integer searchRoomFreeSectionsCount) {
		this.searchRoomFreeSectionsCount = searchRoomFreeSectionsCount;
	}

	public Boolean getUseSearchRoomFreeSectionsCount() {
		return this.useSearchRoomFreeSectionsCount;
	}

	public void setUseSearchRoomFreeSectionsCount(Boolean useSearchRoomFreeSectionsCount) {
		this.useSearchRoomFreeSectionsCount = useSearchRoomFreeSectionsCount;
	}

	public EnumQueryComparator getSearchRoomFreeSectionsCountComparator() {
		return this.searchRoomFreeSectionsCountComparator;
	}

	public void setSearchRoomFreeSectionsCountComparator(EnumQueryComparator searchRoomFreeSectionsCountComparator) {
		this.searchRoomFreeSectionsCountComparator = searchRoomFreeSectionsCountComparator;
	}

	public ReadOnlyCompany getSearchRoomCompany() {
		return this.searchRoomCompany;
	}

	public void setSearchRoomCompany(ReadOnlyCompany searchRoomCompany) {
		this.searchRoomCompany = searchRoomCompany;
	}

	public Boolean getUseSearchRoomCompany() {
		return this.useSearchRoomCompany;
	}

	public void setUseSearchRoomCompany(Boolean useSearchRoomCompany) {
		this.useSearchRoomCompany = useSearchRoomCompany;
	}

	public EnumQueryComparator getSearchRoomCompanyComparator() {
		return this.searchRoomCompanyComparator;
	}

	public void setSearchRoomCompanyComparator(EnumQueryComparator searchRoomCompanyComparator) {
		this.searchRoomCompanyComparator = searchRoomCompanyComparator;
	}

	@Override
	public EnumPermission getFindDynamicPermission() {
		return EnumPermission.ROOM_FIND_DYNAMIC;
	}

	@Override
	public EnumPermission getPrintPermission() {
		return EnumPermission.ROOM_PRINT;
	}

	@Override
	public EnumPermission getExportPermission() {
		return EnumPermission.ROOM_EXPORT;
	}

	@Override
	protected List<? extends ReadOnlyRoom> searchEntities() {
		return roomBean.findRooms(searchRoomId, searchRoomName, searchRoomSection != null ? searchRoomSection.getId() : null, searchRoomSectionsCount,
				searchRoomOccupiedSectionsCount, searchRoomFreeSectionsCount, searchRoomCompany != null ? searchRoomCompany.getId() : null,
				useSearchRoomId, useSearchRoomName, useSearchRoomSectionId, useSearchRoomSectionsCount, useSearchRoomOccupiedSectionsCount,
				useSearchRoomFreeSectionsCount, useSearchRoomCompany, searchRoomIdComparator, searchRoomNameComparator, searchRoomSectionIdComparator,
				searchRoomSectionsCountComparator, searchRoomOccupiedSectionsCountComparator, searchRoomFreeSectionsCountComparator,
				searchRoomCompanyComparator);
	}

	@Override
	protected ReadOnlyRoom entityGetter(Long entityId) {
		return roomBean.findById(entityId);
	}

	/*
	 * -------------------- Create Room Dialog Start --------------------
	 */

	@NotBlank
	private String createRoomDialogName;

	@NotNull
	@Min(0)
	@Max(25)
	private Integer createRoomDialogSectionCount = 0;

	public String getCreateRoomDialogName() {
		return createRoomDialogName;
	}

	public void setCreateRoomDialogName(String createRoomDialogName) {
		this.createRoomDialogName = createRoomDialogName;
	}

	public Integer getCreateRoomDialogSectionCount() {
		return createRoomDialogSectionCount;
	}

	public void setCreateRoomDialogSectionCount(Integer createRoomDialogSectionCount) {
		this.createRoomDialogSectionCount = createRoomDialogSectionCount;
	}

	public void initCreateRoomDialog() {
		createRoomDialogName = null;
		createRoomDialogSectionCount = 0;
	}

	public void createRoom() {
		WebUtils.executeTask((params) -> {
			params.add(WebUtils.getAsString(roomBean.findById(roomBean.createRoom(createRoomDialogName, createRoomDialogSectionCount)),
					RoomConverter.CONVERTER_ID));
			return true;
		}, "lucas.application.roomScreen.createRoom.message", (exception, params) -> {
			return WebUtils.getTranslatedMessage("lucas.application.roomScreen.createRoom.message.notUniqueName", createRoomDialogName);
		});
	}

	/*
	 * -------------------- Create Room Dialog End --------------------
	 */

	/*
	 * -------------------- Edit Room Dialog Start --------------------
	 */

	@NotBlank
	private String editRoomDialogName;

	private ReadOnlyRoom editRoomDialogSelectedRoom;

	public String getEditRoomDialogName() {
		return editRoomDialogName;
	}

	public void setEditRoomDialogName(String editRoomDialogName) {
		this.editRoomDialogName = editRoomDialogName;
	}

	public void initEditRoomDialog() {
		if (!selectedEntities.isEmpty()) {
			editRoomDialogSelectedRoom = selectedEntities.get(0);
			editRoomDialogName = editRoomDialogSelectedRoom.getName();
		}
	}

	public void editRoom() {
		WebUtils.executeTask((params) -> {
			Long id = editRoomDialogSelectedRoom.getId();
			roomBean.setName(id, editRoomDialogName);
			ReadOnlyRoom tmp = roomBean.findById(id);
			params.add(WebUtils.getAsString(tmp, RoomConverter.CONVERTER_ID));
			params.add(editRoomDialogName);
			WebUtils.refreshEntities(ReadOnlyRoom.class, searchResults, selectedEntities, tmp, roomBean::findById, true);
			return true;
		}, "lucas.application.roomScreen.editRoom.message", (exception, params) -> {
			return WebUtils.getTranslatedMessage("lucas.application.roomScreen.editRoom.message.notUniqueName", editRoomDialogName);
		});
	}

	/*
	 * -------------------- Edit Room Dialog End --------------------
	 */

	/*
	 * -------------------- Section Manager Dialog Start --------------------
	 */

	private ReadOnlyRoom sectionManagerDialogSelectedRoom;

	private List<ReadOnlyRoomSection> sectionManagerDialogSelectedSections = new ArrayList<>();

	public static final String SECTION_MANAGER_DIALOG_MESSAGES_COMPONENT_ID = "sectionManagerDialogMessages";

	public ReadOnlyRoom getSectionManagerDialogSelectedRoom() {
		return this.sectionManagerDialogSelectedRoom;
	}

	public List<ReadOnlyRoomSection> getSectionManagerDialogSelectedSections() {
		return this.sectionManagerDialogSelectedSections;
	}

	public void setSectionManagerDialogSelectedSections(List<ReadOnlyRoomSection> sectionManagerDialogSelectedSections) {
		this.sectionManagerDialogSelectedSections = sectionManagerDialogSelectedSections;
	}

	public void initSectionManagerDialog() {
		if (!selectedEntities.isEmpty()) {
			sectionManagerDialogSelectedRoom = selectedEntities.get(0);
		}
	}

	public void addSection() {
		WebUtils.executeTask((params) -> {
			params.add(WebUtils.getAsString(roomBean.findRoomSectionById(roomBean.addSection(sectionManagerDialogSelectedRoom.getId())),
					RoomSectionConverter.CONVERTER_ID));
			ReadOnlyRoom tmp = roomBean.findById(sectionManagerDialogSelectedRoom.getId());
			sectionManagerDialogSelectedRoom = tmp;
			WebUtils.refreshEntities(ReadOnlyRoom.class, searchResults, selectedEntities, tmp, roomBean::findById, true);
			return true;
		}, "lucas.application.roomScreen.createRoomSection", SECTION_MANAGER_DIALOG_MESSAGES_COMPONENT_ID);
	}

	public void removeSection() {
		sectionManagerDialogSelectedSections.forEach(section -> {
			WebUtils.executeTask((params) -> {
				params.add(WebUtils.getAsString(section, RoomSectionConverter.CONVERTER_ID));
				Boolean success = roomBean.removeSection(sectionManagerDialogSelectedRoom.getId(), section.getId());
				if (success) {
					ReadOnlyRoom tmp = roomBean.findById(sectionManagerDialogSelectedRoom.getId());
					sectionManagerDialogSelectedRoom = tmp;
					WebUtils.refreshEntities(ReadOnlyRoom.class, searchResults, selectedEntities, tmp, roomBean::findById, true);
				}
				return success;
			}, "lucas.application.roomScreen.removeRoomSection", SECTION_MANAGER_DIALOG_MESSAGES_COMPONENT_ID);
		});
	}

	/*
	 * -------------------- Section Manager Dialog End --------------------
	 */

	public void removeRooms() {
		Iterator<ReadOnlyRoom> it = selectedEntities.iterator();
		while (it.hasNext()) {
			ReadOnlyRoom room = it.next();
			WebUtils.executeTask(params -> {
				params.add(WebUtils.getAsString(room, RoomConverter.CONVERTER_ID));
				Boolean ret = roomBean.removeRoom(room.getId());
				if (ret) {
					searchResults.remove(room);
					it.remove();
				}
				return ret;
			}, "lucas.application.roomScreen.removeRoom");
		}
	}

}
