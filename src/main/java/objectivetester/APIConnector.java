package objectivetester;

/**
 *
 * @author Steve
 */
import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.tree.DefaultMutableTreeNode;
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
import org.json.JSONArray;
import org.json.JSONTokener;

class APIConnector {

    UserInterface ui;
    Object json;

    APIConnector(UserInterface ui) {
        this.ui = ui;
    }

    //hold an in-memory representation of a JSON object that is updated be GET and sent by POST
    //DELETE is just a simple call
    Integer reqGet(String fullUrl, DefaultMutableTreeNode node) {
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

            unpack(node, "", json, new AtomicInteger(-1));

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

    void unpack(DefaultMutableTreeNode parent, String key, Object value, AtomicInteger idx) {

        AtomicInteger innerIndex = new AtomicInteger(idx.get());

        //System.out.println("class:"+value.getClass());
        if (value instanceof JSONArray) {

            DefaultMutableTreeNode arraynode = parent;
            if (!key.isEmpty()) {
                arraynode = new DefaultMutableTreeNode(new JsonElement(key,Type.KEY));
                parent.add(arraynode);
            }

            JSONArray arr = (JSONArray) value;
            Iterator<Object> innerobjects = arr.iterator();
            while (innerobjects.hasNext()) {
                Object innerobject = innerobjects.next();

                innerIndex.addAndGet(1);

                unpack(arraynode, String.valueOf(innerIndex.get()), innerobject, innerIndex);

            }

        } else if (value instanceof JSONObject) {

            DefaultMutableTreeNode objectnode = parent;
            if (!key.isEmpty()) {
                if (innerIndex.get() > -1) {
                    objectnode = new DefaultMutableTreeNode(new JsonElement(innerIndex.toString(),Type.ARRAY));
                } else {
                    objectnode = new DefaultMutableTreeNode(new JsonElement(key,Type.KEY));
                }
                parent.add(objectnode);
            }

            JSONObject obj = (JSONObject) value;
            Iterator<String> innerkeys = obj.keys();
            while (innerkeys.hasNext()) {
                String innerkey = innerkeys.next();
                Object innervalue = obj.get(innerkey);

                unpack(objectnode, innerkey, innervalue, new AtomicInteger(-1));
            }
        } else {

            DefaultMutableTreeNode keynode = new DefaultMutableTreeNode(new JsonElement(key,Type.KEY));
            parent.add(keynode);
            DefaultMutableTreeNode valuenode = new DefaultMutableTreeNode(new JsonElement(value.toString(),Type.VALUE));
            keynode.add(valuenode);

        }

    }

}
