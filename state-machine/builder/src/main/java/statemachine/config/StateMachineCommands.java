package statemachine.config;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@ShellComponent
@Component
public class StateMachineCommands {

	private SpringMachineBuilder springMachineBuilder = new SpringMachineBuilder();

	@PostConstruct
	public void init() throws Exception {
		springMachineBuilder.start();
	}


	@ShellMethod(value = "event", key = "sendEvent")
	public void sendEvent(SpringMachineBuilder.Events event) throws Exception {
		springMachineBuilder.getStateMachine().sendEvent(event);
	}

}