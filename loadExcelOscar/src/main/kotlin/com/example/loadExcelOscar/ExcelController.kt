package com.example.loadExcelOscar

import com.github.pjfanning.xlsx.StreamingReader
import org.apache.poi.openxml4j.opc.OPCPackage
import org.apache.poi.ss.usermodel.DataFormatter
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.apache.poi.xssf.model.SharedStringsTable
import org.apache.xmlbeans.XmlException
import org.apache.xmlbeans.XmlOptions
import org.openxmlformats.schemas.officeDocument.x2006.relationships.STRelationshipId
import org.openxmlformats.schemas.spreadsheetml.x2006.main.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import kotlin.system.measureTimeMillis


@RestController
class ExcelController {

    @PostMapping("/uploadExcel")
    fun uploadExcel(@RequestParam("file") file: MultipartFile): ResponseEntity<Any> {
        val dataList = mutableListOf<ExcelData>()
        val formatter = DataFormatter()

        file.inputStream.use { inputStream ->
            val workbook = WorkbookFactory.create(inputStream)
            val sheet = workbook.getSheetAt(0) // Suponiendo que los datos están en la primera hoja

                try {
                    val executionTimeMillis = measureTimeMillis {
                        extracted(sheet, formatter, dataList)
                    }
                    println("Tiempo de lectura del archivo: $executionTimeMillis milisegundos ${dataList.size}")
                } catch (e: IllegalArgumentException) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("${e.message}")
                }finally {
                    workbook.close()
                }
        }
        return ResponseEntity.ok(dataList)
    }

    private fun extracted(
        sheet: Sheet,
        formatter: DataFormatter,
        dataList: MutableList<ExcelData>
    ) {
        val rowIterator = sheet.iterator()
        rowIterator.next() // Omitir la fila de encabezado si es necesario

        while (rowIterator.hasNext()) {
            val row = rowIterator.next()
            try {
                val data = ExcelData(
                    region = formatter.formatCellValue(row.getCell(0)),
                    country = formatter.formatCellValue(row.getCell(1)),
                    itemType = formatter.formatCellValue(row.getCell(2)),
                    salesChannel = formatter.formatCellValue(row.getCell(3)),
                    orderPriority = formatter.formatCellValue(row.getCell(4)),
                    orderDate = formatter.formatCellValue(row.getCell(5)),
                    orderId = formatter.formatCellValue(row.getCell(6)),
                    shipDate = formatter.formatCellValue(row.getCell(7)),
                    unitsSold = formatter.formatCellValue(row.getCell(8)),
                    unitPrice = formatter.formatCellValue(row.getCell(9)),
                    unitCost = formatter.formatCellValue(row.getCell(10)),
                    totalRevenue = formatter.formatCellValue(row.getCell(11)),
                    totalCost = formatter.formatCellValue(row.getCell(12)),
                    totalProfit = formatter.formatCellValue(row.getCell(13)),
                )
                dataList.add(data)
            } catch (e: IllegalArgumentException) {
                throw IllegalArgumentException("Error en la fila ${row.rowNum + 1}: ${e.message}")
            }
        }
    }

    @PostMapping("/uploadExcelStream")
    fun uploadExcelStream(@RequestParam("file") file: MultipartFile): ResponseEntity<Any> {
        val dataList = mutableListOf<ExcelData>()
        val formatter = DataFormatter()

        file.inputStream.use { inputStream ->
            val workbook = StreamingReader.builder()
                .rowCacheSize(100) // number of rows to keep in memory (defaults to 10)
                .bufferSize(4096) // buffer size (in bytes) to use when reading InputStream to file (defaults to 1024)
                .open(inputStream) // InputStream or File for XLSX file (required)

            val sheet = workbook.getSheetAt(0) // Suponiendo que los datos están en la primera hoja

            try {
                val executionTimeMillis = measureTimeMillis {
                    extracted(sheet, formatter, dataList)
                }
                println("Tiempo de lectura del archivo: $executionTimeMillis milisegundos ${dataList.size}")
            } catch (e: IllegalArgumentException) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("${e.message}")
            }finally {
                workbook.close()
            }
        }
        return ResponseEntity.ok(dataList)
    }


}