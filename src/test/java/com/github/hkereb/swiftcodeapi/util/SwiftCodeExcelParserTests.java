package com.github.hkereb.swiftcodeapi.util;

import com.github.hkereb.swiftcodeapi.exceptions.InvalidExcelFormatException;
import com.github.hkereb.swiftcodeapi.exceptions.InvalidInputException;
import com.github.hkereb.swiftcodeapi.repository.SwiftCodeRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class SwiftCodeExcelParserTests {
    private SwiftCodeExcelParser parser;
    private SwiftCodeRepository swiftCodeRepository;

    @BeforeEach
    void setUp() {
        swiftCodeRepository = mock(SwiftCodeRepository.class);
        parser = new SwiftCodeExcelParser();
        parser.setSwiftCodeRepository(swiftCodeRepository);
    }

    @Test
    public void parseExcelFile_AllInputDataCorrect_SetAllData() throws InvalidInputException, IOException, InvalidExcelFormatException {
        File tempFile = File.createTempFile("swift_test", ".xlsx");

        try (XSSFWorkbook workbook = new XSSFWorkbook();
             FileOutputStream out = new FileOutputStream(tempFile)) {

            var sheet = workbook.createSheet("Data");
            var header = sheet.createRow(0);
            header.createCell(0).setCellValue("COUNTRY ISO2 CODE");
            header.createCell(1).setCellValue("SWIFT CODE");
            header.createCell(2).setCellValue("CODE TYPE");
            header.createCell(3).setCellValue("NAME");
            header.createCell(4).setCellValue("ADDRESS");
            header.createCell(5).setCellValue("TOWN NAME");
            header.createCell(6).setCellValue("COUNTRY NAME");
            header.createCell(7).setCellValue("TIME ZONE");

            var row = sheet.createRow(1);
            row.createCell(0).setCellValue("TT");
            row.createCell(1).setCellValue("TESTCODEXXX");
            row.createCell(2).setCellValue("BIC11");
            row.createCell(3).setCellValue("TEST BANK NAME");
            row.createCell(4).setCellValue("TEST BANK ADDRESS");
            row.createCell(5).setCellValue("TEST TOWN NAME");
            row.createCell(6).setCellValue("TEST COUNTRY");
            row.createCell(7).setCellValue("TEST TIME/ZONE");

            workbook.write(out);
        }

        try (FileInputStream fis = new FileInputStream(tempFile)) {
            parser.parseExcelFile(fis);
        }

        verify(swiftCodeRepository, times(1)).save(argThat(entity ->
                entity.getSwiftCode().equals("TESTCODEXXX") &&
                        entity.getCountryISO2().equals("TT") &&
                        entity.getBankName().equals("TEST BANK NAME") &&
                        entity.getBankAddress().equals("TEST BANK ADDRESS") &&
                        entity.getTownName().equals("TEST TOWN NAME") &&
                        entity.getCountryName().equals("TEST COUNTRY") &&
                        entity.getTimeZone().equals("TEST TIME/ZONE") &&
                        entity.getCodeType().equals("BIC11") &&
                        entity.getIsHeadquarter()
        ));

        tempFile.deleteOnExit();
    }

    @Test
    void parseExcelFile_BankAddressCellEmpty_SetBankAddressToNull() throws IOException, InvalidExcelFormatException {
        File tempFile = File.createTempFile("swift_test_empty_address", ".xlsx");
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Data");

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("COUNTRY ISO2 CODE");
        header.createCell(1).setCellValue("SWIFT CODE");
        header.createCell(2).setCellValue("CODE TYPE");
        header.createCell(3).setCellValue("NAME");
        header.createCell(4).setCellValue("ADDRESS");
        header.createCell(5).setCellValue("TOWN NAME");
        header.createCell(6).setCellValue("COUNTRY NAME");
        header.createCell(7).setCellValue("TIME ZONE");

        Row row = sheet.createRow(1);
        row.createCell(0).setCellValue("TT");
        row.createCell(1).setCellValue("TESTCODEXXX");
        row.createCell(2).setCellValue("BIC11");
        row.createCell(3).setCellValue("TEST BANK NAME");
        row.createCell(4).setCellValue("");
        row.createCell(5).setCellValue("TEST TOWN NAME");
        row.createCell(6).setCellValue("TEST COUNTRY");
        row.createCell(7).setCellValue("Test TIME/ZONE");

        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            workbook.write(fos);
        }
        workbook.close();

        try (FileInputStream fis = new FileInputStream(tempFile)) {
            parser.parseExcelFile(fis);
        }

        verify(swiftCodeRepository, times(1)).save(argThat(entity ->
                entity.getBankAddress() == null
        ));

        tempFile.deleteOnExit();
    }

    @Test
    public void parseExcelFile_InvalidHeaders_ThrowsInvalidExcelFormatException() throws IOException {
        File tempFile = File.createTempFile("swift_invalid_header", ".xlsx");
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Data");

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("WRONG HEADER 1");
        header.createCell(1).setCellValue("WRONG HEADER 2");

        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            workbook.write(fos);
        }
        workbook.close();

        try (FileInputStream fis = new FileInputStream(tempFile)) {
            Assertions.assertThrows(InvalidExcelFormatException.class, () ->
                    parser.parseExcelFile(fis)
            );
        }

        tempFile.deleteOnExit();
    }

}
