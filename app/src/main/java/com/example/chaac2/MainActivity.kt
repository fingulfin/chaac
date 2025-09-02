package com.example.chaac2

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.Timer
import java.util.TimerTask

class MainActivity : AppCompatActivity() {


    var estacion=Estacion()
    var observacionActual=ObservacionActual()
    val timer = Timer()
    var viento:Float?=0.0f


    fun holaMundo():String{
        return "Holaaaaa"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
//Malo
        timer.schedule(object : TimerTask() {
            override fun run() {

                var saludar=::holaMundo


                Log.i("XY", "hOLA MUNDO ${saludar()}")

                GlobalScope.launch(Dispatchers.IO){

            //Si esta muy buena

                    var txt = findViewById<TextView>(R.id.tutu)
                    var txthora=findViewById<TextView>(R.id.hora)
                    var txtLLuviaDiaria=findViewById<TextView>(R.id.txtLluviaDiaria)
                    var txtLLuviaMensual=findViewById<TextView>(R.id.txtLluviaMensual)
                    var txtLLuviaAnual=findViewById<TextView>(R.id.txtLluviaAnual)
                    var txtTempMaxima=findViewById<TextView>(R.id.txtTempMax)
                    var txtTempMinima=findViewById<TextView>(R.id.txtTempMin)

                    var retrofit = Retrofit.Builder()
                        .baseUrl("https://api.weatherlink.com/")
                        .addConverterFactory(JacksonConverterFactory.create())
                        .build()

                    //PASO 2: GENERAR UN OBJETO PARAHABILITAR TU SERVICIO DE RETROFIT USANDO EL OBJETO
                    //DEL PUNTO ANTERIOR
                    var servicioUsuario = retrofit.create(ServicioRest::class.java)


                    //PASO 2
                    var enviarUsuarios = servicioUsuario.buscarTodos()


                    estacion = Estacion()
                    //SE ENVIA  AL BACK- END Y  EN ESTE MOMENTO SE OBTIENE LA RESPUESTA
                    estacion = enviarUsuarios.execute().body()!!

                    //Aqui viene la magia para compartir con el activity BusquedasActvity
                    //nuestra variable usuarios de arriba
                    //Convertimos lluvia a mm:
                  var lluviaDiariaInches=        estacion.davis_current_observation?.rain_day_in?.toFloat()!!
                  var lluviaDiaraMm=lluviaDiariaInches*25.4

                    lluviaDiaraMm=Math.round(lluviaDiaraMm * 10.0) / 10.0

                    var lluviaMensualInches=        estacion.davis_current_observation?.rain_month_in?.toFloat()!!
                    var lluviaMensualMm=lluviaMensualInches*25.4
                    lluviaMensualMm=Math.round(lluviaMensualMm * 10.0) / 10.0

                    var lluviaAnualInches=        estacion.davis_current_observation?.rain_year_in?.toFloat()!!
                    var lluviaAnualMm=lluviaAnualInches*25.4
                    lluviaAnualMm=Math.round(lluviaAnualMm * 10.0) / 10.0

                    var temperaturaMaxima=estacion.davis_current_observation?.temp_day_high_f?.toFloat()!!;
                    temperaturaMaxima=(temperaturaMaxima-32)/1.8f
                    temperaturaMaxima=Math.round(temperaturaMaxima*10)/10.0f

                    var temperaturaMinima=estacion.davis_current_observation?.temp_day_low_f?.toFloat()!!;
                    temperaturaMinima=(temperaturaMinima-32)/1.8f
                    temperaturaMinima=Math.round(temperaturaMinima*10)/10.0f


                    Log.i("TTT","Usuarios encontrados ${estacion?.temp_c}")
                    //La siguiente corrutina tiene el scope para el thrad principal, es decir
                    //para el de thread principal de la UI
                    launch(Dispatchers.Main) {
                        txt.text=estacion?.temp_c
                       txthora.text=estacion?.observation_time
                        txtLLuviaDiaria.text=""+lluviaDiaraMm
                        txtLLuviaMensual.text=""+lluviaMensualMm
                        txtLLuviaAnual.text=""+lluviaAnualMm
                        txtTempMaxima.text=""+temperaturaMaxima
                        txtTempMinima.text=""+temperaturaMinima

                    }
                    //


                }
             //   println("Timer ticked!")
            }
        }, 0, 20000)




    }
}