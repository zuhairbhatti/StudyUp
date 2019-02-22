package edu.studyup.serviceImpl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import edu.studyup.entity.Event;
import edu.studyup.entity.Location;
import edu.studyup.entity.Student;
import edu.studyup.util.DataStorage;
import edu.studyup.util.StudyUpException;

class EventServiceImplTest {

	EventServiceImpl eventServiceImpl;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		eventServiceImpl = new EventServiceImpl();
		//Create Student
		Student student = new Student();
		student.setFirstName("John");
		student.setLastName("Doe");
		student.setEmail("JohnDoe@email.com");
		student.setId(1);
		
		Student student2 = new Student();
		student2.setFirstName("Joh");
		student2.setLastName("Do");
		student2.setEmail("JohDo@email.com");
		student2.setId(2);
		
		/*
		Student student3 = new Student();
		student3.setFirstName("Jo");
		student3.setLastName("D");
		student3.setEmail("JoD@email.com");
		student3.setId(3);*/
		
		// Create Event1
		Event event = new Event();
		event.setEventID(1);
		event.setDate(new Date());
		event.setName("Event 1");
		Location location = new Location(-122, 37);
		event.setLocation(location);
		List<Student> eventStudents = new ArrayList<>();
		eventStudents.add(student);
		event.setStudents(eventStudents);
		
		DataStorage.eventData.put(event.getEventID(), event);

		
		// Create Event2 Past Event
		Event event2 = new Event();
		event2.setEventID(2);
		event2.setDate(new Date(90,1,1));
		event2.setName("Event 2");
		event2.setLocation(location);
		List<Student> eventStudents2 = new ArrayList<>();
		eventStudents2.add(student);
		eventStudents2.add(student2);
		event2.setStudents(eventStudents2);
		
		DataStorage.eventData.put(event2.getEventID(), event2);
		
		// Create Event3 Future Event
		Event event3 = new Event();
		event3.setEventID(3);
		event3.setDate(new Date(120,1,1));
		event3.setName("Event 3");
		event3.setLocation(location);
		List<Student> eventStudents3 = new ArrayList<>();
		event3.setStudents(eventStudents3);
		
		DataStorage.eventData.put(event3.getEventID(), event3);
		
		

	}

	@AfterEach
	void tearDown() throws Exception {
		DataStorage.eventData.clear();
	}

	@Test
	void testUpdateEventName_GoodCase() throws StudyUpException {
		int eventID = 1;
		eventServiceImpl.updateEventName(eventID, "Renamed Event 1");
		assertEquals("Renamed Event 1", DataStorage.eventData.get(eventID).getName());
	}
	
	@Test
	void testUpdateEvent_WrongEventID_badCase() {
		int eventID = 4;
		Assertions.assertThrows(StudyUpException.class, () -> {
			eventServiceImpl.updateEventName(eventID, "Renamed Event 3");
		  });
	}
	
	@Test
	void addStudentToEvent_WrongEventID_badCase() {
		int eventID = 4;
		Student studentTest = new Student();
		studentTest.setFirstName("Test");
		studentTest.setLastName("Doe");
		studentTest.setEmail("TestDoe@email.com");
		Assertions.assertThrows(StudyUpException.class, () -> {
			eventServiceImpl.addStudentToEvent(studentTest, eventID);
		  });
	}
	
	@Test
	void addStudentToEvent_Null_badCase() throws StudyUpException {
		// Create Event3 Future Event
		Event event5 = new Event();
		event5.setEventID(5);
		event5.setDate(new Date(130,1,1));
		event5.setName("Event 5");
		
		DataStorage.eventData.put(event5.getEventID(), event5);
		
		Student studentTest = new Student();
		studentTest.setFirstName("Test");
		studentTest.setLastName("Doe");
		studentTest.setEmail("TestDoe@email.com");
		
		int eventID = 5;
		
		eventServiceImpl.addStudentToEvent(studentTest, eventID);
		Event eventTest = DataStorage.eventData.get(eventID);
		
		List<Student> ListTest = eventTest.getStudents();
		
		assertEquals(1, ListTest.size());
	}
	
	@Test
	void updateEventName_ShouldThrowMax_When_NameMoreThan20() throws StudyUpException {
		int eventID = 1;
		Assertions.assertThrows(StudyUpException.class, () -> {
			eventServiceImpl.updateEventName(eventID, "This is more than twenty characters");
		});
	}
	
	@Test
	void updateEventName_NameLessThan20() throws StudyUpException {
		int eventID = 1;
		eventServiceImpl.updateEventName(eventID, "wenty characters si");
		assertEquals("wenty characters si", DataStorage.eventData.get(eventID).getName());
	}
	
	@Test
	void updateEventName_NameEquals20() throws StudyUpException {
		int eventID = 1;
		eventServiceImpl.updateEventName(eventID, "wenty characters six");
		assertEquals("wenty characters six", DataStorage.eventData.get(eventID).getName());
	}
	
	@Test
	void getActiveEvents_Test() {
		List<Event> activeEvents =  eventServiceImpl.getActiveEvents();
		
		assertTrue(activeEvents.contains(DataStorage.eventData.get(1)));
		assertFalse(activeEvents.contains(DataStorage.eventData.get(2)));
		assertTrue(activeEvents.contains(DataStorage.eventData.get(3)));
	}
	
	@Test
	void getPastEvents_Test() {	
		List<Event> pastEvents = eventServiceImpl.getPastEvents();
		
		assertTrue(pastEvents.contains(DataStorage.eventData.get(2)));
	}
	
	@Test
	void addStudentToEvent_Test_ZeroStudents() throws StudyUpException {
		int eventID = 3;
		Student studentTest = new Student();
		studentTest.setFirstName("Test");
		studentTest.setLastName("Doe");
		studentTest.setEmail("TestDoe@email.com");
		
		eventServiceImpl.addStudentToEvent(studentTest, eventID);
		Event eventTest = DataStorage.eventData.get(eventID);
		
		List<Student> ListTest = eventTest.getStudents();
		
		assertEquals(1, ListTest.size());	
	}
	
	@Test
	void addStudentToEvent_Test_OneStudents() throws StudyUpException {
		int eventID = 1;
		Student studentTest = new Student();
		studentTest.setFirstName("Test");
		studentTest.setLastName("Doe");
		studentTest.setEmail("TestDoe@email.com");
		
		eventServiceImpl.addStudentToEvent(studentTest, eventID);
		Event eventTest = DataStorage.eventData.get(eventID);
		
		List<Student> ListTest = eventTest.getStudents();
		
		assertEquals(2, ListTest.size());	
	}
	
	@Test
	void addStudentToEvent_Test_TwoStudents() throws StudyUpException {
		int eventID = 2;
		Student studentTest = new Student();
		studentTest.setFirstName("Test");
		studentTest.setLastName("Doe");
		studentTest.setEmail("TestDoe@email.com");
		
		eventServiceImpl.addStudentToEvent(studentTest, eventID);
		Event eventTest = DataStorage.eventData.get(eventID);
		
		List<Student> ListTest = eventTest.getStudents();
		
		assertEquals(2, ListTest.size());	
	}
	
	@Test
	void deleteEvent_Test() {
		int eventID = 3;
		assertEquals(DataStorage.eventData.get(eventID), eventServiceImpl.deleteEvent(eventID));
	}
	

}
