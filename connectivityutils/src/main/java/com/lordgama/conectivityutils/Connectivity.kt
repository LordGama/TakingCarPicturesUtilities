package com.lordgama.conectivityutils

import android.util.Log
import java.io.DataOutputStream
import java.io.InputStream
import java.net.*
import java.util.*

/**
 * @author Jesus Daniel
 * @version 1
 *
 * Clase encargada de manejar la conexión con el servidor.
 *
 */

class Connectivity {

    companion object {
        fun doAGetRequest(url: URL, parameters : HashMap<String, String>, debug: Boolean = false):String{

            if(debug) Log.d("Connectivity","URL: ${url.path}")

            var jsonResponse = ""
            var urlConnection: HttpURLConnection? = null
            var inputStream: InputStream? = null
            var parametersString = ""

            urlConnection = url.openConnection() as HttpURLConnection
            urlConnection.readTimeout = 10000
            urlConnection.connectTimeout = 15000
            urlConnection.requestMethod = "GET"
            urlConnection.doOutput = true
            urlConnection.doInput = true
            urlConnection.setRequestProperty("USER-AGENT", "Mozilla/5.0")
            urlConnection.setRequestProperty("USER-AGENT", "mobile")
            urlConnection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5")

            val wr = DataOutputStream(urlConnection.outputStream)

            for(parameter in parameters){
                parametersString += parameter.key +"="+parameter.value + "&"
            }

            parametersString = parametersString.substring(0,parametersString.length-1)

            if(debug) Log.d("Connectivity","Parameters: ${parametersString}")

            wr.writeBytes(parametersString)
            wr.flush()
            wr.close()

            inputStream = urlConnection.inputStream
            jsonResponse = inputStream.bufferedReader().readText()
            if(debug) Log.d("Debug (Conection)","Result: ${jsonResponse}")

            urlConnection.disconnect()
            inputStream?.close()
            return jsonResponse

        }

        fun doAPostRequest(url: URL,parameters :HashMap<String,String>,token: String,debug: Boolean = false,forbiddenListener: ForbiddenListener? = null,dominio: String): String{
            if(debug)Log.d("Connectivity","URL: ${url.path}")

            val uri = URI.create(dominio)
            val cookieManager = CookieManager()
            val csrf_cookie = HttpCookie("csrftoken", token)
            csrf_cookie.domain = dominio
            cookieManager.cookieStore.add(uri, csrf_cookie)
            CookieHandler.setDefault(cookieManager)

            var jsonResponse = ""
            var urlConnection: HttpURLConnection? = null
            var inputStream: InputStream? = null

            urlConnection = url.openConnection() as HttpURLConnection
            urlConnection.readTimeout = 10000
            urlConnection.connectTimeout = 15000
            urlConnection.requestMethod = "POST"
            urlConnection.doOutput = true
            urlConnection.doInput = true
            urlConnection.addRequestProperty("Cookie", "csrftoken="+token)
            urlConnection.addRequestProperty("X-CSRFToken", token)
            urlConnection.setRequestProperty("USER-AGENT", "Mozilla/5.0")
            urlConnection.setRequestProperty("USER-AGENT", "mobile")
            urlConnection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5")
            urlConnection.setRequestProperty("referer", dominio+url.path)
            urlConnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded; charset=utf-8")

            val wr = DataOutputStream(urlConnection.outputStream)

            var parametersString = ""

            for(parameter in parameters){
                parametersString += parameter.key +"="+parameter.value + "&"
            }

            parametersString = parametersString.substring(0,parametersString.length-1)
            if(debug)Log.d("Connectivity","Parameters: ${parametersString}")


            wr.writeBytes(parametersString)
            wr.flush()
            wr.close()

            if(urlConnection.responseCode == 403){
                forbiddenListener?.tokenExpire()
            }

            if(debug)Log.e("Error",urlConnection.responseMessage)
            inputStream = urlConnection.inputStream
            jsonResponse = inputStream.bufferedReader().readText()

            if(debug)Log.d("Connectivity","Result: ${jsonResponse}")

            urlConnection.disconnect()
            inputStream?.close()
            return jsonResponse
        }
    }

    interface ForbiddenListener{
        fun tokenExpire()
    }

}