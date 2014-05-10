package frontend;

import auth.AccountService;
import templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Frontend extends HttpServlet {

    private final static DateFormat FORMATTER = new SimpleDateFormat("HH:mm:ss");
    private final static String REFRESH_PERIOD = "1000";
    private AccountService accountService;

    public Frontend(AccountService accountServiceInit)
    {
        accountService = accountServiceInit;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        Map<String, Object> pageVariables = new HashMap<>();

        String path = request.getPathInfo();
        switch (path) {
            case "/userid":
                Long userId = getSessionUserId(request.getSession());
                pageVariables.put("refreshPeriod", REFRESH_PERIOD);
                pageVariables.put("serverTime", getTime());
                pageVariables.put("userId", userId);
                response.getWriter().println(PageGenerator.getPage("userid.tml", pageVariables));
                break;
            case "/registrationError":
                response.getWriter().println("Некорректные данные, или такой пользователь уже существует");
                break;
            case "index":
                response.getWriter().println(PageGenerator.getPage("..\\static\\index.html", null));
                break;
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String login = request.getParameter("login");
        String pass = request.getParameter("password");

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        HttpSession currentSession = request.getSession();
        String path = request.getPathInfo();

        switch (path) {
            case "/login":
                if (accountService.checkPassword(login, pass)) {
                    setSessionUserId(currentSession, accountService.getUserId(login));
                    response.sendRedirect("/userid");
                } else
                    response.sendRedirect("/");
                break;
            case "/registerUser":
                if (accountService.registerUser(login, pass)) {
                    response.sendRedirect("/");
                } else {
                    response.sendRedirect("/registrationError");
                }
        }
    }

    public static String getTime()
    {
        return FORMATTER.format(new Date());
    }

    private void setSessionUserId(HttpSession session, long id)
    {
        session.setAttribute("userId", id);
    }

    private long getSessionUserId(HttpSession session)
    {
        Object rawId = session.getAttribute("userId");
        if( rawId != null)
            return (long) rawId;
        else
            return -1;
    }
}

