package com.example.chaac2

data class Articulo(
    val id: String, // O Int, Long, UUID, dependiendo de cómo quieras generar/usar los IDs
    val nombre: String,
    val precio: Double // O BigDecimal para mayor precisión con valores monetarios
)