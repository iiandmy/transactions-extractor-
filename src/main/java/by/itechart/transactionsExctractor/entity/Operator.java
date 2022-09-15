package by.itechart.transactionsExctractor.entity;

import java.util.Objects;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "operator")
@Getter
@Setter
@RequiredArgsConstructor
public class Operator {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String login;
    private String password;
    private String permission;

    public final static String UPLOAD_PERMISSION = "FILE_UPLOAD";
    public final static String EXECUTE_COMMAND = "EXECUTE_COMMAND";

    public Operator(String login, String password) {
        this.login = login;
        this.password = password;
        this.permission = Operator.UPLOAD_PERMISSION;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Operator operator = (Operator) o;
        return id.equals(operator.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Operator{" +
                "id='" + id + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", permission='" + permission + '\'' +
                '}';
    }
}
