package fr.eni.encheres.controllers;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@WebServlet(name = "ServletImages", value = "/images")
public class ServletImages extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String image = request.getParameter("image");
        System.out.println("Sending Image :" + image);
        response.setContentType("image/*");
        String filePath = getServletContext().getRealPath("/images/"+image);
        Path path = Paths.get(filePath);
        byte[] imageContent = Files.readAllBytes(path);
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(imageContent);
        outputStream.flush();
        outputStream.close();
        response.flushBuffer();
        return;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
        return;
    }
}
