package florian_haas.lucas.database.impl;

import java.util.*;

import javax.persistence.criteria.Predicate;

import florian_haas.lucas.database.*;
import florian_haas.lucas.model.*;

@JPADAO
public class UserDAOImpl extends DAOImpl<User> implements UserDAO {

	@Override
	public List<User> findUsers(Long userId, String forename, String surname, Integer schoolGrade, String schoolClass, EnumUserType userType,
			List<String> ranks, Boolean useUserId, Boolean useForename, Boolean useSurname, Boolean useSchoolGrade, Boolean useSchoolClass,
			Boolean useUserType, Boolean useRanks, EnumQueryComparator userIdComparator, EnumQueryComparator forenameComparator,
			EnumQueryComparator surnameComparator, EnumQueryComparator schoolGradeComparator, EnumQueryComparator schoolClassComparator,
			EnumQueryComparator ranksComparator) {
		return readOnlyCriteriaQuery((query, root, builder) -> {
			List<Predicate> predicates = new ArrayList<>();

			getSingularRestriction(User_.id, userId, useUserId, userIdComparator, predicates, builder, root);
			getSingularRestriction(User_.forename, forename, useForename, forenameComparator, predicates, builder, root);
			getSingularRestriction(User_.surname, surname, useSurname, surnameComparator, predicates, builder, root);
			getSingularRestriction(User_.schoolGrade, schoolGrade, useSchoolGrade, schoolGradeComparator, predicates, builder, root);
			getSingularRestriction(User_.schoolClass, schoolClass, useSchoolClass, schoolClassComparator, predicates, builder, root);
			if (useUserType) {
				Predicate pred = null;
				switch (userType) {
					case GUEST:
						pred = builder.and(builder.isNull(root.get(User_.forename)), builder.isNull(root.get(User_.surname)),
								builder.isNull(root.get(User_.schoolGrade)), builder.isNull(root.get(User_.schoolClass)));
						break;
					case PUPIL:
						pred = builder.and(builder.isNotNull(root.get(User_.forename)), builder.isNotNull(root.get(User_.surname)),
								builder.isNotNull(root.get(User_.schoolGrade)), builder.isNotNull(root.get(User_.schoolClass)));
						break;
					case TEACHER:
						pred = builder.and(builder.isNotNull(root.get(User_.forename)), builder.isNotNull(root.get(User_.surname)),
								builder.isNull(root.get(User_.schoolGrade)), builder.isNull(root.get(User_.schoolClass)));
						break;
					default:
						break;
				}
				if (pred != null) {
					predicates.add(pred);
				}
			}
			getPluralRestriction(User_.ranks, ranks, useRanks, ranksComparator, predicates, builder, root);

			return predicates.toArray(new Predicate[predicates.size()]);
		});
	}

}
