package objectivetester;

/**
 *
 * @author Steve
 */

import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.*;
import org.json.JSONObject;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.json.JSONTokener;

class APIConnector implements Runnable {

    UserInterface ui;
    boolean safe = false;
    Object json;

    APIConnector(UserInterface ui) {
        this.ui = ui;
    }

    //hold an in-memory representation of a JSON object that is updated be GET and sent by POST
    //DELETE is just a simple call
    
    
 

    @Override
    public void run() {
        if (null != null) {
            safe = false;

            ui.finished();
            safe = true;
        }
    }

    void close() {

    }

    Integer reqGet(String fullUrl) {
        String data;
        Integer code = 0;

        HttpClientBuilder builder = HttpClients.custom();
        builder.setRedirectStrategy(new LaxRedirectStrategy());
        HttpClient httpclient = builder.build();

        HttpGet get = new HttpGet(fullUrl);

        try {
            HttpResponse response = httpclient.execute(get);
            data = EntityUtils.toString(response.getEntity());
            code = response.getStatusLine().getStatusCode();
            
            json = new JSONTokener(data).nextValue();

            ui.start(json);
        } catch (IOException | ParseException ex) {
            System.out.println(ex);
        }

        return code;
    }

    Integer reqPost(String fullUrl, String data) {
        StringEntity entity = new StringEntity(data, ContentType.APPLICATION_JSON);

        Integer code = 0;

        HttpClientBuilder builder = HttpClients.custom();
        builder.setRedirectStrategy(new LaxRedirectStrategy());
        HttpClient httpclient = builder.build();

        HttpPost post = new HttpPost(fullUrl);

        try {
            post.setEntity(entity);
            HttpResponse response = httpclient.execute(post);
            data = EntityUtils.toString(response.getEntity());
            code = response.getStatusLine().getStatusCode();
            json = new JSONObject(data);

        } catch (IOException | ParseException ex) {
            System.out.println(ex);
        }

        return code;
    }

}
