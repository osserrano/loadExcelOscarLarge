package com.example.loadExcelOscar

data class ExcelData(
    var region: String = "",
    var country: String = "",
    var itemType: String = "",
    var salesChannel: String = "",
    var orderPriority: String = "",
    var orderDate: String = "",
    var orderId: String = "",
    var shipDate: String = "",
    var unitsSold:String = "",
    var unitPrice:String = "",
    var unitCost: String = "",
    var totalRevenue: String = "",
    var totalCost: String = "",
    var totalProfit: String = "",
) {
    init {
        require(region.isNotBlank()) { "El campo 'Region' no puede estar vacío" }
        require(country.isNotBlank()) { "El campo 'Country' no puede estar vacío" }
        require(itemType.isNotBlank()) { "El campo 'Item Type' no puede estar vacío" }
        require(salesChannel.isNotBlank()) { "El campo 'Sales Channel' no puede estar vacío" }
        require(orderPriority.isNotBlank()) { "El campo 'Order Priority' no puede estar vacío" }
        require(orderDate.isNotBlank()) { "El campo 'Order Date' no puede estar vacío" }
        require(orderId.isNotBlank()) { "El campo 'Order ID' no puede estar vacío" }
        require(shipDate.isNotBlank()) { "El campo 'Ship Date' no puede estar vacío" }
        require(unitsSold.isNotBlank()) { "El campo 'Units Sold' debe ser mayor que cero" }
        require(unitPrice.isNotBlank()) { "El campo 'Unit Price' debe ser mayor que cero" }
        require(unitCost.isNotBlank()) { "El campo 'Unit Cost' debe ser mayor que cero" }
        require(totalRevenue.isNotBlank()) { "El campo 'Total Revenue' debe ser mayor que cero" }
        require(totalCost.isNotBlank() ) { "El campo 'Total Cost' debe ser mayor que cero" }
        require(totalProfit.isNotBlank()) { "El campo 'Total Profit' debe ser mayor que cero" }
    }
}
