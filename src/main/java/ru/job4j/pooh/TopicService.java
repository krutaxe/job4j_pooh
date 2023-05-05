package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TopicService implements Service {
    private ConcurrentHashMap<String, ConcurrentHashMap<String,
            ConcurrentLinkedQueue<String>>> topics
            = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        String text = "No result";
        String status = "204";

        if ("POST".equals(req.httpRequestType())) {
            ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> queue =
                    topics.getOrDefault(req.getSourceName(), new ConcurrentHashMap<>());
            if (queue != null) {
                queue.keySet().forEach(key -> queue.get(key).add(req.getParam()));
                text = "";
                status = "200";
            }

        } else if ("GET".equals(req.httpRequestType())) {
            topics.putIfAbsent(req.getSourceName(), new ConcurrentHashMap<>());
            ConcurrentHashMap<String, ConcurrentLinkedQueue<String>>
                    queue = topics.get(req.getSourceName());
            queue.putIfAbsent(req.getParam(), new ConcurrentLinkedQueue<>());
            text = queue.get(req.getParam()).poll();
            if (text == null) {
                text = "";
            } else {
                status = "200";
            }
        }
        return new Resp(text, status);
    }
}
