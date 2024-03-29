package objectivetester;

/**
 *
 * @author Steve
 */
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.*;
import org.json.JSONObject;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.HeaderGroup;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONTokener;

class APIConnector {

    UserInterface ui;
    Boolean isArray = false;
    Long startTime;

    APIConnector(UserInterface ui) {
        this.ui = ui;
    }

    Integer reqGet(String fullUrl, String headers, String cookies, DefaultMutableTreeNode res) {
        Object json;
        String resp = "";
        Integer code = 0;

        HttpClientBuilder builder = HttpClients.custom();
        builder.setRedirectStrategy(new LaxRedirectStrategy());
        HttpClient httpclient = builder.setDefaultCookieStore(buildCookies(cookies, fullUrl)).build();
        startTime = System.currentTimeMillis();

        try {
            HttpGet get = new HttpGet(fullUrl);
            get.setHeaders(buildHeaders(headers));
            HttpResponse response = httpclient.execute(get);
            code = response.getStatusLine().getStatusCode();
            System.out.print(code);
            System.out.print(" " + response.getStatusLine().getReasonPhrase());
            System.out.print(" " + (System.currentTimeMillis() - startTime)+ "ms");

            if (response.getEntity() != null) {
                resp = EntityUtils.toString(response.getEntity());
                System.out.print(" " + resp.length() + "B");
            }

            ui.newCookies(extractCookies(response));

            if (!resp.isEmpty()) {
                json = new JSONTokener(resp).nextValue();

                if (json instanceof JSONArray) {
                    isArray = true;
                    res.setUserObject("Response Array");
                } else if (json instanceof JSONObject) {
                    isArray = false;
                    res.setUserObject("Response Object");
                }

                ui.wipeRes();
                unpack(res, "", json, -1);
            } else {
                ui.wipeRes();
            }

        } catch (IOException | ParseException | IllegalArgumentException ex) {
            // System.out.print(ex.getMessage());
            ui.errorMessage(ex.getMessage());
        }

        return code;
    }

    Integer reqPost(String fullUrl, String headers, String cookies, DefaultMutableTreeNode req,
            DefaultMutableTreeNode res) {
        Object json;
        String resp = "";
        Integer code = 0;

        String data = repack(req);
        StringEntity entity = new StringEntity(data, ContentType.APPLICATION_JSON);

        HttpClientBuilder builder = HttpClients.custom();
        builder.setRedirectStrategy(new LaxRedirectStrategy());
        HttpClient httpclient = builder.setDefaultCookieStore(buildCookies(cookies, fullUrl)).build();
        startTime = System.currentTimeMillis();

        try {
            HttpPost post = new HttpPost(fullUrl);
            post.setEntity(entity);
            post.setHeaders(buildHeaders(headers));
            HttpResponse response = httpclient.execute(post);
            code = response.getStatusLine().getStatusCode();
            System.out.print(code);
            System.out.print(" " + response.getStatusLine().getReasonPhrase());
            System.out.print(" " + (System.currentTimeMillis() - startTime)+ "ms");

            if (response.getEntity() != null) {
                resp = EntityUtils.toString(response.getEntity());
                System.out.print(" " + resp.length() + "B");
            }

            ui.newCookies(extractCookies(response));

            if (!resp.isEmpty()) {
                json = new JSONTokener(resp).nextValue();

                if (json instanceof JSONArray) {
                    isArray = true;
                    res.setUserObject("Response Array");
                } else if (json instanceof JSONObject) {
                    isArray = false;
                    res.setUserObject("Response Object");
                }

                ui.wipeRes();
                unpack(res, "", json, -1);
            } else {
                ui.wipeRes();
            }

        } catch (IOException | ParseException | IllegalArgumentException ex) {
            // System.out.print(ex.getMessage());
            ui.errorMessage(ex.getMessage());
        }

        return code;
    }

