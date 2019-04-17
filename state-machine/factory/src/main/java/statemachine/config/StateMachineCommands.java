package statemachine.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@ShellComponent
@Component
public class StateMachineCommands {

    @Autowired
    private StateMachineFactory<SimpleSpringMachineFactory.States, SimpleSpringMachineFactory.Events> factory;
    private StateMachine<SimpleSpringMachineFactory.States, SimpleSpringMachineFactory.Events> stateMachine;

    @PostConstruct
    public void init() throws Exception {
        stateMachine = factory.getStateMachine("123");
    }


    @ShellMethod(value = "event", key = "sendEvent")
    public void sendEvent(SimpleSpringMachineFactory.Events event) throws Exception {
        stateMachine.sendEvent(event);
    }

}