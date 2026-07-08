package com.jobportal.jobservice.repository;

import com.jobportal.jobservice.domain.Job;
import com.jobportal.jobservice.domain.JobStatus;
import com.jobportal.jobservice.dto.JobSearchRequest;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;

public class JobSpecification {

    private JobSpecification() {
    }

    public static Specification<Job> build(JobSearchRequest request) {
        return (root, query, cb) -> {
            var predicates = new ArrayList<Predicate>();
            predicates.add(cb.isTrue(root.get("active")));

            var jobStatus = request.jobStatus() != null ? request.jobStatus() : JobStatus.OPEN;
            predicates.add(cb.equal(root.get("jobStatus"), jobStatus));

            if (request.jobType() != null) predicates.add(cb.equal(root.get("jobType"), request.jobType()));
            if (request.workMode() != null) predicates.add(cb.equal(root.get("workMode"), request.workMode()));
            if (request.experienceLevel() != null) predicates.add(cb.equal(root.get("experienceLevel"), request.experienceLevel()));
            if (request.companyId() != null) predicates.add(cb.equal(root.get("companyId"), request.companyId()));
            if (request.categoryId() != null) predicates.add(cb.equal(root.get("jobCategory").get("id"), request.categoryId()));

            if (request.keyword() != null && !request.keyword().isBlank()) {
                var pattern = "%" + request.keyword().toLowerCase() + "%";
                predicates.add(cb.or(
                        cb.like(cb.lower(root.get("title")), pattern),
                        cb.like(cb.lower(root.get("description")), pattern)
                ));
            }

            if (request.location() != null && !request.location().isBlank()) {
                var pattern = "%" + request.location().toLowerCase() + "%";
                Path<String> city = root.get("location").get("city");
                Path<String> state = root.get("location").get("state");
                Path<String> country = root.get("location").get("country");
                predicates.add(cb.or(
                        cb.like(cb.lower(city),pattern),
                        cb.like(cb.lower(state),pattern),
                        cb.like(cb.lower(country),pattern)
                ));
            }

            if (request.minSalary() != null) predicates.add(cb.greaterThanOrEqualTo(root.get("salaryRange").get("maxSalary"), request.minSalary()));
            if (request.maxSalary() != null) predicates.add(cb.lessThanOrEqualTo(root.get("salaryRange").get("minSalary"), request.maxSalary()));

            if (request.minOpenings() != null) predicates.add(cb.greaterThanOrEqualTo(root.get("openings"), request.minOpenings()));
            if (request.maxOpenings() != null) predicates.add(cb.lessThanOrEqualTo(root.get("openings"), request.maxOpenings()));

            if (request.skillIds() != null && !request.skillIds().isEmpty()) {
                var subquery = query.subquery(Long.class);
                var subRoot = subquery.correlate(root);
                var skillJoin = subRoot.join("skills");
                subquery.select(skillJoin.get("id")).where(skillJoin.get("id").in(request.skillIds()));
                predicates.add(cb.exists(subquery));
            }

            if (request.tagIds() != null && !request.tagIds().isEmpty()) {
                var subquery = query.subquery(Long.class);
                var subRoot = subquery.correlate(root);
                var tagJoin = subRoot.join("tags");
                subquery.select(tagJoin.get("id")).where(tagJoin.get("id").in(request.tagIds()));
                predicates.add(cb.exists(subquery));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
