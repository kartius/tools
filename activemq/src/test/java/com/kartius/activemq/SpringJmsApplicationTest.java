package com.kartius.activemq;

import com.kartius.activemq.consumer.Receiver;
import com.kartius.activemq.data.CustomData;
import com.kartius.activemq.producer.Sender;
import org.apache.activemq.junit.EmbeddedActiveMQBroker;
import org.junit.AfterClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringJmsApplicationTest {

    private static ApplicationContext applicationContext;

    @Autowired
    void setContext(ApplicationContext applicationContext) {
        SpringJmsApplicationTest.applicationContext = applicationContext;
    }

    @AfterClass
    public static void afterClass() {
//        ((ConfigurableApplicationContext) applicationContext).close();
    }

    @ClassRule
    public static EmbeddedActiveMQBroker broker = new EmbeddedActiveMQBroker();


    @Autowired
    private Sender sender;

    @Autowired
    private Receiver receiver;

    @Test
    public void testSend() throws Exception {
        CustomData customData = new CustomData();
        customData.setCode("CodeTest");
        customData.setName("NameTest");

        CustomData customData1 = new CustomData();
        customData1.setCode("CodeTest1");
        customData1.setName("NameTest1");

        CustomData customData2 = new CustomData();
        customData2.setCode("CodeTest2");
        customData2.setName("NameTest2");

        sender.sendObject("test.q", customData);
        Thread.sleep(1000);
        sender.sendObject("test.q", customData1);
        Thread.sleep(1000);
        sender.sendObject("test.q", customData2);
    }

    @Test
    public void testReceive() throws Exception {
        System.out.println("----------------------------------");
        CustomData customDataResult = receiver.receiveObject("test.q");
        System.out.println(customDataResult.getName());
        System.out.println(customDataResult.getCode());
        System.out.println("----------------------------------");

        System.out.println("----------------------------------");
        CustomData customDataResult1 = receiver.receiveObject("test.q");
        System.out.println(customDataResult1.getName());
        System.out.println(customDataResult1.getCode());
        System.out.println("----------------------------------");
    }
}
