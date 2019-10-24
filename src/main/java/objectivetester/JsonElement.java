package objectivetester;

/**
 *
 * @author steve
 */
class JsonElement {

    String elementName;
    Type elementType;

    JsonElement(String elementName, Type elementType) {
        this.elementName = elementName;
        this.elementType = elementType;

    }

    @Override
    public String toString() {
        //debug
        //return this.elementType.toString()+this.elementName;
        return this.elementName;
    }

    Type getType() {
        return this.elementType;
    }

}

enum Type {
    ARRAY, KEY, VALUE, ARRAYKEY
}
