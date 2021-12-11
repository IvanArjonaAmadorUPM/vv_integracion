package com.practica.integracion;


import com.practica.integracion.DAO.AuthDAO;
import com.practica.integracion.DAO.GenericDAO;
import com.practica.integracion.DAO.User;
import com.practica.integracion.manager.SystemManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;

import javax.naming.OperationNotSupportedException;
import com.practica.integracion.manager.SystemManagerException;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@ExtendWith(MockitoExtension.class)
//@MockitoSettings(strictness = Strictness.LENIENT)
public class TestInvalidUser {

	@Mock
	private static AuthDAO mockAuthDao;
	@Mock
	private static GenericDAO mockGenericDao;


	/**
	 * VALID SYSTEM
	 */

	//---------------------------------JAIME-----------------------
	@DisplayName("startRemote inValid user valid system")
	@Test
	public void testStartRemoteSystemWithInValidUserAndSystem() throws Exception{
		User invalidUser = new User("1","Ana","Lopez","Madrid", new ArrayList<Object>(Arrays.asList(1, 2)));
		when(mockAuthDao.getAuthData(invalidUser.getId())).thenReturn(null);

		String validId = "12345";
		when(mockGenericDao.getSomeData(null, "where id=" + validId)).thenThrow(new OperationNotSupportedException());

		InOrder ordered = inOrder(mockAuthDao, mockGenericDao);
		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);

		assertThrows(SystemManagerException.class, () -> {
			manager.startRemoteSystem(invalidUser.getId(), validId);
		});

