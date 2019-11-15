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
    Boolean isArray = false;

    APIConnector(UserInterface ui) {
        this.ui = ui;
    }

    Integer reqGet(String fullUrl, DefaultMutableTreeNode node) {
        Object json;
        String resp;
        Integer code = 0;

        HttpClientBuilder builder = HttpClients.custom();
        builder.setRedirectStrategy(new LaxRedirectStrategy());
        HttpClient httpclient = builder.build();

        try {
            HttpGet get = new HttpGet(fullUrl.strip());
            HttpResponse response = httpclient.execute(get);
            code = response.getStatusLine().getStatusCode();
            resp = EntityUtils.toString(response.getEntity());
            json = new JSONTokener(resp).nextValue();

            if (json instanceof JSONArray) {
                isArray = true;
                node.setUserObject("Array");
            } else {
                isArray = false;
                node.setUserObject("Object");
            }

            unpack(node, "", json, -1);

        } catch (IOException | ParseException | IllegalArgumentException ex) {
            //System.out.print(ex.getMessage());
            ui.errorMessage(ex.getMessage());
        }

        return code;
    }

    Integer reqPost(String fullUrl, DefaultMutableTreeNode node) {
        Object json;
        Integer code = 0;

        String data = repack(node);
        StringEntity entity = new StringEntity(data, ContentType.APPLICATION_JSON);

        HttpClientBuilder builder = HttpClients.custom();
        builder.setRedirectStrategy(new LaxRedirectStrategy());
        HttpClient httpclient = builder.build();

        try {
            HttpPost post = new HttpPost(fullUrl.strip());
            post.setEntity(entity);
            HttpResponse response = httpclient.execute(post);
            code = response.getStatusLine().getStatusCode();
            String resp = EntityUtils.toString(response.getEntity());

            if (ui.getIgnorePref()) {
                //System.out.print(resp);
            } else {
                //System.out.print(resp);

                json = new JSONTokener(resp).nextValue();

                if (json instanceof JSONArray) {
                    isArray = true;
                    node.setUserObject("Array");
                } else {
                    isArray = false;
                    node.setUserObject("Object");
                }

                ui.wipe();
                unpack(node, "", json, -1);
            }

        } catch (IOException | ParseException | IllegalArgumentException ex) {
            //System.out.print(ex.getMessage());
            ui.errorMessage(ex.getMessage());
        }

        return code;
    }

    void unpack(DefaultMutableTreeNode parent, String key, Object value, int idx) {

        int innerIndex = idx;

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
            DefaultMutableTreeNode valuenode = new DefaultMutableTreeNode(new JsonElement(value, Type.VALUE));
            keynode.add(valuenode);

        }

    }

    String repack(DefaultMutableTreeNode parent) {

        if (isArray) {
            ArrayList arr = new ArrayList<Map>();
            parse(parent, arr, null);
            return new JSONArray(arr).toString();
        } else {
            Map<String, Object> obj = new HashMap<>();
            parse(parent, obj, null);
            return new JSONObject(obj).toString();
        }

    }

    void parse(DefaultMutableTreeNode treeParent, Object parent, String key) {
        Enumeration<TreeNode> e = treeParent.children();
        HashMap h = null;
        ArrayList a = null;
        if (parent instanceof Map) {
            h = (HashMap) parent;
        } else if (parent instanceof ArrayList) {
            a = (ArrayList) parent;
        }

        while (e.hasMoreElements()) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.nextElement();
            JsonElement element = (JsonElement) node.getUserObject();
            if (element.elementType.equals(Type.ARRAY)) {
                Map<String, Object> obj = new HashMap<>();
                parse(node, obj, "");
                a.add(obj);
            } else if (element.elementType.equals(Type.ARRAYKEY)) {
                ArrayList arr = new ArrayList<Map>();
                parse(node, arr, node.toString());
                h.put(node.toString(), arr);
            } else if (element.elementType.equals(Type.KEY)) {
                if (node.getChildCount() > 1) {
                    Map<String, Object> newnode = new HashMap<>();
                    parse(node, newnode, node.toString());
                    if (h != null) {
                        h.put(node.toString(), newnode);
                    } else {
                        a.add(newnode);
                    }
                    //look ahead for values
                } else if (node.getChildCount() == 1) {
                    DefaultMutableTreeNode innerNode = (DefaultMutableTreeNode) node.getFirstChild();
                    JsonElement innerElement = (JsonElement) innerNode.getUserObject();
                    //special case - empty array
                    if (innerElement.elementType.equals(Type.ARRAYKEY)) {
                        ArrayList arr = new ArrayList<Map>();
                        Map<String, Object> obj = new HashMap<>();
                        obj.put(innerElement.toString(), arr);
                        h.put(node.toString(), obj);

                    } else {
                        if (h != null) {
                            h.put(node.toString(), innerElement.elementObject);
                        } else {
                            a.add(innerElement.elementObject);
                        }
                    }
                } else {
                    if (h != null) {
                        h.put(node.toString(), new HashMap<>());
                    } else {
                        a.add(element.elementObject);
                    }
                }

            } else {
                //values should always be found by look-ahead
                System.out.println("UNEXPECTED:" + element.elementType);
            }

        }

    }

    void refresh(DefaultMutableTreeNode node) {
        String data = repack(node);
        System.out.println("data:" + data);
        ui.wipe();
        if (isArray) {
            JSONArray newjson = new JSONArray(data);
            unpack(node, "", newjson, -1);
        } else {
            JSONObject newjson = new JSONObject(data);
            unpack(node, "", newjson, -1);
        }
    }

    void importData(String rawjson, DefaultMutableTreeNode node) {
        
        Object json = new JSONTokener(rawjson).nextValue();
        
        if (json instanceof JSONArray) {
            isArray = true;
            node.setUserObject("Array");
        } else {
            isArray = false;
            node.setUserObject("Object");
        }

        ui.wipe();
        unpack(node, "", json, -1);
    }



}
