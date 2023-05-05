package ru.job4j.pooh;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

public class QueueServiceTest {

    @Test
    public void whenPostThenGetQueue() {
        QueueService queueService = new QueueService();
        String paramForPostMethod1 = "temperature=18";
        String paramForPostMethod2 = "temperature=20";

        queueService.process(new Req("POST", "queue", "weather", paramForPostMethod1));

        queueService.process(new Req("POST", "queue", "weather", paramForPostMethod2));


        Resp result1 = queueService.process(new Req("GET", "queue", "weather", null));

        Resp result2 = queueService.process(new Req("GET", "queue", "weather", null));

        Resp result3 = queueService.process(new Req("GET", "queue", "weather", null));

        assertThat(result1.text()).isEqualTo("temperature=18");
        assertThat(result2.text()).isEqualTo("temperature=20");
        assertThat(result3.text()).isEqualTo("");

        queueService.process(new Req("POST", "queue", "weather", paramForPostMethod1));

        queueService.process(new Req("POST", "queue", "weather", paramForPostMethod1));

        Resp result4 = queueService.process(new Req("GET", "queue", "weather", null));

        Resp result5 = queueService.process(new Req("GET", "queue", "weather", null));

        assertThat(result4.text()).isEqualTo("temperature=18");
        assertThat(result5.text()).isEqualTo("temperature=18");
    }
}