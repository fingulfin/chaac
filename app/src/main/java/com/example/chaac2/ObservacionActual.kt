package com.example.chaac2

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class ObservacionActual {

    @JsonIgnoreProperties(ignoreUnknown = true)
    var rain_day_in:String?=null
    var rain_month_in:String?=null
    var rain_year_in:String?=null
    var temp_day_low_f:String?=null
    var temp_day_high_f:String?=null

}