package rk.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Meeting.class)
public abstract class Meeting_ {

	public static volatile SingularAttribute<Meeting, LocalDateTime> meetingDateEnd;
	public static volatile SingularAttribute<Meeting, LocalDateTime> submitDate;
	public static volatile SingularAttribute<Meeting, LocalDateTime> meetingDateBegin;
	public static volatile SingularAttribute<Meeting, Long> id;
	public static volatile SingularAttribute<Meeting, User> user;
	public static volatile SingularAttribute<Meeting, Room> room;

}

