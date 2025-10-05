package za.ac.cput.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.Shipment;
import za.ac.cput.domain.Order;
import za.ac.cput.repository.ShipmentRepository;
import za.ac.cput.service.ShipmentService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ShipmentServiceImpl implements ShipmentService {

    private final ShipmentRepository shipmentRepository;

    @Autowired
    public ShipmentServiceImpl(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    @Override
    public Shipment save(Shipment shipment) {
        return shipmentRepository.save(shipment);
    }

    @Override
    public Optional<Shipment> findById(String shipmentId) {
        return shipmentRepository.findById(shipmentId);
    }

    @Override
    public List<Shipment> findAll() {
        return shipmentRepository.findAll();
    }

    @Override
    public void deleteById(String shipmentId) {
        shipmentRepository.deleteById(shipmentId);
    }

    @Override
    public Optional<Shipment> findByOrder(Order order) {
        return shipmentRepository.findByOrder(order);
    }

    @Override
    public List<Shipment> findByStatus(String status) {
        return shipmentRepository.findByStatus(status);
    }

    @Override
    public List<Shipment> findByCarrier(String carrier) {
        return shipmentRepository.findByCarrier(carrier);
    }

    @Override
    public Optional<Shipment> findByTrackingNumber(String trackingNumber) {
        return shipmentRepository.findByTrackingNumber(trackingNumber);
    }

    @Override
    public List<Shipment> findByShippedDateBetween(LocalDateTime start, LocalDateTime end) {
        return shipmentRepository.findByShippedDateBetween(start, end);
    }

    @Override
    public List<Shipment> findByDeliveryDateBetween(LocalDateTime start, LocalDateTime end) {
        return shipmentRepository.findByDeliveryDateBetween(start, end);
    }

    @Override
    public List<Shipment> findByStatusOrderByDeliveryDateAsc(String status) {
        return shipmentRepository.findByStatusOrderByDeliveryDateAsc(status);
    }
}
