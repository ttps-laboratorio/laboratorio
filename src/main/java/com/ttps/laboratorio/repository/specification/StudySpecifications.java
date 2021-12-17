package com.ttps.laboratorio.repository.specification;

import java.util.Optional;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.data.jpa.domain.Specification;

import com.ttps.laboratorio.dto.request.StudySearchFilterDTO;
import com.ttps.laboratorio.entity.Checkpoint;
import com.ttps.laboratorio.entity.Patient;
import com.ttps.laboratorio.entity.Study;
import com.ttps.laboratorio.entity.StudyType;

public class StudySpecifications {

	public static Specification<Study> all(StudySearchFilterDTO filter) {
		return withDni(filter.getDni()).and(withStatus(filter.getStudyStatusId()))
				.and(withType(filter.getStudyTypeId()));
	}

	public static Specification<Study> withStatus(Long statusId) {
		return (root, query, builder) -> {
			return Optional.ofNullable(statusId).map((id) -> {
				Join<Study,Checkpoint> checkpointJoin = root.join("checkpoints");
				Subquery<Integer> subquery = query.subquery(Integer.class);
				subquery.select(builder.literal(1));
				Root<Checkpoint> subRoot = subquery.from(Checkpoint.class);
				subquery.groupBy(subRoot.get("study"));
				subquery.having(builder.and(
						builder.equal(checkpointJoin.get("createdAt"), builder.max(subRoot.get("createdAt"))),
						builder.equal(checkpointJoin.get("status").get("id"), id),
						builder.equal(checkpointJoin.get("study"), subRoot.get("study"))));
				return builder.exists(subquery);
			}).orElse(null);
		};
	}

	public static Specification<Study> withDni(Long dni) {
		return (root, query, builder) -> {
			return Optional.ofNullable(dni).map((d) -> {
				Join<Study,Patient> joinPatient = root.join("patient");
				return builder.equal(joinPatient.get("dni"), d);
			}).orElse(null);
		};
	}

	public static Specification<Study> withType(Long typeId) {
		return (root, query, builder) -> {
			return Optional.ofNullable(typeId).map((id) -> {
				Join<Study, StudyType> joinType = root.join("type");
				return builder.equal(joinType.get("id"), id);
			}).orElse(null);
		};
	}
}
