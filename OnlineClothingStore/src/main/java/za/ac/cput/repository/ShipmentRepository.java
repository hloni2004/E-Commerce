package za.ac.cput.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.domain.Shipment;
import za.ac.cput.domain.Order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, String> {
    Optional<Shipment> findByOrder(Order order);
    List<Shipment> findByStatus(String status);
    List<Shipment> findByCarrier(String carrier);
    Optional<Shipment> findByTrackingNumber(String trackingNumber);
    List<Shipment> findByShippedDateBetween(LocalDateTime start, LocalDateTime end);
    List<Shipment> findByDeliveryDateBetween(LocalDateTime start, LocalDateTime end);
    List<Shipment> findByStatusOrderByDeliveryDateAsc(String status);
}
