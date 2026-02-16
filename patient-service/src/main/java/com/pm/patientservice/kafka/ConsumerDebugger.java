package com.pm.patientservice.kafka;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.stereotype.Component;

//@Component
//public class ConsumerDebugger implements CommandLineRunner {

//    private final ApplicationContext applicationContext;
//    private final KafkaListenerEndpointRegistry registry;
//
//    public ConsumerDebugger(ApplicationContext applicationContext, KafkaListenerEndpointRegistry registry) {
//        this.applicationContext = applicationContext;
//        this.registry = registry;
//    }
//
//    @Override
//    public void run(String... args) {
//        System.out.println("=================================================");
//        System.out.println("🔎 KAFKA CONSUMER DIAGNOSTICS");
//        System.out.println("=================================================");
//
//        // CHECK 1: Is the Bean loaded?
//        boolean beanExists = applicationContext.containsBean("kafkaConsumer");
//        System.out.println("1. KafkaConsumer Bean Exists? " + (beanExists ? "✅ YES" : "❌ NO"));
//
//        if (!beanExists) {
//            System.out.println("   🔴 CRITICAL: The @Service 'KafkaConsumer' is not being scanned.");
//            System.out.println("   👉 Check that your class is in a package under your Main Application class.");
//        }
//
//        // CHECK 2: Are the Listeners running?
//        System.out.println("2. Checking Listener Containers:");
//        int containerCount = 0;
//        for (MessageListenerContainer container : registry.getListenerContainers()) {
//            containerCount++;
//            System.out.println("   - Listener ID: " + container.getListenerId());
//            System.out.println("   - Is Running?  " + (container.isRunning() ? "✅ YES" : "❌ NO"));
//            System.out.println("   - Assigned Partitions: " + container.getAssignedPartitions());
//        }
//
//        if (containerCount == 0) {
//            System.out.println("   🔴 CRITICAL: No @KafkaListener endpoints were found!");
//            System.out.println("   👉 Check your @KafkaListener annotation.");
//        }
//
//        System.out.println("=================================================");
//    }
//}