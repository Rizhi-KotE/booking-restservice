package rk.service

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification
import rk.dto.MeetingRestParams
import rk.entity.Meeting
import rk.entity.Meeting_
import rk.entity.Room_
import rk.entity.User_

import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root
import java.awt.print.Pageable
import java.time.LocalDateTime

import static java.lang.Long.parseLong
import static org.springframework.data.jpa.domain.Specifications.where

class MeetingPredicateBuilder {

    static PageRequest buildPageable(MeetingRestParams options){
        def sort = new Sort(options.direction, options.sortColumn)
        new PageRequest(options.page,options.pageSize, sort)
    }

    static Specification<Meeting> buildPredicate(MeetingRestParams params) {
        Specification<Meeting> spec = constantTrue()
        if (params.userId != null) {
            spec = where(spec).and(byUserId(params.userId))
        }
        if (params.roomId != null) {
            spec = where(spec).and(byRoomId(params.roomId))
        }
        if (params.lowerDateTimeBound != null) {
            spec = where(spec).and(byAfterTime(params.lowerDateTimeBound))
        }
        if (params.upperDateTimeBound != null) {
            spec = where(spec).and(byBeforeTime(params.upperDateTimeBound))
        }
        spec
    }

    static Specification<Meeting> byAfterTime(LocalDateTime localDateTime) {
        new Specification<Meeting>() {
            @Override
            Predicate toPredicate(Root<Meeting> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                cb.greaterThanOrEqualTo(root.get(Meeting_.submitDate), localDateTime)
            }
        }
    }

    static Specification<Meeting> byBeforeTime(LocalDateTime localDateTime) {
        new Specification<Meeting>() {
            @Override
            Predicate toPredicate(Root<Meeting> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                cb.lessThanOrEqualTo(root.get(Meeting_.submitDate), localDateTime)
            }
        }
    }

    static Specification<Meeting> byRoomId(long id) {
        new Specification<Meeting>() {
            @Override
            Predicate toPredicate(Root<Meeting> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                cb.equal(root.get(Meeting_.room).get(Room_.id), id)
            }
        }
    }

    static Specification<Meeting> byUserId(long id) {
        new Specification<Meeting>() {
            @Override
            Predicate toPredicate(Root<Meeting> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                cb.equal(root.get(Meeting_.user).get(User_.id), id)
            }
        }
    }

    static Specification<Meeting> constantTrue() {
        new Specification<Meeting>() {
            @Override
            Predicate toPredicate(Root<Meeting> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                cb.and()
            }
        }
    }

}
