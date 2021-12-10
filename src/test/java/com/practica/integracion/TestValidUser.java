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
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@ExtendWith(MockitoExtension.class)
public class TestValidUser {

	/**
	 * RELLENAR POR EL ALUMNO
	 * Salud 
	 */
	int a = 3 + 2;
//---------------------------------JAIME-----------------------

	@Mock
	private static AuthDAO mockAuthDao;
	@Mock
	private static GenericDAO mockGenericDao;
	@DisplayName("startRemote valid user valid system")
	@Test
	public void testStartRemoteSystemWithValidUserAndSystem() throws Exception {
		User validUser = new User("1","Ana","Lopez","Madrid", new ArrayList<Object>(Arrays.asList("hola", 2)));
		when(mockAuthDao.getAuthData(validUser.getId())).thenReturn(validUser);

		String validId = "12345"; // id valido de sistema
		ArrayList<Object> lista = new ArrayList<>(Arrays.asList("uno", "dos"));
		when(mockGenericDao.getSomeData(validUser, "where id=" + validId)).thenReturn(lista);
		// primero debe ejecutarse la llamada al dao de autenticación
		// despues el de  acceso a datos del sistema (la validaciones del orden en cada prueba)
		InOrder ordered = inOrder(mockAuthDao, mockGenericDao);
		// instanciamos el manager con los mock creados
		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);
		// llamada al api a probar
		Collection<Object> retorno = manager.startRemoteSystem(validUser.getId(), validId);
		assertEquals(retorno.toString(), "[uno, dos]");
		// vemos si se ejecutan las llamadas a los dao, y en el orden correcto
		ordered.verify(mockAuthDao).getAuthData(validUser.getId());
		ordered.verify(mockGenericDao).getSomeData(validUser, "where id=" + validId);
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
	//-------------------------Espacio Igor------------------------------------

}
