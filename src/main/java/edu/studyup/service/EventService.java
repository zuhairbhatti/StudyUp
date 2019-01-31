package edu.studyup.service;

import java.util.List;

import edu.studyup.entity.Event;
import edu.studyup.entity.Student;

/**
 * {@code EventService} holds all CRUD services for class {@link Event}
 * 
 * @author Shivani
 *
 */
public interface EventService {

	/**
	 * @param event The specific {@link Event} to be updated.
	 * @return The updated {@code event}
	 */
	public Event updateEvent(Event event);

	/**
	 * @return The list of all active {@code events}. i.e., the events whose date is
	 *         in the future.
	 */
	public List<Event> getActiveEvents();

	/**
	 * @return The list of all past {@code events}. i.e., the events with date <
	 *         today.
	 */
	public List<Event> getPastEvents();

//	/**
//	 * @return The list of present {@code events}. i.e., the events with date as
//	 *         today.
//	 */
//	public List<Event> getCurrentEvents();

	/**
	 * @param student The {@link Student} to be added.
	 * @param eventID The {@code eventID} of the specific {@link Event} to be
	 *                updated.
	 * @return {@code True}, if the student is added else {@code False}.
	 * @throws Exception 
	 */
	public boolean addStudentToEvent(Student student, int eventID) throws Exception;

	/**
	 * @param eventID The {@code eventID} of the specific {@link Event} to delete.
	 * @return {@code True}, if the event exists and is deleted, else {@code False}.
	 */
	public boolean deleteEvent(int eventID);

}
