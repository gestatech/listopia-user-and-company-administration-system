package florian_haas.lucas.business;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.ejb.Local;
import javax.validation.constraints.*;

import org.hibernate.validator.constraints.NotBlank;

import florian_haas.lucas.database.*;
import florian_haas.lucas.database.validation.QueryComparator;
import florian_haas.lucas.model.*;
import florian_haas.lucas.model.validation.ValidEntityId;
import florian_haas.lucas.util.validation.*;

@Local
public interface CompanyBeanLocal {

	public Long createCompany(@NotNull @NotBlankString String name, @NotBlankString String description, @NotNull @NotBlankString String room,
			@NotNull @Min(value = 0) Integer section, @NotNull EnumCompanyType companyType, List<@TypeNotNull User> managers,
			@NotNull @Min(value = 0) Integer requiredEmployeesCount);

	public List<Company> findAll();

	public Company findById(@ValidEntityId(entityClass = Company.class) Long companyId);

	public List<Company> findCompanies(@NotNull Long companyId, @NotNull String name, @NotNull String description, @NotNull String room,
			@NotNull Integer section, @NotNull EnumCompanyType companyType, @NotNull Long parentCompanyId, @NotNull Integer requiredEmployeesCount,
			@NotNull Boolean areEmployeesRequired, @NotNull Boolean useId, @NotNull Boolean useName, @NotNull Boolean useDescription,
			@NotNull Boolean useRoom, @NotNull Boolean useSection, @NotNull Boolean useCompanyType, @NotNull Boolean useParentCompanyId,
			@NotNull Boolean useRequiredEmployeesCount, @NotNull Boolean useAreEmployeesRequired,
			@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC) EnumQueryComparator idComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.TEXT) EnumQueryComparator nameComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.TEXT) EnumQueryComparator descriptionComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.TEXT) EnumQueryComparator roomComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC) EnumQueryComparator sectionComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.LOGIC) EnumQueryComparator companyTypeComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC) EnumQueryComparator parentCompanyIdComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC) EnumQueryComparator requiredEmployeesCountComparator);

	public Boolean setName(@ValidEntityId(entityClass = Company.class) Long companyId, @NotNull @NotBlankString String name);

	public Boolean setDescription(@ValidEntityId(entityClass = Company.class) Long companyId, @NotBlankString String description);

	public Boolean setRoom(@ValidEntityId(entityClass = Company.class) Long companyId, @NotNull @NotBlankString String room);

	public Boolean setSection(@ValidEntityId(entityClass = Company.class) Long companyId, @NotNull @Min(value = 0) Integer section);

	public Boolean setCompanyType(@ValidEntityId(entityClass = Company.class) Long companyId, @NotNull EnumCompanyType companyType);

	public Boolean setParentCompany(@ValidEntityId(entityClass = Company.class) Long companyId,
			@ValidEntityId(entityClass = Company.class) Long parentCompanyId);

	public Boolean removeParentCompany(@ValidEntityId(entityClass = Company.class) Long companyId);

	public Boolean setRequiredEmployeesCount(@ValidEntityId(entityClass = Company.class) Long companyId,
			@NotNull @Min(value = 0) Integer requiredEmployeesCount);

	public Boolean addTaxdata(@ValidEntityId(entityClass = Company.class) Long companyId, @NotNull LocalDate date,
			@NotNull @DecimalMin(value = "0.0", inclusive = true) BigDecimal incomings,
			@NotNull @DecimalMin(value = "0.0", inclusive = true) BigDecimal outgoings);

	public Boolean removeTaxdata(@ValidEntityId(entityClass = Taxdata.class) Long taxdataId);

	public Boolean setIncomings(@ValidEntityId(entityClass = Taxdata.class) Long taxdataId,
			@NotNull @DecimalMin(value = "0.0", inclusive = true) BigDecimal incomings);

	public Boolean setOutgoings(@ValidEntityId(entityClass = Taxdata.class) Long taxdataId,
			@NotNull @DecimalMin(value = "0.0", inclusive = true) BigDecimal outgoings);

	public Boolean addCompanyCard(@ValidEntityId(entityClass = Company.class) Long companyId);

	public Boolean blockCompanyCard(@ValidEntityId(entityClass = CompanyCard.class) Long companyCardId);

	public Boolean unblockCompanyCard(@ValidEntityId(entityClass = CompanyCard.class) Long companyCardId);

	public Boolean existsLocation(@NotBlank String room, @NotNull @Min(0) Integer section);
}
