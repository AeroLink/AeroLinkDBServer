/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interceptors;

import Synapse.Crypt;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author BlackMoon
 */
@WebServlet(name = "apiPort", urlPatterns = {"/apiPort"})
public class apiPort extends HttpServlet {

    public apiPort() {
        if (!Core.core.isRunning) {
            Core.core.build();
            Core.core.startServer();
        }
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet apiPort</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet apiPort at " + Crypt.Encrypt("") + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);

        String Query = request.getParameter("0x1009A");
        String values = request.getParameter("0x1009B") == null ? "" : request.getParameter("0x1009B");
        String method = request.getParameter("0x1009C") == null ? "GET" : request.getParameter("0x1009C");

        System.out.println("Query ->" + Query);
        System.out.println("Values -> " + values);
        JSONObject obj = new JSONObject();
        JSONObject objx = new JSONObject();
        aerolinkDB db = new aerolinkDB();

        switch (method) {
            case "GET":
                JSONArray list = values.equals("") ? db.get(Query) : db.get(Query, values);
                obj.put("result", list);
                break;
            case "INSERT":
                objx = db.insert(Query, values, false);
                obj.put("result", objx);
                break;
            case "INSERT_RT_ID":
                objx = db.insert(Query, values, true);
                obj.put("result", objx);
                break;
            case "UPDATE":
                objx = db.update(Query, values);
                obj.put("result", objx);
                break;
            case "LOGIN":
                JSONArray list2 = db.login(Query, values);

                if (list2.isEmpty()) {
                    objx.put("success", false);
                } else {
                    objx.put("success", true);
                }
                
                list2.add(0, objx);
                obj.put("result", list2);
                break;
            default:
                break;
        }

        write(response, obj);

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void write(HttpServletResponse response, JSONObject map) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(map.toJSONString());
    }

    private void writeArray(HttpServletResponse response, JSONArray map) throws IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(map.toJSONString());
    }
}
