
import java.io.*;
import java.util.ArrayList;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONObject;

//SE NECESITAN LAS LIBRERIAS DE LA CARPETA "LIBRERIAS HTTP"
public class PepeHttpRequest {

	public ArrayList<Info> informacion;
	
    PepeHttpRequest() {
        CloseableHttpClient client = HttpClients.createDefault(); //Crea un cliente HTTP
        HttpGet request = new HttpGet("http://192.99.160.177:8000/get.php?username=FENIXTV&password=1234&type=m3u_plus&output=hls");
        CloseableHttpResponse response = null;//Al inicio la respuesta es nula
        try {
            response = client.execute(request);//El cliente intenta ejecutar el request
            int status = response.getStatusLine().getStatusCode();//Es el estado de la carga del request, es decir cuanto tiempo se tarda
            if (status >= 200 && status < 300) {//Si responde entre ese intervalo, imprime lo que tenga el link
                BufferedReader br;
                br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                String linea = "";
                
                //JCGE: Declaramos una variables donde vamos a guardar la info linea por linea
                String info = "", url = "";
                boolean exit = false; //JCGE: Para evitar la primera linea
                informacion = new ArrayList<Info>();
                
                while ((linea = br.readLine()) != null)
                {
                    switch (Info.TipoLinea(linea)) 
                    {
                        case 1: info = linea;
                            break;
                        case 2: url = linea;
                            break;
                        default: exit = true;
                            break;
                    }
                    
                    if (exit)
                    {
                        exit = false;
                        continue;
                    }
                    
                    if ((!info.equals("")) && (!url.equals("")))
                    {
                        informacion.add(new Info(info, url));
                        info = "";  url = "";
                    }
                }
            } else {//En caso de tardarse, mandara un aviso de cuanto se ha tardado
                System.out.println("Unexpected response status: " + status);
            }
        } catch (IOException | UnsupportedOperationException e) { //Excepciones en caso de que el link no exista, falle o el cliente no hizo bien el request
            e.printStackTrace();
        } finally {
            if (null != response) {
                try {
                    response.close();
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public JSONObject toJson()
    {
        JSONObject theJson = new JSONObject();
        int j = 0;
        for (Info i : informacion)
        {
            JSONObject videoJson = new JSONObject();
            videoJson.put("Id", i.id);
            videoJson.put("Name", i.name);
            videoJson.put("Logo", i.logo);
            videoJson.put("Group-title", i.id);
            theJson.put("Registro "+j, videoJson);
            j++;
        }
        return theJson;
    }
}

