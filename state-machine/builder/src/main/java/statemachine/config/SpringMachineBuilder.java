package statemachine.config;

import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

public class SpringMachineBuilder {

    private StateMachine stateMachine;


    public void start() throws Exception {
        StateMachineBuilder.Builder<States, Events> builder = StateMachineBuilder.builder();
        builder.configureConfiguration()
                .withConfiguration()
                .autoStartup(true)
                .listener(listener());
        builder.configureStates().withStates()
                .initial(States.S1)
                .state(States.S1)
                .state(States.S2)
                .state(States.S3);
        builder.configureTransitions()
                .withExternal()
                .source(States.S1).target(States.S2).event(Events.E1);
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
                System.out.println("State was changed from " + (from != null ? from.getId() : from) + " to " + to.getId());
            }
        };
    }
}
