package by.itechart.transactionsExctractor.entity;


import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "customer")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    private String id;

    private String firstName;
    private String lastName;
    private String email;

    // TODO: use hear lombok
    public User(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
