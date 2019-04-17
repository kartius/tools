package statemachine.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.config.configurers.StateConfigurer;
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
                .initial(States.SUBMITTED)
                .state(States.PAID)
                .end(States.CANCELLED)
                .end(States.FULFILLED);
        builder.configureTransitions()
                .withExternal().source(States.SUBMITTED).target(States.PAID).event(Events.PAY)
                .and()
                .withExternal().source(States.PAID).target(States.FULFILLED).event(Events.FULFILL)
                .and()
                .withExternal().source(States.SUBMITTED).target(States.CANCELLED).event(Events.CANCEL)
                .and()
                .withExternal().source(States.PAID).target(States.CANCELLED).event(Events.CANCEL);
        StateMachine<States, Events> build = builder.build();
        stateMachine = build;
    }

    public enum States {
        SUBMITTED,
        PAID,
        FULFILLED,
        CANCELLED;
    }

    public enum Events {
        FULFILL,
        PAY,
        CANCEL;
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
            return true;
        };
    }
}