		ordered.verify(mockAuthDao, times(1)).getAuthData(invalidUser.getId());
		ordered.verify(mockGenericDao, times(1)).getSomeData(null, "where id=" + validId);

	}
	//-------------------------Espacio Santiago------------------------------------
	@DisplayName("stopRemote inValid user valid system")
	@Test
	public void testStopRemoteSystemWithInValidUserAndSystem() throws Exception{
		User invalidUser = new User("1","Ana","Lopez","Madrid", new ArrayList<Object>(Arrays.asList(1, 2)));
		when(mockAuthDao.getAuthData(invalidUser.getId())).thenReturn(null);

		String validId = "12345"; 
		when(mockGenericDao.getSomeData(null, "where id=" + validId)).thenThrow(new OperationNotSupportedException());

		InOrder ordered = inOrder(mockAuthDao, mockGenericDao);
		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);
		  
		assertThrows(SystemManagerException.class, () -> {
			manager.stopRemoteSystem(invalidUser.getId(), validId);
		});
		  
		ordered.verify(mockAuthDao, times(1)).getAuthData(invalidUser.getId());
		ordered.verify(mockGenericDao, times(1)).getSomeData(null, "where id=" + validId);

	}

	//---------------------------------Ivan-----------------------
	@DisplayName("addRemote invalid user valid system")
	@Test
	public void testAddRemoteSystemWithInValidUserAndSystem() throws Exception {
		User inValidUser = new User("98743","Iván","Arjona","Valencia", new ArrayList<Object>(Arrays.asList(1, 2)));
		when(mockAuthDao.getAuthData(inValidUser.getId())).thenReturn(null);

		Object remote = "12345";
		when(mockGenericDao.updateSomeData(null,remote)).thenThrow(new OperationNotSupportedException());

		InOrder ordered = inOrder(mockAuthDao, mockGenericDao);
		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);

		assertThrows(SystemManagerException.class, () -> {
			manager.addRemoteSystem(inValidUser.getId(), remote);
		});

		ordered.verify(mockAuthDao, times(1)).getAuthData(inValidUser.getId());
		ordered.verify(mockGenericDao, times(1)).updateSomeData(null, remote);

	}

	//-------------------------Espacio Igor------------------------------------
	/*
	La función deleteRemoteSystem, crea un usuario que es el mismo que elimina.
	Esto hace que no se pueda verificar ni utilizar el usuario pasado por argumento.
	*/

	@DisplayName("DeleteRemote inValid user valid system")
	@Test
	public void testDeleteRemoteSystemWithInValidUserAndSystem() throws Exception{
		User inValidUser = new User("1","Ana","Lopez","Madrid", new ArrayList<Object>(Arrays.asList(1, 2)));
		when(mockAuthDao.getAuthData(inValidUser.getId())).thenReturn(null);

		String remote = "12345";
		Mockito.lenient().when(mockGenericDao.deleteSomeData(null,remote)).thenThrow(new OperationNotSupportedException());

		InOrder ordered = inOrder(mockAuthDao, mockGenericDao);
		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);

		assertThrows(SystemManagerException.class, () -> {
			manager.deleteRemoteSystem(inValidUser.getId(), remote);
		});
		ordered.verify(mockAuthDao, times(1)).getAuthData(inValidUser.getId());
		ordered.verify(mockGenericDao, times(1)).deleteSomeData(null, remote);

	}



	/**
	 * INVALID SYSTEM
	 */




	//---------------------------------JAIME-----------------------

	@DisplayName("startRemote inValid user inValid system")
	@Test
	public void testStartRemoteSystemWithInValidUserAndInValidSystem() throws Exception{
		User invalidUser = new User("12","Ana","Lopez","Madrid", new ArrayList<Object>(Arrays.asList(1, 2)));
		when(mockAuthDao.getAuthData(invalidUser.getId())).thenReturn(null);

		String inValidId = "12345";
		when(mockGenericDao.getSomeData(null, "where id=" + inValidId)).thenThrow(new OperationNotSupportedException());

		InOrder ordered = inOrder(mockAuthDao, mockGenericDao);
		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);

		assertThrows(SystemManagerException.class, () -> {
			manager.startRemoteSystem(invalidUser.getId(), inValidId);
		});

		ordered.verify(mockAuthDao, times(1)).getAuthData(invalidUser.getId());
		ordered.verify(mockGenericDao, times(1)).getSomeData(null, "where id=" + inValidId);

	}
	//-------------------------Espacio Santiago------------------------------------
	@DisplayName("stopRemote inValid user inValid system")
	@Test
	public void testStopRemoteSystemWithInValidUserAndInValidSystem() throws Exception{
		User invalidUser = new User("1","Ana","Lopez","Madrid", new ArrayList<Object>(Arrays.asList(1, 2)));
		  when(mockAuthDao.getAuthData(invalidUser.getId())).thenReturn(null);

		  String inValidId = "12345"; 
		  when(mockGenericDao.getSomeData(null, "where id=" + inValidId)).thenThrow(new OperationNotSupportedException());

		  InOrder ordered = inOrder(mockAuthDao, mockGenericDao);
		  SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);
		  
		  assertThrows(SystemManagerException.class, () -> {
			  manager.stopRemoteSystem(invalidUser.getId(), inValidId);
		  });
		  
		  ordered.verify(mockAuthDao, times(1)).getAuthData(invalidUser.getId());
		  ordered.verify(mockGenericDao, times(1)).getSomeData(null, "where id=" + inValidId);

	}


	//---------------------------------Ivan-----------------------
	@DisplayName("addRemote invalid user invalid system")
	@Test
	public void testAddRemoteSystemWithInValidUserAndInValidSystem() throws Exception{
		User inValidUser = new User("98743","Iván","Arjona","Valencia", new ArrayList<Object>(Arrays.asList(1, 2)));
		when(mockAuthDao.getAuthData(inValidUser.getId())).thenReturn(null);

		Object remote = "12345";
		when(mockGenericDao.updateSomeData(null,remote)).thenThrow(new OperationNotSupportedException());

		InOrder ordered = inOrder(mockAuthDao, mockGenericDao);
		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);

		assertThrows(SystemManagerException.class, () -> {
			manager.addRemoteSystem(inValidUser.getId(), remote);
		});

		ordered.verify(mockAuthDao, times(1)).getAuthData(inValidUser.getId());
		ordered.verify(mockGenericDao, times(1)).updateSomeData(null, remote);

	}

	//-------------------------Espacio Igor------------------------------------
	/*
	La función deleteRemoteSystem, crea un usuario que es el mismo que elimina.
	Esto hace que no se pueda verificar ni utilizar el usuario pasado por argumento.
	*/

	@DisplayName("DeleteRemote inValid user inValid system")
	@Test
	public void testDeleteRemoteSystemWithValidUserAndInValidSystem() throws Exception {
		User inValidUser = new User("1","Ana","Lopez","Madrid", new ArrayList<Object>(Arrays.asList(1, 2)));
		when(mockAuthDao.getAuthData(inValidUser.getId())).thenReturn(null);

		String remote = "12345";
		when(mockGenericDao.deleteSomeData(null,remote)).thenThrow(new OperationNotSupportedException());

		InOrder ordered = inOrder(mockAuthDao, mockGenericDao);
		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);

		assertThrows(SystemManagerException.class, () -> {
			manager.deleteRemoteSystem(inValidUser.getId(), remote);
		});

		ordered.verify(mockAuthDao, times(1)).getAuthData(inValidUser.getId());
		ordered.verify(mockGenericDao, times(1)).deleteSomeData(null, remote);

	}
	
}
