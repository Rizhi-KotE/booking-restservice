package rk.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = 'users')
class User {
    @Id
    @GeneratedValue
    long id;

    @Column(name = 'username')
    String username

}
