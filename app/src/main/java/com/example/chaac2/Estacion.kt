package com.example.chaac2

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

// https://api.weatherlink.com/v1/NoaaExt.json?user=001D0A0040AE&pass=celiesita&apiToken=8DA86B5995E94BA3BD3EBD811086F906
@JsonIgnoreProperties(ignoreUnknown = true)
class Estacion{

var temp_c:String?=null
                 //  @JsonProperty("nombre") var nombre:String?,
                //   @JsonProperty("idAndroid") var idAndroid:String?,
                  /*  @JsonProperty("coordenadas")  var coordenadas:ArrayList<Coordenada>? */

}