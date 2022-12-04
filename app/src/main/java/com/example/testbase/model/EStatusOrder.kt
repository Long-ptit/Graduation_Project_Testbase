package com.example.testbase.model

enum class EStatusOrder(val status : String, val id: Int) {
    CONFIRM("Chờ xác nhận", 1),
    PREPARE("Đang chuẩn bị hàng",2 ),
    COMPLETE("Đã gửi hàng thành công", 3),
    CANCEL("Hoàn thành", 4),
}