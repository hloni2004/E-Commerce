package za.ac.cput.service;

import za.ac.cput.domain.Shipment;
import za.ac.cput.domain.Order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ShipmentService {
    Shipment save(Shipment shipment);
    Optional<Shipment> findById(String shipmentId);
    List<Shipment> findAll();
    void deleteById(String shipmentId);

    Optional<Shipment> findByOrder(Order order);
    List<Shipment> findByStatus(String status);
    List<Shipment> findByCarrier(String carrier);
    Optional<Shipment> findByTrackingNumber(String trackingNumber);
    List<Shipment> findByShippedDateBetween(LocalDateTime start, LocalDateTime end);
    List<Shipment> findByDeliveryDateBetween(LocalDateTime start, LocalDateTime end);
    List<Shipment> findByStatusOrderByDeliveryDateAsc(String status);
}
