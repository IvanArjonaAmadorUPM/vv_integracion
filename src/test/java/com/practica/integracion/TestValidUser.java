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
import org.mockito.Spy;

import javax.naming.OperationNotSupportedException;
import com.practica.integracion.manager.SystemManagerException;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@ExtendWith(MockitoExtension.class)

public class TestValidUser {

	@Mock
	private static AuthDAO mockAuthDao;
	@Mock
	private static GenericDAO mockGenericDao;


	/**
	 * VALID SYSTEM
	 */

	//---------------------------------JAIME-----------------------
	@DisplayName("startRemote valid user valid system")
	@Test
	public void testStartRemoteSystemWithValidUserAndSystem() throws Exception {
		User validUser = new User("1","Ana","Lopez","Madrid", new ArrayList<Object>(Arrays.asList(1, 2)));
		when(mockAuthDao.getAuthData(validUser.getId())).thenReturn(validUser);

		String validId = "12345";
		ArrayList<Object> lista = new ArrayList<>(Arrays.asList("uno", "dos"));
		when(mockGenericDao.getSomeData(validUser, "where id=" + validId)).thenReturn(lista);

		InOrder ordered = inOrder(mockAuthDao, mockGenericDao);

		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);

		Collection<Object> retorno = manager.startRemoteSystem(validUser.getId(), validId);
		assertEquals(retorno.toString(), "[uno, dos]");

