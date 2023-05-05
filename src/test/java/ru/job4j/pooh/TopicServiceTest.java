package ru.job4j.pooh;


import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

public class TopicServiceTest {


    @Test
    public void whenTopic() {
        TopicService topicService = new TopicService();
        String paramForPublisher1 = "temperature=18";
        String paramForPublisher2 = "temperature=22";
        String paramForPublisher3 = "temperature=25";
        String paramForSubscriber1 = "client407";
        String paramForSubscriber2 = "client6565";
        String paramForSubscriber3 = "client007";

        topicService.process(new Req("GET", "topic", "weather", paramForSubscriber1));

        topicService.process(new Req("POST", "topic", "weather", paramForPublisher1));

        Resp result1 = topicService.process(new Req("GET", "topic", "weather", paramForSubscriber1));

        Resp result2 = topicService.process(new Req("GET", "topic", "weather", paramForSubscriber2));

        topicService.process(new Req("GET", "topic", "weather", paramForSubscriber3));
        topicService.process(new Req("POST", "topic", "weather", paramForPublisher2));
        topicService.process(new Req("POST", "topic", "weather", paramForPublisher3));

        Resp result3 = topicService.process(new Req("GET", "topic", "weather", paramForSubscriber3));
        Resp result4 = topicService.process(new Req("GET", "topic", "weather", paramForSubscriber3));
        Resp result5 = topicService.process(new Req("GET", "topic", "weather", paramForSubscriber3));

        assertThat(result1.text()).isEqualTo("temperature=18");
        assertThat(result2.text()).isEqualTo("");

        assertThat(result3.text()).isEqualTo("temperature=22");
        assertThat(result4.text()).isEqualTo("temperature=25");
        assertThat(result5.text()).isEqualTo("");


    }
}