package za.ac.cput.factory;

import za.ac.cput.domain.Shipment;
import za.ac.cput.domain.Order;

import java.time.LocalDateTime;

public class ShipmentFactory {
    public static Shipment createShipment(
            String shipmentId,
            Order order,
            String trackingNumber,
            String carrier,
            String status,
            LocalDateTime shippedDate,
            LocalDateTime deliveryDate
    ) {
        if (shipmentId == null || shipmentId.isEmpty())
            throw new IllegalArgumentException("Shipment ID is required");
        if (order == null)
            throw new IllegalArgumentException("Order is required");
        if (trackingNumber == null || trackingNumber.isEmpty())
            throw new IllegalArgumentException("Tracking number is required");
        if (carrier == null || carrier.isEmpty())
            throw new IllegalArgumentException("Carrier is required");
        if (status == null || status.isEmpty())
            throw new IllegalArgumentException("Status is required");
        if (shippedDate == null)
            throw new IllegalArgumentException("Shipped date is required");
        if (deliveryDate == null)
            throw new IllegalArgumentException("Delivery date is required");

        return new Shipment.Builder()
                .setShipmentId(shipmentId)
                .setOrder(order)
                .setTrackingNumber(trackingNumber)
                .setCarrier(carrier)
                .setStatus(status)
                .setShippedDate(shippedDate)
                .setDeliveryDate(deliveryDate)
                .build();
    }
}
