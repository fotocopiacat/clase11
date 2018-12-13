package com.example.android.clase11

import android.os.AsyncTask
import android.util.Log
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

//creamos una clase GETDATA que hara la llamada asincrona
//luego se debe sobreescribir un metodo, en este caso DO IN BACKGROUND
//se debe añadoir metodo ONPOSTEXECUTE
class GetData(var json:String, var tarea: OnTaskCompleted):AsyncTask<String, Int, Boolean>() {

    override fun doInBackground(vararg params: String?): Boolean {
        var inputStream:InputStream? =null
        try{
            var url = URL(params[0])
            var con = url.openConnection() as HttpURLConnection
            con.readTimeout = 1000
            con.connectTimeout = 15000
            con.requestMethod = "GET"
            var respuesta:Int = con.responseCode
          //  con.doInput= true

            Log.d("Servidor",respuesta.toString())
            inputStream=con.inputStream

            json = Scanner(inputStream).useDelimiter("\\A").next()
            Log.i("Contenido",json)
        }
        catch (e:Exception)
        {
         return false
        }
        finally {
            try {
                inputStream?.close()

            } catch (e:Exception)
            {
                Log.e("error",e.message)
            }
        }
     return true
    }

    override fun onPostExecute(result: Boolean?) {
        if (result == true){
            tarea.OnTaskCompleted(json)
        } else {
            tarea.OnTaskCompleted("error")
        }
    }
    interface OnTaskCompleted{
        fun OnTaskCompleted(respuesta:String)
    }
}