		ordered.verify(mockAuthDao).getAuthData(validUser.getId());
		ordered.verify(mockGenericDao).getSomeData(validUser, "where id=" + validId);
	}

	//-------------------------Espacio Santiago------------------------------------
	@DisplayName("stopRemote valid user valid system")
	@Test
	public void testStopRemoteSystemWithValidUserAndSystem() throws Exception{
		User validUser = new User("1","Ana","Lopez","Madrid", new ArrayList<Object>(Arrays.asList(1, 2)));
		when(mockAuthDao.getAuthData(validUser.getId())).thenReturn(validUser);

		String validId = "12345"; // id valido de sistema
		ArrayList<Object> lista = new ArrayList<>(Arrays.asList("uno", "dos"));
		when(mockGenericDao.getSomeData(validUser, "where id=" + validId)).thenReturn(lista);

		InOrder ordered = inOrder(mockAuthDao, mockGenericDao);

		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);

		Collection<Object> retorno = manager.stopRemoteSystem(validUser.getId(), validId);
		assertEquals(retorno.toString(), "[uno, dos]");

		ordered.verify(mockAuthDao, times(1)).getAuthData(validUser.getId());
		ordered.verify(mockGenericDao, times(1)).getSomeData(validUser, "where id=" + validId);

	}


	//-------------------------Espacio Ivan-----------------------------------
	@DisplayName("addRemote valid user valid system")
	@Test
	public void testAddRemoteSystemWithValidUserAndSystem() throws Exception{
		User validUser = new User("98743","Iván","Arjona","Valencia", new ArrayList<Object>(Arrays.asList(1, 2)));
		when(mockAuthDao.getAuthData(validUser.getId())).thenReturn(validUser);

		Object remote = "12345";
		when(mockGenericDao.updateSomeData(validUser,remote)).thenReturn(true);

		InOrder ordered = inOrder(mockAuthDao, mockGenericDao);
		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);

		manager.addRemoteSystem(validUser.getId(),remote);

		ordered.verify(mockAuthDao, times(1)).getAuthData(validUser.getId());
		ordered.verify(mockGenericDao, times(1)).updateSomeData(validUser, remote);

	}


	//-------------------------Espacio Igor------------------------------------
	/*
	La función deleteRemoteSystem, crea un usuario que es el mismo que elimina.
	Esto hace que no se pueda verificar ni utilizar el usuario pasado por argumento.
	*/

	@DisplayName("DeleteRemote valid user valid system")
	@Test
	public void testDeleteRemoteSystemWithValidUserAndSystem() throws Exception{
		User validUser = new User("1","Ana","Lopez","Madrid", new ArrayList<Object>(Arrays.asList(1, 2)));
		when(mockAuthDao.getAuthData(validUser.getId())).thenReturn(validUser);

		String remote = "12345";
		when(mockGenericDao.deleteSomeData(validUser,remote)).thenReturn(true);

		InOrder ordered = inOrder(mockGenericDao);
		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);

		manager.deleteRemoteSystem(validUser.getId(),remote);

		
		ordered.verify(mockGenericDao, times(1)).deleteSomeData(validUser, remote);

	}



	/**
	 * INVALID SYSTEM
	 */
	//--------------------------------Jaime-------------------------
	@DisplayName("startRemote valid user invalid system")
	@Test
	public void testStartRemoteSystemWithValidUserAndInvalidSystem() throws Exception{
		User validUser = new User("12","Ana","Lopez","Madrid", new ArrayList<Object>(Arrays.asList(1, 2)));
		when(mockAuthDao.getAuthData(validUser.getId())).thenReturn(validUser);

		String inValidId = "12345"; // id inválido de sistema
		when(mockGenericDao.getSomeData(validUser, "where id=" + inValidId)).thenThrow(new OperationNotSupportedException());

		InOrder ordered = inOrder(mockAuthDao, mockGenericDao);

		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);

		assertThrows(SystemManagerException.class, () -> {
			manager.startRemoteSystem(validUser.getId(), inValidId);
		});

		ordered.verify(mockAuthDao, times(1)).getAuthData(validUser.getId());
		ordered.verify(mockGenericDao, times(1)).getSomeData(validUser, "where id=" + inValidId);

	}



	//---------------------------------Ivan-----------------------
	@DisplayName("addRemote valid user invalid system")
	@Test
	public void testAddRemoteSystemWithValidUserAndInvalidSystem() throws Exception{
		User validUser = new User("98743","Iván","Arjona","Valencia", new ArrayList<Object>(Arrays.asList(1, 2)));
		when(mockAuthDao.getAuthData(validUser.getId())).thenReturn(validUser);

		Object remote = "12345";
		when(mockGenericDao.updateSomeData(validUser,remote)).thenThrow(new OperationNotSupportedException());

		InOrder ordered = inOrder(mockAuthDao, mockGenericDao);
		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);

		assertThrows(SystemManagerException.class, () -> {
			manager.addRemoteSystem(validUser.getId(),remote);
		});
		ordered.verify(mockAuthDao, times(1)).getAuthData(validUser.getId());
		ordered.verify(mockGenericDao, times(1)).updateSomeData(validUser, remote);

	}

	
	//-------------------------Espacio Santiago------------------------------------
	@DisplayName("stopRemote valid user invalid system")
	@Test
	public void testStopRemoteSystemWithValidUserAndInvalidSystem() throws Exception{
		User validUser = new User("1","Ana","Lopez","Madrid", new ArrayList<Object>(Arrays.asList(1, 2)));
		when(mockAuthDao.getAuthData(validUser.getId())).thenReturn(validUser);

		String inValidId = "12345"; // id inválido de sistema
		when(mockGenericDao.getSomeData(validUser, "where id=" + inValidId)).thenThrow(new OperationNotSupportedException());

		InOrder ordered = inOrder(mockAuthDao, mockGenericDao);

		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);

		assertThrows(SystemManagerException.class, () -> {
			manager.stopRemoteSystem(validUser.getId(), inValidId);
		});

		ordered.verify(mockAuthDao, times(1)).getAuthData(validUser.getId());
		ordered.verify(mockGenericDao, times(1)).getSomeData(validUser, "where id=" + inValidId);

	}

	//-------------------------Espacio Igor------------------------------------

	@DisplayName("DeleteRemote valid user invalid system")
	@Test
	public void testDeleteRemoteSystemWithValidUserAndInValidSystem() throws Exception{

		User validUser = new User("1","Ana","Lopez","Madrid", (List<Object>) new ArrayList<Object>(Arrays.asList(1, 2)));  
		Mockito.lenient().when(mockAuthDao.getAuthData(validUser.getId())).thenReturn(validUser);

		String idInvalid = "12345";
		Mockito.lenient().when(mockGenericDao.deleteSomeData(validUser, idInvalid)).thenThrow(new OperationNotSupportedException());
		 
		InOrder ordered = inOrder(mockGenericDao);
		  
		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);
		
		assertThrows(SystemManagerException.class, () -> {
			  manager.deleteRemoteSystem(validUser.getId(), idInvalid);
		  });		 
		  
		ordered.verify(mockGenericDao, times(1)).deleteSomeData(validUser, idInvalid);

	}







}
