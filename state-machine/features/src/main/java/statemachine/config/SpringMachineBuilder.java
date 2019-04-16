package statemachine.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.guard.Guard;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

@Slf4j
public class SpringMachineBuilder {

    private StateMachine stateMachine;


    public void start() throws Exception {
        StateMachineBuilder.Builder<States, Events> builder = StateMachineBuilder.builder();
        builder.configureConfiguration()
                .withConfiguration()
                .autoStartup(true)
                .listener(listener());
        builder.configureStates().withStates()
                .initial(States.S1, initAction())
                .state(States.S1)
                .state(States.S2)
                .state(States.S3);
        builder.configureTransitions()
                .withExternal()
                .source(States.S1).target(States.S2).event(Events.E1).guard(guard()).action(transitionAtion());
        StateMachine<States, Events> build = builder.build();
        stateMachine = build;
    }

    public enum States {
        S1, S2, S3;
    }

    public enum Events {
        E1, E2;
    }

    public StateMachine getStateMachine() {
        return stateMachine;
    }

    protected StateMachineListener<States, Events> listener() {
        return new StateMachineListenerAdapter<States, Events>() {
            @Override
            public void stateChanged(State<States, Events> from, State<States, Events> to) {
                log.info("---Listener: State was changed from " + (from != null ? from.getId() : from) + " to " + to.getId());
            }
        };
    }

    private Action<States, Events> initAction() {
        return context -> {
            log.info("---Action: Perform action from init state");
        };
    }

    private Action<States, Events> transitionAtion() {
        return context -> {
            log.info("---Action: Perform action from transition");
        };
    }

    private Guard<States, Events> guard() {
        return context -> {
            final boolean b = false;
            return b;
        };
    }
}
