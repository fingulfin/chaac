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
    val timer = Timer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        timer.schedule(object : TimerTask() {
            override fun run() {

                GlobalScope.launch(Dispatchers.IO){

                    var txt = findViewById<TextView>(R.id.tutu)
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


                    Log.i("TTT","Usuarios encontrados ${estacion?.temp_c}")
                    //La siguiente corrutina tiene el scope para el thrad principal, es decir
                    //para el de thread principal de la UI
                    launch(Dispatchers.Main) {
                        txt.text=estacion?.temp_c
                    }
                    //


                }
             //   println("Timer ticked!")
            }
        }, 0, 10000)




    }
}