package statemachine.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.access.StateMachineAccess;
import org.springframework.statemachine.access.StateMachineFunction;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@ShellComponent
@Component
@Slf4j
public class StateMachineCommands {

    @Autowired
    private RedisRuntimePersister persister;

    @Autowired
    private StateMachineFactory<SimpleSpringMachineFactory.States, SimpleSpringMachineFactory.Events> factory;
    private StateMachine<SimpleSpringMachineFactory.States, SimpleSpringMachineFactory.Events> stateMachine;

    @PostConstruct
    public void init() {
        String machineId = "1";
        stateMachine = factory.getStateMachine(machineId);
        restore(machineId);
    }

    private void restore(String machineId) {
        final StateMachineContext<SimpleSpringMachineFactory.States, SimpleSpringMachineFactory.Events> context = persister.read(machineId);
        if (context != null) {
            stateMachine.stop();
            stateMachine.getStateMachineAccessor().doWithAllRegions(function -> function.resetStateMachine(context));
            stateMachine.start();
            log.info(stateMachine.getState().getId().name());
        }
    }


    @ShellMethod(value = "event", key = "sendEvent")
    public void sendEvent(SimpleSpringMachineFactory.Events event) {
        stateMachine.sendEvent(event);
    }

}