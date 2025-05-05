package com.github.hkereb.swiftcodeapi.init;

import com.github.hkereb.swiftcodeapi.service.SwiftCodeService;
import com.github.hkereb.swiftcodeapi.util.SwiftCodeExcelParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class DataInitializer {

    @Autowired
    private SwiftCodeService swiftCodeService;

    @Autowired
    private SwiftCodeExcelParser swiftCodeExcelParser;

    @PostConstruct
    public void init() {
        if (!swiftCodeService.isDatabaseInitialized()) {
            try {
                swiftCodeExcelParser.parseExcelFile("src/main/resources/data/Interns_2025_SWIFT_CODES.xlsx");
                System.out.println("Database successfully initialized.");
            } catch (Exception e) {
                System.err.println("Error initializing database: " + e.getMessage());
            }
        } else {
            System.out.println("Database is already initialized.");
        }
    }
}
