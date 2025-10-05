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
        // Add validation as needed
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
