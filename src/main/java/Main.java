import accountService.AccountService;
import frontend.Frontend;
import dbService.DataService;
import game.GameMechanics;
import messageSystem.MessageSystem;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Main {
    public static void main(String[] args) throws Exception {
        MessageSystem messageSystem = new MessageSystem();
        AccountService accountService1 = new AccountService(new DataService(), messageSystem);
        AccountService accountService2 = new AccountService(new DataService(), messageSystem);
        Frontend frontend = new Frontend(messageSystem);
        GameMechanics gameMechanics = new GameMechanics(messageSystem);

        Thread frontendThread = new Thread(frontend);
        frontendThread.start();

        Thread accountServiceThread1 = new Thread(accountService1);
        accountServiceThread1.start();
        Thread accountServiceThread2 = new Thread(accountService2);
        accountServiceThread2.start();
        Thread gameThread = new  Thread(gameMechanics);
        gameThread.start();

        Server server = new Server(8080);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        server.setHandler(context);
        context.addServlet(new ServletHolder(frontend), "/*");

        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setResourceBase("static");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, context});
        server.setHandler(handlers);

        server.start();
        server.join();
    }
}
