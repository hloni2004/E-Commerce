package za.ac.cput.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @Column(name = "payment_id")
    private String paymentId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "amount")
    private double amount;

    @Column(name = "payment_date")
    private LocalDateTime paymentDate;

    @Column(name = "payment_method")
    private String paymentMethod; // CARD, EFT, COD

    @Column(name = "status")
    private String status; // SUCCESS, FAILED, PENDING

    protected Payment() {}

    private Payment(Builder builder) {
        this.paymentId = builder.paymentId;
        this.order = builder.order;
        this.amount = builder.amount;
        this.paymentDate = builder.paymentDate;
        this.paymentMethod = builder.paymentMethod;
        this.status = builder.status;
    }

    public String getPaymentId() { return paymentId; }
    public Order getOrder() { return order; }
    public double getAmount() { return amount; }
    public LocalDateTime getPaymentDate() { return paymentDate; }
    public String getPaymentMethod() { return paymentMethod; }
    public String getStatus() { return status; }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentId='" + paymentId + '\'' +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                '}';
    }

    public static class Builder {
        private String paymentId;
        private Order order;
        private double amount;
        private LocalDateTime paymentDate;
        private String paymentMethod;
        private String status;

        public Builder setPaymentId(String paymentId) { this.paymentId = paymentId; return this; }
        public Builder setOrder(Order order) { this.order = order; return this; }
        public Builder setAmount(double amount) { this.amount = amount; return this; }
        public Builder setPaymentDate(LocalDateTime paymentDate) { this.paymentDate = paymentDate; return this; }
        public Builder setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; return this; }
        public Builder setStatus(String status) { this.status = status; return this; }

        public Payment build() { return new Payment(this); }
    }
}