    Integer reqDelete(String fullUrl, String headers, String cookies, DefaultMutableTreeNode res) {
        Object json;
        String resp = "";
        Integer code = 0;

        HttpClientBuilder builder = HttpClients.custom();
        builder.setRedirectStrategy(new LaxRedirectStrategy());
        HttpClient httpclient = builder.setDefaultCookieStore(buildCookies(cookies, fullUrl)).build();
        startTime = System.currentTimeMillis();
        
        try {
            HttpDelete delete = new HttpDelete(fullUrl);
            delete.setHeaders(buildHeaders(headers));
            HttpResponse response = httpclient.execute(delete);
            code = response.getStatusLine().getStatusCode();
            System.out.print(code);
            System.out.print(" " + response.getStatusLine().getReasonPhrase());
            System.out.print(" " + (System.currentTimeMillis() - startTime)+ "ms");
            
            if (response.getEntity() != null) {
                resp = EntityUtils.toString(response.getEntity());
                System.out.print(" " + resp.length() + "B");
            }

            ui.newCookies(extractCookies(response));

            if (!resp.isEmpty()) {
                json = new JSONTokener(resp).nextValue();

                if (json instanceof JSONArray) {
                    isArray = true;
                    res.setUserObject("Response Array");
                } else if (json instanceof JSONObject) {
                    isArray = false;
                    res.setUserObject("Response Object");
                }

                ui.wipeRes();
                unpack(res, "", json, -1);
            } else {
                ui.wipeRes();
            }

        } catch (IOException | ParseException | IllegalArgumentException ex) {
            // System.out.print(ex.getMessage());
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
            ArrayList<Map<String, Object>> arr = new ArrayList<Map<String, Object>>();
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
        HashMap<String, Object> h = null;
        ArrayList<Object> a = null;
        if (parent instanceof Map) {
            h = (HashMap<String, Object>) parent;
        } else if (parent instanceof ArrayList) {
            a = (ArrayList<Object>) parent;
        }

        while (e.hasMoreElements()) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.nextElement();
            JsonElement element = (JsonElement) node.getUserObject();
            if (element.elementType.equals(Type.ARRAY)) {
                Map<String, Object> obj = new HashMap<>();
                parse(node, obj, "");
                // System.out.println("a:" + obj.toString());
                a.add(obj);
            } else if (element.elementType.equals(Type.ARRAYKEY)) {
                ArrayList<Map<String, Object>> arr = new ArrayList<Map<String, Object>>();
                parse(node, arr, node.toString());
                // System.out.println("h:" + node.toString());
                h.put(node.toString(), arr);
            } else if (element.elementType.equals(Type.KEY)) {
                if (node.getChildCount() > 1) {
                    Map<String, Object> newnode = new HashMap<>();
                    parse(node, newnode, node.toString());
                    if (h != null) {
                        // System.out.println("hh:" + node.toString());
                        h.put(node.toString(), newnode);
                    } else {
                        // System.out.println("aa:" + newnode.toString());
                        a.add(newnode);
                    }
                    // look ahead for values
                } else if (node.getChildCount() == 1) {
                    DefaultMutableTreeNode innerNode = (DefaultMutableTreeNode) node.getFirstChild();
                    JsonElement innerElement = (JsonElement) innerNode.getUserObject();
                    // special case - empty array
                    if (innerElement.elementType.equals(Type.ARRAYKEY)) {
                        ArrayList<Map<String, Object>> arr = new ArrayList<Map<String, Object>>();
                        Map<String, Object> obj = new HashMap<>();
                        obj.put(innerElement.toString(), arr);
                        // System.out.println("hhh:" + node.toString());
                        h.put(node.toString(), obj);

                    } else {
                        if (h != null) {
                            // System.out.println("hhhh:" + node.toString());
                            // special case - single key val pair
                            if (innerNode.getChildCount() > 0) {
                                DefaultMutableTreeNode valNode = (DefaultMutableTreeNode) innerNode.getFirstChild();
                                // System.out.println("KV:"+innerNode.toString()+","+valNode.toString());
                                Map<String, Object> kv = new HashMap<>();
                                kv.put(innerNode.toString(), valNode.toString());
                                h.put(node.toString(), kv);
                            } else {
                                h.put(node.toString(), innerElement.elementObject);
                            }

                        } else {
                            // System.out.println("aaaa:" + innerElement.toString());
                            a.add(innerElement.elementObject);
                        }
                    }
                } else {
                    if (h != null) {
                        // System.out.println("hhhhh:" + node.toString());
                        h.put(node.toString(), new HashMap<>());
                    } else {
                        // System.out.println("aaaaa:" + element.elementObject.toString());
                        a.add(element.elementObject);
                    }
                }

            } else {
                // values should always be found by look-ahead
                System.out.println("UNEXPECTED:" + element.elementType);
            }

        }

    }

    void refresh(DefaultMutableTreeNode node) {
        String data = repack(node);
        System.out.println("data:" + data);
        ui.wipeReq();
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
            node.setUserObject("Request Array");
        } else {
            isArray = false;
            node.setUserObject("Request Object");
        }

        ui.wipeReq();
        unpack(node, "", json, -1);
    }

    void copyRes(DefaultMutableTreeNode source, DefaultMutableTreeNode target) {
        String data = repack(source);
        ui.wipeReq();
        Object json = new JSONTokener(data).nextValue();
        unpack(target, "", json, -1);
    }

    BasicCookieStore buildCookies(String rawCookies, String reqUrl) {
        BasicCookieStore cookieStore = new BasicCookieStore();

        if (!rawCookies.isEmpty()) {
            String domain = "";
            String path = "";

            try {
                URL url = new URL(reqUrl);
                domain = url.getProtocol() + "://" + url.getHost();
                path = url.getPath();
            } catch (MalformedURLException ex) {
            }

            String[] c = rawCookies.split("\\s*,\\s*");
            ArrayList<String> cookies = new ArrayList<>(Arrays.asList(c));

            for (String cookie : cookies) {
                if (cookie.contains("=")) {
                    String key = cookie.substring(0, cookie.indexOf("="));
                    String val = cookie.substring(1 + cookie.indexOf("="), cookie.length());

                    BasicClientCookie clientCookie = new BasicClientCookie(key, val);
                    clientCookie.setDomain(domain);
                    clientCookie.setPath(path);
                    cookieStore.addCookie(clientCookie);
                }
            }
        }
        return cookieStore;
    }

    Header[] buildHeaders(String rawHeaders) {
        HeaderGroup headerGroup = new HeaderGroup();

        if (!rawHeaders.isEmpty()) {
            String[] h = rawHeaders.split("\\s*,\\s*");
            ArrayList<String> headers = new ArrayList<>(Arrays.asList(h));

            for (String header : headers) {
                if (header.contains("=")) {
                    String key = header.substring(0, header.indexOf("="));
                    String val = header.substring(1 + header.indexOf("="), header.length());

                    headerGroup.addHeader(new BasicHeader(key, val));
                }
            }
        }
        return headerGroup.getAllHeaders();

    }

    String extractCookies(HttpResponse response) {
            String resCookies="";;
            Header[] resHeaders = response.getHeaders("Set-Cookie");
            for (Header resH : resHeaders) {
                resCookies = resCookies+resH.getValue().split(";")[0] + ", ";
            }
            if (!resCookies.isEmpty()) {
                resCookies = resCookies.substring(0, resCookies.lastIndexOf(",") -1);
            }
            return resCookies;
    }

}
