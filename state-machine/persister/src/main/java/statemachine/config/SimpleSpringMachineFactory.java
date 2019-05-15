package statemachine.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;


@EnableStateMachineFactory
@Configuration
@Slf4j
public class SimpleSpringMachineFactory extends StateMachineConfigurerAdapter<SimpleSpringMachineFactory.States, SimpleSpringMachineFactory.Events> {

    @Autowired
    RedisRuntimePersister<States, Events, String> redisPersister;

    @Override
    public void configure(StateMachineConfigurationConfigurer<States, Events> config) throws Exception {
        config.withConfiguration().listener(listener()).autoStartup(true);
        config.withPersistence().runtimePersister(redisPersister);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
        transitions
                .withExternal().source(States.SUBMITTED).target(States.PAID).event(Events.PAY)
                .and()
                .withExternal().source(States.PAID).target(States.FULFILLED).event(Events.FULFILL)
                .and()
                .withExternal().source(States.SUBMITTED).target(States.CANCELLED).event(Events.CANCEL)
                .and()
                .withExternal().source(States.PAID).target(States.CANCELLED).event(Events.CANCEL);
    }

    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {
        states.withStates()
                .initial(States.SUBMITTED)
                .state(States.PAID)
                .end(States.CANCELLED)
                .end(States.FULFILLED);
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

    protected StateMachineListener<States, Events> listener() {
        return new StateMachineListenerAdapter<States, Events>() {
            @Override
            public void stateChanged(State<States, Events> from, State<States, Events> to) {
                System.out.println("State was changed from " + (from != null ? from.getId() : from) + " to " + to.getId());
            }
        };
    }
}
