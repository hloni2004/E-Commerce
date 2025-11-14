package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Shipment;
import za.ac.cput.service.ShipmentService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/shipments")
@CrossOrigin(origins = "*")
public class ShipmentController {

    private final ShipmentService shipmentService;

    @Autowired
    public ShipmentController(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @PostMapping
    public ResponseEntity<Shipment> save(@RequestBody Shipment shipment) {
        Shipment saved = shipmentService.save(shipment);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Shipment> findById(@PathVariable String id) {
        Optional<Shipment> shipment = shipmentService.findById(id);
        return shipment.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<Shipment>> findAll() {
        List<Shipment> shipments = shipmentService.findAll();
        return new ResponseEntity<>(shipments, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        shipmentService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<Shipment> findByOrderId(@PathVariable String orderId) {
        // Note: Service expects Order object, this endpoint may need OrderService to fetch order first
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Shipment>> findByStatus(@PathVariable String status) {
        List<Shipment> shipments = shipmentService.findByStatus(status);
        return new ResponseEntity<>(shipments, HttpStatus.OK);
    }

    @GetMapping("/carrier/{carrier}")
    public ResponseEntity<List<Shipment>> findByCarrier(@PathVariable String carrier) {
        List<Shipment> shipments = shipmentService.findByCarrier(carrier);
        return new ResponseEntity<>(shipments, HttpStatus.OK);
    }

    @GetMapping("/tracking/{trackingNumber}")
    public ResponseEntity<Shipment> findByTrackingNumber(@PathVariable String trackingNumber) {
        Optional<Shipment> shipment = shipmentService.findByTrackingNumber(trackingNumber);
        return shipment.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/shipped/date-range")
    public ResponseEntity<List<Shipment>> findByShippedDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        List<Shipment> shipments = shipmentService.findByShippedDateBetween(start, end);
        return new ResponseEntity<>(shipments, HttpStatus.OK);
    }

    @GetMapping("/delivery/date-range")
    public ResponseEntity<List<Shipment>> findByDeliveryDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        List<Shipment> shipments = shipmentService.findByDeliveryDateBetween(start, end);
        return new ResponseEntity<>(shipments, HttpStatus.OK);
    }

    @GetMapping("/status/{status}/ordered")
    public ResponseEntity<List<Shipment>> findByStatusOrdered(@PathVariable String status) {
        List<Shipment> shipments = shipmentService.findByStatusOrderByDeliveryDateAsc(status);
        return new ResponseEntity<>(shipments, HttpStatus.OK);
    }
}
