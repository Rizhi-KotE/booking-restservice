package rk.repository;

import org.springframework.data.jpa.domain.Specification;
import rk.entity.Meeting;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class MeetingSpecification {
    public static Specification<Meeting> maxLess() {
        return new Specification<Meeting>(){
            @Override
            public Predicate toPredicate(Root<Meeting> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                CriteriaQuery<Meeting> query = cb.createQuery(Meeting.class);
                Root<Meeting> from = query.from(Meeting.class);
                query.select(from);
                return cb.in(from);
            }
        };
    }
}
