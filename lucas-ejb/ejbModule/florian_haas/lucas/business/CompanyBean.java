package florian_haas.lucas.business;

import static florian_haas.lucas.security.EnumPermission.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import javax.annotation.Resource;
import javax.ejb.*;
import javax.inject.Inject;
import javax.validation.Validator;
import javax.validation.executable.*;

import florian_haas.lucas.database.*;
import florian_haas.lucas.model.*;
import florian_haas.lucas.model.validation.ValidEntityId;
import florian_haas.lucas.security.*;
import florian_haas.lucas.util.Utils;

@Stateless
@ValidateOnExecution(type = ExecutableType.IMPLICIT)
@Secured
public class CompanyBean implements CompanyBeanLocal {

	@Inject
	@JPADAO
	private CompanyDAO companyDao;

	@Inject
	@JPADAO
	private TaxdataDAO taxdataDao;

	@Inject
	@JPADAO
	private CompanyCardDAO companyCardDao;

	@Inject
	@JPADAO
	private RoomSectionDAO roomSectionDao;

	@EJB
	private AccountBeanLocal accountBean;

	@Resource
	private Validator validator;

	@EJB
	private EmploymentBeanLocal employmentBean;

	@Override
	@RequiresPermissions(COMPANY_CREATE)
	public Long createCompany(String name, String description, Long roomSectionId, EnumCompanyType companyType, Long parentCompanyId,
			List<Long> managerUserIds, Integer requiredEmployeesCount) {
		checkIsNameUnique(name);
		if (roomSectionId != null) checkIsSectionUnique(roomSectionId);
		RoomSection section = roomSectionId != null ? roomSectionDao.findById(roomSectionId) : null;
		Company company = new Company(name, description, section, companyType, requiredEmployeesCount);
		if (companyType == EnumCompanyType.STATE) {
			company.getAccount().setIsProtected(Boolean.TRUE);
		}
		companyDao.persist(company);
		companyDao.flush();
		managerUserIds.forEach(userId -> {
			employmentBean.addDefaultEmployment(userId, company.getId(), EnumEmployeePosition.MANAGER);
		});
		if (parentCompanyId != null) {
			setParentCompany(company.getId(), parentCompanyId);
		}
		if (section != null) {
			section.setCompany(company);
		}
		return company.getId();
	}

	@Override
	@RequiresPermissions(COMPANY_FIND_ALL)
	public List<Company> findAll() {
		return companyDao.findAll();
	}

	@Override
	@RequiresPermissions(COMPANY_FIND_BY_ID)
	public Company findById(Long companyId) {
		return companyDao.findById(companyId);
	}

	@Override
	@RequiresPermissions(COMPANY_FIND_DYNAMIC)
	public List<Company> findCompanies(Long companyId, String name, String description, Long roomSectionId, EnumCompanyType companyType,
			Long parentCompanyId, Integer requiredEmployeesCount, Boolean areEmployeesRequired, Boolean useId, Boolean useName,
			Boolean useDescription, Boolean useRoomSectionId, Boolean useCompanyType, Boolean useParentCompanyId, Boolean useRequiredEmployeesCount,
			Boolean useAreEmployeesRequired, EnumQueryComparator idComparator, EnumQueryComparator nameComparator,
			EnumQueryComparator descriptionComparator, EnumQueryComparator roomSectionIdComparator, EnumQueryComparator companyTypeComparator,
			EnumQueryComparator parentCompanyIdComparator, EnumQueryComparator requiredEmployeesCountComparator) {
		return companyDao.findCompanies(companyId, name, description, roomSectionId, companyType, parentCompanyId, requiredEmployeesCount,
				areEmployeesRequired, useId, useName, useDescription, useRoomSectionId, useCompanyType, useParentCompanyId, useRequiredEmployeesCount,
				useAreEmployeesRequired, idComparator, nameComparator, descriptionComparator, roomSectionIdComparator, companyTypeComparator,
				parentCompanyIdComparator, requiredEmployeesCountComparator);
	}

	@Override
	@RequiresPermissions(COMPANY_SET_NAME)
	public Boolean setName(Long companyId, String name) {
		Company comp = companyDao.findById(companyId);
		if (comp.getName().equals(name)) return Boolean.FALSE;
		checkIsNameUnique(name);
		comp.setName(name);
		return Boolean.TRUE;
	}

