package florian_haas.lucas.persistence.impl;

import java.util.*;

import javax.persistence.criteria.*;
import javax.persistence.metamodel.SingularAttribute;

import florian_haas.lucas.model.*;
import florian_haas.lucas.persistence.*;

@JPADAO
public class UserDAOImpl extends DAOImpl<User> implements UserDAO {

	@Override
	public List<User> findUsers(Long userId, String forename, String surname, List<EnumSchoolClass> schoolClasses, EnumUserType userType,
			List<String> ranks, Boolean useUserId, Boolean useForename, Boolean useSurname, Boolean useSchoolClass, Boolean useUserType,
			Boolean useRanks, EnumQueryComparator userIdComparator, EnumQueryComparator forenameComparator, EnumQueryComparator surnameComparator,
			EnumQueryComparator searchUserTypeComparator, EnumQueryComparator ranksComparator) {
		return readOnlyCriteriaQuery((query, root, builder) -> {
			List<Predicate> predicates = new ArrayList<>();

			getSingularRestriction(User_.id, userId, useUserId, userIdComparator, predicates, builder, root);
			getSingularRestriction(User_.forename, forename, useForename, forenameComparator, predicates, builder, root);
			getSingularRestriction(User_.surname, surname, useSurname, surnameComparator, predicates, builder, root);
			getSingularRestrictionCollection(User_.schoolClass, schoolClasses, useSchoolClass, null, predicates, builder, root);
			if (useUserType) {
				Predicate pred = null;
				switch (userType) {
					case GUEST:
						pred = builder.or(builder.isNull(root.get(User_.forename)), builder.isNull(root.get(User_.surname)));
						break;
					case PUPIL:
						pred = builder.and(builder.isNotNull(root.get(User_.forename)), builder.isNotNull(root.get(User_.surname)),
								builder.isNotNull(root.get(User_.schoolClass)));
						break;
					case TEACHER:
						pred = builder.and(builder.isNotNull(root.get(User_.forename)), builder.isNotNull(root.get(User_.surname)),
								builder.isNull(root.get(User_.schoolClass)));
						break;
					default:
						break;
				}
				if (pred != null) {
					if (searchUserTypeComparator == EnumQueryComparator.NOT_EQUAL) pred = pred.not();
					predicates.add(pred);
				}
			}
			getPluralRestrictionCollection(User_.ranks, ranks, useRanks, ranksComparator, predicates, builder, root);

			return predicates;
		});
	}

	@Override
	public byte[] getImageFromId(Long userId) {
		List<byte[]> results = readOnlyJPQLQuery("SELECT u.image from User u where u.id=?1", byte[].class, userId);
		return results.isEmpty() || results == null ? null : results.get(0);
	}

	@Override
	public List<User> getAllUsersWithJobRequests() {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<User> query = builder.createQuery(User.class);
		Root<User> user = query.from(User.class);
		query.select(user).where(builder.or(builder.isNotNull(user.get(User_.firstJobRequest)), builder.isNotNull(user.get(User_.secondJobRequest)),
				builder.isNotNull(user.get(User_.thirdJobRequest))));
		return manager.createQuery(query).getResultList();
	}

	@Override
	public void clearJobWishes(Long jobId) {
		clearJobWish(User_.firstJobRequest, jobId);
		clearJobWish(User_.secondJobRequest, jobId);
		clearJobWish(User_.thirdJobRequest, jobId);
	}

	private void clearJobWish(SingularAttribute<User, Job> wish, Long jobId) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaUpdate<User> query = builder.createCriteriaUpdate(User.class);
		Root<User> root = query.from(User.class);
		Path<Job> wishPath = root.get(wish);
		query.set(wishPath, (Job) null).where(builder.and(builder.equal(wishPath.get(Job_.id), jobId)));
		System.out.println(manager.createQuery(query).executeUpdate());
	}

}
