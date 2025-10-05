package za.ac.cput.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "shipment")
public class Shipment {

    @Id
    @Column(name = "shipment_id")
    private String shipmentId;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "tracking_number")
    private String trackingNumber;

    @Column(name = "carrier")
    private String carrier;

    @Column(name = "status")
    private String status; // PENDING, IN_TRANSIT, DELIVERED

    @Column(name = "shipped_date")
    private LocalDateTime shippedDate;

    @Column(name = "delivery_date")
    private LocalDateTime deliveryDate;

    protected Shipment() {}

    private Shipment(Builder builder) {
        this.shipmentId = builder.shipmentId;
        this.order = builder.order;
        this.trackingNumber = builder.trackingNumber;
        this.carrier = builder.carrier;
        this.status = builder.status;
        this.shippedDate = builder.shippedDate;
        this.deliveryDate = builder.deliveryDate;
    }

    public String getShipmentId() { return shipmentId; }
    public Order getOrder() { return order; }
    public String getTrackingNumber() { return trackingNumber; }
    public String getCarrier() { return carrier; }
    public String getStatus() { return status; }
    public LocalDateTime getShippedDate() { return shippedDate; }
    public LocalDateTime getDeliveryDate() { return deliveryDate; }

    @Override
    public String toString() {
        return "Shipment{" +
                "shipmentId='" + shipmentId + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public static class Builder {
        private String shipmentId;
        private Order order;
        private String trackingNumber;
        private String carrier;
        private String status;
        private LocalDateTime shippedDate;
        private LocalDateTime deliveryDate;

        public Builder setShipmentId(String shipmentId) { this.shipmentId = shipmentId; return this; }
        public Builder setOrder(Order order) { this.order = order; return this; }
        public Builder setTrackingNumber(String trackingNumber) { this.trackingNumber = trackingNumber; return this; }
        public Builder setCarrier(String carrier) { this.carrier = carrier; return this; }
        public Builder setStatus(String status) { this.status = status; return this; }
        public Builder setShippedDate(LocalDateTime shippedDate) { this.shippedDate = shippedDate; return this; }
        public Builder setDeliveryDate(LocalDateTime deliveryDate) { this.deliveryDate = deliveryDate; return this; }

        public Shipment build() { return new Shipment(this); }
    }
}
