package frontend;

import messageSystem.Address;
import messageSystem.Message;
import messageSystem.MessageSystem;
import messageSystem.messages.MsgGetUserId;
import templater.PageGenerator;
import messageSystem.Subscriber;

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

public class Frontend extends HttpServlet implements Runnable, Subscriber {

    private final static DateFormat FORMATTER = new SimpleDateFormat("HH:mm:ss");
    private final static String REFRESH_PERIOD = "1000";
    private final static int LOG_INTERVAL = 5000;

    private MessageSystem messageSystem;
    private Address address;
    private AtomicInteger handleCount;
    private Map<String, UserSession> sessions;

    public Frontend(MessageSystem messageSystemInit)
    {
        handleCount = new AtomicInteger();
        sessions = new HashMap<>();
        address = new Address();
        messageSystem = messageSystemInit;
        messageSystem.registerService(this);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        handleCount.incrementAndGet();
        createUniqueUserSession(request.getSession());

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        String path = request.getPathInfo();
        switch (path) {
            case "/userid":
                handleGetUserId(request, response);
                break;
            case "/registrationError":
                handleGetUserId(request, response);
                break;
            case "index":
            case "/":
            case "":

                response.getWriter().println(PageGenerator.getPage("..\\static\\index.html", null));
                break;
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        handleCount.incrementAndGet();
        createUniqueUserSession(request.getSession());

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
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


    private String getSessionId(HttpSession session)
    {
        Object rawID = session.getAttribute("sessionId");
        if( rawID != null)
            return (String) rawID;
        else
            return "";
    }

    private void createUniqueUserSession(HttpSession session)
    {
        if(sessions.containsKey(session.getId()))
            return;
        else {
            sessions.put(session.getId(), new UserSession(session.getId()));
        }
    }

    private boolean checkAuth(HttpSession session)
    {
        if(sessions.containsKey(session.getId())) {
            return sessions.get(session.getId()).getUserId() != null;
        }
        return false;
    }

    void handleLogin(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        String login = request.getParameter("login");
        String pass = request.getParameter("password");
        String sessionId = request.getSession().getId();

        logOut(sessionId);
        Address as_address = messageSystem.getAddressService().getAccountService();
        Message msg = new MsgGetUserId(address, as_address, login, sessionId);
        messageSystem.sendMessage(msg);

        response.sendRedirect("/userid");
    }

    void handleRegistration(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        String login = request.getParameter("login");
        String pass = request.getParameter("password");
    }


    void handleGetUserId(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        Map<String, Object> pageVariables = new HashMap<>();

        Long userId = sessions.get(request.getSession().getId()).getUserId();
        pageVariables.put("refreshPeriod", REFRESH_PERIOD);
        pageVariables.put("serverTime", getTime());
        if(userId == null) {
            pageVariables.put("userId", "Идет авторизация");
        } else if(userId == -1) {
            pageVariables.put("userId", "Неверное имя пользоватея или пароль");
        } else
            pageVariables.put("userId", userId);
        response.getWriter().println(PageGenerator.getPage("userid.tml", pageVariables));
    }

    private void logOut(String sid)
    {
        if(sessions.containsKey(sid))
            sessions.get(sid).setUserId(null);
    }

    public Address getAddress() {
        return address;
    }

    public MessageSystem getMessageSystem() {
        return messageSystem;
    }

    public void updateUserId(String sid, Long uid)
    {
        sessions.get(sid).setUserId(uid);
    }

    public void updateUserInfo(String sid, String name, Long uid)
    {
        sessions.get(sid).setUserId(uid);
        sessions.get(sid).setName(name);
    }


    public void run() {
        TimerTask task = new TimerTask() {
            public void run() {
                System.out.print("Handle Count: ");
                System.out.println(handleCount.get());
            }
        };
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(task, 0, LOG_INTERVAL);
        while(true) {
            try {
                Thread.currentThread().sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            messageSystem.execForSubscriber(this);
        }
    }

}

