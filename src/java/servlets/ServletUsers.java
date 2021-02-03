/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utilisateurs.Server;
import utilisateurs.modeles.User;

/**
 *
 * @author michel
 */

// En Java EE 6 on peut presque se passer du fichier web.xml, les annotations de codes
// sont très pratiques !
@WebServlet(name = "ServletUsers",
     urlPatterns = {"/ServletUsers"},
     initParams = {
         @WebInitParam(name = "ressourceDir", value = "C:\\Users\\kykwy\\Dropbox\\Mon PC (DESKTOP-0HQ9HSH)\\Documents\\mbds2\\jee project\\TP02_KARIMOU_YANNICK\\jsp")
     }
)
public class ServletUsers extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        // appel à init obligatoire sinon cet init sera appelé à chaque fois au lieu d'une seule !
        super.init(config);
        System.out.println("Dans le init ! Appelé une seule fois lors de la première invocation");

        // On charge la base de données des utilisateurs et on initialise dans la classe Server
        // le gestionnaire d'utilisateurs'
        Server.init(config.getInitParameter("ressourceDir"));
    }

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Pratique pour décider de l'action à faire
        String action = request.getParameter("action");
        String forwardTo = "";
        String message = "";

        if (action != null) {
            if (action.equals("listerLesUtilisateurs")) {
                Collection<User> liste = Server.uh.getUsers();
                request.setAttribute("listeDesUsers", liste);
                forwardTo = "index.jsp?action=listerLesUtilisateurs";
                message = "Liste des utilisateurs";
            }else if(action.equals("creerUtilisateursDeTest")){
                /*Server.uh.createUser("kykw","yann","karimou");
                Server.uh.createUser("adm","andre","konan");
                Server.uh.createUser("l'espoir","thomas","edison");
                Server.uh.createUser("le best","paul","kone");*/
                creerUtilisateurDeTest();
                Collection<User> liste = Server.uh.getUsers();
                request.setAttribute("listeDesUsers", liste);
                forwardTo = "index.jsp?action=listerLesUtilisateurs";
                message = "Les utilisateurs ont ete enregistrés avec succès";
            }
            else if(action.equals("creerUnUtilisateur")){
                creerUtilisateur( request);
                 Collection<User> liste = Server.uh.getUsers();
                request.setAttribute("listeDesUsers", liste);
                forwardTo = "index.jsp?action=listerLesUtilisateurs";
                message = "Utilisateur créé avec succès";
            }
        
            else {
                forwardTo = "index.jsp?action=todo";
                message = "La fonctionnalité pour le paramètre " + action + " est à implémenter !";
            }
        }

        RequestDispatcher dp = request.getRequestDispatcher(forwardTo + "&message=" + message);
        dp.forward(request, response);
        // Après un forward, plus rien ne peut être exécuté après !

    }
    private void creerUtilisateurDeTest(){
        
        try {
            User user1 = new User("karim","yannick","karimou");
            Server.uh.addUser(user1);
            User user2 = new User("kad","yann","koffi");
            Server.uh.addUser(user2);
            User user3 = new User("wes","andre","konan");
            Server.uh.addUser(user3);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ServletUsers.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void creerUtilisateur(HttpServletRequest request){
         String nom= request.getParameter("nom");
         String prenom= request.getParameter("prenom");
         String login= request.getParameter("login");
         Server.uh.createUser(login,prenom,nom);
    }
    


    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
