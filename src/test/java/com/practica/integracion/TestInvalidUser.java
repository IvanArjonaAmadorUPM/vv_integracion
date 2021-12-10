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

import javax.naming.OperationNotSupportedException;
import com.practica.integracion.manager.SystemManagerException;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TestInvalidUser {

	@Mock
	private static AuthDAO mockAuthDao;
	@Mock
	private static GenericDAO mockGenericDao;


	/**
	 * VALID SYSTEM
	 */

	//---------------------------------JAIME-----------------------

	//-------------------------Espacio Santiago------------------------------------
	@DisplayName("stopRemote inValid user valid system")
	@Test
	public void testStopRemoteSystemWithInValidUserAndSystem() throws Exception{
		User invalidUser = new User("1","Ana","Lopez","Madrid", new ArrayList<Object>(Arrays.asList(1, 2)));
		  when(mockAuthDao.getAuthData(invalidUser.getId())).thenReturn(null);

		  String validId = "12345"; // id valido de sistema
		  when(mockGenericDao.getSomeData(null, "where id=" + validId)).thenThrow(new OperationNotSupportedException());

		  InOrder ordered = inOrder(mockAuthDao, mockGenericDao);

		  SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);
		  
		  assertThrows(SystemManagerException.class, () -> {
			  manager.stopRemoteSystem(invalidUser.getId(), validId);
		  });
		  
		// vemos si se ejecutan las llamadas a los dao, y en el orden correcto
		  ordered.verify(mockAuthDao, times(1)).getAuthData(invalidUser.getId());
		  ordered.verify(mockGenericDao, times(1)).getSomeData(null, "where id=" + validId);

	}

	/**
	 * INVALID SYSTEM
	 */

	//---------------------------------JAIME-----------------------

	//-------------------------Espacio Santiago------------------------------------
	


}
