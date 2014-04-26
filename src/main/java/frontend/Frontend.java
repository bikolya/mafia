package frontend;

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
import java.util.concurrent.atomic.AtomicLong;

public class Frontend extends HttpServlet {

    private String login = "";
    private AtomicLong userIdGenerator = new AtomicLong();


    public static String getTime()
    {
        Date date = new Date();
        date.getTime();
        DateFormat formatter = new SimpleDateFormat("HH.mm.ss");
        return formatter.format(date);
    }

    private Long getUserId(HttpSession session)
    {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            userId = userIdGenerator.getAndIncrement();
            session.setAttribute("userId", userId);
        }
        return userId;
    }

    private boolean checkAuth(String login, String pass)
    {

        String hadrUser = "1";
        String hardPass = "1";
        String hardUser2 = "2";
        String hardPass2 = "2";
        return (login.equals(hadrUser) && pass.equals(hardPass))
                || (login.equals(hardUser2) && pass.equals(hardPass2) );
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        Map<String, Object> pageVariables = new HashMap<>();

        String path = request.getPathInfo();
        switch (path) {
            case "/userid":
                Long userId = getUserId(request.getSession());
                pageVariables.put("refreshPeriod", "1000");
                pageVariables.put("serverTime", getTime());
                pageVariables.put("userId", userId);
                response.getWriter().println(PageGenerator.getPage("userid.tml", pageVariables));
                return;
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
                if (checkAuth(login, pass)) {
                    if (checkUserId(currentSession)) {
                        closeUserId(currentSession);
                    }
                    openUserId(currentSession);
                    response.sendRedirect("/userid");
                } else
                    response.sendRedirect("/");
                break;
        }
    }

    private boolean checkUserId(HttpSession session)
    {
        return session.getAttribute("userId") != null;
    }

    private void openUserId(HttpSession session)
    {
        session.setAttribute("userId", userIdGenerator.getAndIncrement());
    }

    private void closeUserId(HttpSession session)
    {
        session.setAttribute("userId", null);
    }
}

