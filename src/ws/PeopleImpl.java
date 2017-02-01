package ws;

import model.Activity;
import model.ActivitySelection;
import model.Goal;
import model.HealthMeasure;
import model.HealthMeasureHistory;
import model.Person;
import java.net.URI;
import java.util.List;
import javax.jws.WebService;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.jersey.client.ClientConfig;

@WebService(endpointInterface = "ws.People", serviceName = "PeopleService")
public class PeopleImpl implements People {
	
	private static URI getEx1BaseURI() {
		return UriBuilder.fromUri("https://shrouded-refuge-42685.herokuapp.com/storage").build();
	}
	
	private static URI getEx2BaseURI() {
		return UriBuilder.fromUri("https://safe-ravine-27770.herokuapp.com/blogic").build();
	}
	
	@Override
	public Person getPersonById(int idPerson) {
		System.out.println("Getting Person's information identified by idPerson = " + idPerson + "...");
		ClientConfig clientConfig = new ClientConfig();
		Client client = ClientBuilder.newClient(clientConfig);
		WebTarget service = client.target(getEx1BaseURI()).path("person").path(String.valueOf(idPerson));
		Response response = service.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get();
		int httpStatus = response.getStatus();
		if (httpStatus == 200) {
			Person person = response.readEntity(Person.class);
			return person;
		}
		return null;
	}

	@Override
	public List<Activity> searchActivities(int idPerson, String type) {
		System.out.println("Searching activities...");
		ClientConfig clientConfig = new ClientConfig();
		Client client = ClientBuilder.newClient(clientConfig);
		WebTarget service = client.target(getEx1BaseURI()).path("activity").queryParam("type", type);
		Response response = service.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get();
		int httpStatus = response.getStatus();
		if (httpStatus == 200) {
			List<Activity> activities = response.readEntity(new GenericType<List<Activity>>() {});
			return activities;
		}
		return null;
	}

	@Override
	public Person updateHealthMeasure(int idPerson, double height, double weight, int age) {
		System.out.println("Updating health profile...");
		
		Person person = getPersonById(idPerson);
		
		for (HealthMeasure healthMeasure : person.getHealthMeasures()) {
			if (healthMeasure.getMeasureDefinition().getMeasureName().equalsIgnoreCase("weight")) {
				healthMeasure.setValue(weight);
			} else if (healthMeasure.getMeasureDefinition().getMeasureName().equalsIgnoreCase("height")) {
				healthMeasure.setValue(height);
			} else if (healthMeasure.getMeasureDefinition().getMeasureName().equalsIgnoreCase("age")) {
				healthMeasure.setValue(age);
			}
		}
		
		ClientConfig clientConfig = new ClientConfig();
		Client client = ClientBuilder.newClient(clientConfig);
		WebTarget service = client.target(getEx2BaseURI()).path("person").path(String.valueOf(person.getIdPerson())).path("healthMeasure");
		Response response = service.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(Entity.json(person));
		int httpStatus = response.getStatus();
		if (httpStatus == 200) {
			person= response.readEntity(Person.class);
			return person;
		}
		return null;
	}

	@Override
	public List<Activity> suggestActivities(int idPerson) {
		System.out.println("Getting list of suggestion activities.");
		ClientConfig clientConfig = new ClientConfig();
		Client client = ClientBuilder.newClient(clientConfig);
		WebTarget service = client.target(getEx2BaseURI()).path("person").path(String.valueOf(idPerson)).path("activitySuggestion");
		Response response = service.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get();
		int httpStatus = response.getStatus();
		if (httpStatus == 200) {
			List<Activity> activities = response.readEntity(new GenericType<List<Activity>>() {});
			return activities;
		}
		return null;
	}

	@Override
	public Person addActivityToGoal(int idPerson, Activity ac) {
		System.out.println("Adding activity to Goal...");
		ClientConfig clientConfig = new ClientConfig();
		Client client = ClientBuilder.newClient(clientConfig);
		WebTarget service = client.target(getEx1BaseURI()).path("person").path(String.valueOf(idPerson)).path("goal").path("activitySelection");
		Response response = service.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(Entity.json(ac));
		int httpStatus = response.getStatus();
		if (httpStatus == 200) {
			Person person= response.readEntity(Person.class);
			return person;
		}
		return null;
	}

	@Override
	public List<HealthMeasureHistory> getMeasureHistories(int idPerson) {
		System.out.println("Getting Person's Health Measure History. Person is identified by idPerson = " + idPerson + "...");
		ClientConfig clientConfig = new ClientConfig();
		Client client = ClientBuilder.newClient(clientConfig);
		WebTarget service = client.target(getEx1BaseURI()).path("person").path(String.valueOf(idPerson)).path("healthMeasureHistory");
		Response response = service.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get();
		int httpStatus = response.getStatus();
		if (httpStatus == 200) {
			List<HealthMeasureHistory> healthMeasureHistories = response.readEntity(new GenericType<List<HealthMeasureHistory>>() {});
			return healthMeasureHistories;
		}
		return null;
	}

	@Override
	public List<Goal> getGoalHistories(int idPerson) {
		System.out.println("Getting Person's Goal history. Person is identified by idPerson = " + idPerson + "...");
		ClientConfig clientConfig = new ClientConfig();
		Client client = ClientBuilder.newClient(clientConfig);
		WebTarget service = client.target(getEx1BaseURI()).path("person").path(String.valueOf(idPerson)).path("goalHistory");
		Response response = service.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get();
		int httpStatus = response.getStatus();
		if (httpStatus == 200) {
			List<Goal> goals = response.readEntity(new GenericType<List<Goal>>() {});
			return goals;
		}
		return null;
	}

	@Override
	public Person updateTimeForActivitySelection(int idPerson, ActivitySelection as) {
		System.out.println("Updating a current Activity Selection of a current Goal of a Person with id = " + idPerson + " and with Activity Selection id = " + as.getIdActivitySelection() + "...");
		ClientConfig clientConfig = new ClientConfig();
		Client client = ClientBuilder.newClient(clientConfig);
		WebTarget service = client.target(getEx2BaseURI()).path("person").path(String.valueOf(idPerson)).path("activitySelection");
		Response response = service.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).put(Entity.json(as));
		int httpStatus = response.getStatus();
		if (httpStatus == 200) {
			Person person = response.readEntity(Person.class);
			return person;
		}
		return null;
	}

	@Override
	public Person createNewGoal(int idPerson) {
		System.out.println("Creating new Goal for Person with id = " + idPerson + "...");
		Person  person = getPersonById(idPerson);
		ClientConfig clientConfig = new ClientConfig();
		Client client = ClientBuilder.newClient(clientConfig);
		WebTarget service = client.target(getEx2BaseURI()).path("person").path(String.valueOf(idPerson)).path("goal");
		Response response = service.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(Entity.json(person));
		int httpStatus = response.getStatus();
		if (httpStatus == 200) {
			person = response.readEntity(Person.class);
			return person;
		}
		return null;
	}
}
