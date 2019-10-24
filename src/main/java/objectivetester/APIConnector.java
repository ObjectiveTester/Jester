package objectivetester;

/**
 *
 * @author Steve
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
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
    Boolean isArray = false;

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

            if (json instanceof JSONArray) {
                isArray = true;
            } else {
                isArray = false;
            }

            unpack(node, "", json, -1);

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

    void unpack(DefaultMutableTreeNode parent, String key, Object value, int idx) {

        int innerIndex = idx;

        //System.out.println("class:"+value.getClass());
        if (value instanceof JSONArray) {

            DefaultMutableTreeNode arraynode = parent;
            if (!key.isEmpty()) {
                arraynode = new DefaultMutableTreeNode(new JsonElement(key, Type.ARRAYKEY));
                parent.add(arraynode);
            }

            JSONArray arr = (JSONArray) value;
            Iterator<Object> innerobjects = arr.iterator();
            while (innerobjects.hasNext()) {
                Object innerobject = innerobjects.next();

                innerIndex++;

                unpack(arraynode, Integer.toString(innerIndex), innerobject, innerIndex);

            }

        } else if (value instanceof JSONObject) {

            DefaultMutableTreeNode objectnode = parent;
            if (!key.isEmpty()) {
                if (innerIndex > -1) {
                    objectnode = new DefaultMutableTreeNode(new JsonElement(Integer.toString(innerIndex), Type.ARRAY));
                } else {
                    objectnode = new DefaultMutableTreeNode(new JsonElement(key, Type.KEY));
                }
                parent.add(objectnode);
            }

            JSONObject obj = (JSONObject) value;
            Iterator<String> innerkeys = obj.keys();
            while (innerkeys.hasNext()) {
                String innerkey = innerkeys.next();
                Object innervalue = obj.get(innerkey);

                unpack(objectnode, innerkey, innervalue, -1);
            }
        } else {

            DefaultMutableTreeNode keynode = new DefaultMutableTreeNode(new JsonElement(key, Type.KEY));
            parent.add(keynode);
            DefaultMutableTreeNode valuenode = new DefaultMutableTreeNode(new JsonElement(value.toString(), Type.VALUE));
            keynode.add(valuenode);

        }

    }

    String repack(DefaultMutableTreeNode parent) {

        if (isArray) {
            System.out.println("A");
            ArrayList arr = new ArrayList<Map>();
            recurse(parent, arr, "");
            return new JSONArray(arr).toString();
        } else {
            System.out.println("O");
            Map<String, Object> obj = new HashMap<>();
            recurse(parent, obj, "");
            return new JSONObject(obj).toString();
        }

    }

    void recurse(DefaultMutableTreeNode treeParent, Object parent, String key) {
        Enumeration<TreeNode> e = treeParent.children();
        HashMap h = null;
        ArrayList a = null;
        if (parent instanceof Map) {
            h = (HashMap) parent;
            System.out.println("hey, we're doing an object");
        } else if (parent instanceof ArrayList) {
            a = (ArrayList) parent;
            System.out.println("hey, we're doing an array");
        }

        while (e.hasMoreElements()) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.nextElement();
            
            JsonElement je = (JsonElement) node.getUserObject();
            System.out.println(je.getType() + "->" + node.toString());

            if (je.getType() == Type.ARRAY) {
                System.out.print("ARRAY ");
                System.out.println(node.getParent().getChildCount());
                System.out.println("doing an array " + node.toString());
                
                Map<String, Object> obj = new HashMap<>();
                recurse(node, obj, "");
                a.add(obj);

            } else if (je.getType() == Type.ARRAYKEY) {
                System.out.print("ARRAYKEY ");
                System.out.println(node.getParent().getChildCount());
                System.out.println("doing an arraykey " + node.toString());
                
                ArrayList arr = new ArrayList<Map>();
                recurse(node, arr, node.toString());
                h.put(node.toString(), arr);

            } else if (je.getType() == Type.KEY) {
                System.out.println("KEY " + key + " " + node.getChildCount());

                if (node.getChildCount() > 1) {
                    Map<String, Object> newnode = new HashMap<>();
                    recurse(node, newnode, node.toString());
                    if (h != null) {
                        h.put(node.toString(), newnode);
                    } else {
                        a.add(newnode);
                    }
                } else {

                    if (h != null) {
                        h.put(node.toString(), node.getFirstChild().toString());
                    } else {
                        a.add(node.getFirstChild().toString());
                    }

                }

            } else if (je.getType() == Type.VALUE) {
                System.out.println("NOT SURE:" + je.getType());
            } else {
                System.out.println("NOT SURE:" + je.getType());
            }

        }

    }

}
