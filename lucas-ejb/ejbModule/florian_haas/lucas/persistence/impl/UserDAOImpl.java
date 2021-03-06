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
			List<String> ranks, Integer employmentsCount, Long employmentId, Boolean useUserId, Boolean useForename, Boolean useSurname,
			Boolean useSchoolClass, Boolean useUserType, Boolean useRanks, Boolean useEmploymentsCount, Boolean useEmploymentId,
			EnumQueryComparator userIdComparator, EnumQueryComparator forenameComparator, EnumQueryComparator surnameComparator,
			EnumQueryComparator searchUserTypeComparator, EnumQueryComparator ranksComparator, EnumQueryComparator employmentsCountComparator,
			EnumQueryComparator employmentIdComparator) {
		return readOnlyCriteriaQuery((query, root, builder) -> {
			List<Predicate> predicates = new ArrayList<>();

			getSingularRestriction(User_.id, userId, useUserId, userIdComparator, predicates, builder, root);
			getSingularRestriction(User_.forename, forename, useForename, forenameComparator, predicates, builder, root);
			getSingularRestriction(User_.surname, surname, useSurname, surnameComparator, predicates, builder, root);
			getSingularRestrictionCollection(User_.schoolClass, schoolClasses, useSchoolClass, null, predicates, builder, root);
			if (useUserType) {
				Predicate pred = getUserTypePredicate(userType, builder, root);
				if (pred != null) {
					if (searchUserTypeComparator == EnumQueryComparator.NOT_EQUAL) pred = pred.not();
					predicates.add(pred);
				}
			}
			getPluralRestrictionCollection(User_.ranks, ranks, useRanks, ranksComparator, predicates, builder, root);
			getSingularRestriction(builder.size(root.get(User_.employments)), employmentsCount, useEmploymentsCount, employmentsCountComparator,
					predicates, builder, root);
			getSingularRestriction(root.join(User_.employments, JoinType.LEFT).get(Employment_.id), employmentId, useEmploymentId,
					employmentIdComparator, predicates, builder, root);

			return predicates;
		});
	}

	private Predicate getUserTypePredicate(EnumUserType userType, CriteriaBuilder builder, Root<User> root) {
		switch (userType) {
			case GUEST:
				return builder.or(builder.isNull(root.get(User_.forename)), builder.isNull(root.get(User_.surname)));
			case PUPIL:
				return builder.and(builder.isNotNull(root.get(User_.forename)), builder.isNotNull(root.get(User_.surname)),
						builder.isNotNull(root.get(User_.schoolClass)));
			case TEACHER:
				return builder.and(builder.isNotNull(root.get(User_.forename)), builder.isNotNull(root.get(User_.surname)),
						builder.isNull(root.get(User_.schoolClass)));
		}
		return null;
	}

	@Override
	public byte[] getImageFromId(Long userId) {
		List<byte[]> results = readOnlyJPQLQuery("SELECT u.image from User u where u.id=?1", byte[].class, userId);
		return results.isEmpty() || results == null ? null : results.get(0);
	}

	@Override
	public List<User> getAllUsersWithNoEmployments(EnumSet<EnumUserType> permittedUserTypes) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<User> query = builder.createQuery(User.class);
		Root<User> user = query.from(User.class);
		List<Predicate> enumsPredicate = new ArrayList<>();
		permittedUserTypes.forEach(userType -> {
			enumsPredicate.add(getUserTypePredicate(userType, builder, user));
		});
		query.select(user).where(
				builder.and(builder.or(enumsPredicate.toArray(new Predicate[enumsPredicate.size()])), builder.isEmpty(user.get(User_.employments))));
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
		manager.createQuery(query).executeUpdate();
	}

	@Override
	public List<User> getUsersFromData(String data, Integer resultsCount) {
		data = data.trim();
		if (!data.isEmpty()) {
			CriteriaBuilder builder = manager.getCriteriaBuilder();
			CriteriaQuery<User> query = builder.createQuery(User.class);
			query.distinct(true);
			Root<User> user = query.from(User.class);
			List<Predicate> predicates = new ArrayList<>();
			Expression<String> forename = user.get(User_.forename);
			Expression<String> surname = user.get(User_.surname);
			try {
				Long id = Long.parseLong(data);
				predicates.add(builder.equal(user.get(User_.id), id));
				predicates.add(builder.equal(user.join(User_.idCards, JoinType.LEFT).get(IdCard_.id), id));
			}
			catch (NumberFormatException e) {}
			data = "%" + data.replaceAll(" ", "%") + "%";
			predicates.add(
					builder.or(builder.like(builder.concat(forename, surname), data), builder.like(surname, data), builder.like(forename, data)));
			query.select(user).where(builder.or(predicates.toArray(new Predicate[predicates.size()])));
			return manager.createQuery(query).setMaxResults(resultsCount).getResultList();
		}
		return new ArrayList<>();
	}

}
