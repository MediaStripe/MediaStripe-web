package com.imie.servlets;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imie.entities.Media;
import com.imie.services.impl.MediaService;

/**
 * Servlet implementation class TestMediaService
 */
@WebServlet("/TestMediaService")
public class TestMediaService extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@EJB
	private MediaService mediaService;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestMediaService() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Media media = mediaService.findById(4);
		System.out.println(media);
	}

}
