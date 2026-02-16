package com.pm.patientservice.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import patient.events.PatientEvent;

public class KafkaConsumer {
    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

    // 1. Group ID ensures we track which messages we've read.
    // 2. We accept byte[] because the Producer sent event.toByteArray()
    @KafkaListener(topics = "patient", groupId = "patient-service-group")
    public void consume(byte[] messageData) {
        try {
            // Convert bytes back to Protobuf Object
            PatientEvent event = PatientEvent.parseFrom(messageData);

            log.info("Received Patient Event: ID={}, Name={}, Type={}",
                    event.getPatientId(), event.getName(), event.getEventType());

            // Add your business logic here (e.g., save to DB, trigger notification)

        } catch (Exception e) {
            log.error("Error deserializing PatientEvent from Kafka", e);
        }
    }
}
