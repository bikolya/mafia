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
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Frontend extends HttpServlet implements Runnable {

    private final static DateFormat FORMATTER = new SimpleDateFormat("HH:mm:ss");
    private final static String REFRESH_PERIOD = "1000";
    private final static int HANDLE_COUNT_LOG_INTERVAL = 5000;
    private AccountService accountService;

    private AtomicInteger handleCount;
    private Map<String, UserSession> sessions;
    private SessionIdGen sessionIdGen;

    public Frontend(AccountService accountServiceInit)
    {
        accountService = accountServiceInit;
        handleCount = new AtomicInteger();
        sessions = new HashMap<>();
        sessionIdGen = new SessionIdGen();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        handleCount.incrementAndGet();

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        setUniqueSessionId(request.getSession());

        String path = request.getPathInfo();
        switch (path) {
            case "/userid":
                handleGetUserId(request, response);
                break;
            case "/registrationError":
                handleGetUserId(request, response);
                break;
            case "index":
                response.getWriter().println(PageGenerator.getPage("..\\static\\index.html", null));
                break;
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        handleCount.incrementAndGet();

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        HttpSession currentSession = request.getSession();
        String path = request.getPathInfo();

        switch (path) {
            case "/login":
                handleLogin(request, response);
                break;
            case "/registerUser":
                handleRegistration(request, response);
                break;
        }
    }

    public static String getTime()
    {
        return FORMATTER.format(new Date());
    }

    private long getSessionUserId(HttpSession session)
    {
        Object rawId = session.getAttribute("userId");
        if( rawId != null)
            return (long) rawId;
        else
            return -1;
    }

    private String getSessionId(HttpSession session)
    {
        Object rawID = session.getAttribute("sessionId");
        if( rawID != null)
            return (String) rawID;
        else
            return "";
    }

    private void setSessionUserId(HttpSession session, long id)
    {
        session.setAttribute("userId", id);
    }

    private void setUniqueSessionId(HttpSession session)
    {
        if(session.getAttribute("sessionId") == null)
            session.setAttribute("sessionId", sessionIdGen.nextSessionId());
    }

    private void keepSession(UserSession userSession)
    {
        sessions.put(userSession.getSessionId(), userSession);
    }

    void handleLogin(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        String login = request.getParameter("login");
        String pass = request.getParameter("password");
        HttpSession currSession = request.getSession();

        if (accountService.checkPassword(login, pass)) {
            long userId = accountService.getUserId(login);
            setSessionUserId(currSession, userId);

            String sid = getSessionId(currSession);
            keepSession(new UserSession(sid, login, userId));

            response.sendRedirect("/userid");
        } else
            response.sendRedirect("/");
    }

    void handleRegistration(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        String login = request.getParameter("login");
        String pass = request.getParameter("password");

        if (accountService.registerUser(login, pass)) {
            response.sendRedirect("/");
        } else {
            response.sendRedirect("/registrationError");
        }
    }

    void handleGetUserId(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        Map<String, Object> pageVariables = new HashMap<>();

        Long userId = getSessionUserId(request.getSession());
        pageVariables.put("refreshPeriod", REFRESH_PERIOD);
        pageVariables.put("serverTime", getTime());
        pageVariables.put("userId", userId);
        response.getWriter().println(PageGenerator.getPage("userid.tml", pageVariables));
    }

    public void run() {
        TimerTask task = new TimerTask() {
            public void run() {
                System.out.print("Handle Count: ");
                System.out.println(handleCount.get());
            }
        };
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(task, 0, HANDLE_COUNT_LOG_INTERVAL);
    }

}

