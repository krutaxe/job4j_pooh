package ru.job4j.pooh;

public class Req {
    private final String httpRequestType;
    private final String poohMode;
    private final String sourceName;
    private final String param;

    public Req(String httpRequestType, String poohMode, String sourceName, String param) {
        this.httpRequestType = httpRequestType;
        this.poohMode = poohMode;
        this.sourceName = sourceName;
        this.param = param;
    }

    public static Req of(String content) {
        String httpRequestType = content.split(" ")[0];
        String poohMode = content.split(" ")[1];
        String sourceName = content.split(" ")[2];
        String param = content.split(" ")[3];

        if (content.split(" ").length > 4) {
            poohMode = content.split("/")[1];
            sourceName = content.split("/")[2];

            if (httpRequestType.equals("GET") && poohMode.equals("topic")) {
                param = content.split("/")[3].split(" ")[0];
            }

            if (httpRequestType.equals("GET") && poohMode.equals("queue")) {
                sourceName = content.split("/")[2].split(" ")[0];
                param = "";
            }

            if (httpRequestType.equals("POST")) {
                sourceName = content.split("/")[2].split(" ")[0];
                param = content.split(System.lineSeparator())[7];
            }
        }
        return new Req(httpRequestType, poohMode, sourceName, param);
    }

    public String httpRequestType() {
        return httpRequestType;
    }

    public String getPoohMode() {
        return poohMode;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getParam() {
        return param;
    }
}