	@Override
	@RequiresPermissions(COMPANY_SET_DESCRIPTION)
	public Boolean setDescription(Long companyId, String description) {
		Company comp = companyDao.findById(companyId);
		if ((comp.getDescription() == null && description == null) || (comp.getDescription() != null && comp.getDescription().equals(description)))
			return Boolean.FALSE;
		comp.setDescription(description);
		return Boolean.TRUE;
	}

	@Override
	@RequiresPermissions(COMPANY_SET_SECTION)
	public Boolean setSection(Long companyId, Long sectionId) {
		Company comp = companyDao.findById(companyId);
		RoomSection oldSection = comp.getSection();
		RoomSection section = sectionId != null ? roomSectionDao.findById(sectionId) : null;
		if ((oldSection == null && section == null) || oldSection != null && oldSection.equals(section)) return Boolean.FALSE;
		if (sectionId != null) checkIsSectionUnique(sectionId);
		comp.setSection(section);
		if (section != null) section.setCompany(comp);
		if (oldSection != null) oldSection.setCompany(null);
		return Boolean.TRUE;
	}

	@Override
	@RequiresPermissions(COMPANY_SET_COMPANY_TYPE)
	public Boolean setCompanyType(Long companyId, EnumCompanyType companyType) {
		Company comp = companyDao.findById(companyId);
		if (comp.getCompanyType().equals(companyType)) return Boolean.FALSE;
		comp.getAccount().setIsProtected(companyType == EnumCompanyType.STATE ? Boolean.TRUE : Boolean.FALSE);
		comp.setCompanyType(companyType);
		return Boolean.TRUE;
	}

	@Override
	@RequiresPermissions(COMPANY_SET_PARENT_COMPANY)
	public Boolean setParentCompany(Long companyId, Long parentCompanyId) {
		return setParentCompanyHelper(companyId, parentCompanyId);
	}

	@Override
	@RequiresPermissions(COMPANY_REMOVE_PARENT_COMPANY)
	public Boolean removeParentCompany(Long companyId) {
		return setParentCompanyHelper(companyId, null);
	}

	private Boolean setParentCompanyHelper(Long companyId, Long newParentCompany) {
		Company comp = companyDao.findById(companyId);
		Company parent = newParentCompany != null ? findById(newParentCompany) : null;
		Company oldParentCompany = comp.getParentCompany();
		if ((oldParentCompany == null && parent == null) || (oldParentCompany != null && oldParentCompany.equals(parent))) return Boolean.FALSE;
		if (comp.getParentCompany() != null) {
			oldParentCompany.removeChildCompany(comp);
		}
		comp.setParentCompany(parent);
		if (parent != null) parent.addChildCompany(comp);
		return Boolean.TRUE;
	}

	@Override
	@RequiresPermissions(COMPANY_SET_REQUIRED_EMPLOYEES_COUNT)
	public Boolean setRequiredEmployeesCount(Long companyId, Integer requiredEmployeesCount) {
		Company comp = companyDao.findById(companyId);
		if (comp.getRequiredEmployeesCount().equals(requiredEmployeesCount)) return Boolean.FALSE;
		comp.setRequiredEmployeesCount(requiredEmployeesCount);
		return Boolean.TRUE;
	}

	@Override
	@RequiresPermissions(COMPANY_ADD_TAXDATA)
	public Boolean addTaxdata(Long companyId, LocalDate date, BigDecimal incomings, BigDecimal outgoings) {
		Company comp = companyDao.findById(companyId);
		Taxdata taxdata = new Taxdata(comp, date, incomings, outgoings);
		comp.addTaxdata(taxdata);
		return Boolean.TRUE;
	}

	@Override
	@RequiresPermissions(COMPANY_REMOVE_TAXDATA)
	public Boolean removeTaxdata(Long taxdataId) {
		Taxdata taxdata = taxdataDao.findById(taxdataId);
		Company comp = taxdata.getCompany();
		return comp.removeTaxdata(taxdata);
	}

	@Override
	@RequiresPermissions(COMPANY_SET_INCOMINGS)
	public Boolean setIncomings(Long taxdataId, BigDecimal incomings) {
		Taxdata taxdata = taxdataDao.findById(taxdataId);
		if (Utils.isEqual(taxdata.getIncomings(), incomings)) return Boolean.FALSE;
		taxdata.setIncomings(incomings);
		return Boolean.TRUE;
	}

