package ru.job4j.pooh;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueService implements Service {
    private ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> queue
            = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        ConcurrentLinkedQueue<String> linkedQueue = new ConcurrentLinkedQueue<>();
        String text = "No result";
        String status = "204";
        if ("POST".equals(req.httpRequestType())) {
            queue.putIfAbsent(req.getSourceName(), linkedQueue);
            queue.get(req.getSourceName()).add(req.httpRequestType() + " " + req.getSourceName()
                    + " " + req.getPoohMode() + " " + req.getParam());
            text = "";
            status = "200";
        }

        if ("GET".equals(req.httpRequestType())) {
            if (!queue.isEmpty()) {
                text = queue.getOrDefault(req.getSourceName(), linkedQueue).poll();
                if (text == null) {
                    text = "";
                } else {
                    text = Req.of(text).getParam();
                }
            }
        }
        return new Resp(text, status);
    }
}
