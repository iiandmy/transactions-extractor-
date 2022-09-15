package by.itechart.transactionsExctractor.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
public class Transaction {

    @Id
    @Column(name = "id")
    private String transactionId;

    //TODO check ManyToOne OR OneToMany
    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "operator_id")
    private Operator operator;

    //TODO String->Date example 2021-04-29 16:46:13
    @Column(name="time_date")
    private String creationTime;
    private long amount;
    private String currency;
    private Boolean status;

    //for xml file
    public Transaction(String transactionId, User user, String timestamp, long amount, String currency, Boolean status) {
        this.transactionId = transactionId;
        this.user = user;
        this.creationTime = timestamp;
        this.amount = amount;
        this.currency = currency;
        this.status = status;
    }

    public void copy(Transaction transaction) {
        this.amount = transaction.amount;
        this.currency = transaction.currency;
        this.creationTime = transaction.creationTime;
        this.status = transaction.status;
        this.user = transaction.user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return amount == that.amount && transactionId.equals(that.transactionId) && user.equals(that.user) &&
                creationTime.equals(that.creationTime) && currency.equals(that.currency) && status.equals(that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId, user, creationTime, amount, currency, status);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId='" + transactionId + '\'' +
                ", user=" + user +
                ", timestamp='" + creationTime + '\'' +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", status=" + status +
                '}';
    }
}
