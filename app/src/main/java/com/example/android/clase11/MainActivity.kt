package com.example.android.clase11

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.lang.Exception

class MainActivity : AppCompatActivity(),GetData.OnTaskCompleted {
    override fun OnTaskCompleted(respuesta: String) {
        if (!respuesta.equals("error"))
        {
            try {
                var lista = ArrayList<String>()
                var json = JSONObject(respuesta)
                var jsonData = json.optJSONArray("serie")
                for (i in 0..jsonData.length()-1)
                {
                    var dolar = jsonData.getJSONObject(i)
                    var texto = dolar.getString("valor")
                    lista.add(texto)
                }

                var adaptador = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,lista)
                lvListar.adapter = adaptador
            }
            catch (e:Exception)
            {
                Log.e("json error", "${e.message}, ${e.cause}")
            }
        } else
        {
            var alerta = AlertDialog.Builder(this)
            alerta.setTitle("ops, algo pasó :(")
            alerta.setMessage("ocurrió un errors")
            alerta.setNegativeButton("cancelar",{dialog, which ->  dialog.cancel()})
            alerta.show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    btnActualizar.setOnClickListener {
        var tarea = GetData("",this)
        tarea.execute("https://mindicador.cl/api/dolar")
    }

    }
}
