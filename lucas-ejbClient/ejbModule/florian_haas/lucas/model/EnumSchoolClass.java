package florian_haas.lucas.model;

import java.util.*;
import java.util.function.Predicate;

import florian_haas.lucas.persistence.EnumQueryComparator;
import florian_haas.lucas.util.Utils;

public enum EnumSchoolClass {

	A5,
	B5,
	C5,
	D5,
	E5,
	A6,
	B6,
	C6,
	D6,
	E6,
	A7,
	B7,
	C7,
	D7,
	E7,
	A8,
	B8,
	C8,
	D8,
	E8,
	A9,
	B9,
	C9,
	D9,
	E9,
	A10,
	B10,
	C10,
	D10,
	E10,
	A11,
	B11,
	C11,
	D11,
	E11,
	A12,
	B12,
	C12,
	D12,
	E12;

	private final Integer grade;
	private final String schoolClass;

	public static final Set<Integer> GRADES;
	public static final Set<String> CLASSES;

	public static final EnumSchoolClass[] ALL_VALUES = EnumSchoolClass.values();

	static {
		Set<Integer> tmpGrades = new HashSet<>();
		Set<String> tmpClasses = new HashSet<>();
		for (EnumSchoolClass value : EnumSchoolClass.values()) {
			tmpGrades.add(value.grade);
			tmpClasses.add(value.schoolClass);
		}
		GRADES = Collections.unmodifiableSet(tmpGrades);
		CLASSES = Collections.unmodifiableSet(tmpClasses);
	}

	private EnumSchoolClass() {
		this.grade = Integer.parseInt(this.name().substring(1, this.name().length()));
		this.schoolClass = Character.toString(this.name().charAt(0)).toLowerCase();
	}

	public static EnumSchoolClass getEnumFromValue(String value) {
		EnumSchoolClass ret = null;
		for (EnumSchoolClass val : ALL_VALUES) {
			if (val.name().trim().equals(value.trim())) {
				ret = val;
				break;
			}
		}
		return ret;
	}

	public Integer getGrade() {
		return this.grade;
	}

	public String getSchoolClass() {
		return this.schoolClass;
	}

	@Override
	public String toString() {
		return this.grade.toString() + this.schoolClass;
	}

	public static List<EnumSchoolClass> getMatchingClasses(Integer schoolGrade, String schoolClass, EnumQueryComparator schoolGradeComparator,
			EnumQueryComparator schoolClassComparator) {
		List<EnumSchoolClass> ret = new ArrayList<>();
		if (schoolGradeComparator == EnumQueryComparator.NULL && schoolClass != null && schoolClassComparator != EnumQueryComparator.NULL
				|| schoolGrade != null && schoolGradeComparator != EnumQueryComparator.NULL && schoolClassComparator == EnumQueryComparator.NULL) {
			;
		} else if (schoolGradeComparator == EnumQueryComparator.NULL || schoolClassComparator == EnumQueryComparator.NULL) {
			ret.add(null);
		} else if (schoolClass != null || schoolGrade != null) {
			for (EnumSchoolClass value : EnumSchoolClass.values()) {
				Predicate<Integer> gradePred = getPredicateFromQueryComparator(schoolGradeComparator, EnumQueryComparator.NUMERIC_COMPARATORS,
						value.getGrade());
				Predicate<String> schoolClassPred = getPredicateFromQueryComparator(schoolClassComparator, EnumQueryComparator.NUMERIC_COMPARATORS,
						value.getSchoolClass());
				if ((schoolGrade != null && schoolClass != null && gradePred.test(schoolGrade) && schoolClassPred.test(schoolClass))
						|| (schoolGrade != null && schoolClass == null && gradePred.test(schoolGrade))
						|| (schoolGrade == null && schoolClass != null && schoolClassPred.test(schoolClass))) {
					ret.add(value);
				}
			}
		}
		return ret;
	}

	private static <T extends Comparable<? super T>> Predicate<T> getPredicateFromQueryComparator(EnumQueryComparator comparator,
			EnumQueryComparator[] possibleValues, T val) {
		Predicate<T> ret = val::equals;
		if (comparator != null && Arrays.asList(possibleValues).contains(comparator)) {
			switch (comparator) {
				case EQUAL:
				case CONTAINS:
				case MEMBER_OF:
				case CONTAINS_NOT:
				case NOT_MEMBER_OF:
				case EMPTY:
				case NOT_EMPTY:
					break;
				case NULL:
					ret = (arg) -> {
						return Boolean.FALSE;
					};
					break;
				case NOT_NULL:
					ret = (arg) -> {
						return Boolean.TRUE;
					};
					break;
				case GREATER_EQUAL:
					ret = (arg) -> {
						return Utils.isGreatherEqual(val, arg);
					};
					break;
				case GREATER_THAN:
					ret = (arg) -> {
						return Utils.isGreatherThan(val, arg);
					};
					break;
				case LESS_EQUAL:
					ret = (arg) -> {
						return Utils.isLessEqual(val, arg);
					};
					break;
				case LESS_THAN:
					ret = (arg) -> {
						return Utils.isLessThan(val, arg);
					};
					break;
				case NOT_EQUAL:
					ret = (arg) -> {
						return !arg.equals(val);
					};
					break;
			}
		}
		return ret;
	}
}
