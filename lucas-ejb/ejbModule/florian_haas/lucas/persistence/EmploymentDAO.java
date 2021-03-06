package florian_haas.lucas.persistence;

import java.util.*;

import florian_haas.lucas.model.*;

public interface EmploymentDAO extends ReadOnlyDAO<Employment> {

	public List<Employment> findEmployments(Long employmentId, Long userId, Long jobId, Set<EnumWorkShift> shifts, Boolean useEmploymentId,
			Boolean useUserId, Boolean useJobId, Boolean useShift, EnumQueryComparator employmentIdComparator, EnumQueryComparator userIdComparator,
			EnumQueryComparator jobIdComparator, EnumQueryComparator shiftComparator);

	public List<Employment> getEmploymentsFromData(String data, Integer resultsCount);

}
