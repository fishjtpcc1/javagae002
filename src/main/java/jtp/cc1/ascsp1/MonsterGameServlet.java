package jtp.cc1.ascsp1;

import java.util.logging.Logger;
import java.io.IOException;
import javax.servlet.http.*;

interface SceneI {
  //public String method(Game g);
  //public String draw(Game g, Game[] savedGames);
  //public SceneI whereToNext(Game g, String input);
  //public void doGet(HttpServletRequest req, HttpServletResponse resp);
  
}
    
/**
 * MonsterGameServlet
 * Based on Julian's fish and chicken restaurant
 * Receives i/o from tier1 html consolesim via ajax
 * uses session to store running game
 * user view state model:
 *  start: menu
 *  menu:
 *    1: new game
 *    2: filesave
 *    invalid: oops: menu
 *  game:
 *    paused: menu
 *    won: win
 *    lost: lost
 *    invalid: oops: game
 *  filesave:
 *    success: menu
 *    fail: opps: filesave
 *  win: menu
 *  lost: menu
 */
public class MonsterGameServlet extends HttpServlet {
  
  // statics are class variables and are not cloned in objects - eg only one logger is used by all instances
  private static final long serialVersionUID = 1L; // know: because HttpServlet is serializable
  private static final Logger log = Logger.getLogger(MonsterGameServlet.class.getName());
  private static int reuseCount = 0; // to prove server class reuse behaviour
  
  @Override // to help me prevent stupid polymorphic mistakes, the @Override annotation is used here to assert to compiler that this method is present in the superclass
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws java.io.IOException {
    // fresh start
    HttpSession oldSession = req.getSession();
    oldSession.invalidate();
    HttpSession mySession = req.getSession(true);
    reuseCount++;
    log.info("server reuseCount:"+reuseCount+" session id:"+mySession.getId()+" is new:"+mySession.isNew());
    // hand off to initial Scene
    MenuScene here = new MenuScene();
    here.doGet(req,resp);
  }

  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws java.io.IOException {
    // resume from where we left off
    HttpSession mySession = req.getSession(false);
    Scene here = (Scene)mySession.getAttribute("here"); // know: casting IS required tho implied in assignment
    here.doPost(req,resp);
  }

}
