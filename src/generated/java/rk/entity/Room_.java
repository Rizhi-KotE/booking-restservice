package rk.entity;

import java.time.LocalTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Room.class)
public abstract class Room_ {

	public static volatile SingularAttribute<Room, LocalTime> officeHoursBegin;
	public static volatile SingularAttribute<Room, Long> id;
	public static volatile SingularAttribute<Room, LocalTime> officeHoursEnd;

}

