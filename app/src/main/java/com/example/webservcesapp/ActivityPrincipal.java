package com.example.webservcesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityPrincipal extends AppCompatActivity {
EditText id,nombre,equipo,foto;
Button bt1,bt2,bt3, bt4;
String url="";
RequestQueue respuestaService;
ArrayList<String> datos=new ArrayList<>();
ListView lista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        relacionarVistas();
        ArrayAdapter adaptador =new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,datos);
        lista.setAdapter(adaptador);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?>parent, View view, int position, long id){
                if(position==0){

                }
            }
        });
    }
    public void relacionarVistas(){
        id=(EditText) findViewById(R.id.id);
        nombre=(EditText) findViewById(R.id.nombre);
        equipo=(EditText) findViewById(R.id.equipo);
        foto=(EditText) findViewById(R.id.foto);
        bt1=(Button) findViewById(R.id.bt1);
        bt2=(Button) findViewById(R.id.bt2);
        bt3=(Button) findViewById(R.id.bt3);
        bt4=(Button) findViewById(R.id.bt4);
        lista=(ListView) findViewById(R.id.lista);
    }

    public void insercion(View v){
        url="https://btspueblitas.000webhostapp.com/services/insercion.php";
        webServices();
    }
    public void busqueda(View v){
        url="https://btspueblitas.000webhostapp.com/services/buscar.php?id="+id.getText();
        JsonArrayRequest respuestaBusqueda=new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject json = null;

                String datoTemp = "";
                String datoTemp1 = "";
                String datoTemp2 = "";
                String datoTemp3 = "";
                for (int i = 0; i < response.length(); i++) {
                    try {
                        json = response.getJSONObject(i);
                        datoTemp += "id: " + json.getString("id");
                        datos.add(datoTemp);
                        datoTemp += id;
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "Error en el formato", Toast.LENGTH_SHORT).show();
                    }
                    try {
                        json = response.getJSONObject(i);
                        datoTemp1 += "nombre: " + json.getString("nombre_jugador");
                        datos.add(datoTemp1);
                        datoTemp += nombre;
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "Error en el formato", Toast.LENGTH_SHORT).show();
                    }
                    try {
                        json = response.getJSONObject(i);
                        datoTemp2 += "equipo: " + json.getString("equipo");
                        datos.add(datoTemp2);
                        datoTemp += equipo;
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "Error en el formato", Toast.LENGTH_SHORT).show();
                    }
                    try {
                        json = response.getJSONObject(i);
                        datoTemp3 += "foto: " + json.getString("foto");
                        datos.add(datoTemp3);
                        datoTemp += foto;
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "Error en el formato", Toast.LENGTH_SHORT).show();
                    }
                }
                Toast.makeText(getApplicationContext(), "Datos Encontrado", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        respuestaService= Volley.newRequestQueue(this);
        respuestaService.add(respuestaBusqueda);
    }

    public void actualizacion(View v){
        url="https://btspueblitas.000webhostapp.com/services/actualizar.php";
        webServices1();
    }
    public void eliminacion(View v) {
        url = "https://btspueblitas.000webhostapp.com/services/Eliminar.php?id="+id.getText();
        StringRequest respuesta3 = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error de comunicacion", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> idEliminar = new HashMap<>();
                idEliminar.put("id", id.getText().toString());
                return idEliminar;
            }
        };
        respuestaService = Volley.newRequestQueue(this);
        respuestaService.add(respuesta3);
    }
    public void webServices(){
        StringRequest respuesta= new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String>parametros=new HashMap<>();
                parametros.put("nombre",nombre.getText().toString());
                parametros.put("equipo",equipo.getText().toString());
                parametros.put("foto",foto.getText().toString());
                return parametros;
            }
        };
        respuestaService=Volley.newRequestQueue(this);
        respuestaService.add(respuesta);


    }
    public void webServices1(){
        StringRequest respuesta= new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String>parametros=new HashMap<>();
                parametros.put("id",id.getText().toString());
                parametros.put("nombre",nombre.getText().toString());
                parametros.put("equipo",equipo.getText().toString());
                parametros.put("foto",foto.getText().toString());
                return parametros;
            }
        };
        respuestaService=Volley.newRequestQueue(this);
        respuestaService.add(respuesta);


    }
}