	@Override
	@RequiresPermissions(COMPANY_SET_OUTGOINGS)
	public Boolean setOutgoings(Long taxdataId, BigDecimal outgoings) {
		Taxdata taxdata = taxdataDao.findById(taxdataId);
		if (Utils.isEqual(taxdata.getOutgoings(), outgoings)) return Boolean.FALSE;
		taxdata.setOutgoings(outgoings);
		return Boolean.TRUE;
	}

	@Override
	@RequiresPermissions(COMPANY_ADD_COMPANY_CARD)
	public Long addCompanyCard(Long companyId) {
		Company company = companyDao.findById(companyId);
		CompanyCard companyCard = new CompanyCard(company);
		company.addCompanyCard(companyCard);
		companyDao.flush();
		return companyCard.getId();
	}

	@Override
	@RequiresPermissions(COMPANY_BLOCK_COMPANY_CARD)
	public Boolean blockCompanyCard(Long companyCardId) {
		CompanyCard companyCard = companyCardDao.findById(companyCardId);
		if (companyCard.getBlocked()) { return Boolean.FALSE; }
		companyCard.setBlocked(Boolean.TRUE);
		return Boolean.TRUE;
	}

	@Override
	@RequiresPermissions(COMPANY_UNBLOCK_COMPANY_CARD)
	public Boolean unblockCompanyCard(Long companyCardId) {
		CompanyCard companyCard = companyCardDao.findById(companyCardId);
		if (!companyCard.getBlocked()) { return Boolean.FALSE; }
		companyCard.setBlocked(Boolean.FALSE);
		return Boolean.TRUE;
	}

	public static final String NAME_NOT_UNIQUE_EXCEPTION_MARKER = "notUniqueName";
	public static final String SECTION_NOT_UNIQUE_EXCEPTION_MARKER = "notUniqueLocation";

	private void checkIsNameUnique(String name) {
		if (!companyDao.isNameUnique(name)) throw new LucasException("The name is used by another company", NAME_NOT_UNIQUE_EXCEPTION_MARKER);
	}

	private void checkIsSectionUnique(Long sectionId) {
		if (!companyDao.isRoomSectionUnique(sectionId))
			throw new LucasException("Another company is assigned to the section", SECTION_NOT_UNIQUE_EXCEPTION_MARKER);
	}

	@Override
	@RequiresPermissions(COMPANY_GET_MANAGERS)
	public List<Employment> getManagers(Long companyId) {
		return companyDao.findById(companyId).getManagers();
	}

	@Override
	@RequiresPermissions(COMPANY_GET_ADVISORS)
	public List<Employment> getAdvisors(Long companyId) {
		return companyDao.findById(companyId).getAdvisors();
	}

	@Override
	@RequiresPermissions(COMPANY_GET_EMPLOYEES)
	public List<Employment> getEmployees(Long companyId) {
		return companyDao.findById(companyId).getEmployees();
	}

	@Override
	@RequiresPermissions(COMPANY_FIND_COMPANY_CARD_BY_ID)
	public CompanyCard findCompanyCardById(@ValidEntityId(entityClass = CompanyCard.class) Long companyCardId) {
		return companyCardDao.findById(companyCardId);
	}

	@Override
	@RequiresPermissions(COMPANY_GET_COMPANY_CARDS)
	public Set<CompanyCard> getCompanyCards(Long companyId) {
		Company company = companyDao.findById(companyId);
		return company.getCompanyCards();
	}

	@Override
	@RequiresPermissions(COMPANY_SET_VALID_DATE_COMPANY_CARD)
	public Boolean setValidDate(Long companyCardId, LocalDate validDate) {
		CompanyCard companyCard = companyCardDao.findById(companyCardId);
		if (companyCard.getValidDay() != null && companyCard.getValidDay().equals(validDate)
				|| companyCard.getValidDay() == null && validDate == null)
			return Boolean.FALSE;
		companyCard.setValidDay(validDate);
		return Boolean.TRUE;
	}

	@Override
	@RequiresPermissions(COMPANY_REMOVE_COMPANY_CARD)
	public Boolean removeCompanyCard(@ValidEntityId(entityClass = CompanyCard.class) Long companyCardId) {
		CompanyCard card = companyCardDao.findById(companyCardId);
		Boolean removed = card.getCompany().removeCompanyCard(card);
		if (removed) {
			companyCardDao.delete(card);
		}
		return removed;
	}

}
