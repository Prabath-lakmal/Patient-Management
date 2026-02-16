package com.pm.patientservice.kafka;

import com.pm.patientservice.model.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import patient.events.PatientEvent;

@Service
public class KafkaProducer {
    private static final Logger log = LoggerFactory.getLogger(KafkaProducer.class);
    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, byte[]> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(Patient patient){
        // We use the ID as the key to ensure order guarantees
        String patientId = patient.getId().toString();

        PatientEvent event = PatientEvent.newBuilder()
                .setPatientId(patientId)
                .setName(patient.getName())
                .setEmail(patient.getEmail())
                .setEventType("PATIENT_CREATED")
                .build();
        try{
            // FIX 1: Send with Key (patientId)
            kafkaTemplate.send("patient", patientId, event.toByteArray());
            log.info("Event sent successfully for patient: {}", patientId);
        }catch(Exception e){
            // FIX 2: Log the exception 'e' to see connection errors in the future
            log.error("Error sending PatientCreated event for ID: {}", patientId, e);
        }
    }
}
